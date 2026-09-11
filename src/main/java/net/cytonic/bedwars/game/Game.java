package net.cytonic.bedwars.game;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.damage.DamageType;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.Material;
import net.minestom.server.registry.RegistryKey;
import net.minestom.server.sound.SoundEvent;
import net.minestom.server.timer.Task;
import net.minestom.server.timer.TaskSchedule;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import net.cytonic.bedwars.config.BedwarsMap;
import net.cytonic.bedwars.config.BedwarsMapConfig;
import net.cytonic.bedwars.config.BedwarsMapConfig.GeneratorConfig;
import net.cytonic.bedwars.config.BedwarsMapConfig.TeamConfig;
import net.cytonic.bedwars.config.BedwarsMode;
import net.cytonic.bedwars.config.TeamColor;
import net.cytonic.bedwars.data.enums.AxeLevel;
import net.cytonic.bedwars.data.enums.GameState;
import net.cytonic.bedwars.data.enums.GeneratorType;
import net.cytonic.bedwars.data.enums.PickaxeLevel;
import net.cytonic.bedwars.player.BedwarsPlayer;
import net.cytonic.bedwars.server.BedwarsServer;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.logging.Logger;
import net.cytonic.cytosis.utils.Msg;

@Getter
@Setter
public class Game {

    public static final Pos SPAWN_POS = new Pos(0.5, 40, 0.5);
    private final UUID id = UUID.randomUUID();
    private final BedwarsMap map;
    private final BedwarsMapConfig config;
    private final BedwarsMode mode;
    private final BedwarsWorld world;
    private final Map<TeamColor, Team> teams = new HashMap<>();
    private final Set<UUID> spectators = new HashSet<>();
    @UnknownNullability
    private Task startTask;
    private int startTime = 0;
    private boolean started;
    private GameState state = GameState.WAITING;
    private Set<Generator> diamondGenerators = new HashSet<>();
    private Set<Generator> emeraldGenerators = new HashSet<>();

    public Game(BedwarsMap map, BedwarsMode mode) {
        this.map = map;
        this.config = map.getConfig();
        this.mode = mode;
        this.world = new BedwarsWorld(id, map);
        for (TeamConfig config : config.teams()) {
            teams.put(config.color(), new Team(
                config.color(),
                config.spawnPos(),
                config.itemShopPos(),
                config.teamShopPos(),
                config.teamChestPos(),
                config.generatorPos(),
                this.config.ironGeneratorTime(),
                this.config.ironGeneratorLimit(),
                this.config.goldGeneratorTime(),
                this.config.goldGeneratorLimit(),
                config.bedPos()
            ));
        }
    }

    @Nullable
    public Team getTeam(TeamColor teamColor) {
        return teams.get(teamColor);
    }

    public boolean isSpectator(BedwarsPlayer player) {
        return spectators.contains(player.getUuid());
    }

    public List<BedwarsPlayer> getPlayers() {
        List<BedwarsPlayer> players = new ArrayList<>();
        world.getPlayers().forEach(player -> players.add((BedwarsPlayer) player));
        return players;
    }

    public int getMaxPlayers() {
        return config.teamSize().getPlayersPerTeam() * config.teams().size();
    }

    public void waitStart() {
        this.state = GameState.STARTING;

        this.startTime = 5;
        this.startTask = MinecraftServer.getSchedulerManager().submitTask(() -> {
            if (startTime <= 0) {
                start();
                return TaskSchedule.stop();
            }

            Component component = switch (startTime) {
                case 5 -> Msg.yellow("The game starts in <dark_gray>%s</dark_gray> seconds!", startTime);
                case 4 -> Msg.yellow("The game starts in <gray>%s</gray> seconds!", startTime);
                case 3 -> Msg.yellow("The game starts in <green>%s</green> seconds!", startTime);
                case 2 -> Msg.yellow("The game starts in <yellow>%s</yellow> seconds!", startTime);
                case 1 -> Msg.yellow("The game starts in <red>%s</red> seconds!", startTime);
                default -> throw new IllegalStateException("Unexpected value: " + startTime);
            };

            getPlayers().forEach(player -> player.sendMessage(component));

            startTime--;
            return TaskSchedule.seconds(1);
        });
    }

    public void cancelStart() {
        this.state = GameState.WAITING;
        if (startTask != null) {
            this.startTask.cancel();
        }
        this.startTask = null;

        getPlayers().forEach(player -> player.sendMessage(
            Msg.redSplash("START CANCELED!", "There are not enough players to start the game!")));
    }

    public void start() {
        this.state = GameState.PLAY;
        this.started = true;

        if (startTask != null) {
            this.startTask.cancel();
        }
        this.startTask = null;

        world.removeSpawnPlatform();

        GeneratorConfig diamondGeneratorConfig = config.diamondGenerators();
        for (Pos pos : config.diamondGenerators().positions()) {
            Generator generator = new Generator(GeneratorType.DIAMOND, pos, diamondGeneratorConfig.time(),
                diamondGeneratorConfig.limit());
            generator.start(world);
            diamondGenerators.add(generator);
        }

        GeneratorConfig emeraldGeneratorConfig = config.emeraldGenerators();
        for (Pos pos : config.emeraldGenerators().positions()) {
            Generator generator = new Generator(GeneratorType.EMERALD, pos, emeraldGeneratorConfig.time(),
                emeraldGeneratorConfig.limit());
            generator.start(world);
            emeraldGenerators.add(generator);
        }

        //do teams last
        List<BedwarsPlayer> players = getPlayers();
        int numTeams = teams.size();
        int teamSize = players.size() / numTeams;
        int remainingPlayers = players.size() % numTeams;
        int playerIndex = 0;

        for (Team team : teams.values()) {
            int currentTeamSize = teamSize + (remainingPlayers > 0 ? 1 : 0);
            for (int i = 0; i < currentTeamSize; i++) {
                if (playerIndex < players.size()) {
                    team.addPlayer(players.get(playerIndex++));
                }
            }
            if (remainingPlayers > 0) {
                remainingPlayers--;
            }
            if (team.getPlayers().isEmpty()) {
                team.setBed(false);
                team.setAlive(false);
                world.breakBed(team);
                world.setBlock(team.getTeamChestPos(), Block.AIR);
                continue;
            }
            team.start(world, config);
        }
    }

    public void end() {
        this.state = GameState.ENDED;

        for (Team team : teams.values()) {
            team.end();
        }

        diamondGenerators.forEach(Generator::stop);
        emeraldGenerators.forEach(Generator::stop);

        BedwarsServer server = Cytosis.getServer();

        Team team = teams.values().stream().filter(Team::isAlive).findFirst().orElseThrow();

        for (BedwarsPlayer player : getPlayers()) {
            if (team.getPlayers().contains(player)) {
                player.showTitle(Title.title(Msg.gold("<b>VICTORY!"), Msg.mm(""),
                    Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(2), Duration.ofSeconds(1))));
            } else {
                player.showTitle(Title.title(Msg.red("<b>GAME OVER!"), Msg.mm(""),
                    Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(2), Duration.ofSeconds(1))));
            }

            player.sendMessage(Msg.mm(""));
            player.sendMessage(
                Msg.mm("<gold><b>GAME OVER!</b></gold> ")
                    .append(team.getName())
                    .append(Msg.grey(" has won the game!"))
            );
            player.sendMessage(Msg.mm(""));
        }

        MinecraftServer.getSchedulerManager().buildTask(() -> {
            getPlayers().forEach(BedwarsPlayer::sendToLobby);

            MinecraftServer.getSchedulerManager().buildTask(() -> server.games().remove(id))
                .delay(TaskSchedule.seconds(2)).schedule();
        }).delay(TaskSchedule.seconds(10)).schedule();

        Game newGame = new Game(map, mode);
        server.games().put(newGame.getId(), newGame);
    }

    public void kill(BedwarsPlayer player, @Nullable BedwarsPlayer killer, RegistryKey<DamageType> damageType) {
        player.clearLastDamage();
        Component component = player.getBedwarsFormattedName().appendSpace();
        if (damageType.equals(DamageType.PLAYER_ATTACK)) {
            if (killer == null) {
                kill(player, null, DamageType.OUT_OF_WORLD);
                return;
            }
            component = component.append(Msg.grey("was slain by ")).append(killer.getBedwarsFormattedName());
        } else if (damageType.equals(DamageType.FALL)) {
            component = component.append(Msg.grey("has fallen to their death"));
        } else if (damageType.equals(DamageType.ON_FIRE)) {
            component = component.append(Msg.grey("was roasted like a turkey"));
        } else if (damageType.equals(DamageType.LAVA)) {
            component = component.append(Msg.grey("discovered lava is hot"));
        } else if (damageType.equals(DamageType.OUT_OF_WORLD)) {
            if (killer != null) {
                component = component.append(Msg.grey("was knocked into the void by "))
                    .append(killer.getBedwarsFormattedName());
            } else {
                component = component.append(Msg.grey("fell into the abyss"));
            }
        } else if (damageType.equals(DamageType.FREEZE)) {
            component = component.append(Msg.grey("turned into an ice cube"));
        } else if (damageType.equals(DamageType.DROWN)) {
            component = component.append(Msg.grey("forgot how to swim"));
        } else if (damageType.equals(DamageType.EXPLOSION)) {
            component = component.append(Msg.grey("went <red><b>BOOM!"));
        } else if (damageType.equals(DamageType.ARROW) || damageType.equals(DamageType.TRIDENT)) {
            component = component.append(Msg.grey("was remotely terminated"));
        } else {
            Logger.error("unknown damage type: " + damageType.key());
            component = component.append(Msg.grey("died under mysterious circumstances"));
        }

        player.teleport(SPAWN_POS);
        Team team = player.getBedwarsTeam();
        if (!team.hasBed()) {
            player.showTitle(Title.title(Msg.red("<b>YOU DIED!"), Msg.yellow("You won't respawn"),
                Title.Times.times(Duration.ofMillis(100), Duration.ofMillis(2750), Duration.ofMillis(100))));
            component = component.append(Msg.red("<b> FINAL KILL!"));
            for (BedwarsPlayer bedwarsPlayer : getPlayers()) {
                bedwarsPlayer.sendMessage(component);
            }

            player.setGameMode(GameMode.SPECTATOR);
            player.applyInvisibility();

            player.setAlive(false);

            if (!team.hasBed() && team.getAlivePlayers().isEmpty()) {
                team.setAlive(false);
                for (BedwarsPlayer bedwarsPlayer : getPlayers()) {
                    bedwarsPlayer.sendMessage(Msg.mm(""));
                    bedwarsPlayer.sendMessage(
                        Msg.mm("<red><b>TEAM ELIMINATED!</b></red> ")
                            .append(team.getName())
                            .append(Msg.red(" has been eliminated"))
                    );
                    bedwarsPlayer.sendMessage(Msg.mm(""));
                }
            }

            if (teams.values().stream().filter(Team::isAlive).count() == 1) {
                end();
            }
            return;
        }

        for (BedwarsPlayer bedwarsPlayer : getPlayers()) {
            bedwarsPlayer.sendMessage(component);
        }

        if (killer != null) {
            player.swapItems(killer, Material.IRON_INGOT);
            player.swapItems(killer, Material.GOLD_INGOT);
            player.swapItems(killer, Material.DIAMOND);
            player.swapItems(killer, Material.EMERALD);
        }

        player.setRespawning(true);
        player.getInventory().clear();
        player.setGameMode(GameMode.SPECTATOR);
        player.applyInvisibility();
        player.setAxeLevel(AxeLevel.getByOrdinal(player.getAxeLevel().ordinal() - 1));
        player.setPickaxeLevel(PickaxeLevel.getByOrdinal(player.getPickaxeLevel().ordinal() - 1));
        player.setHealth(20);
        player.setFireTicks(0);
        player.setVelocity(Vec.ZERO);
        player.showTitle(Title.title(Msg.red("<b>You DIED!"), Msg.yellow("You will respawn soon"),
            Title.Times.times(Duration.ofMillis(100), Duration.ofMillis(2750), Duration.ofMillis(100))));

        player.setRespawnTime(5);
        MinecraftServer.getSchedulerManager().submitTask(() -> {
            int timeLeft = player.getRespawnTime();
            if (timeLeft == 0) {
                player.setRespawnTime(0);

                player.setGameMode(GameMode.SURVIVAL);
                player.setInvulnerable(true);
                MinecraftServer.getSchedulerManager()
                    .buildTask(() -> player.setInvulnerable(false)).delay(TaskSchedule.seconds(10)).schedule();
                player.setVelocity(Vec.ZERO);
                player.removeInvisibility();
                player.teleport(team.getSpawnPos());
                player.applyItems();
                player.setRespawning(false);

                return TaskSchedule.stop();
            }

            if (timeLeft <= 3) {
                player.showTitle(Title.title(Msg.yellow("Respawning in " + timeLeft), Msg.mm(""),
                    Title.Times.times(Duration.ofMillis(0), Duration.ofMillis(1150), Duration.ofSeconds(1))));
                player.playSound(Sound.sound(SoundEvent.UI_BUTTON_CLICK, Sound.Source.AMBIENT, .8f, 1f));
            }

            player.setRespawnTime(timeLeft - 1);
            return TaskSchedule.seconds(1);
        });
    }
}

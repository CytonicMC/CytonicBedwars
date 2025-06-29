package net.cytonic.cytonicbedwars.managers;

import lombok.Getter;
import lombok.Setter;
import net.cytonic.cytonicbedwars.Config;
import net.cytonic.cytonicbedwars.ItemAbilityDispatcher;
import net.cytonic.cytonicbedwars.data.enums.AxeLevel;
import net.cytonic.cytonicbedwars.data.enums.GameState;
import net.cytonic.cytonicbedwars.data.enums.PickaxeLevel;
import net.cytonic.cytonicbedwars.data.objects.PlayerList;
import net.cytonic.cytonicbedwars.data.objects.Scoreboard;
import net.cytonic.cytonicbedwars.data.objects.Stats;
import net.cytonic.cytonicbedwars.data.objects.Team;
import net.cytonic.cytonicbedwars.menu.itemShop.BlocksShopMenu;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytonicbedwars.runnables.GameRunnable;
import net.cytonic.cytonicbedwars.runnables.RespawnRunnable;
import net.cytonic.cytonicbedwars.runnables.WaitingRunnable;
import net.cytonic.cytonicbedwars.utils.Items;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.logging.Logger;
import net.cytonic.cytosis.npcs.NPC;
import net.cytonic.cytosis.player.CytosisPlayer;
import net.cytonic.cytosis.utils.Msg;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.util.Ticks;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EquipmentSlot;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.damage.DamageType;
import net.minestom.server.item.ItemStack;
import net.minestom.server.network.packet.server.play.TeamsPacket;
import net.minestom.server.registry.RegistryKey;
import net.minestom.server.scoreboard.TeamBuilder;
import net.minestom.server.sound.SoundEvent;
import net.minestom.server.timer.TaskSchedule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.*;

@Getter
@Setter
public class GameManager {
    private final List<Team> teams = new ArrayList<>();
    private final List<UUID> spectators = new ArrayList<>();
    private final List<NPC> npcList = new ArrayList<>();
    private final StatsManager statsManager;
    private final WorldManager worldManager;
    private final PlayerInventoryManager playerInventoryManager;
    private final GeneratorManager generatorManager;
    private final DatabaseManager databaseManager;
    public boolean STARTED = false;
    private GameState beforeFrozen;
    private GameState gameState;
    private WaitingRunnable waitingRunnable;
    private GameRunnable gameRunnable;
    private final ItemAbilityDispatcher itemAbilityDispatcher;

    public GameManager() {
        statsManager = new StatsManager();
        worldManager = new WorldManager();
        playerInventoryManager = new PlayerInventoryManager();
        generatorManager = new GeneratorManager();
        databaseManager = new DatabaseManager();
        databaseManager.createTables();
        itemAbilityDispatcher = new ItemAbilityDispatcher();
        Cytosis.getSideboardManager().setSideboardCreator(new Scoreboard());
        Cytosis.getSideboardManager().cancelUpdates();
        Cytosis.getSideboardManager().autoUpdateBoards(TaskSchedule.tick(1));
        Cytosis.getPlayerListManager().setCreator(new PlayerList());
    }

    public void setup() {
        worldManager.loadWorld();
        gameState = GameState.WAITING;
    }

    public void freeze() {
        beforeFrozen = gameState;
        gameState = GameState.FROZEN;
    }

    public void thaw() {
        gameState = beforeFrozen;
        beforeFrozen = null;
    }

    public void start() {
        worldManager.removeSpawnPlatform();
        STARTED = true;
        setGameState(GameState.PLAY);
        Cytosis.getOnlinePlayers().forEach(player -> statsManager.addPlayer(player.getUuid()));
        // split players into teams
        teams.addAll(splitPlayersIntoTeams(Cytosis.getOnlinePlayers().stream().toList()));

        teams.forEach(team -> team.getPlayers().forEach(player -> {
            team.getMcTeam().addMember(player.getUsername());
            player.teleport(team.getSpawnLocation());
            player.setGameMode(GameMode.SURVIVAL);
            player.getInventory().setItemStack(0, Items.DEFAULT_SWORD);
            setEquipment(player);
        }));
        generatorManager.registerTeamGenerators();
        generatorManager.registerDiamondGenerators();
        generatorManager.registerEmeraldGenerators();

        gameRunnable = new GameRunnable();

        for (Team team : teams) {
            NPC itemShop = NPC.ofHumanoid(team.getItemShopLocation(), Cytosis.getDefaultInstance())
                    .interactTrigger((npc, npcInteractType, player) -> new BlocksShopMenu().open(player))
                    .skin(Config.itemShopSkin)
                    .lines(Msg.gold("<b>ITEM SHOP"))
                    .invulnerable()
                    .build();
            npcList.add(itemShop);

            NPC teamShop = NPC.ofHumanoid(team.getTeamShopLocation(), Cytosis.getDefaultInstance())
                    .interactTrigger((npc, npcInteractType, player) -> player.sendMessage(Msg.red("Coming soon")))
                    .skin(Config.teamShopSkin)
                    .lines(Msg.red("Coming soon"))
                    .invulnerable()
                    .build();
            npcList.add(teamShop);
        }
    }

    private void setEquipment(BedwarsPlayer player) {
        player.getInventory().setEquipment(EquipmentSlot.CHESTPLATE, player.getHeldSlot(), (Items.get(String.format("%s_CHEST", getPlayerTeam(player).orElseThrow().getColor().toString().toUpperCase()))));
        player.getInventory().setEquipment(EquipmentSlot.LEGGINGS, player.getHeldSlot(), (Items.get(String.format(player.getArmorLevel().getLegsID(), getPlayerTeam(player).orElseThrow().getColor().toString().toUpperCase()))));
        player.getInventory().setEquipment(EquipmentSlot.BOOTS, player.getHeldSlot(), Items.get(String.format(player.getArmorLevel().getBootsID(), getPlayerTeam(player).orElseThrow().getColor().toString().toUpperCase())));
    }

    private List<Team> splitPlayersIntoTeams(List<CytosisPlayer> players) {
        int numTeams = Config.teams.size();
        int teamSize = players.size() / numTeams;
        int remainingPlayers = players.size() % numTeams;
        List<Team> result = new ArrayList<>();

        int playerIndex = 0;
        for (Team team : Config.teams.values()) {
            net.minestom.server.scoreboard.Team mcTeam = new TeamBuilder(team.getDisplayName(), MinecraftServer.getTeamManager())
                    .collisionRule(TeamsPacket.CollisionRule.PUSH_OTHER_TEAMS)
                    .teamColor(team.getColor())
                    .prefix(Msg.mm(team.getPrefix()))
                    .build();
            mcTeam.setSeeInvisiblePlayers(true);
            mcTeam.setAllowFriendlyFire(false);
            List<BedwarsPlayer> teamPlayers = new ArrayList<>();
            int currentTeamSize = teamSize + (remainingPlayers > 0 ? 1 : 0);
            for (int i = 0; i < currentTeamSize; i++) {
                if (playerIndex < players.size()) {
                    if (!(players.get(playerIndex) instanceof BedwarsPlayer player)) return List.of();
                    teamPlayers.add(player);
                    playerIndex++;
                }
            }
            team.setPlayers(teamPlayers);
            if (!team.getPlayers().isEmpty()) {
                team.setMcTeam(mcTeam);
                team.setBed(true);
                result.add(team);
            } else {
                worldManager.breakBed(team);
            }
            if (remainingPlayers > 0) {
                remainingPlayers--;
            }
        }

        return result;
    }

    public void end() {
        STARTED = false;
        setGameState(GameState.ENDED);
        gameRunnable.stop();
        gameRunnable = null;
        npcList.forEach((npc -> npc.getActions().clear()));
        generatorManager.removeGenerators();
        MinecraftServer.getSchedulerManager().buildTask(() -> {
            Cytosis.getOnlinePlayers().forEach(p -> {
                if (!(p instanceof BedwarsPlayer player)) return;
                player.sendToLobby();
            });
            cleanup();
        }).delay(Duration.ofSeconds(10)).schedule();
        Team winningTeam = teams.stream().filter(Team::isAlive).findFirst().orElseThrow();
        List<BedwarsPlayer> winners = winningTeam.getPlayers();
        Cytosis.getOnlinePlayers().forEach(player -> {
            if (winners.stream().map(BedwarsPlayer::getUuid).toList().contains(player.getUuid())) {
                player.showTitle(Title.title(Msg.gold("<b>VICTORY!"), Msg.mm(""), Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(2), Duration.ofSeconds(1))));
            } else {
                player.showTitle(Title.title(Msg.red("<b>GAME OVER!"), Msg.mm(""), Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(2), Duration.ofSeconds(1))));
            }
            player.sendMessage(Msg.mm(""));
            player.sendMessage(Msg.goldSplash("GAME OVER!", "<%s>%s <gray>has won the game!", winningTeam.getColor(), winningTeam.getDisplayName()));
            player.sendMessage(Msg.mm(""));
            Stats stats = statsManager.getStats(player.getUuid());
            if (stats == null) {
                player.sendMessage(Msg.whoops("You don't have any stats!"));
            } else {
                player.sendMessage(Msg.gold("<b>STATS:"));
                player.sendMessage(Msg.grey("   Kills: <white>%s", stats.getKills()));
                player.sendMessage(Msg.grey("   Final Kills: <white>%s", stats.getFinalKills()));
                player.sendMessage(Msg.grey("   Deaths: <white>%s", stats.getDeaths()));
                player.sendMessage(Msg.grey("   Beds broken: <white>%s", stats.getBedsBroken()));
                player.sendMessage(Msg.grey("   Damage Dealt: <white>%s", stats.getDamageDealt()));
                player.sendMessage(Msg.grey("   Damage Taken: <white>%s", stats.getDamageTaken()));
            }
        });
    }


    public void cleanup() {
        STARTED = false;
        setGameState(GameState.CLEANUP);
        worldManager.redoWorld();
        npcList.forEach((npc -> Cytosis.getNpcManager().removeNPC(npc)));
        for (Entity entity : Cytosis.getDefaultInstance().getEntities()) {
            if (entity instanceof BedwarsPlayer) continue;
            entity.remove();
        }
        teams.clear();
        databaseManager.saveStats();
        statsManager.getStats().clear();
        setup();
    }

    /**
     * Gets the players team if they are on one
     */
    public Optional<Team> getPlayerTeam(BedwarsPlayer player) {
        return getPlayerTeam(player.getUuid());
    }

    public Optional<Team> getTeamFromColor(NamedTextColor color) {
        for (Team team : teams) {
            if (team.getColor().equals(color)) {
                return Optional.of(team);
            }
        }
        return Optional.empty();
    }

    /**
     * Gets the players team if they are on one
     */
    public Optional<Team> getPlayerTeam(UUID player) {
        for (Team team : teams) {
            for (BedwarsPlayer teamPlayer : team.getPlayers()) {
                if (teamPlayer.getUuid().equals(player))
                    return Optional.of(team);
            }
        }
        return Optional.empty();
    }

    /**
     * Gets the {@link BedwarsPlayer} if they are on a team
     */
    public Optional<BedwarsPlayer> getPlayer(UUID uuid) {
        for (Team team : teams) {
            for (BedwarsPlayer teamPlayer : team.getPlayers()) {
                if (teamPlayer.getUuid().equals(uuid))
                    return Optional.of(teamPlayer);
            }
        }
        return Optional.empty();
    }

    public void breakBed(BedwarsPlayer player, Team team) {
        Component message = Msg.whiteSplash("<newline>BED DESTRUCTION!", "<%s>%s Bed<reset><gray> was destroyed by <%s>%s<reset><gray>!<newline>", team.getColor().toString(), team.getName(), getPlayerTeam(player).orElseThrow().getColor(), player.getUsername());
        Cytosis.getOnlinePlayers().forEach(p -> {
            player.playSound(Sound.sound(SoundEvent.ENTITY_GENERIC_EXPLODE, Sound.Source.PLAYER, 1f, 100f));
            p.sendMessage(message);
        });
        for (BedwarsPlayer p : team.getPlayers()) {
            Title title = Title.title(Msg.red("<b>BED DESTROYED!"), Msg.white("You will no longer respawn!"),
                    Title.Times.times(Ticks.duration(10L), Ticks.duration(100L), Ticks.duration(20L)));
            p.showTitle(title);
        }
        // todo: display animations, messages, etc.
        statsManager.getStats(player.getUuid()).addBedBreak();
        team.setBed(false);
    }

    public void kill(@NotNull BedwarsPlayer dead, @Nullable BedwarsPlayer killer, @NotNull RegistryKey<DamageType> damageType) {
        Team deadTeam = getPlayerTeam(dead).orElseThrow();
        statsManager.getStats(dead.getUuid()).addDeath();

        //degrade tools
        if (AxeLevel.getOrdered(dead.getAxeLevel(), -1) != null) {
            dead.setAxeLevel(AxeLevel.getOrdered(dead.getAxeLevel(), -1));
        }

        if (PickaxeLevel.getOrdered(dead.getPickaxeLevel(), -1) != null) {
            dead.setPickaxeLevel(PickaxeLevel.getOrdered(dead.getPickaxeLevel(), -1));
        }

        boolean finalKill = false;
        Component message = Msg.mm("%s%s<reset> ", deadTeam.getPrefix(), dead.getUsername());
        if (!deadTeam.hasBed()) {
            finalKill = true;
        }
        if (damageType.equals(DamageType.PLAYER_ATTACK)) {
            if (killer == null) {
                kill(dead, null, DamageType.OUT_OF_WORLD);
            } else {
                if (!finalKill) {
                    statsManager.getStats(killer.getUuid()).addKill();
                }
                message = message.append(Msg.grey("was slain by %s%s", getPlayerTeam(killer).orElseThrow().getPrefix(), killer.getUsername()));
                killer.getInventory().addItemStack(Items.get("IRON").withAmount(playerInventoryManager.itemCount(dead, "IRON")));
                killer.getInventory().addItemStack(Items.get("GOLD").withAmount(playerInventoryManager.itemCount(dead, "GOLD")));
                killer.getInventory().addItemStack(Items.get("DIAMOND").withAmount(playerInventoryManager.itemCount(dead, "DIAMOND")));
                killer.getInventory().addItemStack(Items.get("EMERALD").withAmount(playerInventoryManager.itemCount(dead, "EMERALD")));
            }
        } else if (damageType.equals(DamageType.FALL)) {
            message = message.append(Msg.grey("has fallen to their death"));
        } else if (damageType.equals(DamageType.ON_FIRE)) {
            message = message.append(Msg.grey("was roasted like a turkey"));
        } else if (damageType.equals(DamageType.LAVA)) {
            message = message.append(Msg.grey("discovered lava is hot"));
        } else if (damageType.equals(DamageType.OUT_OF_WORLD)) {
            message = message.append(Msg.grey("fell into the abyss"));
        } else if (damageType.equals(DamageType.FREEZE)) {
            message = message.append(Msg.grey("turned into an ice cube"));
        } else if (damageType.equals(DamageType.DROWN)) {
            message = message.append(Msg.grey("forgot how to swim"));
        } else if (damageType.equals(DamageType.EXPLOSION)) {
            message = message.append(Msg.grey("went <red><b>BOOM!"));
        } else if (damageType.equals(DamageType.ARROW) || damageType.equals(DamageType.TRIDENT)) {
            message = message.append(Msg.grey("was remotely terminated"));
        } else {
            Logger.error("unknown damage type: " + damageType.key());
            message = message.append(Msg.grey("died under mysterious circumstances"));
        }

        dead.teleport(Config.spawnPlatformCenter);
        if (finalKill) {
            dead.showTitle(Title.title(Msg.red("<b>YOU DIED!"), Msg.yellow("You won't respawn"), Title.Times.times(Duration.ofMillis(100), Duration.ofMillis(2750), Duration.ofMillis(100))));
            message = message.append(Msg.red("<b> FINAL KILL!"));
            Component finalMessage = message;
            dead.setGameMode(GameMode.SPECTATOR);
            Cytosis.getOnlinePlayers().forEach((player -> player.sendMessage(finalMessage)));
            dead.setAlive(false);
            if (deadTeam.getAlivePlayers().isEmpty()) {
                deadTeam.setAlive(false);
                Cytosis.getOnlinePlayers().forEach(player -> {
                    player.sendMessage(Msg.mm(""));
                    player.sendMessage(Msg.whiteSplash("TEAM ELIMINATED!", "<%s>%s <red>has been eliminated!", deadTeam.getColor(), deadTeam.getDisplayName()));
                    player.sendMessage(Msg.mm(""));
                });
            }
            if (killer != null) {
                statsManager.getStats(killer.getUuid()).addFinalKill();
            }
            if (teams.stream().filter(Team::isAlive).count() == 1) {
                end();
            }
            return;
        }
        Component finalMessage = message;
        // respawn logic...
        dead.setRespawning(true);
        Cytosis.getOnlinePlayers().forEach((player -> player.sendMessage(finalMessage)));
        dead.showTitle(Title.title(Msg.red("<b>You DIED!"), Msg.yellow("You will respawn soon"), Title.Times.times(Duration.ofMillis(100), Duration.ofMillis(2750), Duration.ofMillis(100))));
        dead.setGameMode(GameMode.SPECTATOR);
        dead.getInventory().setItemStack(0, ItemStack.AIR);
        dead.getInventory().setItemStack(6, ItemStack.AIR);
        dead.getInventory().setItemStack(8, ItemStack.AIR);
        dead.setHealth(20);
        dead.setFireTicks(0); // reset fire
        new RespawnRunnable(6, dead);
    }

    public void respawnPlayer(BedwarsPlayer dead) {
        dead.setGameMode(GameMode.SURVIVAL);
        dead.setInvulnerable(true);// make them invincible for 5 sec
        MinecraftServer.getSchedulerManager().buildTask(() -> dead.setInvulnerable(false)).delay(Duration.ofSeconds(5)).schedule();
        dead.setVelocity(Vec.ZERO);
        dead.teleport(getPlayerTeam(dead).orElseThrow().getSpawnLocation());

        dead.getInventory().setItemStack(0, Items.DEFAULT_SWORD);

        // set armor
        setEquipment(dead);

        // set tools
        //todo: check for enchants / team upgrades
        dead.getInventory().addItemStack(Items.get(dead.getAxeLevel().getItemID()));
        dead.getInventory().addItemStack(Items.get(dead.getPickaxeLevel().getItemID()));
        if (dead.hasShears()) {
            dead.getInventory().addItemStack(Items.SHEARS);
        }
        dead.setRespawning(false);
        dead.setAlive(true);
    }


    public GameState nextGameState() {
        gameState = gameState.getNext();
        switch (Objects.requireNonNull(gameState)) {
            case DIAMOND_2, DIAMOND_3 -> {
                generatorManager.increaseDiamondsSpawnSpeed(gameState == GameState.DIAMOND_2 ? 20 : 12);
                Cytosis.getOnlinePlayers().forEach(player -> player.sendMessage(Msg.aquaSplash("GENERATORS", "Diamonds generators have upgraded to Tier %s!", gameState == GameState.DIAMOND_2 ? "II" : "III")));
            }
            case EMERALD_2, EMERALD_3 -> {
                generatorManager.increaseEmeraldsSpawnSpeed(gameState == GameState.EMERALD_2 ? 400 : 240);
                Cytosis.getOnlinePlayers().forEach(player -> player.sendMessage(Msg.greenSplash("GENERATORS", "Emerald generators have upgraded to Tier %s!", gameState == GameState.DIAMOND_2 ? "II" : "III")));

            }
            case BED_DESTRUCTION -> {
                teams.stream().filter(Team::isAlive).forEach(team -> {
                    team.setBed(false);
                    worldManager.breakBed(team);
                });
                Cytosis.getOnlinePlayers().forEach(player -> {
                    player.sendMessage(Msg.redSplash("BED DESTROY", "All beds have been destroyed!"));
                    player.sendMessage(Msg.yellow("You can no longer respawn!"));
                });
            }
            case SUDDEN_DEATH -> {
                //todo
                Cytosis.getOnlinePlayers().forEach(player -> player.sendMessage(Msg.red("Wow ender dragons crazy so cool")));
            }
            case ENDED -> end();
        }
        return gameState;
    }
}

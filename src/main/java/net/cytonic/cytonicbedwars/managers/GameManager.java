package net.cytonic.cytonicbedwars.managers;

import lombok.Getter;
import lombok.Setter;
import net.cytonic.cytonicbedwars.CytonicBedwarsSettings;
import net.cytonic.cytonicbedwars.data.enums.ArmorLevel;
import net.cytonic.cytonicbedwars.data.enums.AxeLevel;
import net.cytonic.cytonicbedwars.data.enums.GameState;
import net.cytonic.cytonicbedwars.data.enums.PickaxeLevel;
import net.cytonic.cytonicbedwars.data.objects.Scoreboard;
import net.cytonic.cytonicbedwars.data.objects.Team;
import net.cytonic.cytonicbedwars.runnables.RespawnRunnable;
import net.cytonic.cytonicbedwars.utils.Items;
import net.cytonic.cytonicbedwars.utils.Utils;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.logging.Logger;
import net.cytonic.cytosis.npcs.NPC;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.damage.DamageType;
import net.minestom.server.network.packet.server.play.TeamsPacket;
import net.minestom.server.registry.DynamicRegistry;
import net.minestom.server.sound.SoundEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.*;

import static net.cytonic.utils.MiniMessageTemplate.MM;

public class GameManager {

    //todo: Move these to an enum or something
    private static final String NPC_SKIN_VALUE = "ewogICJ0aW1lc3RhbXAiIDogMTY2MjQ2NzA5Njc1NywKICAicHJvZmlsZUlkIiA6ICJmNTgyNGRmNGIwMTU0MDA4OGRhMzUyYTQxODU1MDQ0NCIsCiAgInByb2ZpbGVOYW1lIiA6ICJGb3hHYW1lcjUzOTIiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTI5YWI4YmRiMjI4ZTQ3MjZiNzQ1MzZhY2EwNTlhMTZjYWNjNzBjNThlNGEyZGFhMTQzZDIxOWYzNzRhOGI0YSIKICAgIH0KICB9Cn0=";
    private static final String NPC_SKIN_SIGNATURE = "yKToy4cFqIM5A3JWqXkeOaWjOd8MjAm+ECb1ga8tlBZzGvsLVHVaatVcvdYvLqxeUcWrrGLE8F4cqdVl+XyqUyILjmqw8elFwKCS28fIryuvAMaH28SRjDUsAVtTyt6xHSh2yx30IvuN+OmatcTTYQO0AmTzG6VlrOd4COzfrcOEteZb6yqh43hfxpawlavdQw7LQ3ecFXe5JPINNzXPEbbcAYeV9Gh9j6ej9n2P8KsMcTfEjb+UWh82fLegPt3pBQWdXUJVyh1SualBqVaX8hqk38KbfwtC7A9FWvycY7OacjXOyTeWEqZnGUNwc1YgXnS5EidzG/xXNJz2pgzOBlwtAv80jAXnVQcyJkuhSijSehKvMuEEd1gcY7O3itAdSb0636zjAhcKsqskzUhaRNK8QNpbIowBDA2t4EXaFkGSpBSRrOVthox6MhxDLC+ZKADNuiGEtVgpw6vY5gfulovaIX7wOWGLrxGrA6JsA9Fq7XuwHq8d8k8kI6XNRSxdKoKgHhdmlzjPax/GelXt6a9VkRoagtY8EmnliWyOorIMazjdDKq+QmddHH3sDAeahLtXoCf64Jus8bqqyNL4B0E3HwlKjQ2XZw1v/G9c70uJscaoUgpATwvHg2+dH0uxs2MSkN/GZM3GWbmyerFz+AapDjsZhBhylJ570jcbuS4=";
    private static final String QUESTIONMARK_NPC_SKIN_VALUE = "ewogICJ0aW1lc3RhbXAiIDogMTYyMjc1MDc3ODY4NywKICAicHJvZmlsZUlkIiA6ICJmOTJiZDcxNGE0NmE0MmQ0OTQ1ZjdiMGNmNzExMDllNiIsCiAgInByb2ZpbGVOYW1lIiA6ICJzaGlrb2xpbmsiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWNmYWU4NjM4ZmNkYzRlNDk2ODdmMjFmZGEwN2EzOThmYjc0Mjk4NjBhZDU5NzAwNjAxYTA0OGI5N2ExODFmNCIKICAgIH0KICB9Cn0=";
    private static final String QUESTIONMARK_NPC_SKIN_SIGNATURE = "nnMt+n50dRoN5svTwxTsyzl/alouayqbAeSXFoJ2XagYevGsh+iCmIDIysYgJ5OXSl5QpDBeF/KAinf54fgk0Eat2KAStMQwcPMkHnaQTmey6GMGOIc2ZtGLwlybaEMtX/4wbANFswqECA7+CjxWvgOTm9//wX2fyblV961oAkaCCpw+OrojlQ6/KfahG2XgyEhdmJhnSw44XLD/LCXyCd97HSpTfmLgZYqXZtBnafMSwSbFi1bcbdwqRBcIm6VnfW9lgD0KdgCN/G+26kMtS55J50BprO8vZpv0lYxmJ9UzVnkQKDCYKzWAiEBmTMon+Sxet4K4uOs8OxlkCwEMHWCzR5MjCWESKvDWlZHkBnejY8RsIYbDn1CxWQ6zjlgJ1DlPZtYHlodlTVoKMuaeit21f2bvEdN6GvemzDtPwZI25hLt6/X/3axY3tQUeKoqu5rsa0L2PsjjNswEUJcO4AQynQlwAw7BVv5GBgITrN/zW3S6UmA+btft7zTB6/y8JcEJhx0m96AR5bwo67liBw+7EvyxRvQFKocQIL83Pvo7fsB+tmIpjwFSaCQEiCh47AjAsvnjqbzwbfZ7ttSFJUk4piVgRXyyEORBAIjNjEH+2J0TILy97k12/HJKkG9C/KXdOYv88j6paQKSWE6OuCxolcTwJWpcTtzh7xD2Lxs=";

    @Getter
    private final List<Team> teamlist = new ArrayList<>();
    @Getter
    private final Map<Team, Boolean> beds = new HashMap<>();
    private final Map<Team, net.minestom.server.scoreboard.Team> mcTeams = new HashMap<>();
    private final List<NPC> npcs = new ArrayList<>();
    @Getter
    private final StatsManager statsManager;
    @Getter
    private final WorldManager worldManager;
    @Getter
    private final MenuManager menuManager;
    @Getter
    private final PlayerInventoryManager playerInventoryManager;
    @Getter
    private final GeneratorManager generatorManager;
    public List<UUID> spectators = new ArrayList<>();
    public Map<UUID, ArmorLevel> armorLevels = new HashMap<>();
    public Map<UUID, AxeLevel> axes = new HashMap<>();
    public Map<UUID, PickaxeLevel> pickaxes = new HashMap<>();
    public Map<UUID, Boolean> shears = new HashMap<>();
    public boolean STARTED = false;
    @Getter
    private Map<Team, List<UUID>> playerTeams = new HashMap<>();
    @Getter
    private List<UUID> alivePlayers = new ArrayList<>();
    private GameState beforeFrozen;
    @Getter
    @Setter
    private GameState gameState;

    public GameManager() {
        statsManager = new StatsManager();
        worldManager = new WorldManager();
        menuManager = new MenuManager();
        playerInventoryManager = new PlayerInventoryManager();
        generatorManager = new GeneratorManager();
    }

    public void setup() {
        worldManager.loadWorld();
        worldManager.createSpawnPlatform();
        Cytosis.getSideboardManager().setSideboardCreator(new Scoreboard());
        gameState = GameState.WAITING;
        CytonicBedwarsSettings.teams.forEach((_, t) -> {
            teamlist.add(t);
            beds.put(t, true);
        });
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
        generatorManager.registerTeamGenerators();
        generatorManager.registerDiamondGenerators();
        generatorManager.registerEmeraldGenerators();
        Cytosis.getOnlinePlayers().forEach(player -> statsManager.propagatePlayer(player.getUuid()));
        // split players into teams
        List<UUID> players = new ArrayList<>();
        alivePlayers = players;
        Cytosis.getOnlinePlayers().forEach(player -> players.add(player.getUuid()));
        playerTeams = splitPlayersIntoTeams(players);

        playerTeams.keySet().forEach(team -> {
            List<UUID> uuids = playerTeams.get(team);
            uuids.forEach(uuid -> {
                Player p = Cytosis.getPlayer(uuid).orElseThrow();
                mcTeams.get(team).addMember(p.getUsername());
                p.teleport(team.spawnLocation());
                p.setGameMode(GameMode.SURVIVAL);
                setArmor(uuid, ArmorLevel.NONE);
                setAxe(uuid, AxeLevel.NONE);
                setPickaxe(uuid, PickaxeLevel.NONE);
                shears.put(uuid, false);
            });
        });
        for (Team t : teamlist) {
            NPC teamShop = NPC.ofHumanoid(t.teamShopLocation(), Cytosis.getDefaultInstance())
                    .interactTrigger((_, _, player) -> player.sendMessage(MM."<RED>Coming soon"))
                    .skin(QUESTIONMARK_NPC_SKIN_VALUE, QUESTIONMARK_NPC_SKIN_SIGNATURE)
                    .lines(MM."<RED>Coming soon")
                    .build();
            npcs.add(teamShop);

            NPC itemShop = NPC.ofHumanoid(t.itemShopLocation(), Cytosis.getDefaultInstance())
                    .interactTrigger((_, _, player) -> player.openInventory(menuManager.getBlocksShop()))
                    .skin(NPC_SKIN_VALUE, NPC_SKIN_SIGNATURE)
                    .lines(MM."<GOLD><bold>ITEM SHOP")
                    .build();
            npcs.add(itemShop);
        }
    }

    private Map<Team, List<UUID>> splitPlayersIntoTeams(List<UUID> players) {
        int numTeams = teamlist.size();
        int teamSize = players.size() / numTeams;
        int remainingPlayers = players.size() % numTeams;
        Map<Team, List<UUID>> result = new HashMap<>();

        int playerIndex = 0;
        for (Team team : teamlist) {
            net.minestom.server.scoreboard.Team t = MinecraftServer.getTeamManager().createTeam(team.displayName());
            t.setCollisionRule(TeamsPacket.CollisionRule.PUSH_OTHER_TEAMS);
            t.setSeeInvisiblePlayers(true);
            t.setAllowFriendlyFire(false);
            t.setTeamColor(Utils.getColor(team.color()));
            t.setPrefix(MM."\{team.prefix()}");
            mcTeams.put(team, t);
            List<UUID> teamPlayers = new ArrayList<>();
            int currentTeamSize = teamSize + (remainingPlayers > 0 ? 1 : 0);
            for (int i = 0; i < currentTeamSize; i++) {
                if (playerIndex < players.size()) {
                    teamPlayers.add(players.get(playerIndex));
                    playerIndex++;
                }
            }
            result.put(team, teamPlayers);
            if (remainingPlayers > 0) {
                remainingPlayers--;
            }
        }

        return result;
    }

    public void cleanup() {
        STARTED = false;
        setGameState(GameState.CLEANUP);
        npcs.forEach((npc -> Cytosis.getNpcManager().removeNPC(npc)));
    }

    public Optional<Team> getPlayerTeam(UUID uuid) {
        for (Team t : teamlist) {
            if (playerTeams.get(t).contains(uuid))
                return Optional.of(t);
        }
        return Optional.empty();
    }

    public void breakBed(Player player, Team t) {
        player.playSound(Sound.sound(SoundEvent.ENTITY_GENERIC_EXPLODE, Sound.Source.PLAYER, 1f, 100f));
        Cytosis.getOnlinePlayers().forEach(p -> p.sendMessage(MM."\{getPlayerTeam(player.getUuid()).orElseThrow().color()} \{player.getUsername()} <red>destroyed \{t.color()}\{t.displayName()}<white>'s bed!"));
        // todo: display animations, messages, etc.
        beds.put(t, false);
    }

    public void kill(Player dead, @Nullable Player killer, DynamicRegistry.Key<DamageType> cause) {
        alivePlayers.remove(dead.getUuid());
        statsManager.addPlayerDeath(dead.getUuid());

        //degrade tools
        setAxe(dead.getUuid(), AxeLevel.getOrdered(getAxe(dead.getUuid()), -1));
        Logger.debug(STR."pickaxe level maybe? = \{getPickaxe(dead.getUuid())}");
        Logger.debug(STR." pickaxe level = \{PickaxeLevel.getOrdered(getPickaxe(dead.getUuid()), -1)}");
        setPickaxe(dead.getUuid(), PickaxeLevel.getOrdered(getPickaxe(dead.getUuid()), -1));

        boolean finalkill = false;
        Component message = MM."\{getPlayerTeam(dead.getUuid()).orElseThrow().prefix()} \{dead.getUsername()}<RESET>";
        if (!beds.get(getPlayerTeam(dead.getUuid()).orElseThrow())) {
            finalkill = true;
        }
        switch (cause.toString().toUpperCase()) {
            case "KILL" -> {
                if (killer == null) {
                    kill(dead, null, DamageType.OUT_OF_WORLD);
                } else {
                    statsManager.addPlayerKill(killer.getUuid());
                    message = message.append(MM."<GRAY> was slain by \{getPlayerTeam(killer.getUuid()).orElseThrow().prefix()}\{killer.getUsername()}");
                }
            }
            case "FALL" -> message = message.append(MM."<GRAY> has fallen to their death");
            case "FIRE", "FIRE_TICK" -> message = message.append(MM."<GRAY> was roasted like a turkey");
            case "LAVA" -> message = message.append(MM."<GRAY> discovered lava is hot");
            case "VOID" -> message = message.append(MM."<GRAY> fell into the abyss");
            case "FREEZE" -> message = message.append(MM."<GRAY> turned into an ice cube");
            case "DROWNING" -> message = message.append(MM."<GRAY> forgot how to swim");
            case "ENTITY_EXPLOSION", "BLOCK_EXPLOSION" -> message = message.append(MM."<GRAY> went <RED><BOLD>BOOM!");
            case "PROJECTILE" -> message = message.append(MM."<GRAY> was remotely terminated");
            default -> {
                Logger.info(String.valueOf(cause));
                message = message.append(MM."<GRAY> died under mysterious circumstances");
            }
        }

        dead.teleport(new Pos(0, 100, 0));
        if (finalkill) {
            dead.showTitle(Title.title(MM."<BOLD><RED>YOU DIED!", MM."<YELLOW>You won't respawn", Title.Times.times(Duration.ofMillis(100), Duration.ofMillis(2750), Duration.ofMillis(100))));
            message = message.append(MM."<BOLD><RED> FINAL KILL!");
            Component finalMessage = message;
            dead.setGameMode(GameMode.SPECTATOR);
            Cytosis.getOnlinePlayers().forEach((player -> player.sendMessage(finalMessage)));
            return;
        }
        Component finalMessage = message;
        // respawn logic...
        Cytosis.getOnlinePlayers().forEach((player -> player.sendMessage(finalMessage)));
        dead.showTitle(Title.title(MM."<BOLD><RED>You DIED!", MM."<YELLOW>You will respawn soon", Title.Times.times(Duration.ofMillis(100), Duration.ofMillis(750), Duration.ofMillis(100))));
        dead.setGameMode(GameMode.SPECTATOR);
        dead.getInventory().clear();
        dead.setHealth(20);
        dead.setFireTicks(0); // reset fire
        new RespawnRunnable(6, dead);
    }

    public void respawnPlayer(Player dead) {
        dead.setGameMode(GameMode.SURVIVAL);
        dead.setInvulnerable(true);// make them invincible for 5 sec
        MinecraftServer.getSchedulerManager().buildTask(() -> dead.setInvulnerable(false)).delay(Duration.ofSeconds(5)).schedule();
        dead.teleport(getPlayerTeam(dead.getUuid()).orElseThrow().spawnLocation());

        // set armor
        Logger.debug(String.format(armorLevels.get(dead.getUuid()).getLegsID(), getPlayerTeam(dead.getUuid()).orElseThrow().color().replaceAll("<", "").replaceAll(">", "").toUpperCase()));
        dead.getInventory().setLeggings(Items.get(String.format(armorLevels.get(dead.getUuid()).getLegsID(), getPlayerTeam(dead.getUuid()).orElseThrow().color().replaceAll("<", "").replaceAll(">", "").toUpperCase())));
        dead.getInventory().setBoots(Items.get(String.format(armorLevels.get(dead.getUuid()).getBootsID(), getPlayerTeam(dead.getUuid()).orElseThrow().color().replaceAll("<", "").replaceAll(">", "").toUpperCase())));
        dead.getInventory().setChestplate(Items.get(String.format("%s_CHEST", getPlayerTeam(dead.getUuid()).orElseThrow().color().replaceAll("<", "").replaceAll(">", "").toUpperCase())));

        // set tools
        //todo: check for enchants / team upgrades
        Logger.debug(getPickaxe(dead.getUuid()).getItemID());
        dead.getInventory().addItemStack(Items.get(getPickaxe(dead.getUuid()).getItemID()));
        dead.getInventory().addItemStack(Items.get(getAxe(dead.getUuid()).getItemID()));
        if (shears.containsKey(dead.getUuid())) {
            dead.getInventory().addItemStack(Items.SHEARS);
        }

        alivePlayers.add(dead.getUuid());
    }

    public void setArmor(UUID uuid, @NotNull ArmorLevel level) {
        armorLevels.put(uuid, level);
    }

    public void setAxe(UUID uuid, @NotNull AxeLevel level) {
        axes.put(uuid, level);
    }

    public void setPickaxe(UUID uuid, @NotNull PickaxeLevel level) {
        pickaxes.put(uuid, level);
    }

    public PickaxeLevel getPickaxe(UUID uuid) {
        return pickaxes.getOrDefault(uuid, PickaxeLevel.NONE);
    }

    public AxeLevel getAxe(UUID uuid) {
        return axes.getOrDefault(uuid, AxeLevel.NONE);
    }
}

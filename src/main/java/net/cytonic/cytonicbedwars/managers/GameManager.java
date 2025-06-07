package net.cytonic.cytonicbedwars.managers;

import lombok.Getter;
import lombok.Setter;
import net.cytonic.cytonicbedwars.CytonicBedwarsSettings;
import net.cytonic.cytonicbedwars.ItemAbilityDispatcher;
import net.cytonic.cytonicbedwars.data.enums.ArmorLevel;
import net.cytonic.cytonicbedwars.data.enums.AxeLevel;
import net.cytonic.cytonicbedwars.data.enums.GameState;
import net.cytonic.cytonicbedwars.data.enums.PickaxeLevel;
import net.cytonic.cytonicbedwars.data.objects.PlayerList;
import net.cytonic.cytonicbedwars.data.objects.Scoreboard;
import net.cytonic.cytonicbedwars.data.objects.Team;
import net.cytonic.cytonicbedwars.menu.itemShop.BlocksShopMenu;
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
import net.kyori.adventure.title.Title;
import net.kyori.adventure.util.Ticks;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EquipmentSlot;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.damage.DamageType;
import net.minestom.server.network.packet.server.play.TeamsPacket;
import net.minestom.server.registry.DynamicRegistry;
import net.minestom.server.scoreboard.TeamBuilder;
import net.minestom.server.sound.SoundEvent;
import net.minestom.server.timer.TaskSchedule;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.*;

@Getter
@Setter
public class GameManager {

    //todo: Move these to an enum or something
    private static final String NPC_SKIN_VALUE = "ewogICJ0aW1lc3RhbXAiIDogMTY2MjQ2NzA5Njc1NywKICAicHJvZmlsZUlkIiA6ICJmNTgyNGRmNGIwMTU0MDA4OGRhMzUyYTQxODU1MDQ0NCIsCiAgInByb2ZpbGVOYW1lIiA6ICJGb3hHYW1lcjUzOTIiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTI5YWI4YmRiMjI4ZTQ3MjZiNzQ1MzZhY2EwNTlhMTZjYWNjNzBjNThlNGEyZGFhMTQzZDIxOWYzNzRhOGI0YSIKICAgIH0KICB9Cn0=";
    private static final String NPC_SKIN_SIGNATURE = "yKToy4cFqIM5A3JWqXkeOaWjOd8MjAm+ECb1ga8tlBZzGvsLVHVaatVcvdYvLqxeUcWrrGLE8F4cqdVl+XyqUyILjmqw8elFwKCS28fIryuvAMaH28SRjDUsAVtTyt6xHSh2yx30IvuN+OmatcTTYQO0AmTzG6VlrOd4COzfrcOEteZb6yqh43hfxpawlavdQw7LQ3ecFXe5JPINNzXPEbbcAYeV9Gh9j6ej9n2P8KsMcTfEjb+UWh82fLegPt3pBQWdXUJVyh1SualBqVaX8hqk38KbfwtC7A9FWvycY7OacjXOyTeWEqZnGUNwc1YgXnS5EidzG/xXNJz2pgzOBlwtAv80jAXnVQcyJkuhSijSehKvMuEEd1gcY7O3itAdSb0636zjAhcKsqskzUhaRNK8QNpbIowBDA2t4EXaFkGSpBSRrOVthox6MhxDLC+ZKADNuiGEtVgpw6vY5gfulovaIX7wOWGLrxGrA6JsA9Fq7XuwHq8d8k8kI6XNRSxdKoKgHhdmlzjPax/GelXt6a9VkRoagtY8EmnliWyOorIMazjdDKq+QmddHH3sDAeahLtXoCf64Jus8bqqyNL4B0E3HwlKjQ2XZw1v/G9c70uJscaoUgpATwvHg2+dH0uxs2MSkN/GZM3GWbmyerFz+AapDjsZhBhylJ570jcbuS4=";
    private static final String QUESTIONMARK_NPC_SKIN_VALUE = "ewogICJ0aW1lc3RhbXAiIDogMTYyMjc1MDc3ODY4NywKICAicHJvZmlsZUlkIiA6ICJmOTJiZDcxNGE0NmE0MmQ0OTQ1ZjdiMGNmNzExMDllNiIsCiAgInByb2ZpbGVOYW1lIiA6ICJzaGlrb2xpbmsiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWNmYWU4NjM4ZmNkYzRlNDk2ODdmMjFmZGEwN2EzOThmYjc0Mjk4NjBhZDU5NzAwNjAxYTA0OGI5N2ExODFmNCIKICAgIH0KICB9Cn0=";
    private static final String QUESTIONMARK_NPC_SKIN_SIGNATURE = "nnMt+n50dRoN5svTwxTsyzl/alouayqbAeSXFoJ2XagYevGsh+iCmIDIysYgJ5OXSl5QpDBeF/KAinf54fgk0Eat2KAStMQwcPMkHnaQTmey6GMGOIc2ZtGLwlybaEMtX/4wbANFswqECA7+CjxWvgOTm9//wX2fyblV961oAkaCCpw+OrojlQ6/KfahG2XgyEhdmJhnSw44XLD/LCXyCd97HSpTfmLgZYqXZtBnafMSwSbFi1bcbdwqRBcIm6VnfW9lgD0KdgCN/G+26kMtS55J50BprO8vZpv0lYxmJ9UzVnkQKDCYKzWAiEBmTMon+Sxet4K4uOs8OxlkCwEMHWCzR5MjCWESKvDWlZHkBnejY8RsIYbDn1CxWQ6zjlgJ1DlPZtYHlodlTVoKMuaeit21f2bvEdN6GvemzDtPwZI25hLt6/X/3axY3tQUeKoqu5rsa0L2PsjjNswEUJcO4AQynQlwAw7BVv5GBgITrN/zW3S6UmA+btft7zTB6/y8JcEJhx0m96AR5bwo67liBw+7EvyxRvQFKocQIL83Pvo7fsB+tmIpjwFSaCQEiCh47AjAsvnjqbzwbfZ7ttSFJUk4piVgRXyyEORBAIjNjEH+2J0TILy97k12/HJKkG9C/KXdOYv88j6paQKSWE6OuCxolcTwJWpcTtzh7xD2Lxs=";

    private final List<Team> teamlist = new ArrayList<>();
    private final Map<Team, Boolean> beds = new HashMap<>();
    private final Map<Team, net.minestom.server.scoreboard.Team> mcTeams = new HashMap<>();
    private final List<NPC> npcs = new ArrayList<>();
    private final StatsManager statsManager;
    private final WorldManager worldManager;
    private final PlayerInventoryManager playerInventoryManager;
    private final GeneratorManager generatorManager;
    public List<UUID> spectators = new ArrayList<>();
    public Map<UUID, ArmorLevel> armorLevels = new HashMap<>();
    public Map<UUID, AxeLevel> axes = new HashMap<>();
    public Map<UUID, PickaxeLevel> pickaxes = new HashMap<>();
    public Map<UUID, Boolean> shears = new HashMap<>();
    public boolean STARTED = false;
    private Map<Team, List<UUID>> playerTeams = new HashMap<>();
    private List<UUID> alivePlayers = new ArrayList<>();
    private GameState beforeFrozen;
    private GameState gameState;
    private WaitingRunnable waitingRunnable;
    private final ItemAbilityDispatcher itemAbilityDispatcher;

    public GameManager() {
        statsManager = new StatsManager();
        worldManager = new WorldManager();
        playerInventoryManager = new PlayerInventoryManager();
        generatorManager = new GeneratorManager();
        itemAbilityDispatcher = new ItemAbilityDispatcher();
        Cytosis.getSideboardManager().setSideboardCreator(new Scoreboard());
        Cytosis.getSideboardManager().cancelUpdates();
        Cytosis.getSideboardManager().autoUpdateBoards(TaskSchedule.tick(1));
        Cytosis.getPlayerListManager().setCreator(new PlayerList());
    }

    public void setup() {
        worldManager.loadWorld();
        gameState = GameState.WAITING;
        CytonicBedwarsSettings.teams.forEach((s, t) -> {
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
            if (playerTeams.get(team) != null && playerTeams.get(team).isEmpty()) {
                beds.put(team, false);
                worldManager.breakBed(Cytosis.getDefaultInstance().getBlock(team.bedLocation()), new BlockVec(team.bedLocation()));
                return;
            }
            List<UUID> uuids = playerTeams.get(team);
            uuids.forEach(uuid -> {
                Player p = Cytosis.getPlayer(uuid).orElseThrow();
                mcTeams.get(team).addMember(p.getUsername());
                p.teleport(team.spawnLocation());
                p.setGameMode(GameMode.SURVIVAL);
                setArmor(uuid, ArmorLevel.NONE);
                p.getInventory().setItemStack(0, Items.DEFAULT_SWORD);
                setEquipment(p);

                setAxe(uuid, AxeLevel.NONE);
                setPickaxe(uuid, PickaxeLevel.NONE);
                shears.put(uuid, false);
            });
        });
        for (Team t : teamlist) {
            NPC teamShop = NPC.ofHumanoid(t.teamShopLocation(), Cytosis.getDefaultInstance())
                    .interactTrigger((npc, npcInteractType, player) -> player.sendMessage(Msg.mm("<RED>Coming soon")))
                    .skin(QUESTIONMARK_NPC_SKIN_VALUE, QUESTIONMARK_NPC_SKIN_SIGNATURE)
                    .lines(Msg.mm("<RED>Coming soon"))
                    .invulnerable()
                    .build();
            npcs.add(teamShop);

            NPC itemShop = NPC.ofHumanoid(t.itemShopLocation(), Cytosis.getDefaultInstance())
                    .interactTrigger((npc, npcInteractType, player) -> new BlocksShopMenu().open(player))
                    .skin(NPC_SKIN_VALUE, NPC_SKIN_SIGNATURE)
                    .lines(Msg.mm("<GOLD><bold>ITEM SHOP"))
                    .invulnerable()
                    .build();
            npcs.add(itemShop);
        }
    }

    private void setEquipment(Player player) {
        player.getInventory().setEquipment(EquipmentSlot.BOOTS, player.getHeldSlot(), Items.get(String.format(armorLevels.get(player.getUuid()).getBootsID(), getPlayerTeam(player.getUuid()).orElseThrow().color().toString().toUpperCase())));
        player.getInventory().setEquipment(EquipmentSlot.LEGGINGS, player.getHeldSlot(), (Items.get(String.format(armorLevels.get(player.getUuid()).getLegsID(), getPlayerTeam(player.getUuid()).orElseThrow().color().toString().toUpperCase()))));
        player.getInventory().setEquipment(EquipmentSlot.CHESTPLATE, player.getHeldSlot(), (Items.get(String.format("%s_CHEST", getPlayerTeam(player.getUuid()).orElseThrow().color().toString().toUpperCase()))));
    }

    private Map<Team, List<UUID>> splitPlayersIntoTeams(List<UUID> players) {
        int numTeams = teamlist.size();
        int teamSize = players.size() / numTeams;
        int remainingPlayers = players.size() % numTeams;
        Map<Team, List<UUID>> result = new HashMap<>();

        int playerIndex = 0;
        for (Team team : teamlist) {
            net.minestom.server.scoreboard.Team t = new TeamBuilder(team.displayName(), MinecraftServer.getTeamManager())
                    .collisionRule(TeamsPacket.CollisionRule.PUSH_OTHER_TEAMS)
                    .teamColor(team.color())
                    .prefix(Msg.mm(team.prefix()))
                    .build();
            t.setSeeInvisiblePlayers(true);
            t.setAllowFriendlyFire(false);
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

    public void end() {
        STARTED = false;
        setGameState(GameState.ENDED);
        npcs.forEach((npc -> npc.getActions().clear()));
        generatorManager.removeGenerators();
        MinecraftServer.getSchedulerManager().buildTask(() -> {
            for (CytosisPlayer player : Cytosis.getOnlinePlayers()) {
                sendToLobby(player);
            }
            cleanup();
        }).delay(Duration.ofSeconds(10)).schedule();
    }


    public void cleanup() {
        STARTED = false;
        setGameState(GameState.CLEANUP);
        worldManager.redoWorld();
        npcs.forEach((npc -> Cytosis.getNpcManager().removeNPC(npc)));
        for (Entity entity : Cytosis.getDefaultInstance().getEntities()) {
            if (entity instanceof CytosisPlayer) continue;
            entity.remove();
        }
        axes.clear();
        pickaxes.clear();
        teamlist.clear();
        beds.clear();
        mcTeams.clear();
        alivePlayers.clear();
        shears.clear();
        spectators.clear();
        setup();
    }

    public Optional<Team> getPlayerTeam(UUID uuid) {
        for (Team t : teamlist) {
            if (playerTeams.get(t).contains(uuid))
                return Optional.of(t);
        }
        return Optional.empty();
    }

    public void breakBed(Player player, Team t) {
        Component message = Msg.mm("<newline><white><b>BED DESTRUCTION ></b></white> <%s>%s Bed<reset><gray> was destroyed by <%s>%s<reset><gray>!<newline>", t.color().toString(), t.displayName().split(" ")[0], getPlayerTeam(player.getUuid()).orElseThrow().color(), player.getUsername());
        Cytosis.getOnlinePlayers().forEach(p -> {
            player.playSound(Sound.sound(SoundEvent.ENTITY_GENERIC_EXPLODE, Sound.Source.PLAYER, 1f, 100f));
            p.sendMessage(message);
        });
        for (UUID uuid : playerTeams.get(t)) {
            CytosisPlayer p = Cytosis.getPlayer(uuid).orElseThrow();
            Title title = Title.title(Msg.mm("<b><red>BED DESTROYED!"), Msg.mm("<white>You will no longer respawn!"),
                    Title.Times.times(Ticks.duration(10L), Ticks.duration(100L), Ticks.duration(20L)));
            p.showTitle(title);
        }
        // todo: display animations, messages, etc.
        beds.put(t, false);
    }

    public void kill(@NotNull CytosisPlayer dead, @Nullable CytosisPlayer killer, @NotNull DynamicRegistry.Key<DamageType> damageType) {
        alivePlayers.remove(dead.getUuid());
        statsManager.addPlayerDeath(dead.getUuid());

        //degrade tools
        if (!(AxeLevel.getOrdered(getAxe(dead.getUuid()), -1) == null)) {
            setAxe(dead.getUuid(), AxeLevel.getOrdered(getAxe(dead.getUuid()), -1));
        }

        if (PickaxeLevel.getOrdered(getPickaxe(dead.getUuid()), -1) != null) {
            setPickaxe(dead.getUuid(), PickaxeLevel.getOrdered(getPickaxe(dead.getUuid()), -1));
            Logger.debug(PickaxeLevel.getOrdered(getPickaxe(dead.getUuid()), -1).getItemID());
        }

        boolean finalkill = false;
        Component message = Msg.mm(getPlayerTeam(dead.getUuid()).orElseThrow().prefix() + dead.getUsername() + "<RESET>");
        if (!beds.get(getPlayerTeam(dead.getUuid()).orElseThrow())) {
            finalkill = true;
        }
        if (damageType.equals(DamageType.PLAYER_ATTACK)) {
            if (killer == null) {
                kill(dead, null, DamageType.OUT_OF_WORLD);
            } else {
                statsManager.addPlayerKill(killer.getUuid());
                message = message.append(Msg.mm("<GRAY> was slain by " + getPlayerTeam(killer.getUuid()).orElseThrow().prefix() + killer.getUsername()));
            }
        } else if (damageType.equals(DamageType.FALL)) {
            message = message.append(Msg.mm("<GRAY> has fallen to their death"));
        } else if (damageType.equals(DamageType.ON_FIRE)) {
            message = message.append(Msg.mm("<GRAY> was roasted like a turkey"));
        } else if (damageType.equals(DamageType.LAVA)) {
            message = message.append(Msg.mm("<GRAY> discovered lava is hot"));
        } else if (damageType.equals(DamageType.OUT_OF_WORLD)) {
            message = message.append(Msg.mm("<GRAY> fell into the abyss"));
        } else if (damageType.equals(DamageType.FREEZE)) {
            message = message.append(Msg.mm("<GRAY> turned into an ice cube"));
        } else if (damageType.equals(DamageType.DROWN)) {
            message = message.append(Msg.mm("<GRAY> forgot how to swim"));
        } else if (damageType.equals(DamageType.EXPLOSION)) {
            message = message.append(Msg.mm("<GRAY> went <RED><BOLD>BOOM!"));
        } else if (damageType.equals(DamageType.ARROW) || damageType.equals(DamageType.TRIDENT)) {
            message = message.append(Msg.mm("<GRAY> was remotely terminated"));
        } else {
            Logger.error("unknown damage type: " + damageType.key());
            message = message.append(Msg.mm("<GRAY> died under mysterious circumstances"));
        }

        dead.teleport(new Pos(0, 30, 0));
        if (finalkill) {
            dead.showTitle(Title.title(Msg.mm("<BOLD><RED>YOU DIED!"), Msg.mm("<YELLOW>You won't respawn"), Title.Times.times(Duration.ofMillis(100), Duration.ofMillis(2750), Duration.ofMillis(100))));
            message = message.append(Msg.mm("<BOLD><RED> FINAL KILL!"));
            Component finalMessage = message;
            dead.setGameMode(GameMode.SPECTATOR);
            Cytosis.getOnlinePlayers().forEach((player -> player.sendMessage(finalMessage)));
            getPlayerTeams().get(getPlayerTeam(dead.getUuid()).orElseThrow()).remove(dead.getUuid());
            return;
        }
        Component finalMessage = message;
        // respawn logic...
        Cytosis.getOnlinePlayers().forEach((player -> player.sendMessage(finalMessage)));
        dead.showTitle(Title.title(Msg.mm("<BOLD><RED>You DIED!"), Msg.mm("<YELLOW>You will respawn soon"), Title.Times.times(Duration.ofMillis(100), Duration.ofMillis(2750), Duration.ofMillis(100))));
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

        dead.getInventory().setItemStack(0, Items.DEFAULT_SWORD);

        // set armor
        setEquipment(dead);

        // set tools
        //todo: check for enchants / team upgrades
        dead.getInventory().addItemStack(Items.get(getPickaxe(dead.getUuid()).getItemID()));
        dead.getInventory().addItemStack(Items.get(getAxe(dead.getUuid()).getItemID()));
        if (shears.get(dead.getUuid())) {
            dead.getInventory().addItemStack(Items.SHEARS);
        }

        alivePlayers.add(dead.getUuid());
    }

    public void sendToLobby(CytosisPlayer player) {
        Cytosis.getNatsManager().sendPlayerToGenericServer(player.getUuid(), "cytonic", "lobby", "The Lobby");
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

package dev.foxikle.webnetbedwars.managers;

import com.infernalsuite.aswm.api.SlimePlugin;
import com.infernalsuite.aswm.api.exceptions.UnknownWorldException;
import com.infernalsuite.aswm.api.exceptions.WorldLockedException;
import dev.foxikle.customnpcs.actions.Action;
import dev.foxikle.customnpcs.actions.ActionType;
import dev.foxikle.customnpcs.actions.conditions.Conditional;
import dev.foxikle.customnpcs.api.NPC;
import dev.foxikle.customnpcs.data.Settings;
import dev.foxikle.webnetbedwars.WebNetBedWars;
import dev.foxikle.webnetbedwars.data.enums.ArmorLevel;
import dev.foxikle.webnetbedwars.data.enums.AxeLevel;
import dev.foxikle.webnetbedwars.data.enums.GameState;
import dev.foxikle.webnetbedwars.data.enums.PickaxeLevel;
import dev.foxikle.webnetbedwars.data.objects.Team;
import dev.foxikle.webnetbedwars.runnables.RespawnRunnable;
import dev.foxikle.webnetbedwars.utils.Items;
import net.minecraft.world.level.biome.TheEndBiomeSource;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public class GameManager {
    private final WebNetBedWars plugin;
    private final List<Team> teamlist = new ArrayList<>();

    private Map<Team, List<UUID>> playerTeams = new HashMap<>();
    private List<UUID> alivePlayers = new ArrayList<>();
    private final Map<Team, Boolean> beds = new HashMap<>();
    private final Map<Team, org.bukkit.scoreboard.Team> mcTeams = new HashMap<>();
    private final List<NPC> npcs = new ArrayList<>();
    public List<UUID> spectators = new ArrayList<>();

    public Map<UUID, ArmorLevel> armorLevels = new HashMap<>();
    public Map<UUID, AxeLevel> axes = new HashMap<>();
    public Map<UUID, PickaxeLevel> pickaxes = new HashMap<>();
    public Map<UUID, Boolean> shears = new HashMap<>();


    private GameState beforeFrozen;
    private GameState gameState;

    private static final String NPC_SKIN_VALUE = "ewogICJ0aW1lc3RhbXAiIDogMTY2MjQ2NzA5Njc1NywKICAicHJvZmlsZUlkIiA6ICJmNTgyNGRmNGIwMTU0MDA4OGRhMzUyYTQxODU1MDQ0NCIsCiAgInByb2ZpbGVOYW1lIiA6ICJGb3hHYW1lcjUzOTIiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTI5YWI4YmRiMjI4ZTQ3MjZiNzQ1MzZhY2EwNTlhMTZjYWNjNzBjNThlNGEyZGFhMTQzZDIxOWYzNzRhOGI0YSIKICAgIH0KICB9Cn0=";
    private static final String NPC_SKIN_SIGNATURE = "yKToy4cFqIM5A3JWqXkeOaWjOd8MjAm+ECb1ga8tlBZzGvsLVHVaatVcvdYvLqxeUcWrrGLE8F4cqdVl+XyqUyILjmqw8elFwKCS28fIryuvAMaH28SRjDUsAVtTyt6xHSh2yx30IvuN+OmatcTTYQO0AmTzG6VlrOd4COzfrcOEteZb6yqh43hfxpawlavdQw7LQ3ecFXe5JPINNzXPEbbcAYeV9Gh9j6ej9n2P8KsMcTfEjb+UWh82fLegPt3pBQWdXUJVyh1SualBqVaX8hqk38KbfwtC7A9FWvycY7OacjXOyTeWEqZnGUNwc1YgXnS5EidzG/xXNJz2pgzOBlwtAv80jAXnVQcyJkuhSijSehKvMuEEd1gcY7O3itAdSb0636zjAhcKsqskzUhaRNK8QNpbIowBDA2t4EXaFkGSpBSRrOVthox6MhxDLC+ZKADNuiGEtVgpw6vY5gfulovaIX7wOWGLrxGrA6JsA9Fq7XuwHq8d8k8kI6XNRSxdKoKgHhdmlzjPax/GelXt6a9VkRoagtY8EmnliWyOorIMazjdDKq+QmddHH3sDAeahLtXoCf64Jus8bqqyNL4B0E3HwlKjQ2XZw1v/G9c70uJscaoUgpATwvHg2+dH0uxs2MSkN/GZM3GWbmyerFz+AapDjsZhBhylJ570jcbuS4=";

    private final StatsManager statsManager;
    private final ScoreboardManager scoreboardManager;
    private final WorldManager worldManager;
    private final MenuManager menuManager;
    private final PlayerInventoryManager playerInventoryManager;
    private final GeneratorManager generatorManager;

    public boolean STARTED = false;

    public GameManager(WebNetBedWars plugin) {
        this.plugin = plugin;
        statsManager = new StatsManager();
        scoreboardManager = new ScoreboardManager(this, plugin);
        worldManager = new WorldManager(plugin, this);
        menuManager = new MenuManager(plugin);
        playerInventoryManager = new PlayerInventoryManager(plugin);
        generatorManager = new GeneratorManager(plugin, this);
    }

    public void setup() {
        SlimePlugin plugin = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");
        worldManager.fetchWorld().whenComplete((slimeWorld, throwable) -> { // depends on waiting for the world to load...
            Bukkit.getScheduler().runTask(this.plugin, () -> {
                try {
                    plugin.loadWorld(slimeWorld, true);
                } catch (UnknownWorldException | WorldLockedException | IOException e) {
                    this.plugin.getLogger().log(Level.SEVERE, "An error occoured whilst loading map!", e);
                }
            });
            Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
                worldManager.createSpawnPlatform();
                scoreboardManager.init();
                gameState = GameState.WAITING;
                FileConfiguration config = this.plugin.getConfig();
                ConfigurationSection section = config.getConfigurationSection("Teams");
                for (String key : section.getKeys(false)) {
                    ConfigurationSection teamSection = section.getConfigurationSection(key);
                    try {
                        Bukkit.getScoreboardManager().getMainScoreboard().getTeam(key).unregister();
                    } catch (Exception ignored) {
                    }
                    Team t = new Team(
                            key,
                            teamSection.getString("TAB_PREFIX"),
                            ChatColor.valueOf(teamSection.getString("TEAM_COLOR")),
                            Material.valueOf(teamSection.getString("BED_ITEM")),
                            this.plugin.getLocation(teamSection.getCurrentPath() + ".SPAWN_LOCATION"),
                            this.plugin.getLocation(teamSection.getCurrentPath() + ".GENERATOR_LOCATION"),
                            this.plugin.getLocation(teamSection.getCurrentPath() + ".ITEM_SHOP_LOCATION"),
                            this.plugin.getLocation(teamSection.getCurrentPath() + ".TEAM_SHOP_LOCATION"),
                            this.plugin.getLocation(teamSection.getCurrentPath() + ".TEAM_CHEST_LOCATION"),
                            Material.valueOf(teamSection.getString("WOOL_ITEM")),
                            Material.valueOf(teamSection.getString("GLASS_ITEM")),
                            Material.valueOf(teamSection.getString("TERRACOTTA_ITEM"))
                    );
                    teamlist.add(t);
                    beds.put(t, true);
                }
            }, 1);
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
        Bukkit.getOnlinePlayers().forEach(player -> statsManager.propagatePlayer(player.getUniqueId()));
        // split players into teams
        List<UUID> players = new ArrayList<>();
        alivePlayers = players;
        Bukkit.getOnlinePlayers().forEach(player -> players.add(player.getUniqueId()));
        playerTeams = splitPlayersIntoTeams(players);

        playerTeams.keySet().forEach(team -> {
            List<UUID> uuids = playerTeams.get(team);
            uuids.forEach(uuid -> {
                Player p = Bukkit.getPlayer(uuid);
                if (p != null) {
                    p.getInventory().clear();
                    p.setGameMode(GameMode.SURVIVAL);
                    mcTeams.get(team).addEntry(p.getName());
                    p.teleport(team.spawnLocation());
                    setArmor(uuid, ArmorLevel.NONE);
                    setAxe(uuid, AxeLevel.NONE);
                    setPickaxe(uuid, PickaxeLevel.NONE);
                    shears.put(uuid, false);

                    p.setInvulnerable(false);
                    p.getInventory().addItem(Items.DEFAULT_SWORD);
                    playerInventoryManager.setArmor(p, team, ArmorLevel.NONE);
                }
            });
        });
        for (Team t : teamlist) {
            NPC teamShop = new NPC(t.teamShopLocation().getWorld());
            Settings teamSettings = new Settings(true, false, false, t.teamShopLocation().getYaw(), NPC_SKIN_VALUE, NPC_SKIN_SIGNATURE, "Shop Keeper", "<red><bold>COMING SOON</bold></aqua>");

            teamShop.setPostion(t.teamShopLocation())
                    .setActions(
                            List.of(
                                    new Action(
                                            ActionType.RUN_COMMAND,
                                            new ArrayList<>(List.of("openteamshop")),
                                            0,
                                            Conditional.SelectionMode.ONE,
                                            List.of()
                                    )
                            )
                    )
                    .create();
            teamShop.setSettings(teamSettings);
            teamShop.reloadSettings();
            npcs.add(teamShop);

            NPC itemShop = new NPC(t.itemShopLocation().getWorld());
            Settings itemSettings = new Settings(true, false, false, t.itemShopLocation().getYaw(), NPC_SKIN_VALUE, NPC_SKIN_SIGNATURE, "Shop Keeper", "<GOLD><bold>ITEM SHOP</bold></gold>");
            itemShop
                    .setPostion(t.itemShopLocation())
                    .setActions(
                            List.of(
                                    new Action(
                                            ActionType.RUN_COMMAND,
                                            new ArrayList<>(List.of("openitemshop")),
                                            0,
                                            Conditional.SelectionMode.ONE,
                                            List.of()
                                    )
                            )
                    )
                    .create();
            itemShop.setSettings(itemSettings);
            itemShop.reloadSettings();
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
            org.bukkit.scoreboard.Team t = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(team.displayName());
            t.setOption(org.bukkit.scoreboard.Team.Option.COLLISION_RULE, org.bukkit.scoreboard.Team.OptionStatus.FOR_OTHER_TEAMS);
            t.setCanSeeFriendlyInvisibles(true);
            t.setAllowFriendlyFire(false);
            t.setColor(team.color());
            t.setPrefix(ChatColor.translateAlternateColorCodes('&', team.prefix()));
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

    public GameState getGameState() {
        return gameState;
    }

    private void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public StatsManager getStatsManager() {
        return statsManager;
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public PlayerInventoryManager getPlayerInventoryManager() {
        return playerInventoryManager;
    }

    public List<Team> getTeamlist() {
        return teamlist;
    }

    public Map<Team, Boolean> getBeds() {
        return beds;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public Map<Team, List<UUID>> getPlayerTeams() {
        return playerTeams;
    }

    public void cleanup() {
        STARTED = false;
        setGameState(GameState.CLEANUP);
        mcTeams.values().forEach(org.bukkit.scoreboard.Team::unregister);
        npcs.forEach(NPC::remove);
    }

    @Nullable
    public Team getPlayerTeam(UUID uuid) {
        for (Team t : teamlist) {
            if (playerTeams.get(t).contains(uuid))
                return t;
        }
        return null;
    }

    public void breakBed(Player player, Team t) {
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1000f, 1f);
        Bukkit.broadcastMessage(getPlayerTeam(player.getUniqueId()).color() + player.getName() + ChatColor.YELLOW + " destroyed " + t.color() + t.displayName() + ChatColor.YELLOW + "'s bed!");
        // todo: display animations, messages, etc.
        beds.put(t, false);
    }

    public void kill(Player dead, @Nullable Player killer, EntityDamageEvent.DamageCause cause) {
        alivePlayers.remove(dead.getUniqueId());
        statsManager.addPlayerDeath(dead.getUniqueId());

        //degrade tools
        setAxe(dead.getUniqueId(), AxeLevel.getOrdered(getAxe(dead.getUniqueId()), -1));
        setPickaxe(dead.getUniqueId(), PickaxeLevel.getOrdered(getPickaxe(dead.getUniqueId()), -1));

        boolean finalkill = false;
        String message = ChatColor.translateAlternateColorCodes('&', getPlayerTeam(dead.getUniqueId()).prefix()) + dead.getName() + ChatColor.RESET;
        if (!beds.get(getPlayerTeam(dead.getUniqueId()))) {
            finalkill = true;
        }
        switch (cause) {
            case KILL -> {
                if (killer == null) {
                    kill(dead, null, EntityDamageEvent.DamageCause.CUSTOM);
                } else {
                    statsManager.addPlayerKill(killer.getUniqueId());
                    message += ChatColor.GRAY + " was slain by " + ChatColor.translateAlternateColorCodes('&', getPlayerTeam(killer.getUniqueId()).prefix()) + killer.getName();
                }
            }
            case FALL -> message += ChatColor.GRAY + " has fallen to their death";
            case FIRE, FIRE_TICK -> message += ChatColor.GRAY + " was roasted like a turkey";
            case LAVA -> message += ChatColor.GRAY + " discovered lava is hot";
            case VOID -> message += ChatColor.GRAY + " fell into the abyss";
            case FREEZE -> message += ChatColor.GRAY + " turned into an ice cube";
            case DROWNING -> message += ChatColor.GRAY + " forgot how to swim";
            case ENTITY_EXPLOSION, BLOCK_EXPLOSION -> message += ChatColor.GRAY + " went " + ChatColor.RED + "" + ChatColor.BOLD + "BOOM!";
            case PROJECTILE -> message += ChatColor.GRAY + " was remotley terminated";
            default -> {
                plugin.getLogger().info(String.valueOf(cause));
                message += ChatColor.GRAY + " died under mysterious circumstances";
            }
        }

        dead.teleport(plugin.getLocation("SpawnPlatformCenter"));

        if (finalkill) {
            dead.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "YOU DIED!", ChatColor.YELLOW + "You won't repsawn", 5, 55, 5);
            message += ChatColor.DARK_RED + "" + ChatColor.BOLD + " FINAL KILL!";
            dead.setGameMode(GameMode.SPECTATOR);
            Bukkit.broadcastMessage(message);
            for (Team t : plugin.getGameManager().getTeamlist()) {
                Location TeamChest = t.chestLocation();
        }
            return;
        }
        // respawn logic...
        Bukkit.broadcastMessage(message);
        dead.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "You DIED!", ChatColor.YELLOW + "You will repsawn soon", 5, 15, 5);
        dead.setGameMode(GameMode.SPECTATOR);
        dead.getInventory().clear();
        dead.setHealth(20.0);
        dead.setFireTicks(0); // reset fire

        new RespawnRunnable(plugin, 6, dead).runTaskTimer(plugin, 0, 20);
    }

    public void respawnPlayer(Player dead) {
        dead.setGameMode(GameMode.SURVIVAL);
        dead.setNoDamageTicks(100);// make them invincible for 5 sec
        dead.teleport(getPlayerTeam(dead.getUniqueId()).spawnLocation());

        // set armor
        dead.getInventory().setLeggings(Items.get(String.format(armorLevels.get(dead.getUniqueId()).getLegsID(), getPlayerTeam(dead.getUniqueId()).color().name())));
        dead.getInventory().setBoots(Items.get(String.format(armorLevels.get(dead.getUniqueId()).getBootsID(), getPlayerTeam(dead.getUniqueId()).color().name())));
        dead.getInventory().setChestplate(Items.get(String.format("%s_CHEST", getPlayerTeam(dead.getUniqueId()).color().name())));

        // set tools
        //todo: check for enchants / team upgrades
        dead.getInventory().addItem(Items.get(getPickaxe(dead.getUniqueId()).getItemID()), Items.get(getAxe(dead.getUniqueId()).getItemID()));
        if (shears.get(dead.getUniqueId())) {
            dead.getInventory().addItem(Items.SHEARS);
        }

        alivePlayers.add(dead.getUniqueId());
    }

    public List<UUID> getAlivePlayers() {
        return alivePlayers;
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
        if (pickaxes.getOrDefault(uuid, PickaxeLevel.NONE) == null) {
            return PickaxeLevel.NONE;
        }
        return pickaxes.getOrDefault(uuid, PickaxeLevel.NONE);
    }

    public AxeLevel getAxe(UUID uuid) {
        if (axes.getOrDefault(uuid, AxeLevel.NONE) == null) {
            return AxeLevel.NONE;
        }
        return axes.getOrDefault(uuid, AxeLevel.NONE);
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }
}

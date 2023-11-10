package dev.foxikle.webnetbedwars.managers;

import dev.foxikle.webnetbedwars.WebNetBedWars;
import dev.foxikle.webnetbedwars.data.enums.GameState;
import dev.foxikle.webnetbedwars.data.objects.Team;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import net.minecraft.world.level.block.Blocks;


import java.lang.reflect.Field;
import java.util.*;

public class GameManager {
    private final WebNetBedWars plugin;
    private List<Team> teamlist = new ArrayList<>();
    private Map<Team, List<UUID>> playerTeams = new HashMap<>();
    private Map<Team, org.bukkit.scoreboard.Team> mcTeams = new HashMap<>();

    private StatsManager statsManager;
    private GameState gameState;

    public boolean STARTED = false;

    public GameManager(WebNetBedWars plugin) {
        this.plugin = plugin;
        statsManager = new StatsManager();
    }

    public void setup() {
        gameState = GameState.WAITING;
        FileConfiguration config = plugin.getConfig();
        ConfigurationSection section = config.getConfigurationSection("Teams");
        for (String key : section.getKeys(false)) {
            ConfigurationSection teamSection = section.getConfigurationSection(key);
            teamlist.add(new Team(
                    key,
                    teamSection.getString("TAB_PREFIX"),
                    ChatColor.valueOf(teamSection.getString("TEAM_COLOR")),
                    Material.valueOf(teamSection.getString("BED_ITEM")),
                    teamSection.getLocation("SPAWN_LOCATION"),
                    teamSection.getLocation("GENERATOR_LOCATION"),
                    teamSection.getLocation("ITEM_SHOP_LOCATION"),
                    teamSection.getLocation("TEAM_SHOP_LOCATION"),
                    teamSection.getLocation("TEAM_CHEST_LOCATION")
                    ));
        }
    }

    public void start() {
        STARTED = true;
        setGameState(GameState.PLAY);
        Bukkit.getOnlinePlayers().forEach(player -> statsManager.propagatePlayer(player.getUniqueId()));
        // split players into teams
        List<UUID> players = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(player -> players.add(player.getUniqueId()));
        playerTeams = splitPlayersIntoTeams(players);

        playerTeams.keySet().forEach(team -> {
            List<UUID> uuids = playerTeams.get(team);
            uuids.forEach(uuid -> {
                Player p = Bukkit.getPlayer(uuid);
                if(p != null){
                    mcTeams.get(team).addEntry(p.getName());
                    p.teleport(team.spawnLocation());
                }
            });
        });
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

    public List<Team> getTeamlist() {
        return teamlist;
    }

    public void cleanup(){
        STARTED = false;
        setGameState(GameState.CLEANUP);
        mcTeams.values().forEach(org.bukkit.scoreboard.Team::unregister);
    }


}

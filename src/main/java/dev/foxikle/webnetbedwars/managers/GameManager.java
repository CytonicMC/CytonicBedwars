package dev.foxikle.webnetbedwars.managers;

import dev.foxikle.webnetbedwars.WebNetBedWars;
import dev.foxikle.webnetbedwars.data.enums.GameState;
import dev.foxikle.webnetbedwars.data.objects.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class GameManager {
    private final WebNetBedWars plugin;
    private List<Team> teamlist = new ArrayList<>();
    private Map<Team, List<UUID>> playerTeams = new HashMap<>();

    private StatsManager statsManager;
    private GameState gameState;

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
        setGameState(GameState.PLAY);
        Bukkit.getOnlinePlayers().forEach(player -> statsManager.propagatePlayer(player.getUniqueId()));
        // split players into teams
        List<UUID> players = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(player -> players.add(player.getUniqueId()));
        playerTeams = splitPlayersIntoTeams(players);

    }

    private Map<Team, List<UUID>> splitPlayersIntoTeams(List<UUID> players) {
        int numTeams = teamlist.size();
        int teamSize = players.size() / numTeams;
        int remainingPlayers = players.size() % numTeams;
        Map<Team, List<UUID>> result = new HashMap<>();

        int playerIndex = 0;
        for (Team team : teamlist) {
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

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public StatsManager getStatsManager() {
        return statsManager;
    }

    public List<Team> getTeamlist() {
        return teamlist;
    }
}

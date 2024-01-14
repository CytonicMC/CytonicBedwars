package dev.foxikle.webnetbedwars.managers;

import dev.foxikle.webnetbedwars.WebNetBedWars;
import dev.foxikle.webnetbedwars.data.objects.Team;
import dev.foxikle.webnetbedwars.utils.Items;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class GeneratorManager {
    private final WebNetBedWars plugin;
    private final GameManager gameManager;

    public GeneratorManager(WebNetBedWars plugin, GameManager gameManager) {
        this.plugin = plugin;
        this.gameManager = gameManager;
    }


    public void registerTeamGenerators() {
        World world = Bukkit.getWorld(plugin.getConfig().getString("MapName"));

        for (Team t : plugin.getGameManager().getTeamlist()) {
//iron
            Bukkit.getScheduler().runTaskTimer(plugin, () -> world.dropItemNaturally(t.generatorLocation(), Items.IRON), 0, plugin.getConfig().getInt("GeneratorsWaitTime.iron"));
//gold
            Bukkit.getScheduler().runTaskTimer(plugin, () -> world.dropItemNaturally(t.generatorLocation(), Items.GOLD), 0, plugin.getConfig().getInt("GeneratorsWaitTime.gold"));
        }
    }
}

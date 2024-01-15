package dev.foxikle.webnetbedwars.listeners;

import dev.foxikle.webnetbedwars.WebNetBedWars;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class HungerDepleteListener implements Listener {
    private final WebNetBedWars plugin;
    public HungerDepleteListener(WebNetBedWars plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onHungerDeplete(FoodLevelChangeEvent e) {

        e.getEntity().setFoodLevel(20);
        e.setCancelled(true);
    }
}
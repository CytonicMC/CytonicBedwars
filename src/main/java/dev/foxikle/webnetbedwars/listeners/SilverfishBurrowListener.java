package dev.foxikle.webnetbedwars.listeners;

import dev.foxikle.webnetbedwars.WebNetBedWars;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class SilverfishBurrowListener implements Listener {
    private final WebNetBedWars plugin;

    public SilverfishBurrowListener(WebNetBedWars plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSilverfishBurrow(EntityChangeBlockEvent event) {
        if(event.getEntityType() == EntityType.SILVERFISH)
            event.setCancelled(true);
    }
}

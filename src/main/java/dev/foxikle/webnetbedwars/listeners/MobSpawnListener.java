package dev.foxikle.webnetbedwars.listeners;

import dev.foxikle.webnetbedwars.WebNetBedWars;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class MobSpawnListener implements Listener {
    private final WebNetBedWars plugin;

    public MobSpawnListener(WebNetBedWars plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMobSpawn(EntitySpawnEvent e) {
        if(e.getEntityType() == EntityType.ENDERMITE || e.getEntityType() == EntityType.CHICKEN)
            e.setCancelled(true);
    }
}

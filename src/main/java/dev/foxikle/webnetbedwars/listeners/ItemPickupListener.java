package dev.foxikle.webnetbedwars.listeners;

import dev.foxikle.webnetbedwars.WebNetBedWars;
import dev.foxikle.webnetbedwars.data.objects.Generator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class ItemPickupListener implements Listener {
    private final WebNetBedWars plugin;

    public ItemPickupListener(WebNetBedWars plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        if(event.getEntity() instanceof Player player) {
            if (event.getItem().getPersistentDataContainer().has(Generator.SPLIT_KEY)) {
                player.getNearbyEntities(3, 3, 3).forEach(entity -> {
                    if(entity instanceof Player p) {
                        p.getInventory().addItem(event.getItem().getItemStack());
                    }
                });
            }
        }
    }
}

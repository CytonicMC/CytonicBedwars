package dev.foxikle.webnetbedwars.listeners;

import dev.foxikle.webnetbedwars.WebNetBedWars;
import dev.foxikle.webnetbedwars.utils.Items;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemMergeEvent;

public class ItemMergeListener implements Listener {
    private final WebNetBedWars plugin;

    public ItemMergeListener(WebNetBedWars plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemMerge(ItemMergeEvent event) {
        if(event.getEntity().getPersistentDataContainer().has(Items.NAMESPACE)) {
            event.setCancelled(true);
        }
    }
}

package net.cytonic.cytonicbedwars.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;

import net.cytonic.cytonicbedwars.WebNetBedWars;
import net.cytonic.cytonicbedwars.utils.Items;

public class InteractListener implements Listener {
    private final WebNetBedWars plugin;

    public InteractListener(WebNetBedWars plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnInteract(PlayerInteractEvent event){
        if(event.getItem() == null) return;
        if(event.getItem().getItemMeta() == null) return;
        if(event.getItem().getItemMeta().getPersistentDataContainer().getKeys().contains(Items.NAMESPACE)){
            String key = event.getItem().getItemMeta().getPersistentDataContainer().get(Items.NAMESPACE, PersistentDataType.STRING);
            if(key != null){
                plugin.getItemAbilityDispatcher().dispatch(key, event.getPlayer(), event);
                event.setCancelled(true);
            }
        }
    }
}

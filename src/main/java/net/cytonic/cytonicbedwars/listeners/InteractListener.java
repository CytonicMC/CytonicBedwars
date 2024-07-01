package net.cytonic.cytonicbedwars.listeners;

import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytonicbedwars.utils.Items;
import net.minestom.server.event.player.PlayerBlockInteractEvent;
import net.minestom.server.event.player.PlayerEntityInteractEvent;

public class InteractListener {

    private final CytonicBedWars plugin;
    public InteractListener(CytonicBedWars plugin) {
        this.plugin = plugin;
    }

    public void onInteract(PlayerBlockInteractEvent event) {
        if (event.getPlayer().getItemInHand(event.getHand()).toItemNBT().getString(Items.NAMESPACE).equals(Items.NAMESPACE)) {
            String key = event.getPlayer().getItemInHand(event.getHand()).toItemNBT().getString(Items.NAMESPACE);
            plugin.getItemAbilityDispatcher().dispatch(key, event.getPlayer(), event);
        }
    }

    public void onInteract(PlayerEntityInteractEvent event) {
        if (event.getPlayer().getItemInHand(event.getHand()).toItemNBT().getString(Items.NAMESPACE).equals(Items.NAMESPACE)) {
            String key = event.getPlayer().getItemInHand(event.getHand()).toItemNBT().getString(Items.NAMESPACE);
            plugin.getItemAbilityDispatcher().dispatch(key, event.getPlayer(), event);
        }
    }
}

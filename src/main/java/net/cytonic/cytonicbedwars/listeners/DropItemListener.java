package net.cytonic.cytonicbedwars.listeners;

import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytonicbedwars.utils.Items;
import net.minestom.server.event.item.ItemDropEvent;
import net.minestom.server.item.ItemStack;

public class DropItemListener {

    private final CytonicBedWars plugin;

    public DropItemListener(CytonicBedWars plugin) {
        this.plugin = plugin;
    }

    public void onDrop(ItemDropEvent event) {
        ItemStack item = event.getItemStack();
        if (item.toItemNBT().getBoolean(Items.NO_DROP)) {
            event.setCancelled(true);
        }
    }

//todo prevent dropping items while falling
//        if(player.getFallDistance() > 0){
//            // prevent dropping items whilst falling
//            event.setCancelled(true);
//        }
}

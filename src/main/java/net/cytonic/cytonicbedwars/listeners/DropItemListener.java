package net.cytonic.cytonicbedwars.listeners;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.utils.Items;
import net.minestom.server.event.item.ItemDropEvent;
import net.minestom.server.item.ItemComponent;
import net.minestom.server.item.ItemStack;

@NoArgsConstructor
public class DropItemListener {

    public void onDrop(ItemDropEvent event) {
        ItemStack item = event.getItemStack();
        if (item.has(ItemComponent.CUSTOM_DATA)) {
            if (item.get(ItemComponent.CUSTOM_DATA).nbt().getBoolean(Items.NO_DROP)) {
                event.setCancelled(true);
            }
        }
    }

//todo prevent dropping items while falling
//        if(player.getFallDistance() > 0){
//            // prevent dropping items whilst falling
//            event.setCancelled(true);
//        }
}

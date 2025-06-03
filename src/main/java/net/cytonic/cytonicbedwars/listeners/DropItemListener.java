package net.cytonic.cytonicbedwars.listeners;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.utils.Items;
import net.minestom.server.event.item.ItemDropEvent;
import net.minestom.server.component.DataComponents;
import net.minestom.server.item.ItemStack;

import java.util.Objects;

@NoArgsConstructor
public class DropItemListener {

    public static void onDrop(ItemDropEvent event) {
        ItemStack item = event.getItemStack();
        if (item.has(DataComponents.CUSTOM_DATA)) {
            if (Objects.requireNonNull(item.get(DataComponents.CUSTOM_DATA)).nbt().getBoolean(Items.NO_DROP)) {
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

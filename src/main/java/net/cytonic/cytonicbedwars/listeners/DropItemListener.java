package net.cytonic.cytonicbedwars.listeners;

import io.github.togar2.pvp.feature.fall.VanillaFallFeature;
import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.utils.Items;
import net.cytonic.cytosis.events.api.Listener;
import net.cytonic.cytosis.events.api.Priority;
import net.minestom.server.component.DataComponents;
import net.minestom.server.event.item.ItemDropEvent;
import net.minestom.server.item.ItemStack;

import java.util.Objects;

@NoArgsConstructor
@SuppressWarnings("unused")
public class DropItemListener {

    @Listener
    @Priority(-1)
    public void onDrop(ItemDropEvent event) {
        ItemStack item = event.getItemStack();
        if (item.has(DataComponents.CUSTOM_DATA)) {
            if (Objects.requireNonNull(item.get(DataComponents.CUSTOM_DATA)).nbt().getBoolean(Items.NO_DROP)) {
                event.setCancelled(true);
            }
        }
        if (event.getPlayer().getTag(VanillaFallFeature.FALL_DISTANCE) > 1) {
            event.setCancelled(true);
        }
    }
}

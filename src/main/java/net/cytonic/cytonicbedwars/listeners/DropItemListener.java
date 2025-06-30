package net.cytonic.cytonicbedwars.listeners;

import io.github.togar2.pvp.feature.fall.VanillaFallFeature;
import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.utils.Items;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.events.api.Listener;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.ItemEntity;
import net.minestom.server.event.item.ItemDropEvent;
import net.minestom.server.item.ItemStack;

import java.time.Duration;

@NoArgsConstructor
@SuppressWarnings("unused")
public class DropItemListener {

    @Listener
    public void onDrop(ItemDropEvent event) {
        ItemStack item = event.getItemStack();
        if (item.hasTag(Items.NO_DROP)) {
            if (item.getTag(Items.NO_DROP)) {
                event.setCancelled(true);
            }
        }
        if (event.getPlayer().getTag(VanillaFallFeature.FALL_DISTANCE) > 1) {
            event.setCancelled(true);
        }

        if (!event.isCancelled()) {
            Pos playerPos = event.getPlayer().getPosition();
            ItemEntity itemEntity = new ItemEntity(event.getItemStack());
            itemEntity.setPickupDelay(Duration.ofMillis(500));
            itemEntity.setInstance(Cytosis.getDefaultInstance(), playerPos.add(0, 1.5, 0));
            itemEntity.setVelocity(playerPos.direction().mul(6));
        }
    }
}

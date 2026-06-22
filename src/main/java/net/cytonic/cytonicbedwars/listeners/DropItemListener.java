package net.cytonic.cytonicbedwars.listeners;

import java.time.Duration;

import io.github.togar2.pvp.feature.fall.VanillaFallFeature;
import lombok.NoArgsConstructor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.ItemEntity;
import net.minestom.server.event.item.ItemDropEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.item.ItemStack;
import net.minestom.server.utils.time.Tick;

import net.cytonic.cytonicbedwars.utils.Items;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.events.api.Listener;

@NoArgsConstructor
@SuppressWarnings("unused")
public class DropItemListener {

    //todo @Listener
    public void onDrop(ItemDropEvent event) {
        ItemStack item = event.getItemStack();
        if (item.hasTag(Items.NO_DROP) && item.getTag(Items.NO_DROP)) {
            event.setCancelled(true);
            return;
        }

        if (!event.getPlayer().hasTag(VanillaFallFeature.FALL_DISTANCE)
            || event.getPlayer().getTag(VanillaFallFeature.FALL_DISTANCE) > 1) {
            event.setCancelled(true);
            return;
        }

        if (item.hasTag(Items.NAMESPACE) && item.getTag(Items.NAMESPACE).contains("SWORD")) {
            int swords = -1; //the sword in the event is still in the inventory
            for (ItemStack itemStack : event.getPlayer().getInventory().getItemStacks()) {
                if (itemStack.hasTag(Items.NAMESPACE) && itemStack.getTag(Items.NAMESPACE).contains("SWORD")) {
                    swords++;
                }
            }
            if (swords == 0) {
                MinecraftServer.getSchedulerManager()
                    .buildTask(() -> event.getPlayer().getInventory().addItemStack(Items.DEFAULT_SWORD))
                    .delay(Duration.ofMillis(1)).schedule();
            }
        }

        Pos playerPos = event.getPlayer().getPosition();
        ItemEntity itemEntity = new ItemEntity(event.getItemStack());
        itemEntity.setPickupDelay(Tick.server(40));
        itemEntity.setInstance(Cytosis.CONTEXT.getComponent(InstanceContainer.class), playerPos.add(0, 1.5, 0));
        itemEntity.setVelocity(playerPos.direction().mul(6));
    }
}

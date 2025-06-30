package net.cytonic.cytonicbedwars.listeners;

import io.github.togar2.pvp.feature.fall.VanillaFallFeature;
import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.utils.Items;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.events.api.Listener;
import net.cytonic.cytosis.logging.Logger;
import net.minestom.server.MinecraftServer;
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
                return;
            }
        }
        if (event.getPlayer().getTag(VanillaFallFeature.FALL_DISTANCE) > 1) {
            event.setCancelled(true);
            return;
        }

        if (item.hasTag(Items.NAMESPACE) && item.getTag(Items.NAMESPACE).contains("SWORD") && !item.getTag(Items.NAMESPACE).contains("MENU")) {
            int swords = -1; //the sword in the event is still in the inventory
            for (ItemStack itemStack : event.getPlayer().getInventory().getItemStacks()) {
                if (itemStack.hasTag(Items.NAMESPACE) && itemStack.getTag(Items.NAMESPACE).contains("SWORD") && !itemStack.getTag(Items.NAMESPACE).contains("MENU")) {
                    swords++;
                }
            }
            Logger.debug("you have %s swords", swords);
            if (swords == 0) {
                MinecraftServer.getSchedulerManager().buildTask(() -> event.getPlayer().getInventory().addItemStack(Items.DEFAULT_SWORD)).delay(Duration.ofMillis(1)).schedule();
            }
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

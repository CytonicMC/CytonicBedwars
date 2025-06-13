package net.cytonic.cytonicbedwars.listeners;

import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytosis.events.api.Listener;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Player;
import net.minestom.server.event.item.PickupItemEvent;
import net.minestom.server.item.ItemStack;

@SuppressWarnings("unused")
public class PickupItemListener {

    @Listener
    public static void onPickup(PickupItemEvent event) {
        final Entity entity = event.getLivingEntity();
        if (entity instanceof Player player) {
            if (CytonicBedWars.getGameManager().spectators.contains(player.getUuid())) {
                event.setCancelled(true);
                return;
            }
            final ItemStack itemStack = event.getItemEntity().getItemStack();
            event.setCancelled(!player.getInventory().addItemStack(itemStack));
        }
    }
}

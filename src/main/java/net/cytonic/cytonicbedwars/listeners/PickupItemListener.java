package net.cytonic.cytonicbedwars.listeners;

import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytosis.events.api.Listener;
import net.minestom.server.event.item.PickupItemEvent;

@SuppressWarnings("unused")
public class PickupItemListener {

    @Listener
    public static void onPickup(PickupItemEvent event) {
        if (CytonicBedWars.getGameManager().spectators.contains(event.getEntity().getUuid())) {
            event.setCancelled(true);
        }
    }
}

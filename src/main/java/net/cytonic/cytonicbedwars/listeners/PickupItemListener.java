package net.cytonic.cytonicbedwars.listeners;

import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.minestom.server.event.item.PickupItemEvent;

public class PickupItemListener {

    public static void onPickup(PickupItemEvent event) {
        if (CytonicBedWars.getGameManager().spectators.contains(event.getEntity().getUuid())) {
            event.setCancelled(true);
        }
    }
}

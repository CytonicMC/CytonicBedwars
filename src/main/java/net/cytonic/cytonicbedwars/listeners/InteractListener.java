package net.cytonic.cytonicbedwars.listeners;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytonicbedwars.utils.Items;
import net.cytonic.cytosis.player.CytosisPlayer;
import net.minestom.server.event.player.PlayerBlockInteractEvent;
import net.minestom.server.event.player.PlayerEntityInteractEvent;
import net.minestom.server.component.DataComponents;

@NoArgsConstructor
public class InteractListener {

    public static void onInteract(PlayerBlockInteractEvent event) {
        if (event.getPlayer().getItemInHand(event.getHand()).has(DataComponents.CUSTOM_DATA)) {
            if (event.getPlayer().getItemInHand(event.getHand()).get(DataComponents.CUSTOM_DATA).nbt().getString(Items.NAMESPACE).equals(Items.NAMESPACE)) {
                String key = event.getPlayer().getItemInHand(event.getHand()).get(DataComponents.CUSTOM_DATA).nbt().getString(Items.NAMESPACE);
                CytonicBedWars.getGameManager().getItemAbilityDispatcher().dispatch(key, (CytosisPlayer) event.getPlayer(), event);
            }
        }
    }

    public static void onInteract(PlayerEntityInteractEvent event) {
        if (event.getPlayer().getItemInHand(event.getHand()).has(DataComponents.CUSTOM_DATA)) {
            if (event.getPlayer().getItemInHand(event.getHand()).get(DataComponents.CUSTOM_DATA).nbt().getString(Items.NAMESPACE).equals(Items.NAMESPACE)) {
                String key = event.getPlayer().getItemInHand(event.getHand()).get(DataComponents.CUSTOM_DATA).nbt().getString(Items.NAMESPACE);
                CytonicBedWars.getGameManager().getItemAbilityDispatcher().dispatch(key, (CytosisPlayer) event.getPlayer(), event);
            }
        }
    }
}

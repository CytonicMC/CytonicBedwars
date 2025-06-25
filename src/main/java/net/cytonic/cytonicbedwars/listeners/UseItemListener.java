package net.cytonic.cytonicbedwars.listeners;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytonicbedwars.utils.Items;
import net.cytonic.cytosis.events.api.Listener;
import net.minestom.server.component.DataComponents;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.item.ItemStack;

import java.util.Objects;

@NoArgsConstructor
@SuppressWarnings("unused")
public class UseItemListener {

    @Listener
    public void onInteract(PlayerUseItemEvent event) {
        ItemStack item = event.getPlayer().getItemInHand(event.getHand());
        if (item.has(DataComponents.CUSTOM_DATA)) {
            if (Objects.requireNonNull(item.get(DataComponents.CUSTOM_DATA)).nbt().get(Items.NAMESPACE) != null) {
                String key = Objects.requireNonNull(item.get(DataComponents.CUSTOM_DATA)).nbt().getString(Items.NAMESPACE);
                CytonicBedWars.getGameManager().getItemAbilityDispatcher().dispatch(key, (BedwarsPlayer) event.getPlayer(), event);
            }
        }
    }
}

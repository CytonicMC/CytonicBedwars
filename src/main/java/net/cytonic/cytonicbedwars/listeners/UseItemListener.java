package net.cytonic.cytonicbedwars.listeners;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.ItemAbilityDispatcher;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytonicbedwars.utils.Items;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.events.api.Listener;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.item.ItemStack;

@NoArgsConstructor
@SuppressWarnings("unused")
public class UseItemListener {

    @Listener
    public void onInteract(PlayerUseItemEvent event) {
        if (!(event.getPlayer() instanceof BedwarsPlayer player)) return;
        ItemStack item = event.getPlayer().getItemInHand(event.getHand());
        if (item.hasTag(Items.NAMESPACE)) {
            String key = item.getTag(Items.NAMESPACE);
            Cytosis.CONTEXT.getComponent(ItemAbilityDispatcher.class).dispatch(key, player, event);
        }
    }
}

package net.cytonic.cytonicbedwars.listeners;

import io.github.togar2.pvp.events.PlayerExhaustEvent;
import lombok.NoArgsConstructor;
import net.cytonic.cytosis.events.api.Listener;

@NoArgsConstructor
@SuppressWarnings("unused")
public class ExhaustListener {

    @Listener
    private void onHunger(PlayerExhaustEvent event) {
        event.setCancelled(true);
    }
}

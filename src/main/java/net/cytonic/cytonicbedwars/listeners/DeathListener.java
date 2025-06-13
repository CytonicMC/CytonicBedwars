package net.cytonic.cytonicbedwars.listeners;

import io.github.togar2.pvp.events.EntityPreDeathEvent;
import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytosis.events.api.Listener;
import net.cytonic.cytosis.player.CytosisPlayer;

@NoArgsConstructor
@SuppressWarnings("unused")
public class DeathListener {

    @Listener
    public void onDeath(EntityPreDeathEvent event) {
        event.setCancelDeath(true);
        if (event.getEntity() instanceof CytosisPlayer player) {
            if (event.getDamage().getAttacker() != null && event.getDamage().getAttacker() instanceof CytosisPlayer attacker) {
                CytonicBedWars.getGameManager().kill(player, attacker, event.getDamage().getType());
            } else {
                CytonicBedWars.getGameManager().kill(player, null, event.getDamage().getType());
            }
        }

    }
}

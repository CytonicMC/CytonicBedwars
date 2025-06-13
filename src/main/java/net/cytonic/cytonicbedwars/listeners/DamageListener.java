package net.cytonic.cytonicbedwars.listeners;

import io.github.togar2.pvp.events.FinalDamageEvent;
import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytosis.events.api.Listener;
import net.cytonic.cytosis.player.CytosisPlayer;

@NoArgsConstructor
@SuppressWarnings("unused")
public class DamageListener {

    @Listener
    public void onDamage(FinalDamageEvent event) {
        if (event.getEntity() instanceof CytosisPlayer player) {
            if (CytonicBedWars.getGameManager().spectators.contains(player.getUuid())) {
                event.setCancelled(true);
                return;
            }
            if (!CytonicBedWars.getGameManager().STARTED) {
                event.setCancelled(true);
                return;
            }

            if (event.getDamage().getAttacker() instanceof CytosisPlayer damager) {
                CytonicBedWars.getGameManager().getStatsManager().getStats(damager.getUuid()).addDamageDealt(event.getDamage().getAmount());
            }
            CytonicBedWars.getGameManager().getStatsManager().getStats(player.getUuid()).addDamageTaken(event.getDamage().getAmount());
        }
    }
}

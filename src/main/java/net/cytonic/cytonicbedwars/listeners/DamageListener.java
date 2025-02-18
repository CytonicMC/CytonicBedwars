package net.cytonic.cytonicbedwars.listeners;

import io.github.togar2.pvp.events.FinalDamageEvent;
import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytosis.player.CytosisPlayer;

@NoArgsConstructor
public class DamageListener {

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
                CytonicBedWars.getGameManager().getStatsManager().addPlayerDamageDealt(damager.getUuid(), event.getDamage().getAmount());
            }
            CytonicBedWars.getGameManager().getStatsManager().addPlayerDamageTaken(player.getUuid(), event.getDamage().getAmount());
        }
    }
}

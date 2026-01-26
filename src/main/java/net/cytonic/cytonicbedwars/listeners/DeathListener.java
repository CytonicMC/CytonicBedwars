package net.cytonic.cytonicbedwars.listeners;

import io.github.togar2.pvp.events.EntityPreDeathEvent;
import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.managers.GameManager;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.events.api.Listener;

@NoArgsConstructor
@SuppressWarnings("unused")
public class DeathListener {

    @Listener
    public void onDeath(EntityPreDeathEvent event) {
        event.setCancelDeath(true);
        if (event.getEntity() instanceof BedwarsPlayer player) {
            if (event.getDamage().getAttacker() != null && event.getDamage().getAttacker() instanceof BedwarsPlayer attacker) {
                Cytosis.CONTEXT.getComponent(GameManager.class).kill(player, attacker, event.getDamage().getType());
            } else {
                Cytosis.CONTEXT.getComponent(GameManager.class).kill(player, null, event.getDamage().getType());
            }
        }

    }
}

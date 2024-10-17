package net.cytonic.cytonicbedwars.listeners;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.damage.DamageType;
import net.minestom.server.event.entity.EntityDamageEvent;

@NoArgsConstructor
public class DamageListener {

    public void onDamage(EntityDamageEvent event) {
        if (!CytonicBedWars.getGameManager().STARTED) return;
        if (event.getEntity() instanceof Player player) {
            if (CytonicBedWars.getGameManager().spectators.contains(player.getUuid())) {
                event.setCancelled(true);
                return;
            }

            if (event.getDamage().getAttacker() instanceof Player damager) {
                CytonicBedWars.getGameManager().getStatsManager().addPlayerDamageDealt(damager.getUuid(), event.getDamage().getAmount());
            }
            CytonicBedWars.getGameManager().getStatsManager().addPlayerDamageTaken(player.getUuid(), event.getDamage().getAmount());

            if (player.getHealth() - event.getDamage().getAmount() <= 0) {
                event.setCancelled(true);
                if (event.getDamage().getAttacker() instanceof Player damager) {
                    CytonicBedWars.getGameManager().kill(player, damager, DamageType.GENERIC_KILL);
                    return;
                }
                if (event.getDamage().getAttacker() != null && event.getDamage().getType() == DamageType.FIREBALL) {
                    return;
                }
                CytonicBedWars.getGameManager().kill(player, null, event.getDamage().getType());
            }
        }
    }
}

package net.cytonic.cytonicbedwars.listeners;

import net.cytonic.cytonicbedwars.CytonicBedWars;

import net.minestom.server.entity.Player;
import net.minestom.server.entity.damage.DamageType;
import net.minestom.server.event.entity.EntityDamageEvent;

public class DamageListener {

    private final CytonicBedWars plugin;

    public DamageListener(CytonicBedWars plugin) {
        this.plugin = plugin;
    }


    public void onDamage(EntityDamageEvent event) {
        if (!plugin.getGameManager().STARTED) return;
        if (event.getEntity() instanceof Player player) {
            if (plugin.getGameManager().spectators.contains(player.getUuid())) {
                event.setCancelled(true);
                return;
            }

            if (event.getDamage().getAttacker() instanceof Player damager) {
                plugin.getGameManager().getStatsManager().addPlayerDamageDealt(damager.getUuid(), event.getDamage().getAmount());
            }
            plugin.getGameManager().getStatsManager().addPlayerDamageTaken(player.getUuid(), event.getDamage().getAmount());

            if (player.getHealth() - event.getDamage().getAmount() <= 0) {
                event.setCancelled(true);
                if (event.getDamage().getAttacker() instanceof Player damager) {
                    plugin.getGameManager().kill(player, damager, DamageType.GENERIC_KILL);
                    return;
                }
                if (event.getDamage().getAttacker() != null && event.getDamage().getType() == DamageType.FIREBALL) {
                    return;
                }
                plugin.getGameManager().kill(player, null, event.getDamage().getType());
            }
        }
    }
}

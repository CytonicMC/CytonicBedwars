package dev.foxikle.webnetbedwars.listeners;

import dev.foxikle.webnetbedwars.WebNetBedWars;
import dev.foxikle.webnetbedwars.managers.GameManager;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {

    private final WebNetBedWars plugin;

    public DamageListener(WebNetBedWars plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event) {
        if(!plugin.getGameManager().STARTED) return;
        if(event.getEntity() instanceof Player player) {
            if(plugin.getGameManager().spectators.contains(player.getUniqueId())) {
                event.setCancelled(true);
                return;
            }

            if(event.getDamager() instanceof Player damager) {
                plugin.getGameManager().getStatsManager().addPlayerDamageDealt(damager.getUniqueId(), event.getFinalDamage());
            }
            plugin.getGameManager().getStatsManager().addPlayerDamageTaken(player.getUniqueId(), event.getFinalDamage());


            if(player.getHealth() - event.getFinalDamage() <= 0) {
                event.setCancelled(true);
                if(event.getDamager() instanceof Player damager){
                    plugin.getGameManager().kill(player, damager, EntityDamageEvent.DamageCause.KILL);
                    return;
                }
                if(event.getDamager() instanceof Fireball && event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
                    return;
                }
                plugin.getGameManager().kill(player, null, event.getCause());
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(!plugin.getGameManager().STARTED) return;
        if(event.getEntity() instanceof Player player) {
            if(plugin.getGameManager().spectators.contains(player.getUniqueId())) {
                event.setCancelled(true);
                return;
            }

            plugin.getGameManager().getStatsManager().addPlayerDamageTaken(player.getUniqueId(), event.getFinalDamage());

            if(player.getHealth() - event.getFinalDamage() <= 0) {
                event.setCancelled(true);
                plugin.getGameManager().kill(player, null, event.getCause());
            }
        }
    }

    @EventHandler
    public void onDamageByBlock(EntityDamageByBlockEvent event) {
        if(!plugin.getGameManager().STARTED) return;
        if(event.getEntity() instanceof Player player) {
            if(plugin.getGameManager().spectators.contains(player.getUniqueId())) {
                event.setCancelled(true);
                return;
            }

            plugin.getGameManager().getStatsManager().addPlayerDamageTaken(player.getUniqueId(), event.getFinalDamage());

            if(player.getHealth() - event.getFinalDamage() <= 0) {
                event.setCancelled(true);
                plugin.getGameManager().kill(player, null, event.getCause());
            }
        }
    }
}

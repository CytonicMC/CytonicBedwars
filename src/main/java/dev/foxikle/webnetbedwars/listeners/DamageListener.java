package dev.foxikle.webnetbedwars.listeners;

import dev.foxikle.webnetbedwars.WebNetBedWars;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageListener implements Listener {

    private final WebNetBedWars plugin;

    public DamageListener(WebNetBedWars plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onKill(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Player player) {
            if(player.getHealth() - event.getFinalDamage() <= 0) {
                //todo: check for final kills
                if(event.getDamager() instanceof Player damager){
                    plugin.getGameManager().getStatsManager().addPlayerKill(damager.getUniqueId());
                    plugin.getGameManager().getStatsManager().addPlayerDamageDealt(damager.getUniqueId(), event.getFinalDamage());
                }
                plugin.getGameManager().getStatsManager().addPlayerDeath(player.getUniqueId());
                plugin.getGameManager().getStatsManager().addPlayerDamageTaken(player.getUniqueId(), event.getFinalDamage());
                // dead
                //todo: animations and stuff
                event.setCancelled(true);
            }
        }
    }
}

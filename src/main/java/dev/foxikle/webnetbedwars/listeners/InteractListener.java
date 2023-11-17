package dev.foxikle.webnetbedwars.listeners;

import dev.foxikle.webnetbedwars.WebNetBedWars;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {
    private final WebNetBedWars plugin;

    public InteractListener(WebNetBedWars plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnInteract(PlayerInteractEvent event){
        if(event.getItem() == null)
            return;
        if(event.getAction().isRightClick()){
            if(event.getItem().getType() == Material.FIRE_CHARGE){
                event.setCancelled(true);
                event.getItem().setAmount(event.getItem().getAmount()-1);
                Fireball fb = event.getPlayer().getLocation().getWorld().spawn(event.getPlayer().getEyeLocation().add(event.getPlayer().getEyeLocation().getDirection()), Fireball.class);
                fb.setDirection(event.getPlayer().getEyeLocation().getDirection());
                fb.setVisualFire(false);
                fb.setIsIncendiary(false);
                fb.setShooter(event.getPlayer());
                return;
            }
            if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if(event.getItem().getType() == Material.TNT) {
                    event.setCancelled(true);
                    event.getItem().setAmount(event.getItem().getAmount()-1);
                    event.getPlayer().getLocation().getWorld().spawn(event.getInteractionPoint(), TNTPrimed.class);
                    return;
                }
            }
        }

    }
}

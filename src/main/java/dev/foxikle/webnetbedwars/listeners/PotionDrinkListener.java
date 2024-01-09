package dev.foxikle.webnetbedwars.listeners;

import dev.foxikle.webnetbedwars.WebNetBedWars;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.nio.Buffer;

public class PotionDrinkListener implements Listener {

    private final WebNetBedWars plugin;

    public PotionDrinkListener(WebNetBedWars plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPotionDrink(PlayerItemConsumeEvent e) {
        if(e.getItem().getType() == Material.POTION || e.getItem().getType() == Material.GLASS_BOTTLE) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> e.getPlayer().getInventory().getItem(e.getHand()).setAmount(0), 1);
        }
    }
}

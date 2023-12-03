package dev.foxikle.webnetbedwars.listeners;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import dev.foxikle.webnetbedwars.WebNetBedWars;
import dev.foxikle.webnetbedwars.utils.Items;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ArmorEquipListener implements Listener {
    private final WebNetBedWars plugin;

    public ArmorEquipListener(WebNetBedWars plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onArmorEquip(PlayerArmorChangeEvent event) {
        if(event.getOldItem().getType() == Material.BARRIER && event.getPlayer().getGameMode() == GameMode.ADVENTURE) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                event.getPlayer().getInventory().setBoots(Items.SPECTATOR_ARMOR);
                event.getPlayer().getInventory().setLeggings(Items.SPECTATOR_ARMOR);
                event.getPlayer().getInventory().setChestplate(Items.SPECTATOR_ARMOR);
            }, 1);
        }
    }
}

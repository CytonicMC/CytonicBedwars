package dev.foxikle.webnetbedwars.listeners;

import dev.foxikle.webnetbedwars.WebNetBedWars;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;

import static org.bukkit.Material.AIR;

public class BlockPlaceListener implements Listener {
    private final WebNetBedWars plugin;

    public BlockPlaceListener(WebNetBedWars plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        if(e.getBlockPlaced().getType() == Material.SPONGE) {
            Bukkit.getScheduler().runTaskLater(plugin,  () -> e.getBlockPlaced().setType(AIR), 1);
        }
        e.getBlock().setMetadata("blockdata", new FixedMetadataValue(plugin, true));
    }
}

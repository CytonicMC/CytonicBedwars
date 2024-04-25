package net.cytonic.cytonicbedwars.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;

import net.cytonic.cytonicbedwars.WebNetBedWars;

public class BlockPlaceListener implements Listener {
    private final WebNetBedWars plugin;

    public BlockPlaceListener(WebNetBedWars plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        //todo: implement bounds
        e.getBlock().setMetadata("blockdata", new FixedMetadataValue(plugin, true));
    }
}

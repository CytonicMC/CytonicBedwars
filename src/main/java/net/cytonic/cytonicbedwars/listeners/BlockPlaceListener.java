package net.cytonic.cytonicbedwars.listeners;

import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.instance.block.Block;

public class BlockPlaceListener {

    private final CytonicBedWars plugin;

    public BlockPlaceListener(CytonicBedWars plugin) {
        this.plugin = plugin;
    }

    public void onBlockPlace(PlayerBlockPlaceEvent e) {
        //todo: implement bounds
        if (e.getBlock().hasNbt()) {
            Block block = e.getBlock().withNbt(e.getBlock().nbt().putBoolean("placedByPlayer", true));
            e.setBlock(block);
        }
    }
}

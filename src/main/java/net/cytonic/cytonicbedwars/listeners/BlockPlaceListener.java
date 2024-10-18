package net.cytonic.cytonicbedwars.listeners;

import lombok.NoArgsConstructor;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.instance.block.Block;

@NoArgsConstructor
public class BlockPlaceListener {

    public void onBlockPlace(PlayerBlockPlaceEvent e) {
        //todo: implement bounds
        if (e.getBlock().hasNbt()) {
            Block block = e.getBlock().withNbt(e.getBlock().nbt().putBoolean("placedByPlayer", true));
            e.setBlock(block);
        }
    }
}

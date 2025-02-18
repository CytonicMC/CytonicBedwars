package net.cytonic.cytonicbedwars.listeners;

import lombok.NoArgsConstructor;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.ItemComponent;

import java.util.Objects;

@NoArgsConstructor
public class BlockPlaceListener {

    public void onBlockPlace(PlayerBlockPlaceEvent e) {
        //todo: implement bounds
        String id = Objects.requireNonNull(e.getPlayer().getItemInHand(e.getHand()).get(ItemComponent.CUSTOM_DATA)).nbt().getString("bwID");
        if (e.getBlock().hasNbt()) {
            Block block = e.getBlock().withNbt(Objects.requireNonNull(e.getBlock().nbt()).putBoolean("placedByPlayer", true).putString("bwID", id));
            e.setBlock(block);
        } else {
            Block block = e.getBlock().withNbt(CompoundBinaryTag.builder().putBoolean("placedByPlayer", true).putString("bwID", id).build());
            e.setBlock(block);
        }
    }
}

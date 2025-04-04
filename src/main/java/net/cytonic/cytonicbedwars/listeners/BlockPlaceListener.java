package net.cytonic.cytonicbedwars.listeners;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.CytonicBedwarsSettings;
import net.cytonic.cytosis.utils.Msg;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.ItemComponent;

import java.util.Objects;

@NoArgsConstructor
public class BlockPlaceListener {

    public static void onBlockPlace(PlayerBlockPlaceEvent e) {
        if (e.getPlayer().getItemInHand(e.getHand()).has(ItemComponent.CUSTOM_DATA)) {
            String id = Objects.requireNonNull(e.getPlayer().getItemInHand(e.getHand()).get(ItemComponent.CUSTOM_DATA)).nbt().getString("bwID");
            if (e.getBlock().hasNbt()) {
                Block block = e.getBlock().withNbt(Objects.requireNonNull(e.getBlock().nbt()).putBoolean("placedByPlayer", true).putString("bwID", id));
                e.setBlock(block);
            } else {
                Block block = e.getBlock().withNbt(CompoundBinaryTag.builder().putBoolean("placedByPlayer", true).putString("bwID", id).build());
                e.setBlock(block);
            }
        }
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        Pos spawn = CytonicBedwarsSettings.spawnPlatformCenter;
        if (distance(e.getBlockPosition().x(), spawn.x(), e.getBlockPosition().z(), spawn.z()) > 105.0 || e.getBlockPosition().y() >= 50) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(Msg.whoops("You cannot place blocks that far from the map!"));
        }
    }

    private static double distance(double x1, double x2, double z1, double z2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(z1 - z2, 2));
    }
}

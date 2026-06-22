package net.cytonic.cytonicbedwars.blockHandlers;

import java.util.Random;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.minestom.server.instance.block.BlockHandler;
import net.minestom.server.network.packet.server.play.BlockActionPacket;
import net.minestom.server.sound.SoundEvent;

import net.cytonic.cytonicbedwars.player.BedwarsPlayer;

public class EnderChestBlockHandler implements BlockHandler {

    @Override
    public boolean onInteract(Interaction interaction) {
        if (!(interaction.getPlayer() instanceof BedwarsPlayer player)) return false;
        if (!player.getGame().isStarted()) return false;
        if (player.isSpectator()) return false;
        player.setEnderChestPos(interaction.getBlockPosition());

        player.openInventory(player.getEnderChest());
        interaction.getInstance().playSound(
            Sound.sound(SoundEvent.BLOCK_ENDER_CHEST_OPEN, Sound.Source.BLOCK, 0.5f,
                new Random().nextFloat() * 0.1F + 0.9F), interaction.getBlockPosition());
        interaction.getInstance().sendGroupedPacket(
            new BlockActionPacket(interaction.getBlockPosition(), (byte) 1, (byte) 1, interaction.getBlock()));
        return true;
    }

    @Override
    public Key getKey() {
        return Key.key("minecraft:ender_chest");
    }
}

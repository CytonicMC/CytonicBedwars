package net.cytonic.bedwars.blockHandlers;

import java.util.Random;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.minestom.server.instance.block.BlockHandler;
import net.minestom.server.network.packet.server.play.BlockActionPacket;
import net.minestom.server.sound.SoundEvent;

import net.cytonic.bedwars.game.Team;
import net.cytonic.bedwars.player.BedwarsPlayer;

public class ChestBlockHandler implements BlockHandler {

    @Override
    public boolean onInteract(Interaction interaction) {
        if (!(interaction.getPlayer() instanceof BedwarsPlayer player)) return false;
        if (!player.getGame().isStarted()) return false;
        if (player.isSpectator()) return false;
        Team team = player.getBedwarsTeam();
        if (!team.getTeamChestPos().sameBlock(interaction.getBlockPosition())) return false;

        player.openInventory(team.getTeamChest());
        interaction.getInstance().playSound(
            Sound.sound(SoundEvent.BLOCK_CHEST_OPEN, Sound.Source.MASTER, 0.5f, new Random().nextFloat() * 0.1F + 0.9F),
            interaction.getBlockPosition());
        interaction.getInstance().sendGroupedPacket(
            new BlockActionPacket(interaction.getBlockPosition(), (byte) 1, (byte) 1, interaction.getBlock()));
        return true;
    }

    @Override
    public Key getKey() {
        return Key.key("minecraft:chest");
    }
}

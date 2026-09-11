package net.cytonic.bedwars.blockHandlers;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.sound.Sound.Source;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.BlockHandler;
import net.minestom.server.sound.SoundEvent;

public class BellBlockHandler implements BlockHandler {

    private static final Sound SOUND = Sound.sound(SoundEvent.BLOCK_BELL_USE, Source.BLOCK, 2, 1);

    @Override
    public boolean onInteract(Interaction interaction) {
        Instance instance = interaction.getInstance();
        instance.playSound(SOUND, interaction.getBlockPosition());
        return true;
    }

    @Override
    public Key getKey() {
        return Key.key("minecraft:bell");
    }
}

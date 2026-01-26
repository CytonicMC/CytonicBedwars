package net.cytonic.cytonicbedwars.blockHandlers;

import net.cytonic.cytonicbedwars.data.objects.Team;
import net.cytonic.cytonicbedwars.managers.GameManager;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytonicbedwars.utils.Items;
import net.cytonic.cytosis.Cytosis;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.inventory.InventoryCloseEvent;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.BlockHandler;
import net.minestom.server.item.ItemStack;
import net.minestom.server.network.packet.server.play.BlockActionPacket;
import net.minestom.server.sound.SoundEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class ChestBlockHandler implements BlockHandler {
    @Override
    public boolean onInteract(@NotNull Interaction interaction) {
        if (!Cytosis.CONTEXT.getComponent(GameManager.class).isSTARTED()) return false;
        if (Cytosis.CONTEXT.getComponent(GameManager.class).getSpectators().contains(interaction.getPlayer().getUuid()))
            return false;
        BedwarsPlayer player = (BedwarsPlayer) interaction.getPlayer();
        Team team = Cytosis.CONTEXT.getComponent(GameManager.class).getPlayerTeam(player).orElseThrow();
        if (!team.getChestLocation().sameBlock(interaction.getBlockPosition())) return false;
        EventListener<@NotNull InventoryPreClickEvent> listener = EventListener.of(InventoryPreClickEvent.class, event -> {
            ItemStack item = event.getClickedItem();
            if (item.hasTag(Items.NAMESPACE) && item.getTag(Items.NAMESPACE).equals("DEFAULT_SWORD")) {
                event.setCancelled(true);
            }
        });
        team.getTeamChest().eventNode().addListener(EventListener.of(InventoryCloseEvent.class, event -> {
            Cytosis.CONTEXT.getComponent(InstanceContainer.class).sendGroupedPacket(new BlockActionPacket(interaction.getBlockPosition(), (byte) 1, (byte) 0, interaction.getBlock()));
            Cytosis.CONTEXT.getComponent(InstanceContainer.class).playSound(Sound.sound(SoundEvent.BLOCK_CHEST_CLOSE, Sound.Source.MASTER, 0.5f, new Random().nextFloat() * 0.1F + 0.9F), interaction.getBlockPosition());
        }));
        player.eventNode().addListener(listener);
        player.openInventory(team.getTeamChest());
        team.getTeamChest().addViewer(player);
        Cytosis.CONTEXT.getComponent(InstanceContainer.class).playSound(Sound.sound(SoundEvent.BLOCK_CHEST_OPEN, Sound.Source.MASTER, 0.5f, new Random().nextFloat() * 0.1F + 0.9F), interaction.getBlockPosition());
        Cytosis.CONTEXT.getComponent(InstanceContainer.class).sendGroupedPacket(new BlockActionPacket(interaction.getBlockPosition(), (byte) 1, (byte) 1, interaction.getBlock()));
        return true;
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("minecraft:chest");
    }
}

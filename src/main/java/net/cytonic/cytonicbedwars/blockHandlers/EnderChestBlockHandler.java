package net.cytonic.cytonicbedwars.blockHandlers;

import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytonicbedwars.utils.Items;
import net.kyori.adventure.key.Key;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.inventory.InventoryCloseEvent;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.instance.block.BlockHandler;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.item.ItemStack;
import net.minestom.server.network.packet.server.play.BlockActionPacket;
import org.jetbrains.annotations.NotNull;

public class EnderChestBlockHandler implements BlockHandler {
    @Override
    public boolean onInteract(@NotNull Interaction interaction) {
        if (!CytonicBedWars.getGameManager().isSTARTED()) return false;
        if (CytonicBedWars.getGameManager().getSpectators().contains(interaction.getPlayer().getUuid())) return false;
        BedwarsPlayer player = (BedwarsPlayer) interaction.getPlayer();
        EventListener<InventoryPreClickEvent> listener = EventListener.of(InventoryPreClickEvent.class, event -> {
            ItemStack item = event.getClickedItem();
            if (item.hasTag(Items.NAMESPACE) && item.getTag(Items.NAMESPACE).equals("DEFAULT_SWORD")) {
                event.setCancelled(true);
            }
        });
        player.getEnderChest().eventNode().addListener(EventListener.of(InventoryCloseEvent.class, event -> {
            BedwarsPlayer bedwarsPlayer = (BedwarsPlayer) event.getPlayer();
            bedwarsPlayer.setEnderChest((Inventory) event.getInventory());
            bedwarsPlayer.sendPacket(new BlockActionPacket(interaction.getBlockPosition(), (byte) 1, (byte) 0, interaction.getBlock()));
            bedwarsPlayer.eventNode().removeListener(listener);
        }));
        player.eventNode().addListener(listener);
        player.openEnderChest();
        player.sendPacket(new BlockActionPacket(interaction.getBlockPosition(), (byte) 1, (byte) 1, interaction.getBlock()));
        return true;
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("minecraft:ender_chest");
    }
}

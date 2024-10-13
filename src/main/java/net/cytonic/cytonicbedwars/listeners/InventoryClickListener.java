package net.cytonic.cytonicbedwars.listeners;

import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytonicbedwars.utils.Items;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.utils.MiniMessageTemplate;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.item.ItemStack;
import java.util.Arrays;

public class InventoryClickListener {

    private final CytonicBedWars plugin;
    public InventoryClickListener(CytonicBedWars plugin) {
        this.plugin = plugin;
    }

    public void onInventoryClick(InventoryPreClickEvent event) {
        if (event.getInventory().getTitle().equals(Component.text("Spectate a Player"))) {
            spectatorSelectorMenu(event);
            return;
        }

        if (event.getInventory().getTitle().equals(Component.text("Flight Speed"))) {
            spectatorSpeedMenu(event);
            return;
        }

        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        ItemStack item = event.getClickedItem();
        if (item.toItemNBT().getBoolean(Items.MOVE_BLACKLIST)) {
            event.setCancelled(true);
            if (item.toItemNBT().getBoolean(Items.ALLOWED_SLOTS)) {
                int[] slots = item.toItemNBT().getIntArray(Items.ALLOWED_SLOTS);
                if (Arrays.stream(slots).allMatch(value -> value != event.getSlot())) {
                    event.setClickedItem(ItemStack.AIR);
                    event.setCursorItem(ItemStack.AIR);
                    event.getPlayer().sendMessage(Component.text(STR."Hey! We noticed a blacklisted item in your inventory, so we took it. Sorry! (slot \{event.getSlot()})", NamedTextColor.RED));
                }
            }
        }
    }

    private void spectatorSpeedMenu(InventoryPreClickEvent event) {
        event.setCancelled(true);
        Player player = event.getPlayer();
        ItemStack item = event.getClickedItem();
        String speedString =
                switch (item.toItemNBT().getString("FLIGHT_SPEED")) {
                    case "0.01f" -> "0.1x";
                    case "0.05f" -> "0.5x";
                    case "0.1f" -> "1x";
                    case "0.2f" -> "2x";
                    case "0.5f" -> "5x";
                    default -> "ERROR";
                };
        float speed = item.toItemNBT().getFloat("FLIGHT_SPEED");
        player.setFlyingSpeed(speed);
        player.closeInventory();
        player.sendMessage(MiniMessageTemplate.MM."<GREEN>Your flight speed is now <GOLD>\{speedString}");
    }

    private void spectatorSelectorMenu(InventoryPreClickEvent event) {
        event.setCancelled(true);
        event.getPlayer().teleport(Cytosis.getPlayer(event.getClickedItem().toItemNBT().getString("UUID")).orElseThrow().getPosition());
    }
}

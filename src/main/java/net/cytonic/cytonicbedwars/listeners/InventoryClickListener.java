package net.cytonic.cytonicbedwars.listeners;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.utils.Items;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.entity.GameMode;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.item.ItemComponent;
import net.minestom.server.item.ItemStack;

import java.util.Arrays;

@NoArgsConstructor
public class InventoryClickListener {

    public void onInventoryClick(InventoryPreClickEvent event) {

        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        ItemStack item = event.getClickedItem();
        if (item.has(ItemComponent.CUSTOM_DATA)) {
            if (item.get(ItemComponent.CUSTOM_DATA).nbt().getBoolean(Items.MOVE_BLACKLIST)) {
                event.setCancelled(true);
                if (item.get(ItemComponent.CUSTOM_DATA).nbt().getBoolean(Items.ALLOWED_SLOTS)) {
                    int[] slots = item.get(ItemComponent.CUSTOM_DATA).nbt().getIntArray(Items.ALLOWED_SLOTS);
                    if (Arrays.stream(slots).allMatch(value -> value != event.getSlot())) {
                        event.setClickedItem(ItemStack.AIR);
                        event.setCursorItem(ItemStack.AIR);
                        event.getPlayer().sendMessage(Component.text(STR."Hey! We noticed a blacklisted item in your inventory, so we took it. Sorry! (slot \{event.getSlot()})", NamedTextColor.RED));
                    }
                }
            }
        }
    }
}

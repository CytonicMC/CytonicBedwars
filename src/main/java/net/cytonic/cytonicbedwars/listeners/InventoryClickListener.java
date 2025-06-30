package net.cytonic.cytonicbedwars.listeners;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.utils.Items;
import net.cytonic.cytosis.events.api.Listener;
import net.cytonic.cytosis.utils.Msg;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.minestom.server.entity.GameMode;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.item.ItemStack;

import java.util.Arrays;

@NoArgsConstructor
@SuppressWarnings("unused")
public class InventoryClickListener {

    @Listener
    public void onInventoryClick(InventoryPreClickEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        ItemStack item = event.getClickedItem();
        if (item.hasTag(Items.MOVE_BLACKLIST)) {
            if (item.getTag(Items.MOVE_BLACKLIST)) {
                event.setCancelled(true);
                return;
            }
            int[] slots = ((CompoundBinaryTag) item.getTag(Items.ALLOWED_SLOTS)).getIntArray("allowed_slots");
            if (Arrays.stream(slots).allMatch(value -> value != event.getSlot())) {
                event.getPlayer().getInventory().setCursorItem(ItemStack.AIR);
                event.getPlayer().sendMessage(Msg.redSplash("HEY!", "We noticed a blacklisted item in your inventory, so we took it. Sorry! (slot %d)", event.getSlot()));
            }
        }
    }
}

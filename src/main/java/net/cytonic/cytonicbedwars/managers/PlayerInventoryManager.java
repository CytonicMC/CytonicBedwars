package net.cytonic.cytonicbedwars.managers;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.data.enums.AxeLevel;
import net.cytonic.cytonicbedwars.data.enums.PickaxeLevel;
import net.cytonic.cytonicbedwars.utils.Items;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemComponent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import java.util.Objects;

@NoArgsConstructor
public class PlayerInventoryManager {

    public boolean takeItem(String id, int quantity, Player player) {
        int toRemove = quantity;
        if (itemCount(player, id) >= quantity) {
            int i = 0;
            for (ItemStack stack : player.getInventory().getItemStacks()) {
                if (stack.has(ItemComponent.CUSTOM_DATA)) {
                    if (stack.amount() <= toRemove) { // go ahead to remove the entire stack
                        if (Objects.requireNonNull(stack.get(ItemComponent.CUSTOM_DATA)).nbt().getString(Items.NAMESPACE).equals(id)) {
                            toRemove -= stack.amount();
                            player.getInventory().setItemStack(i, stack.withAmount(0));
                        }

                        if (toRemove == 0) {
                            return true;
                        }
                    } else { // only remove some
                        if (Objects.requireNonNull(stack.get(ItemComponent.CUSTOM_DATA)).nbt().getString(Items.NAMESPACE).equals(id)) {
                            player.getInventory().setItemStack(i, stack.withAmount(stack.amount() - toRemove));
                            return true;
                        }
                    }
                }
                i++;
            }
        } else {
            return false;
        }
        return false;
    }

    public int itemCount(Player player, String id) {
        int count = 0;
        for (ItemStack stack : player.getInventory().getItemStacks()) {
            if (!stack.has(ItemComponent.CUSTOM_DATA)) continue;
            if (Objects.requireNonNull(stack.get(ItemComponent.CUSTOM_DATA)).nbt().getString(Items.NAMESPACE).equals(id)) {
                count += stack.amount();
            }
        }
        return count;
    }

    public boolean hasSpace(Player player) {
        int i = 0;
        for (ItemStack stack : player.getInventory().getItemStacks()) {
            if (i >= 36) return false;
            if (stack.material() == Material.AIR) {
                return true;
            }
            i++;
        }
        return false;
    }

    public boolean setAxe(AxeLevel level, Player player) {
        if (level == AxeLevel.WOODEN) { // doesn't have an axe to remove
            player.getInventory().addItemStack(Items.WOODEN_AXE);
            return true;
        }
        ItemStack[] items = player.getInventory().getItemStacks();
        for (int i = 0; i < player.getInventory().getItemStacks().length; i++) {
            if (items[i].has(ItemComponent.CUSTOM_DATA)) {
                String oldID = AxeLevel.getOrdered(level, -1).getItemID();
                String id = Objects.requireNonNull(items[i].get(ItemComponent.CUSTOM_DATA)).nbt().getString(Items.NAMESPACE);
                if (id.equals(oldID)) {
                    player.getInventory().setItemStack(i, Items.get(level.getItemID()));
                    return true;
                }
            }
        }
        return false;
    }

    public boolean setPickaxe(PickaxeLevel level, Player player) {
        if (level == PickaxeLevel.WOODEN) { // doesn't have an axe to remove
            player.getInventory().addItemStack(Items.WOODEN_PICKAXE);
            return true;
        }
        ItemStack[] items = player.getInventory().getItemStacks();
        for (int i = 0; i < player.getInventory().getItemStacks().length; i++) {
            if (items[i].has(ItemComponent.CUSTOM_DATA)) {
                String oldID = PickaxeLevel.getOrdered(level, -1).getItemID();
                String id = Objects.requireNonNull(items[i].get(ItemComponent.CUSTOM_DATA)).nbt().getString(Items.NAMESPACE);
                if (id.equals(oldID)) {
                    player.getInventory().setItemStack(i, Items.get(level.getItemID()));
                    return true;
                }
            }
        }
        return false;
    }
}

package net.cytonic.cytonicbedwars.managers;

import net.cytonic.cytonicbedwars.data.enums.AxeLevel;
import net.cytonic.cytonicbedwars.data.enums.PickaxeLevel;
import net.cytonic.cytonicbedwars.utils.Items;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

public class PlayerInventoryManager {

    public PlayerInventoryManager() {}

    public boolean takeItem(String id, int quantity, Player player) {
        int toRemove = quantity;
        if (itemCount(player, id) >= quantity) {
            for (ItemStack stack : player.getInventory().getItemStacks()) {
                if (stack.amount() <= toRemove) { // go ahead to remove the entire stack
                    if (stack.toItemNBT().getString(Items.NAMESPACE).equals(id)) {
                        toRemove -= stack.amount();
                        stack = stack.builder().amount(0).build();
                    }

                    if (toRemove == 0) {
                        return true;
                    }
                } else { // only remove some
                    if (stack.toItemNBT().getString(Items.NAMESPACE).equals(id)) {
                        stack = stack.withAmount(stack.amount() - toRemove);
                        return true;
                    }
                }
            }
        } else {
            return false;
        }
        return false;
    }

    public int itemCount(Player player, String id) {
        int count = 0;
        for (ItemStack stack : player.getInventory().getItemStacks()) {
            if (stack.toItemNBT().getString(Items.NAMESPACE).equals(id)) {
                count += stack.amount();
            }
        }
        return count;
    }

    public boolean hasSpace(Player player) {
        for (ItemStack stack : player.getInventory().getItemStacks()) {
            if (stack.material() == Material.AIR) {
                return true;
            }
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
            String oldID = AxeLevel.getOrdered(level, -1).getItemID();
            String id = items[i].toItemNBT().getString(Items.NAMESPACE);
            if (id.equals(oldID)) {
                player.getInventory().setItemStack(i, Items.get(level.getItemID()));
                return true;
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
            String oldID = PickaxeLevel.getOrdered(level, -1).getItemID();
            String id = items[i].toItemNBT().getString(Items.NAMESPACE);
            if (id.equals(oldID)) {
                player.getInventory().setItemStack(i, Items.get(level.getItemID()));
                return true;
            }
        }
        return false;
    }
}

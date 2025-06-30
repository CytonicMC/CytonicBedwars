package net.cytonic.cytonicbedwars.managers;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.data.enums.AxeLevel;
import net.cytonic.cytonicbedwars.data.enums.PickaxeLevel;
import net.cytonic.cytonicbedwars.utils.Items;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;

@NoArgsConstructor
public class PlayerInventoryManager {

    public boolean takeItem(String id, int quantity, Player player) {
        int toRemove = quantity;
        if (itemCount(player, id) >= quantity) {
            int i = 0;
            for (ItemStack stack : player.getInventory().getItemStacks()) {
                if (stack.hasTag(Items.NAMESPACE)) {
                    if (stack.amount() <= toRemove) { // go ahead to remove the entire stack
                        if (stack.getTag(Items.NAMESPACE).equals(id)) {
                            toRemove -= stack.amount();
                            player.getInventory().setItemStack(i, stack.withAmount(0));
                        }

                        if (toRemove == 0) {
                            return true;
                        }
                    } else { // only remove some
                        if (stack.getTag(Items.NAMESPACE).equals(id)) {
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
            if (!stack.hasTag(Items.NAMESPACE)) continue;
            if (stack.getTag(Items.NAMESPACE).equals(id)) {
                count += stack.amount();
            }
        }
        return count;
    }

    public boolean hasSpace(Player player) {
        for (ItemStack stack : player.getInventory().getItemStacks()) {
            if (stack.isAir()) {
                return true;
            }
        }
        return false;
    }

    public void setAxe(AxeLevel level, Player player) {
        if (level == AxeLevel.WOODEN) { // doesn't have an axe to remove
            player.getInventory().addItemStack(Items.WOODEN_AXE);
            return;
        }
        ItemStack[] items = player.getInventory().getItemStacks();
        for (int i = 0; i < player.getInventory().getItemStacks().length; i++) {
            if (items[i].hasTag(Items.NAMESPACE)) {
                String oldID = AxeLevel.getOrdered(level, -1).getItemID();
                String id = items[i].getTag(Items.NAMESPACE);
                if (id.equals(oldID)) {
                    player.getInventory().setItemStack(i, Items.get(level.getItemID()));
                    return;
                }
            }
        }
    }

    public void setPickaxe(PickaxeLevel level, Player player) {
        if (level == PickaxeLevel.WOODEN) { // doesn't have an axe to remove
            player.getInventory().addItemStack(Items.WOODEN_PICKAXE);
            return;
        }
        ItemStack[] items = player.getInventory().getItemStacks();
        for (int i = 0; i < player.getInventory().getItemStacks().length; i++) {
            if (items[1].hasTag(Items.NAMESPACE)) {
                String oldID = PickaxeLevel.getOrdered(level, -1).getItemID();
                String id = items[i].getTag(Items.NAMESPACE);
                if (id.equals(oldID)) {
                    player.getInventory().setItemStack(i, Items.get(level.getItemID()));
                    return;
                }
            }
        }
    }

    public void setSword(ItemStack sword, Player player) {
        takeItem("DEFAULT_SWORD", 1, player);
        player.getInventory().addItemStack(sword);
    }
}

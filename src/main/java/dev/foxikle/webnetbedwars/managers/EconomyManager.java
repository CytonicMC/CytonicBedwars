package dev.foxikle.webnetbedwars.managers;

import dev.foxikle.webnetbedwars.WebNetBedWars;
import dev.foxikle.webnetbedwars.utils.Items;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class EconomyManager {
    private final WebNetBedWars plugin;

    public EconomyManager(WebNetBedWars plugin) {
        this.plugin = plugin;
    }

    public boolean takeItem (String id, int quantity, Player player) {
        int toRemove = quantity;
        if(itemCount(player, id) >= quantity) {
            for (ItemStack stack : player.getInventory().getContents()) {
                if (stack != null) {
                    if (stack.getAmount() <= toRemove) { // go ahead to remove the entire stack
                        if (stack.getItemMeta() != null) {
                            if (stack.getItemMeta().getPersistentDataContainer().getKeys().contains(Items.NAMESPACE)) {
                                if(stack.getItemMeta().getPersistentDataContainer().get(Items.NAMESPACE, PersistentDataType.STRING).equals(id)) {
                                    toRemove -= stack.getAmount();
                                    stack.setAmount(0);
                                }
                            }
                            if (toRemove == 0) {
                                return true;
                            }
                        }
                    } else { // only remove some
                        if (stack.getItemMeta() != null) {
                            if (stack.getItemMeta().getPersistentDataContainer().getKeys().contains(Items.NAMESPACE)) {
                                if(stack.getItemMeta().getPersistentDataContainer().get(Items.NAMESPACE, PersistentDataType.STRING).equals(id)) {
                                    stack.setAmount(stack.getAmount() - toRemove);
                                    return true;
                                }
                            }
                        }
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
        for (ItemStack stack : player.getInventory().getContents()) {
            if (stack != null && stack.getItemMeta() != null) {
                if(stack.getItemMeta().getPersistentDataContainer().getKeys().contains(Items.NAMESPACE)){
                    if(stack.getItemMeta().getPersistentDataContainer().get(Items.NAMESPACE, PersistentDataType.STRING).equals(id)) {
                        count += stack.getAmount();
                    }
                }
            }
        }
        return count;
    }

    public boolean hasSpace(Player player) {
        for (ItemStack stack : player.getInventory().getStorageContents()) {
            if (stack == null || stack.getType() == Material.AIR) {
                return true;
            }
        }
        return false;
    }
}

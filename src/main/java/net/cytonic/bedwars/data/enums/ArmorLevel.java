package net.cytonic.bedwars.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

@Getter
@AllArgsConstructor
public enum ArmorLevel {
    NONE(ItemStack.of(Material.LEATHER_HELMET), ItemStack.of(Material.LEATHER_CHESTPLATE)),
    CHAINMAIL(ItemStack.of(Material.CHAINMAIL_HELMET), ItemStack.of(Material.CHAINMAIL_CHESTPLATE)),
    IRON(ItemStack.of(Material.IRON_HELMET), ItemStack.of(Material.IRON_CHESTPLATE)),
    DIAMOND(ItemStack.of(Material.DIAMOND_HELMET), ItemStack.of(Material.DIAMOND_CHESTPLATE)),
    NETHERITE(ItemStack.of(Material.NETHERITE_HELMET), ItemStack.of(Material.NETHERITE_CHESTPLATE));
    private final ItemStack head;
    private final ItemStack chest;
}

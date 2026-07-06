package net.cytonic.cytonicbedwars.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

@Getter
@AllArgsConstructor
public enum PickaxeLevel {
    NONE(ItemStack.AIR),
    WOODEN(ItemStack.of(Material.WOODEN_PICKAXE)),
    STONE(ItemStack.of(Material.STONE_PICKAXE)),
    IRON(ItemStack.of(Material.IRON_PICKAXE)),
    DIAMOND(ItemStack.of(Material.DIAMOND_PICKAXE));
    private final ItemStack itemStack;

    public static PickaxeLevel getByOrdinal(int index) {
        for (PickaxeLevel level : values()) {
            if (level == NONE) continue;
            if (level.ordinal() == index) {
                return level;
            }
        }
        return NONE;
    }
}

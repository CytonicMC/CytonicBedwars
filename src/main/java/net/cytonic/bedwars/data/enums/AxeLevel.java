package net.cytonic.bedwars.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

@Getter
@AllArgsConstructor
public enum AxeLevel {
    NONE(ItemStack.AIR),
    WOODEN(ItemStack.of(Material.WOODEN_AXE)),
    STONE(ItemStack.of(Material.STONE_AXE)),
    IRON(ItemStack.of(Material.IRON_AXE)),
    DIAMOND(ItemStack.of(Material.DIAMOND_AXE));
    private final ItemStack itemStack;

    public static AxeLevel getByOrdinal(int index) {
        for (AxeLevel level : values()) {
            if (level == NONE) continue;
            if (level.ordinal() == index) {
                return level;
            }
        }
        return NONE;
    }
}

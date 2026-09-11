package net.cytonic.bedwars.data.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minestom.server.item.Material;

@Getter
@AllArgsConstructor
public enum Currency {
    IRON("<grey>Iron", "<gray>", Material.IRON_INGOT, false),
    GOLD("<gold>Gold", "<gold>", Material.GOLD_INGOT, false),
    DIAMOND("<aqua>Diamond", "<aqua>", Material.DIAMOND, true),
    EMERALD("<green>Emerald", "<green>", Material.EMERALD, true);
    @Getter(AccessLevel.NONE)
    private final String name;
    private final String color;
    private final Material material;
    private final boolean canBePlural;

    public String getName(boolean plural) {
        return canBePlural ? (plural ? name + "s" : name) : name;
    }
}

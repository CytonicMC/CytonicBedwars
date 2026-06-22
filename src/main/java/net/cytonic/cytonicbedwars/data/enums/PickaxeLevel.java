package net.cytonic.cytonicbedwars.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PickaxeLevel {
    NONE(""),
    WOODEN("WOODEN_PICKAXE"),
    STONE("STONE_PICKAXE"),
    IRON("IRON_PICKAXE"),
    DIAMOND("DIAMOND_PICKAXE");
    private final String itemID;

    public static PickaxeLevel getByOrdinal(int index) {
        for (PickaxeLevel level : values()) {
            if (level == NONE) continue;
            if (level.ordinal() == index) {
                return level;
            }
        }
        return WOODEN;
    }
}

package net.cytonic.cytonicbedwars.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AxeLevel {
    NONE(""),
    WOODEN("WOODEN_AXE"),
    STONE("STONE_AXE"),
    IRON("IRON_AXE"),
    DIAMOND("DIAMOND_AXE");
    private final String itemID;

    public static AxeLevel getByOrdinal(int index) {
        for (AxeLevel level : values()) {
            if (level == NONE) continue;
            if (level.ordinal() == index) {
                return level;
            }
        }
        return WOODEN;
    }
}

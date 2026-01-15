package net.cytonic.cytonicbedwars.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minestom.server.item.Material;

@Getter
@AllArgsConstructor
public enum PriceType {
    IRON("<grey>Iron", Material.IRON_NUGGET),
    GOLD("<gold>Gold", Material.GOLD_NUGGET),
    DIAMOND("<aqua>Diamond", Material.DIAMOND),
    EMERALD("<green>Emerald", Material.EMERALD);
    private final String name;
    private final Material material;
}

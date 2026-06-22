package net.cytonic.cytonicbedwars.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minestom.server.item.Material;

@Getter
@AllArgsConstructor
public enum ArmorLevel {
    NONE(Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE),
    CHAINMAIL(Material.CHAINMAIL_HELMET, Material.CHAINMAIL_CHESTPLATE),
    IRON(Material.IRON_HELMET, Material.IRON_CHESTPLATE),
    DIAMOND(Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE),
    NETHERITE(Material.NETHERITE_HELMET, Material.NETHERITE_CHESTPLATE);
    private final Material head;
    private final Material chest;
}

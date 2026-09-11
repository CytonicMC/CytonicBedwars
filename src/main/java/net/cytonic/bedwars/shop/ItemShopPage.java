package net.cytonic.bedwars.shop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import net.cytonic.cytosis.utils.Msg;

@Getter
@AllArgsConstructor
public enum ItemShopPage {
    BLOCKS("Blocks", "Building Blocks", Material.BRICKS),
    COMBAT("Combat", "Weapons", Material.DIAMOND_SWORD),
    ARMOR("Armor", Material.DIAMOND_CHESTPLATE),
    TOOLS("Tools", Material.DIAMOND_PICKAXE),
    POTIONS("Potions", Material.BREWING_STAND),
    UTILS("Utilities", Material.FIRE_CHARGE),
    ROTATING("Rotating Items", "</green><red><b>Coming Soon", Material.ENDERMAN_SPAWN_EGG);
    private final String title;
    private final String itemName;
    private final Material material;
    private final ItemStack itemStack;

    ItemShopPage(String title, Material material) {
        this(title, title, material);
    }

    ItemShopPage(String title, String itemName, Material material) {
        this.title = title;
        this.itemName = itemName;
        this.material = material;
        this.itemStack = ItemStack.builder(material).customName(Msg.green(itemName)).build();
    }
}

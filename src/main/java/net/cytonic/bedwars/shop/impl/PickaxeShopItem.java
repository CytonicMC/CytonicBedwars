package net.cytonic.bedwars.shop.impl;

import java.util.List;

import com.google.j2objc.annotations.UsedByReflection;
import net.minestom.server.item.Material;

import net.cytonic.bedwars.data.enums.Currency;
import net.cytonic.bedwars.data.enums.PickaxeLevel;
import net.cytonic.bedwars.shop.ItemShopPage;
import net.cytonic.cytosis.utils.Msg;

@UsedByReflection
public class PickaxeShopItem extends UpgradableShopItem {

    public PickaxeShopItem() {
        super("pickaxe", Msg.mm("Upgradable Pickaxe"), List.of(),
            List.of(
                new Tier(Msg.mm("Wooden Pickaxe (Efficiency I)"), 10, Currency.IRON, Material.WOODEN_PICKAXE),
                new Tier(Msg.mm("Iron Pickaxe (Efficiency II)"), 20, Currency.IRON, Material.STONE_PICKAXE),
                new Tier(Msg.mm("Golden Pickaxe (Efficiency III, Sharpness II)"), 3, Currency.GOLD,
                    Material.GOLDEN_PICKAXE),
                new Tier(Msg.mm("Diamond Pickaxe (Efficiency III)"), 6, Currency.GOLD, Material.DIAMOND_PICKAXE)
            ),
            player -> player.getPickaxeLevel().ordinal(),
            (player, index) -> player.setPickaxeLevel(PickaxeLevel.getByOrdinal(index)),
            ItemShopPage.TOOLS, 22);
    }

    @Override
    public boolean isSameType(Material material) {
        return material == Material.WOODEN_PICKAXE ||
            material == Material.STONE_PICKAXE ||
            material == Material.GOLDEN_PICKAXE ||
            material == Material.DIAMOND_PICKAXE;
    }
}

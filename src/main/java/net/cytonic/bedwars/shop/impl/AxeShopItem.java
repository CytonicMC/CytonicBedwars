package net.cytonic.bedwars.shop.impl;

import java.util.List;

import com.google.j2objc.annotations.UsedByReflection;
import net.kyori.adventure.text.Component;
import net.minestom.server.item.Material;

import net.cytonic.bedwars.data.enums.AxeLevel;
import net.cytonic.bedwars.data.enums.Currency;
import net.cytonic.bedwars.shop.ItemShopPage;
import net.cytonic.cytosis.utils.Msg;

@UsedByReflection
public class AxeShopItem extends UpgradableShopItem {

    public AxeShopItem() {
        super("axe", Msg.mm("Upgradable Axe"), List.of(
                Msg.mm("This is an upgradable item."),
                Msg.mm("It will lose 1 tier upon death!"),
                Component.empty(),
                Msg.mm("You will permanently respawn with at"),
                Msg.mm("least the lowest tier.")),
            List.of(
                new Tier(Msg.mm("Wooden Axe (Efficiency I)"), 10, Currency.IRON, Material.WOODEN_AXE),
                new Tier(Msg.mm("Stone Axe (Efficiency I)"), 20, Currency.IRON, Material.STONE_AXE),
                new Tier(Msg.mm("Golden Axe (Efficiency II)"), 3, Currency.GOLD, Material.GOLDEN_AXE),
                new Tier(Msg.mm("Diamond Axe (Efficiency III)"), 6, Currency.GOLD, Material.DIAMOND_AXE)
            ),
            player -> player.getAxeLevel().ordinal(),
            (player, index) -> player.setAxeLevel(AxeLevel.getByOrdinal(index)),
            ItemShopPage.TOOLS, 21);
    }

    @Override
    public boolean isSameType(Material material) {
        return material == Material.WOODEN_AXE ||
            material == Material.STONE_AXE ||
            material == Material.GOLDEN_AXE ||
            material == Material.DIAMOND_AXE;
    }
}

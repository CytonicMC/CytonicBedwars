package net.cytonic.bedwars.shop.impl;

import java.util.List;

import com.google.j2objc.annotations.UsedByReflection;
import net.minestom.server.item.Material;

import net.cytonic.bedwars.data.enums.Currency;
import net.cytonic.bedwars.player.BedwarsPlayer;
import net.cytonic.bedwars.shop.ItemShopPage;
import net.cytonic.bedwars.shop.ShopItem;
import net.cytonic.cytosis.utils.Msg;

@UsedByReflection
public class ShearsShopItem extends ShopItem {

    public ShearsShopItem() {
        super("shears", Msg.mm("Shears"), List.of(), 24, Currency.IRON, 1, Material.SHEARS, ItemShopPage.TOOLS, 23);
    }

    @Override
    public void onPurchase(BedwarsPlayer player) {
        if (player.hasShears()) {
            player.sendMessage(Msg.red("You already purchased a pair of shears!"));
            return;
        }
        player.getInventory().addItemStack(getDisplay());
        player.setShears(true);
    }

    @Override
    public boolean doesNotHave(BedwarsPlayer player) {
        return !player.hasShears();
    }
}

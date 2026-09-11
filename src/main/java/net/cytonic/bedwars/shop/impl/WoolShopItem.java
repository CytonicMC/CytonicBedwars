package net.cytonic.bedwars.shop.impl;

import java.util.List;

import com.google.j2objc.annotations.UsedByReflection;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import net.cytonic.bedwars.data.enums.Currency;
import net.cytonic.bedwars.player.BedwarsPlayer;
import net.cytonic.bedwars.shop.ItemShopPage;
import net.cytonic.bedwars.shop.ShopItem;
import net.cytonic.cytosis.utils.Msg;

@UsedByReflection
public class WoolShopItem extends ShopItem {

    public WoolShopItem() {
        super("wool", Msg.mm("Wool"),
            List.of(Msg.mm("Great for bridging across islands."), Msg.mm("Turns into your team's color.")),
            4, Currency.IRON, 16, Material.WHITE_WOOL,
            ItemShopPage.BLOCKS, 21);
    }

    @Override
    public void onPurchase(BedwarsPlayer player) {
        player.getInventory().addItemStack(ItemStack.of(player.getBedwarsTeam().getWoolType()).withAmount(16));
    }
}

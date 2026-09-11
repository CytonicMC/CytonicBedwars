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
public class TerracottaShopItem extends ShopItem {

    public TerracottaShopItem() {
        super("terracotta", Msg.mm("Terracotta"),
            List.of(Msg.mm("Basic block to defend your bed.")),
            12, Currency.IRON, 16, Material.TERRACOTTA,
            ItemShopPage.BLOCKS, 30);
    }

    @Override
    public void onPurchase(BedwarsPlayer player) {
        player.getInventory().addItemStack(ItemStack.of(player.getBedwarsTeam().getTerracottaType()).withAmount(16));
    }
}

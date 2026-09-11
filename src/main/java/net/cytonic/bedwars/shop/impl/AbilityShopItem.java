package net.cytonic.bedwars.shop.impl;

import java.util.List;

import net.kyori.adventure.text.Component;
import net.minestom.server.item.Material;

import net.cytonic.bedwars.data.enums.Currency;
import net.cytonic.bedwars.itemAbility.ItemAbility;
import net.cytonic.bedwars.player.BedwarsPlayer;
import net.cytonic.bedwars.shop.ItemShopPage;
import net.cytonic.bedwars.shop.ShopItem;
import net.cytonic.protocol.utils.ExcludeFromIndex;

@ExcludeFromIndex
public class AbilityShopItem extends ShopItem {

    public AbilityShopItem(String id, Component name, List<Component> description, int price,
        Currency currency, Material display, ItemShopPage itemShopPage, int slot) {
        super(id, name, description, price, currency, 1, display, itemShopPage, slot);
    }

    @Override
    public void onPurchase(BedwarsPlayer player) {
        player.getInventory().addItemStack(getDisplay().withTag(ItemAbility.TAG, getId()));
    }
}

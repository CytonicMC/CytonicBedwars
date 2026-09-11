package net.cytonic.bedwars.shop.impl;

import java.util.List;
import java.util.function.Function;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.minestom.server.item.ItemStack;

import net.cytonic.bedwars.data.enums.Currency;
import net.cytonic.bedwars.player.BedwarsPlayer;
import net.cytonic.bedwars.shop.ItemShopPage;
import net.cytonic.bedwars.shop.ShopItem;
import net.cytonic.protocol.utils.ExcludeFromIndex;

@Getter
@ExcludeFromIndex
public abstract class SinglePurchaseShopItem extends ShopItem {

    private final Function<BedwarsPlayer, Boolean> hasPurchased;

    public SinglePurchaseShopItem(String id, Component name,
        List<Component> description, int price,
        Currency currency, int amount, ItemStack display,
        ItemShopPage itemShopPage, int slot, Function<BedwarsPlayer, Boolean> hasPurchased) {
        super(id, name, description, price, currency, amount, display, itemShopPage, slot);
        this.hasPurchased = hasPurchased;
    }
}

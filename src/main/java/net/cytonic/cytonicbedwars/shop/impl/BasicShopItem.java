package net.cytonic.cytonicbedwars.shop.impl;

import java.util.List;

import net.kyori.adventure.text.Component;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import net.cytonic.cytonicbedwars.data.enums.Currency;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytonicbedwars.shop.ItemShopPage;
import net.cytonic.cytonicbedwars.shop.ShopItem;
import net.cytonic.protocol.utils.ExcludeFromIndex;

@ExcludeFromIndex
public class BasicShopItem extends ShopItem {

    public BasicShopItem(String id, Component name, List<Component> description, int cost, Currency currency,
        int amount, Material material, ItemShopPage itemShopPage, int slot) {
        super(id, name, description, cost, currency, amount, material, itemShopPage, slot);
    }

    public BasicShopItem(String id, Component name, List<Component> description, int cost, Currency currency,
        int amount, ItemStack display, ItemShopPage itemShopPage, int slot) {
        super(id, name, description, cost, currency, amount, display, itemShopPage, slot);
    }

    @Override
    public void onPurchase(BedwarsPlayer player) {
        player.getInventory().addItemStack(this.getDisplay().withAmount(this.getAmount()));
    }
}

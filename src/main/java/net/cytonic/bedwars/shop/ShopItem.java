package net.cytonic.bedwars.shop;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import net.cytonic.bedwars.data.enums.Currency;
import net.cytonic.bedwars.player.BedwarsPlayer;

@Getter
@AllArgsConstructor
public abstract class ShopItem {

    private final String id;
    private final Component name;
    private final List<Component> description;
    private final int price;
    private final Currency currency;
    private final int amount;
    private final ItemStack display;
    private final ItemShopPage itemShopPage;
    private final int slot;

    public ShopItem(String id, Component name, List<Component> description, int price, Currency currency,
        int amount, Material display, ItemShopPage itemShopPage, int slot) {
        this(id, name, description, price, currency, amount, ItemStack.builder(display).hideExtraTooltip().build(),
            itemShopPage, slot);
    }

    public abstract void onPurchase(BedwarsPlayer player);

    public boolean doesNotHave(BedwarsPlayer player) {
        return false;
    }

    public void handlePurchase(BedwarsPlayer player) {
        player.removeItems(currency.getMaterial(), price);
        onPurchase(player);
    }
}

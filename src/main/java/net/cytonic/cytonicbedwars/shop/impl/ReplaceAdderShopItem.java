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
public class ReplaceAdderShopItem extends ShopItem {

    private final Material replacementMaterial;

    public ReplaceAdderShopItem(String id, Component name, List<Component> description, int cost, Currency currency,
        Material material, ItemShopPage itemShopPage, int slot) {
        super(id, name, description, cost, currency, 1, material, itemShopPage, slot);
        this.replacementMaterial = Material.WOODEN_SWORD;
    }

    public ReplaceAdderShopItem(String id, Component name, List<Component> description, int cost, Currency currency,
        Material material, Material replacementMaterial, ItemShopPage itemShopPage, int slot) {
        super(id, name, description, cost, currency, 1, material, itemShopPage, slot);
        this.replacementMaterial = replacementMaterial;
    }

    public ReplaceAdderShopItem(String id, Component name, List<Component> description, int cost, Currency currency,
        ItemStack display, Material replacementMaterial, ItemShopPage itemShopPage, int slot) {
        super(id, name, description, cost, currency, 1, display, itemShopPage, slot);
        this.replacementMaterial = replacementMaterial;
    }

    @Override
    public void onPurchase(BedwarsPlayer player) {
        var inventory = player.getInventory();
        for (int i = 0; i < inventory.getSize(); i++) {
            var item = inventory.getItemStack(i);
            if (item.material().key().equals(replacementMaterial.key())) {
                inventory.setItemStack(i, getDisplay());
                return;
            }
        }

        inventory.addItemStack(getDisplay());
    }
}
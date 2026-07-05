package net.cytonic.cytonicbedwars.shop.impl;

import java.util.ArrayList;
import java.util.List;

import net.kyori.adventure.text.Component;
import net.minestom.server.inventory.PlayerInventory;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import net.cytonic.cytonicbedwars.data.enums.Currency;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytonicbedwars.shop.ItemShopPage;
import net.cytonic.cytonicbedwars.shop.ShopItem;
import net.cytonic.protocol.utils.ExcludeFromIndex;

@ExcludeFromIndex
public class ReplaceAdderShopItem extends ShopItem {

    private final List<Material> replacementMaterials;

    public ReplaceAdderShopItem(String id, Component name, List<Component> description, int cost, Currency currency,
        Material material, ItemShopPage itemShopPage, int slot) {
        super(id, name, description, cost, currency, 1, material, itemShopPage, slot);
        replacementMaterials = new ArrayList<>();
    }

    public ReplaceAdderShopItem(String id, Component name, List<Component> description, int cost, Currency currency,
        Material material, List<Material> replacementMaterials, ItemShopPage itemShopPage, int slot) {
        super(id, name, description, cost, currency, 1, material, itemShopPage, slot);
        this.replacementMaterials = replacementMaterials;
    }

    public ReplaceAdderShopItem(String id, Component name, List<Component> description, int cost, Currency currency,
        ItemStack display, List<Material> replacementMaterials, ItemShopPage itemShopPage, int slot) {
        super(id, name, description, cost, currency, 1, display, itemShopPage, slot);
        this.replacementMaterials = replacementMaterials;
    }

    @Override
    public void onPurchase(BedwarsPlayer player) {
        PlayerInventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItemStack(i);
            if (replacementMaterials.contains(item.material())) {
                inventory.setItemStack(i, getDisplay());
                return;
            }
        }
        inventory.addItemStack(getDisplay());
    }
}
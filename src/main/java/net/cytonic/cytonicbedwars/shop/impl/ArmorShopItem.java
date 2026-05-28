package net.cytonic.cytonicbedwars.shop.impl;

import java.util.List;

import net.kyori.adventure.text.Component;
import net.minestom.server.entity.EquipmentSlot;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import net.cytonic.cytonicbedwars.data.enums.ArmorLevel;
import net.cytonic.cytonicbedwars.data.enums.Currency;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytonicbedwars.shop.ItemShopPage;
import net.cytonic.cytonicbedwars.shop.ShopItem;
import net.cytonic.protocol.utils.ExcludeFromIndex;

@ExcludeFromIndex
public class ArmorShopItem extends ShopItem {

    private final Material boots;
    private final Material leggings;
    private final ArmorLevel armorLevel;

    public ArmorShopItem(String id, Component name, List<Component> description, int price, Currency currency,
        Material boots, Material leggings, ArmorLevel armorLevel, int slot) {
        super(id, name, description, price, currency, 1, boots, ItemShopPage.ARMOR, slot);
        this.boots = boots;
        this.leggings = leggings;
        this.armorLevel = armorLevel;
    }

    @Override
    public void onPurchase(BedwarsPlayer player) {
        player.setEquipment(EquipmentSlot.BOOTS, ItemStack.of(boots));
        player.setEquipment(EquipmentSlot.LEGGINGS, ItemStack.of(leggings));
        player.setArmorLevel(armorLevel);
    }

    @Override
    public boolean doesNotHave(BedwarsPlayer player) {
        return player.getArmorLevel() == armorLevel;
    }
}

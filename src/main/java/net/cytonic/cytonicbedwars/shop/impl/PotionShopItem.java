package net.cytonic.cytonicbedwars.shop.impl;

import java.util.List;

import net.kyori.adventure.text.Component;
import net.minestom.server.component.DataComponents;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.component.PotionContents;
import net.minestom.server.potion.CustomPotionEffect;
import net.minestom.server.potion.CustomPotionEffect.Settings;
import net.minestom.server.potion.PotionEffect;

import net.cytonic.cytonicbedwars.data.enums.Currency;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytonicbedwars.shop.ItemShopPage;
import net.cytonic.protocol.utils.ExcludeFromIndex;

@ExcludeFromIndex
public class PotionShopItem extends BasicShopItem {

    private final PotionEffect potionEffect;
    private final int duration;
    private final int amplifier;

    public PotionShopItem(String id, Component name,
        List<Component> description, int cost,
        Currency currency, Material material,
        ItemShopPage itemShopPage, int slot, PotionEffect potionEffect, int duration,
        int amplifier) {
        super(id, name, description, cost, currency, 1, material, itemShopPage, slot);
        this.potionEffect = potionEffect;
        this.duration = duration;
        this.amplifier = amplifier;
    }

    @Override
    public void onPurchase(BedwarsPlayer player) {
        CustomPotionEffect effect = new CustomPotionEffect(potionEffect,
            new Settings(amplifier, duration, false, false, true, null));
        ItemStack itemStack = ItemStack.builder(Material.POTION)
            .set(DataComponents.POTION_CONTENTS, new PotionContents(effect))
            .build();
        player.getInventory().addItemStack(itemStack);
    }
}

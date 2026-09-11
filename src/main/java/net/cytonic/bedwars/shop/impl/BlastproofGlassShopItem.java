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
public class BlastproofGlassShopItem extends ShopItem {

    public BlastproofGlassShopItem() {
        super("blast_proof_glass", Msg.mm("Blast-Proof Glass"), List.of(), 4, Currency.IRON, 16, Material.GLASS,
            ItemShopPage.BLOCKS, 22);
    }

    @Override
    public void onPurchase(BedwarsPlayer player) {
        player.getInventory().addItemStack(ItemStack.of(player.getBedwarsTeam().getGlassType()).withAmount(16));
    }
}

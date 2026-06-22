package net.cytonic.cytonicbedwars.shop.impl;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import net.cytonic.cytonicbedwars.data.enums.Currency;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytonicbedwars.shop.ItemShopPage;
import net.cytonic.cytonicbedwars.shop.ShopItem;
import net.cytonic.protocol.utils.ExcludeFromIndex;

@ExcludeFromIndex
public abstract class UpgradableShopItem extends ShopItem {

    @Getter
    private final List<Tier> tiers;
    private final Function<BedwarsPlayer, Integer> getter;
    private final BiConsumer<BedwarsPlayer, Integer> setter;

    public UpgradableShopItem(String id, Component name, List<Component> description, List<Tier> tiers,
        Function<BedwarsPlayer, Integer> getLevelSupplier, BiConsumer<BedwarsPlayer, Integer> setter,
        ItemShopPage itemShopPage, int slot) {
        Tier firstTier = tiers.getFirst();
        super(id, name, description, firstTier.price(), firstTier.currency(), 1, firstTier.material(),
            itemShopPage, slot);
        this.tiers = tiers;
        this.getter = getLevelSupplier;
        this.setter = setter;
    }

    public Tier getTier(int index) {
        if (index >= 0 && index < tiers.size()) {
            return tiers.get(index);
        }
        return tiers.getLast();
    }

    public int getNextLevel(BedwarsPlayer player) {
        return getter.apply(player);
    }

    public Tier getNextTier(BedwarsPlayer player) {
        return getTier(getNextLevel(player));
    }

    public int getPrice() {
        return 0;
    }

    @Override
    public void onPurchase(BedwarsPlayer player) {
        int levelToGive = getNextLevel(player);
        Tier tier = getTier(levelToGive);

        for (int i = 0; i < player.getInventory().getSize(); i++) {
            ItemStack stack = player.getInventory().getItemStack(i);
            if (isSameType(stack.material())) {
                player.getInventory().setItemStack(i, ItemStack.AIR);
            }
        }

        player.getInventory().addItemStack(ItemStack.of(tier.material()));
        setter.accept(player, levelToGive + 1);
    }

    @Override
    public void handlePurchase(BedwarsPlayer player) {
        int nextLevel = getNextLevel(player);
        if (nextLevel >= tiers.size()) {
            return;//maxed out
        }
        Tier tier = getTier(nextLevel);
        player.removeItems(tier.currency().getMaterial(), tier.price);

        onPurchase(player);
    }

    public abstract boolean isSameType(Material material);

    public record Tier(Component name, int price, Currency currency, Material material) {

    }
}

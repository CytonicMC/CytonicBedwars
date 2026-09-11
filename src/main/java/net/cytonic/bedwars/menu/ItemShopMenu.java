package net.cytonic.bedwars.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import me.devnatan.inventoryframework.context.OpenContext;
import me.devnatan.inventoryframework.context.RenderContext;
import me.devnatan.inventoryframework.state.MutableState;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.entity.EquipmentSlot;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import net.cytonic.bedwars.player.BedwarsPlayer;
import net.cytonic.bedwars.shop.ItemShopItemRegistry;
import net.cytonic.bedwars.shop.ItemShopPage;
import net.cytonic.bedwars.shop.ShopItem;
import net.cytonic.bedwars.shop.impl.SinglePurchaseShopItem;
import net.cytonic.bedwars.shop.impl.UpgradableShopItem;
import net.cytonic.bedwars.shop.impl.UpgradableShopItem.Tier;
import net.cytonic.cytosis.menus.MenuUtils;
import net.cytonic.cytosis.utils.Msg;
import net.cytonic.minestomInventoryFramework.context.Context;

public class ItemShopMenu extends View {

    private static final ItemStack selectedPage = ItemStack.builder(Material.LIME_STAINED_GLASS_PANE).build();
    private static final List<List<Material>> TIERED_ITEM_GROUPS = List.of(
        List.of(Material.CHAINMAIL_BOOTS, Material.IRON_BOOTS,
            Material.DIAMOND_BOOTS, Material.NETHERITE_BOOTS)
    );
    private final MutableState<ItemShopPage> itemShopPageState = mutableState(ItemShopPage.BLOCKS);

    @Override
    public void onInit(ViewConfigBuilder config) {
        config.cancelInteractions();
        config.size(5);
    }

    @Override
    public void onOpen(OpenContext context) {
        context.modifyConfig().title(Msg.mm("Item Shop ➜ %s", itemShopPageState.get(context).getTitle()));
    }

    @Override
    public void onUpdate(Context context) {
        context.updateTitleForPlayer(Msg.mm("Item Shop ➜ %s", itemShopPageState.get(context).getTitle()));
    }

    @Override
    public void onFirstRender(RenderContext context) {
        context.unsetSlot().onRender(slotRenderContext -> {
            slotRenderContext.setItem(selectedPage);
            slotRenderContext.setSlot(itemShopPageState.get(slotRenderContext).ordinal() + 10);
        });
        for (ItemShopPage value : ItemShopPage.values()) {
            context.slot(value.ordinal() + 1, value.getItemStack()).onClick(slotClickContext ->
                itemShopPageState.set(value, slotClickContext)).updateOnClick();

            ItemShopItemRegistry.REGISTRY.getOrDefault(value, new HashMap<>()).forEach((_, shopItem) ->
                context.slot(shopItem.getSlot(), shopItem.getDisplay()).updateOnClick()
                    .displayIf(displayContext -> value.equals(itemShopPageState.get(displayContext)))
                    .onClick(slotClickContext -> {
                        BedwarsPlayer player = (BedwarsPlayer) slotClickContext.getPlayer();
                        if (shopItem instanceof UpgradableShopItem upgradableShopItem) {
                            if (upgradableShopItem.getNextLevel(player) >= upgradableShopItem.getTiers().size()) {
                                player.sendMessage(
                                    Msg.red("You have already purchased the maximum tier of this item!"));
                                return;
                            }
                            Tier nextTier = upgradableShopItem.getNextTier(player);
                            if (!hasPlayerEnoughCurrencyForTier(player, nextTier)) {
                                int owned = Arrays.stream(player.getInventory().getItemStacks())
                                    .filter(s -> s.material() == nextTier.currency().getMaterial())
                                    .mapToInt(ItemStack::amount)
                                    .sum();
                                int needed = nextTier.price() - owned;
                                playErrorSound(player);
                                player.sendMessage(
                                    Msg.red("You don't have enough %s! Needed %d more!",
                                        nextTier.currency().getName(nextTier.price() > 1),
                                        needed));
                                return;
                            }
                            upgradableShopItem.handlePurchase(player);
                            player.sendMessage(Msg.green("You purchased ").append(nextTier.name()).append(Msg.mm("!")));
                            playBuySound(player);
                            slotClickContext.update();
                            return;
                        }

                        if (shopItem instanceof SinglePurchaseShopItem singlePurchaseShopItem) {
                            if (singlePurchaseShopItem.getHasPurchased().apply(player)) {
                                playErrorSound(player);
                                player.sendMessage(Msg.red("You have already purchased this item!"));
                                return;
                            }
                            singlePurchaseShopItem.onPurchase(player);
                            playBuySound(player);
                            slotClickContext.update();
                            return;
                        }

                        if (!hasPlayerEnoughCurrency(player, shopItem)) {
                            playErrorSound(player);
                            player.sendMessage(Msg.red("You don't have enough %s!",
                                shopItem.getCurrency().getName(shopItem.getPrice() > 1)));
                            return;
                        }
                        if (shopItem.doesNotHave(player)) {
                            playErrorSound(player);
                            player.sendMessage(Msg.red("You already have the highest tier available!"));
                            return;
                        }

                        if (hasBetterItem(player, shopItem.getDisplay().material())) {
                            playErrorSound(player);
                            player.sendMessage(Msg.red("You already have a better item!"));
                            return;
                        }
                        shopItem.handlePurchase(player);
                        playBuySound(player);
                        slotClickContext.update();
                    }).onRender(slotRenderContext -> {
                        BedwarsPlayer player = (BedwarsPlayer) slotRenderContext.getPlayer();
                        if (shopItem instanceof UpgradableShopItem upgradableShopItem) {
                            int nextLevel = upgradableShopItem.getNextLevel(player);
                            Tier nextTier = upgradableShopItem.getNextTier(player);
                            boolean hasEnough = hasPlayerEnoughCurrencyForTier(player, nextTier);

                            List<Component> lore = new ArrayList<>();
                            lore.add(Msg.grey("Cost: %s %s", nextTier.currency().getColor() + nextTier.price(),
                                nextTier.currency().getName(nextTier.price() > 1)));
                            lore.add(Component.empty());
                            if (!upgradableShopItem.getDescription().isEmpty()) {
                                lore.addAll(
                                    upgradableShopItem.getDescription().stream().map(it -> it.color(NamedTextColor.GRAY))
                                        .toList());
                                lore.add(Component.empty());
                            }
                            if (nextLevel >= upgradableShopItem.getTiers().size()) {
                                lore.add(Msg.red("You have already purchased the maximum tier of this item!"));
                            } else if (hasEnough) {
                                lore.add(Msg.green("Click to buy!"));
                            } else {
                                lore.add(Msg.red("You don't have enough %s<red>!",
                                    nextTier.currency().getName(nextTier.price() > 1)));
                            }
                            ItemStack itemStack = ItemStack.builder(nextTier.material())
                                .customName(hasEnough ? nextTier.name().color(NamedTextColor.GREEN)
                                    : nextTier.name().color(NamedTextColor.RED))
                                .lore(lore).build();
                            slotRenderContext.setItem(itemStack);
                            return;
                        }

                        boolean hasEnough = hasPlayerEnoughCurrency(player, shopItem);
                        List<Component> lore = new ArrayList<>();
                        lore.add(Msg.grey("Cost: %s %s", shopItem.getCurrency().getColor() + shopItem.getPrice(),
                            shopItem.getCurrency().getName(shopItem.getPrice() > 1)));
                        lore.add(Component.empty());
                        if (!shopItem.getDescription().isEmpty()) {
                            lore.addAll(
                                shopItem.getDescription().stream().map(it -> it.color(NamedTextColor.GRAY))
                                    .toList());
                            lore.add(Component.empty());
                        }
                        if (!hasEnough) {
                            lore.add(
                                Msg.red("You don't have enough %s<red>!",
                                    shopItem.getCurrency().getName(shopItem.getPrice() > 1)));
                        } else if (shopItem.doesNotHave(player)) {
                            lore.add(Msg.green("UNLOCKED"));
                        } else {
                            lore.add(Msg.green("Click to buy!"));
                        }

                        Component name = hasEnough ? shopItem.getName().color(NamedTextColor.GREEN)
                            : shopItem.getName().color(NamedTextColor.RED);
                        slotRenderContext.setItem(
                            shopItem.getDisplay().builder().customName(name).lore(lore).build());
                    }));
        }
        for (int i = 0; i < 45; i++) {
            int finalI = i;
            context.availableSlot(MenuUtils.BORDER)
                .hideIf(displayContext ->
                    ItemShopItemRegistry.REGISTRY
                        .getOrDefault(itemShopPageState.get(displayContext), new HashMap<>())
                        .containsKey(finalI) ||
                        Arrays.stream(ItemShopPage.values()).map(ItemShopPage::ordinal)
                            .anyMatch(it -> finalI == 1 + it)
                        || itemShopPageState.get(displayContext).ordinal() + 10 == finalI);
        }
    }

    private static boolean hasPlayerEnoughCurrency(BedwarsPlayer player, ShopItem shopItem) {
        Material currencyMaterial = shopItem.getCurrency().getMaterial();

        int playerAmount = 0;
        for (ItemStack item : player.getInventory().getItemStacks()) {
            if (item.material() == currencyMaterial) {
                playerAmount += item.amount();
            }
        }

        return playerAmount >= shopItem.getPrice();
    }

    private static boolean hasPlayerEnoughCurrencyForTier(BedwarsPlayer player, Tier tier) {
        Material cur = tier.currency().getMaterial();
        int have = 0;
        for (ItemStack it : player.getInventory().getItemStacks()) {
            if (it.material() == cur) have += it.amount();
        }
        return have >= tier.price();
    }

    private static void playErrorSound(BedwarsPlayer player) {
        player.playSound(Sound.sound(Key.key("entity.villager.no"), Sound.Source.MASTER, 1.0f, 1.0f));
    }

    private static void playBuySound(BedwarsPlayer player) {
        player.playSound(
            Sound.sound(Key.key("minecraft:entity.experience_orb.pickup"), Sound.Source.MASTER, 1.0f, 1.0f));
    }

    private static boolean hasBetterItem(Player player, Material materialToBuy) {
        for (List<Material> group : TIERED_ITEM_GROUPS) {
            if (!group.contains(materialToBuy)) {
                continue;
            }

            int tierToBuy = group.indexOf(materialToBuy);
            for (ItemStack stack : player.getInventory().getItemStacks()) {
                if (group.contains(stack.material()) && group.indexOf(stack.material()) > tierToBuy) {
                    return true;
                }
            }
            for (ItemStack stack : List.of(
                player.getEquipment(EquipmentSlot.BOOTS),
                player.getEquipment(EquipmentSlot.LEGGINGS),
                player.getEquipment(EquipmentSlot.CHESTPLATE),
                player.getEquipment(EquipmentSlot.HELMET))) {
                if (group.contains(stack.material()) && group.indexOf(stack.material()) > tierToBuy) {
                    return true;
                }
            }
            return false;
        }

        return false;
    }
}

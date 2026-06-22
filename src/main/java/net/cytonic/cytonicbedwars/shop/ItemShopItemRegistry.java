package net.cytonic.cytonicbedwars.shop;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import net.minestom.server.component.DataComponents;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.component.EnchantmentList;
import net.minestom.server.item.enchant.Enchantment;
import net.minestom.server.potion.PotionEffect;
import org.jboss.jandex.DotName;

import net.cytonic.cytonicbedwars.data.enums.ArmorLevel;
import net.cytonic.cytonicbedwars.data.enums.Currency;
import net.cytonic.cytonicbedwars.shop.impl.ArmorShopItem;
import net.cytonic.cytonicbedwars.shop.impl.BasicShopItem;
import net.cytonic.cytonicbedwars.shop.impl.PotionShopItem;
import net.cytonic.cytonicbedwars.shop.impl.ReplaceAdderShopItem;
import net.cytonic.cytosis.logging.Logger;
import net.cytonic.cytosis.utils.Msg;
import net.cytonic.cytosis.utils.Utils;
import net.cytonic.protocol.utils.ExcludeFromIndex;
import net.cytonic.protocol.utils.IndexHolder;

public class ItemShopItemRegistry {

    @Getter
    private static final Map<ItemShopPage, Map<Integer, ShopItem>> itemMap = new HashMap<>();

    static {
        IndexHolder.get().getAllKnownSubclasses(ShopItem.class).stream()
            .filter(ci -> ci.name().startsWith(DotName.createSimple("net.cytonic")))
            .filter(ci -> !ci.hasAnnotation(ExcludeFromIndex.class))
            .forEach(ci -> {
                try {
                    Class<?> clazz = Utils.loadClass(ci.name().toString());
                    Constructor<?> constructor = clazz.getDeclaredConstructor();
                    constructor.setAccessible(true);
                    ShopItem instance = (ShopItem) constructor.newInstance();
                    itemMap.computeIfAbsent(instance.getItemShopPage(), _ -> new HashMap<>())
                        .put(instance.getSlot(), instance);
                } catch (Exception e) {
                    Logger.error("An error occurred whilst loading menu views!", e);
                }
            });

        itemMap.computeIfAbsent(ItemShopPage.ARMOR, _ -> new HashMap<>()).put(22,
            new ArmorShopItem("chainmail_armor", Msg.mm("Permanent Chainmail Armor"), List.of(), 24, Currency.IRON,
                Material.CHAINMAIL_BOOTS, Material.CHAINMAIL_LEGGINGS,
                ArmorLevel.CHAINMAIL, 22));
        itemMap.get(ItemShopPage.ARMOR).put(30,
            new ArmorShopItem("iron_armor", Msg.mm("Permanent Iron Armor"), List.of(), 12, Currency.GOLD,
                Material.IRON_BOOTS, Material.IRON_LEGGINGS,
                ArmorLevel.IRON, 30));
        itemMap.get(ItemShopPage.ARMOR).put(31,
            new ArmorShopItem("diamond_armor", Msg.mm("Permanent Diamond Armor"), List.of(), 6, Currency.EMERALD,
                Material.DIAMOND_BOOTS, Material.DIAMOND_LEGGINGS,
                ArmorLevel.DIAMOND, 31));
        itemMap.get(ItemShopPage.ARMOR).put(32,
            new ArmorShopItem("netherite_armor", Msg.mm("Permanent Netherite Armor"), List.of(), 16, Currency.EMERALD,
                Material.NETHERITE_BOOTS, Material.NETHERITE_LEGGINGS,
                ArmorLevel.NETHERITE, 32));

        itemMap.computeIfAbsent(ItemShopPage.BLOCKS, _ -> new HashMap<>()).put(23,
            new BasicShopItem("end_stone", Msg.mm("End Stone"), List.of(), 24, Currency.IRON, 12, Material.END_STONE,
                ItemShopPage.BLOCKS, 23));
        itemMap.computeIfAbsent(ItemShopPage.BLOCKS, _ -> new HashMap<>()).put(31,
            new BasicShopItem("obsidian", Msg.mm("Obsidian"), List.of(), 6, Currency.EMERALD, 6, Material.OBSIDIAN,
                ItemShopPage.BLOCKS, 31));
        itemMap.computeIfAbsent(ItemShopPage.BLOCKS, _ -> new HashMap<>()).put(32,
            new BasicShopItem("planks", Msg.mm("Planks"), List.of(), 4, Currency.GOLD, 8, Material.OAK_PLANKS,
                ItemShopPage.BLOCKS, 32));

        itemMap.computeIfAbsent(ItemShopPage.COMBAT, _ -> new HashMap<>()).put(19,
            new ReplaceAdderShopItem("stone_sword", Msg.mm("Stone Sword"), List.of(), 10, Currency.IRON,
                Material.STONE_SWORD, ItemShopPage.COMBAT, 19));
        itemMap.computeIfAbsent(ItemShopPage.COMBAT, _ -> new HashMap<>()).put(20,
            new ReplaceAdderShopItem("iron_sword", Msg.mm("Iron Sword"), List.of(), 7, Currency.GOLD,
                Material.IRON_SWORD, Material.STONE_SWORD, ItemShopPage.COMBAT, 20));
        itemMap.computeIfAbsent(ItemShopPage.COMBAT, _ -> new HashMap<>()).put(21,
            new ReplaceAdderShopItem("diamond_sword", Msg.mm("Diamond Sword"), List.of(), 6, Currency.EMERALD,
                Material.DIAMOND_SWORD, Material.IRON_SWORD, ItemShopPage.COMBAT, 21));

        itemMap.computeIfAbsent(ItemShopPage.COMBAT, _ -> new HashMap<>()).put(22,
            new BasicShopItem("shield", Msg.mm("Shield"), List.of(), 3, Currency.GOLD, 1,
                Material.SHIELD, ItemShopPage.COMBAT, 22));

        itemMap.computeIfAbsent(ItemShopPage.COMBAT, _ -> new HashMap<>()).put(23,
            new ReplaceAdderShopItem("crossbow_1", Msg.mm("Crossbow"), List.of(), 12, Currency.GOLD,
                Material.CROSSBOW, Material.CROSSBOW, ItemShopPage.COMBAT, 23));
        itemMap.computeIfAbsent(ItemShopPage.COMBAT, _ -> new HashMap<>()).put(24,
            new ReplaceAdderShopItem("crossbow_2", Msg.mm("Crossbow"), List.of(), 20, Currency.GOLD,
                ItemStack.builder(Material.CROSSBOW)
                    .set(DataComponents.ENCHANTMENTS,
                        new EnchantmentList(Map.of(Enchantment.QUICK_CHARGE, 1))).build(),
                Material.CROSSBOW, ItemShopPage.COMBAT, 24));
        itemMap.computeIfAbsent(ItemShopPage.COMBAT, _ -> new HashMap<>()).put(25,
            new ReplaceAdderShopItem("crossbow_3", Msg.mm("Crossbow"), List.of(), 6, Currency.EMERALD,
                ItemStack.builder(Material.CROSSBOW)
                    .set(DataComponents.ENCHANTMENTS,
                        new EnchantmentList(Map.of(Enchantment.QUICK_CHARGE, 2, Enchantment.MULTISHOT, 1))).build(),
                Material.CROSSBOW, ItemShopPage.COMBAT, 25));

        itemMap.computeIfAbsent(ItemShopPage.COMBAT, _ -> new HashMap<>()).put(28,
            new BasicShopItem("bow_1", Msg.mm("Bow"), List.of(), 10, Currency.GOLD, 1,
                Material.BOW, ItemShopPage.COMBAT, 28));

        itemMap.computeIfAbsent(ItemShopPage.COMBAT, _ -> new HashMap<>()).put(29,
            new BasicShopItem("bow_2", Msg.mm("Bow"), List.of(), 18, Currency.GOLD, 1,
                ItemStack.builder(Material.BOW)
                    .set(DataComponents.ENCHANTMENTS,
                        new EnchantmentList(Map.of(Enchantment.POWER, 1))).build(),
                ItemShopPage.COMBAT, 29));

        itemMap.computeIfAbsent(ItemShopPage.COMBAT, _ -> new HashMap<>()).put(30,
            new BasicShopItem("bow_3", Msg.mm("Bow"), List.of(), 12, Currency.GOLD, 1,
                ItemStack.builder(Material.BOW)
                    .set(DataComponents.ENCHANTMENTS,
                        new EnchantmentList(Map.of(Enchantment.PUNCH, 1, Enchantment.POWER, 2))).build(),
                ItemShopPage.COMBAT, 30));

        itemMap.computeIfAbsent(ItemShopPage.COMBAT, _ -> new HashMap<>()).put(31,
            new BasicShopItem("arrow", Msg.mm("Arrow"), List.of(), 2, Currency.GOLD, 8,
                Material.ARROW, ItemShopPage.COMBAT, 31));

        itemMap.computeIfAbsent(ItemShopPage.COMBAT, _ -> new HashMap<>()).put(32,
            new ReplaceAdderShopItem("trident_1", Msg.mm("Trident"), List.of(), 12, Currency.GOLD,
                ItemStack.builder(Material.TRIDENT)
                    .set(DataComponents.ENCHANTMENTS,
                        new EnchantmentList(Map.of(Enchantment.LOYALTY, 1))).build(),
                Material.TRIDENT, ItemShopPage.COMBAT, 32));

        itemMap.computeIfAbsent(ItemShopPage.COMBAT, _ -> new HashMap<>()).put(33,
            new ReplaceAdderShopItem("trident_2", Msg.mm("Trident"), List.of(), 5, Currency.EMERALD,
                ItemStack.builder(Material.TRIDENT)
                    .set(DataComponents.ENCHANTMENTS,
                        new EnchantmentList(Map.of(Enchantment.RIPTIDE, 1))).build(),
                Material.TRIDENT, ItemShopPage.COMBAT, 33));

        itemMap.computeIfAbsent(ItemShopPage.COMBAT, _ -> new HashMap<>()).put(34,
            new ReplaceAdderShopItem("trident_3", Msg.mm("Trident"), List.of(), 5, Currency.GOLD,
                ItemStack.builder(Material.TRIDENT)
                    .set(DataComponents.ENCHANTMENTS,
                        new EnchantmentList(Map.of(Enchantment.LOYALTY, 2, Enchantment.CHANNELING, 1))).build(),
                Material.TRIDENT, ItemShopPage.COMBAT, 34));

        itemMap.computeIfAbsent(ItemShopPage.POTIONS, _ -> new HashMap<>()).put(22,
            new PotionShopItem("invisibility", Msg.mm("Invisibility (30s)"), List.of(), 1, Currency.EMERALD,
                Material.POTION, ItemShopPage.POTIONS, 22, PotionEffect.INVISIBILITY, 600, 1));
        itemMap.computeIfAbsent(ItemShopPage.POTIONS, _ -> new HashMap<>()).put(30,
            new PotionShopItem("fire_resistance", Msg.mm("Fire Resistance (60s)"), List.of(), 6, Currency.GOLD,
                Material.POTION, ItemShopPage.POTIONS, 30, PotionEffect.FIRE_RESISTANCE, 1200, 1));
        itemMap.computeIfAbsent(ItemShopPage.POTIONS, _ -> new HashMap<>()).put(31,
            new PotionShopItem("jump_boost", Msg.mm("Jump Boost (60s)"), List.of(), 1, Currency.EMERALD,
                Material.POTION, ItemShopPage.POTIONS, 31, PotionEffect.JUMP_BOOST, 1200, 5));
        itemMap.computeIfAbsent(ItemShopPage.POTIONS, _ -> new HashMap<>()).put(32,
            new PotionShopItem("speed", Msg.mm("Speed (60s)"), List.of(), 1, Currency.EMERALD,
                Material.POTION, ItemShopPage.POTIONS, 32, PotionEffect.JUMP_BOOST, 1200, 2));
    }
}

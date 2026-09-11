package net.cytonic.bedwars.shop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minestom.server.component.DataComponents;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.component.EnchantmentList;
import net.minestom.server.item.enchant.Enchantment;
import net.minestom.server.potion.PotionEffect;

import net.cytonic.bedwars.data.enums.ArmorLevel;
import net.cytonic.bedwars.data.enums.Currency;
import net.cytonic.bedwars.shop.impl.AbilityShopItem;
import net.cytonic.bedwars.shop.impl.ArmorShopItem;
import net.cytonic.bedwars.shop.impl.BasicShopItem;
import net.cytonic.bedwars.shop.impl.PotionShopItem;
import net.cytonic.bedwars.shop.impl.ReplaceAdderShopItem;
import net.cytonic.cytosis.utils.Msg;
import net.cytonic.protocol.utils.JandexUtils;

public class ItemShopItemRegistry {

    public static final Map<ItemShopPage, Map<Integer, ShopItem>> REGISTRY = new HashMap<>();

    static {
        JandexUtils.getExtendedClasses(ShopItem.class).forEach(ItemShopItemRegistry::register);

        register(new ArmorShopItem("chainmail_armor", Msg.mm("Permanent Chainmail Armor"), List.of(), 24, Currency.IRON,
            Material.CHAINMAIL_BOOTS, Material.CHAINMAIL_LEGGINGS,
            ArmorLevel.CHAINMAIL, 22));
        register(new ArmorShopItem("iron_armor", Msg.mm("Permanent Iron Armor"), List.of(), 12, Currency.GOLD,
            Material.IRON_BOOTS, Material.IRON_LEGGINGS,
            ArmorLevel.IRON, 30));
        register(new ArmorShopItem("diamond_armor", Msg.mm("Permanent Diamond Armor"), List.of(), 6, Currency.EMERALD,
            Material.DIAMOND_BOOTS, Material.DIAMOND_LEGGINGS,
            ArmorLevel.DIAMOND, 31));
        register(
            new ArmorShopItem("netherite_armor", Msg.mm("Permanent Netherite Armor"), List.of(), 16, Currency.EMERALD,
                Material.NETHERITE_BOOTS, Material.NETHERITE_LEGGINGS,
                ArmorLevel.NETHERITE, 32));

        register(
            new BasicShopItem("end_stone", Msg.mm("End Stone"), List.of(), 24, Currency.IRON, 12, Material.END_STONE,
                ItemShopPage.BLOCKS, 23));
        register(new BasicShopItem("obsidian", Msg.mm("Obsidian"), List.of(), 6, Currency.EMERALD, 6, Material.OBSIDIAN,
            ItemShopPage.BLOCKS, 31));
        register(new BasicShopItem("planks", Msg.mm("Planks"), List.of(), 4, Currency.GOLD, 8, Material.OAK_PLANKS,
            ItemShopPage.BLOCKS, 32));

        register(new ReplaceAdderShopItem("stone_sword", Msg.mm("Stone Sword"), List.of(), 10, Currency.IRON,
            Material.STONE_SWORD, ItemShopPage.COMBAT, 19));
        register(new ReplaceAdderShopItem("iron_sword", Msg.mm("Iron Sword"), List.of(), 7, Currency.GOLD,
            Material.IRON_SWORD, List.of(Material.WOODEN_SWORD, Material.STONE_SWORD), ItemShopPage.COMBAT, 20));
        register(new ReplaceAdderShopItem("diamond_sword", Msg.mm("Diamond Sword"), List.of(), 6, Currency.EMERALD,
            Material.DIAMOND_SWORD, List.of(Material.WOODEN_SWORD, Material.STONE_SWORD, Material.IRON_SWORD),
            ItemShopPage.COMBAT, 21));

        register(new BasicShopItem("shield", Msg.mm("Shield"), List.of(), 3, Currency.GOLD, 1,
            Material.SHIELD, ItemShopPage.COMBAT, 22));

        register(new ReplaceAdderShopItem("crossbow_1", Msg.mm("Crossbow"), List.of(), 12, Currency.GOLD,
            Material.CROSSBOW, List.of(Material.CROSSBOW), ItemShopPage.COMBAT, 23));
        register(new ReplaceAdderShopItem("crossbow_2", Msg.mm("Crossbow"), List.of(), 20, Currency.GOLD,
            ItemStack.builder(Material.CROSSBOW)
                .set(DataComponents.ENCHANTMENTS,
                    new EnchantmentList(Map.of(Enchantment.QUICK_CHARGE, 1))).build(),
            List.of(Material.CROSSBOW), ItemShopPage.COMBAT, 24));
        register(new ReplaceAdderShopItem("crossbow_3", Msg.mm("Crossbow"), List.of(), 6, Currency.EMERALD,
            ItemStack.builder(Material.CROSSBOW)
                .set(DataComponents.ENCHANTMENTS,
                    new EnchantmentList(Map.of(Enchantment.QUICK_CHARGE, 2, Enchantment.MULTISHOT, 1))).build(),
            List.of(Material.CROSSBOW), ItemShopPage.COMBAT, 25));

        register(new BasicShopItem("bow_1", Msg.mm("Bow"), List.of(), 10, Currency.GOLD, 1,
            Material.BOW, ItemShopPage.COMBAT, 28));

        register(new BasicShopItem("bow_2", Msg.mm("Bow"), List.of(), 18, Currency.GOLD, 1,
            ItemStack.builder(Material.BOW)
                .set(DataComponents.ENCHANTMENTS,
                    new EnchantmentList(Map.of(Enchantment.POWER, 1))).build(),
            ItemShopPage.COMBAT, 29));

        register(new BasicShopItem("bow_3", Msg.mm("Bow"), List.of(), 12, Currency.GOLD, 1,
            ItemStack.builder(Material.BOW)
                .set(DataComponents.ENCHANTMENTS,
                    new EnchantmentList(Map.of(Enchantment.PUNCH, 1, Enchantment.POWER, 2))).build(),
            ItemShopPage.COMBAT, 30));

        register(new BasicShopItem("arrow", Msg.mm("Arrow"), List.of(), 2, Currency.GOLD, 8,
            Material.ARROW, ItemShopPage.COMBAT, 31));

        register(new ReplaceAdderShopItem("trident_1", Msg.mm("Trident"), List.of(), 12, Currency.GOLD,
            ItemStack.builder(Material.TRIDENT)
                .set(DataComponents.ENCHANTMENTS,
                    new EnchantmentList(Map.of(Enchantment.LOYALTY, 1))).build(),
            List.of(Material.TRIDENT), ItemShopPage.COMBAT, 32));

        register(new ReplaceAdderShopItem("trident_2", Msg.mm("Trident"), List.of(), 5, Currency.EMERALD,
            ItemStack.builder(Material.TRIDENT)
                .set(DataComponents.ENCHANTMENTS,
                    new EnchantmentList(Map.of(Enchantment.RIPTIDE, 1))).build(),
            List.of(Material.TRIDENT), ItemShopPage.COMBAT, 33));

        register(new ReplaceAdderShopItem("trident_3", Msg.mm("Trident"), List.of(), 5, Currency.GOLD,
            ItemStack.builder(Material.TRIDENT)
                .set(DataComponents.ENCHANTMENTS,
                    new EnchantmentList(Map.of(Enchantment.LOYALTY, 2, Enchantment.CHANNELING, 1))).build(),
            List.of(Material.TRIDENT), ItemShopPage.COMBAT, 34));

        register(new PotionShopItem("invisibility", Msg.mm("Invisibility (30s)"), List.of(), 1, Currency.EMERALD,
            Material.POTION, ItemShopPage.POTIONS, 22, PotionEffect.INVISIBILITY, 600, 1));
        register(new PotionShopItem("fire_resistance", Msg.mm("Fire Resistance (60s)"), List.of(), 6, Currency.GOLD,
            Material.POTION, ItemShopPage.POTIONS, 30, PotionEffect.FIRE_RESISTANCE, 1200, 1));
        register(new PotionShopItem("jump_boost", Msg.mm("Jump Boost (60s)"), List.of(), 1, Currency.EMERALD,
            Material.POTION, ItemShopPage.POTIONS, 31, PotionEffect.JUMP_BOOST, 1200, 5));
        register(new PotionShopItem("speed", Msg.mm("Speed (60s)"), List.of(), 1, Currency.EMERALD,
            Material.POTION, ItemShopPage.POTIONS, 32, PotionEffect.JUMP_BOOST, 1200, 2));

        register(new BasicShopItem("golden_apple", Msg.mm("Golden Apple"), List.of(), 3, Currency.GOLD,
            1, Material.GOLDEN_APPLE, ItemShopPage.UTILS, 20));
        register(new BasicShopItem("ender_pearl", Msg.mm("Ender Pearl"), List.of(), 4, Currency.EMERALD,
            1, Material.ENDER_PEARL, ItemShopPage.UTILS, 21));
        register(new AbilityShopItem("fireball", Msg.mm("Fireball"), List.of(), 40, Currency.IRON, Material.FIRE_CHARGE,
            ItemShopPage.UTILS, 23));
        register(new AbilityShopItem("tnt", Msg.mm("TNT"), List.of(), 4, Currency.GOLD, Material.TNT,
            ItemShopPage.UTILS, 24));
    }

    private static void register(ShopItem item) {
        REGISTRY.computeIfAbsent(item.getItemShopPage(), _ -> new HashMap<>()).put(item.getSlot(), item);
    }
}

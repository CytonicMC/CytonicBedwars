package net.cytonic.cytonicbedwars.utils;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.data.enums.MappableItem;
import net.cytonic.cytonicbedwars.data.objects.Team;
import net.cytonic.cytosis.utils.Msg;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.component.DataComponents;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.component.CustomData;
import net.minestom.server.item.component.EnchantmentList;
import net.minestom.server.item.component.PotionContents;
import net.minestom.server.item.component.TooltipDisplay;
import net.minestom.server.item.enchant.Enchantment;
import net.minestom.server.potion.CustomPotionEffect;
import net.minestom.server.potion.PotionEffect;
import net.minestom.server.registry.DynamicRegistry;

import java.util.*;
import java.util.stream.Stream;

@NoArgsConstructor
public class Items {
    private static final Map<String, ItemStack> itemRegistry = new HashMap<>();
    public static String NAMESPACE = "bwID";
    public static String MOVE_BLACKLIST = "move_blacklist";
    public static String ALLOWED_SLOTS = "allowed_slots";
    public static String NO_DROP = "no_drop";
    public static String PRICE_AMOUNT = "price_amount";
    public static String PRICE = "price";
    public static String AMOUNT = "amount";

    // item constants

    // spectator items
    public static ItemStack SPECTATOR_TARGET_SELECTOR = createItem("<GREEN>Target Selector", "SPECTATOR_COMPASS", Material.COMPASS, true, true, List.of(0), new HashMap<>(), "<gray>Right click to teleport to a player!");
    public static ItemStack SPECTATOR_SPEED_SELECTOR = createItem("<AQUA>Speed Selector", "SPECTATOR_SPEED_SELECTOR", Material.HEART_OF_THE_SEA, true, true, List.of(4), new HashMap<>(), "<gray>Right click to change flight speed.");
    public static ItemStack SPECTATOR_LOBBY_REQUEST = createItem("<RED>Go to Lobby", "LOBBY_REQUEST", Material.RED_BED, true, true, List.of(8), new HashMap<>(), "<gold>To the lobby!");
    public static ItemStack SPECTATOR_ARMOR = createItem(" ", "SPECTATOR_ARMOR", Material.BARRIER, true, true, List.of(36, 37, 38, 39), new HashMap<>());

    // shop items

    // all 16 of the glasses
    public static ItemStack RED_BLAST_PROOF_GLASS = createItem("<GREEN>Blast-Proof Glass", "RED_BLAST_PROOF_GLASS", Material.RED_STAINED_GLASS, false, false, List.of(), new HashMap<>());
    public static ItemStack ORANGE_BLAST_PROOF_GLASS = createItem("<GREEN>Blast-Proof Glass", "ORANGE_BLAST_PROOF_GLASS", Material.ORANGE_STAINED_GLASS, false, false, List.of(), new HashMap<>());
    public static ItemStack YELLOW_BLAST_PROOF_GLASS = createItem("<GREEN>Blast-Proof Glass", "YELLOW_BLAST_PROOF_GLASS", Material.YELLOW_STAINED_GLASS, false, false, List.of(), new HashMap<>());
    public static ItemStack LIME_BLAST_PROOF_GLASS = createItem("<GREEN>Blast-Proof Glass", "LIME_BLAST_PROOF_GLASS", Material.LIME_STAINED_GLASS, false, false, List.of(), new HashMap<>());
    public static ItemStack GREEN_BLAST_PROOF_GLASS = createItem("<GREEN>Blast-Proof Glass", "GREEN_BLAST_PROOF_GLASS", Material.GREEN_STAINED_GLASS, false, false, List.of(), new HashMap<>());
    public static ItemStack LIGHT_BLUE_BLAST_PROOF_GLASS = createItem("<GREEN>Blast-Proof Glass", "LIGHT_BLUE_BLAST_PROOF_GLASS", Material.LIGHT_BLUE_STAINED_GLASS, false, false, List.of(), new HashMap<>());
    public static ItemStack BLUE_BLAST_PROOF_GLASS = createItem("<GREEN>Blast-Proof Glass", "BLUE_BLAST_PROOF_GLASS", Material.BLUE_STAINED_GLASS, false, false, List.of(), new HashMap<>());
    public static ItemStack CYAN_BLAST_PROOF_GLASS = createItem("<GREEN>Blast-Proof Glass", "CYAN_BLAST_PROOF_GLASS", Material.CYAN_STAINED_GLASS, false, false, List.of(), new HashMap<>());
    public static ItemStack MAGENTA_BLAST_PROOF_GLASS = createItem("<GREEN>Blast-Proof Glass", "MAGENTA_BLAST_PROOF_GLASS", Material.MAGENTA_STAINED_GLASS, false, false, List.of(), new HashMap<>());
    public static ItemStack PINK_BLAST_PROOF_GLASS = createItem("<GREEN>Blast-Proof Glass", "PINK_BLAST_PROOF_GLASS", Material.PINK_STAINED_GLASS, false, false, List.of(), new HashMap<>());
    public static ItemStack PURPLE_BLAST_PROOF_GLASS = createItem("<GREEN>Blast-Proof Glass", "PURPLE_BLAST_PROOF_GLASS", Material.PURPLE_STAINED_GLASS, false, false, List.of(), new HashMap<>());
    public static ItemStack WHITE_BLAST_PROOF_GLASS = createItem("<GREEN>Blast-Proof Glass", "WHITE_BLAST_PROOF_GLASS", Material.WHITE_STAINED_GLASS, false, false, List.of(), new HashMap<>());
    public static ItemStack GREY_BLAST_PROOF_GLASS = createItem("<GREEN>Blast-Proof Glass", "GREY_BLAST_PROOF_GLASS", Material.GRAY_STAINED_GLASS, false, false, List.of(), new HashMap<>());
    public static ItemStack LIGHT_GREY_BLAST_PROOF_GLASS = createItem("<GREEN>Blast-Proof Glass", "LIGHT_GREY_BLAST_PROOF_GLASS", Material.LIGHT_GRAY_STAINED_GLASS, false, false, List.of(), new HashMap<>());
    public static ItemStack BLACK_BLAST_PROOF_GLASS = createItem("<GREEN>Blast-Proof Glass", "BLACK_BLAST_PROOF_GLASS", Material.BLACK_STAINED_GLASS, false, false, List.of(), new HashMap<>());

    // all 16 of the wools
    public static ItemStack RED_WOOL = createItem("Wool", "RED_WOOL", Material.RED_WOOL, false, false, List.of(), new HashMap<>());
    public static ItemStack ORANGE_WOOL = createItem("Wool", "ORANGE_WOOL", Material.ORANGE_WOOL, false, false, List.of(), new HashMap<>());
    public static ItemStack YELLOW_WOOL = createItem("Wool", "YELLOW_WOOL", Material.YELLOW_WOOL, false, false, List.of(), new HashMap<>());
    public static ItemStack LIME_WOOL = createItem("Wool", "LIME_WOOL", Material.LIME_WOOL, false, false, List.of(), new HashMap<>());
    public static ItemStack GREEN_WOOL = createItem("Wool", "GREEN_WOOL", Material.GREEN_WOOL, false, false, List.of(), new HashMap<>());
    public static ItemStack LIGHT_BLUE_WOOL = createItem("Wool", "LIGHT_BLUE_WOOL", Material.LIGHT_BLUE_WOOL, false, false, List.of(), new HashMap<>());
    public static ItemStack BLUE_WOOL = createItem("Wool", "BLUE_WOOL", Material.BLUE_WOOL, false, false, List.of(), new HashMap<>());
    public static ItemStack CYAN_WOOL = createItem("Wool", "CYAN_WOOL", Material.CYAN_WOOL, false, false, List.of(), new HashMap<>());
    public static ItemStack MAGENTA_WOOL = createItem("Wool", "MAGENTA_WOOL", Material.MAGENTA_WOOL, false, false, List.of(), new HashMap<>());
    public static ItemStack PINK_WOOL = createItem("Wool", "PINK_WOOL", Material.PINK_WOOL, false, false, List.of(), new HashMap<>());
    public static ItemStack PURPLE_WOOL = createItem("Wool", "PURPLE_WOOL", Material.PURPLE_WOOL, false, false, List.of(), new HashMap<>());
    public static ItemStack WHITE_WOOL = createItem("Wool", "WHITE_WOOL", Material.WHITE_WOOL, false, false, List.of(), new HashMap<>());
    public static ItemStack GREY_WOOL = createItem("Wool", "GREY_WOOL", Material.GRAY_WOOL, false, false, List.of(), new HashMap<>());
    public static ItemStack LIGHT_GREY_WOOL = createItem("Wool", "LIGHT_GREY_WOOL", Material.LIGHT_GRAY_WOOL, false, false, List.of(), new HashMap<>());
    public static ItemStack BLACK_WOOL = createItem("Wool", "BLACK_WOOL", Material.BLACK_WOOL, false, false, List.of(), new HashMap<>());

    public static ItemStack RED_TERRACOTTA = createItem("Terracotta", "RED_TERRACOTTA", Material.RED_TERRACOTTA, false, false, List.of(), new HashMap<>());
    public static ItemStack ORANGE_TERRACOTTA = createItem("Terracotta", "ORANGE_TERRACOTTA", Material.ORANGE_TERRACOTTA, false, false, List.of(), new HashMap<>());
    public static ItemStack YELLOW_TERRACOTTA = createItem("Terracotta", "YELLOW_TERRACOTTA", Material.YELLOW_TERRACOTTA, false, false, List.of(), new HashMap<>());
    public static ItemStack LIME_TERRACOTTA = createItem("Terracotta", "LIME_TERRACOTTA", Material.LIME_TERRACOTTA, false, false, List.of(), new HashMap<>());
    public static ItemStack GREEN_TERRACOTTA = createItem("Terracotta", "GREEN_TERRACOTTA", Material.GREEN_TERRACOTTA, false, false, List.of(), new HashMap<>());
    public static ItemStack LIGHT_BLUE_TERRACOTTA = createItem("Terracotta", "LIGHT_BLUE_TERRACOTTA", Material.LIGHT_BLUE_TERRACOTTA, false, false, List.of(), new HashMap<>());
    public static ItemStack BLUE_TERRACOTTA = createItem("Terracotta", "BLUE_TERRACOTTA", Material.BLUE_TERRACOTTA, false, false, List.of(), new HashMap<>());
    public static ItemStack CYAN_TERRACOTTA = createItem("Terracotta", "CYAN_TERRACOTTA", Material.CYAN_TERRACOTTA, false, false, List.of(), new HashMap<>());
    public static ItemStack MAGENTA_TERRACOTTA = createItem("Terracotta", "MAGENTA_TERRACOTTA", Material.MAGENTA_TERRACOTTA, false, false, List.of(), new HashMap<>());
    public static ItemStack PINK_TERRACOTTA = createItem("Terracotta", "PINK_TERRACOTTA", Material.PINK_TERRACOTTA, false, false, List.of(), new HashMap<>());
    public static ItemStack PURPLE_TERRACOTTA = createItem("Terracotta", "PURPLE_TERRACOTTA", Material.PURPLE_TERRACOTTA, false, false, List.of(), new HashMap<>());
    public static ItemStack WHITE_TERRACOTTA = createItem("Terracotta", "WHITE_TERRACOTTA", Material.WHITE_TERRACOTTA, false, false, List.of(), new HashMap<>());
    public static ItemStack GREY_TERRACOTTA = createItem("Terracotta", "GREY_TERRACOTTA", Material.GRAY_TERRACOTTA, false, false, List.of(), new HashMap<>());
    public static ItemStack LIGHT_GREY_TERRACOTTA = createItem("Terracotta", "LIGHT_GREY_TERRACOTTA", Material.LIGHT_GRAY_TERRACOTTA, false, false, List.of(), new HashMap<>());
    public static ItemStack BLACK_TERRACOTTA = createItem("Terracotta", "BLACK_TERRACOTTA", Material.BLACK_TERRACOTTA, false, false, List.of(), new HashMap<>());

    public static ItemStack OBSIDIAN = createItem("<DARK_PURPLE>Obsidian", "OBSIDIAN", Material.OBSIDIAN, false, false, List.of(), new HashMap<>());
    public static ItemStack PLANKS = createItem("Planks", "PLANKS", Material.OAK_PLANKS, false, false, List.of(), new HashMap<>());
    public static ItemStack END_STONE = createItem("End Stone", "END_STONE", Material.END_STONE, false, false, List.of(), new HashMap<>());

    // util items
    public static ItemStack FIREBALL = createItem("Fireball", "FIREBALL", Material.FIRE_CHARGE, false, false, List.of(), new HashMap<>());
    public static ItemStack TNT = createItem("TNT", "TNT", Material.TNT, false, false, List.of(), new HashMap<>());

    // weapons
    public static ItemStack DEFAULT_SWORD = createItem("Default Sword", "DEFAULT_SWORD", Material.WOODEN_SWORD, false, true, List.of(), new HashMap<>());
    public static ItemStack STONE_SWORD = createItem("Stone Sword", "STONE_SWORD", Material.STONE_SWORD, false, false, List.of(), new HashMap<>());
    public static ItemStack IRON_SWORD = createItem("Iron Sword", "IRON_SWORD", Material.IRON_SWORD, false, false, List.of(), new HashMap<>());
    public static ItemStack DIAMOND_SWORD = createItem("Diamond Sword", "DIAMOND_SWORD", Material.DIAMOND_SWORD, false, false, List.of(), new HashMap<>());
    public static ItemStack SHIELD = createItem("Shield", "SHIELD", Material.SHIELD, false, false, List.of(), new HashMap<>());
    public static ItemStack BOW_1 = createItem("Bow", "BOW_1", Material.BOW, false, false, List.of(), new HashMap<>());
    public static ItemStack BOW_2 = createItem("Bow", "BOW_2", Material.BOW, false, false, List.of(), Map.of(Enchantment.POWER, 1));
    public static ItemStack BOW_3 = createItem("Bow", "BOW_3", Material.BOW, false, false, List.of(), Map.of(Enchantment.PUNCH, 1, Enchantment.POWER, 2));
    public static ItemStack CROSSBOW_1 = createItem("Crossbow", "CROSSBOW_1", Material.CROSSBOW, false, false, List.of(), new HashMap<>());
    public static ItemStack CROSSBOW_2 = createItem("Crossbow", "CROSSBOW_2", Material.CROSSBOW, false, false, List.of(), Map.of(Enchantment.QUICK_CHARGE, 1));
    public static ItemStack CROSSBOW_3 = createItem("Crossbow", "CROSSBOW_3", Material.CROSSBOW, false, false, List.of(), Map.of(Enchantment.QUICK_CHARGE, 2, Enchantment.MULTISHOT, 1));
    public static ItemStack TRIDENT_1 = createItem("Trident", "TRIDENT_1", Material.TRIDENT, false, false, List.of(), Map.of(Enchantment.LOYALTY, 1));
    public static ItemStack TRIDENT_2 = createItem("Trident", "TRIDENT_2", Material.TRIDENT, false, false, List.of(), Map.of(Enchantment.RIPTIDE, 1));
    public static ItemStack TRIDENT_3 = createItem("Trident", "TRIDENT_3", Material.TRIDENT, false, false, List.of(), Map.of(Enchantment.LOYALTY, 2, Enchantment.CHANNELING, 1));
    public static ItemStack ARROW = createItem("Arrow", "ARROW", Material.ARROW, false, false, List.of(), new HashMap<>());

    // shop menu parts
    public static ItemStack MENU_SELECTED_PAGE = createItem(" ", "MENU_SELECTED_PAGE", Material.LIME_STAINED_GLASS_PANE, false, true, List.of(), Map.of(Enchantment.UNBREAKING, 1));
    public static ItemStack MENU_FILLER = createItem(" ", "MENU_FILLER", Material.GRAY_STAINED_GLASS_PANE, true, false, List.of(), new HashMap<>());
    public static ItemStack MENU_BLOCKS = createItem("<GREEN>Building Blocks", "MENU_BLOCKS", Material.BRICKS, false, true, List.of(), new HashMap<>(), "<gray>Click to browse!");
    public static ItemStack MENU_COMBAT = createItem("<GREEN>Weapons", "MENU_COMBAT", Material.DIAMOND_SWORD, false, true, List.of(), new HashMap<>(), "<gray>Click to browse!");
    public static ItemStack MENU_ARMOR = createItem("<GREEN>Armor", "MENU_ARMOR", Material.DIAMOND_CHESTPLATE, false, true, List.of(), new HashMap<>(), "<gray>Click to browse!");
    public static ItemStack MENU_TOOLS = createItem("<GREEN>Tools", "MENU_TOOLS", Material.DIAMOND_PICKAXE, false, true, List.of(), new HashMap<>(), "<gray>Click to browse!");
    public static ItemStack MENU_POTIONS = createItem("<GREEN>Potions", "MENU_POTIONS", Material.BREWING_STAND, false, true, List.of(), new HashMap<>(), "<gray>Click to browse!");
    public static ItemStack MENU_UTILS = createItem("<GREEN>Utilities", "MENU_UTILS", Material.FIRE_CHARGE, false, true, List.of(), new HashMap<>(), "<gray>Click to browse!");
    public static ItemStack MENU_ROTATING_ITEMS = createItem("<GREEN>Rotating Items", "MENU_ROTATING", Material.ENDERMAN_SPAWN_EGG, false, true, List.of(), new HashMap<>(), "<gray>Click to browse!");
    public static ItemStack MENU_WOOL = createItem("Wool", "MENU_WOOL", Material.WHITE_WOOL, false, true, List.of(), new HashMap<>(), "", "<gray>> 4 Iron");
    public static ItemStack MENU_PLANKS = createItem("Planks", "MENU_PLANKS", Material.OAK_PLANKS, false, true, List.of(), new HashMap<>(), "", "<gold>> 4 Gold");
    public static ItemStack MENU_TERRACOTTA = createItem("Terracotta", "MENU_TERRACOTTA", Material.TERRACOTTA, false, true, List.of(), new HashMap<>(), "", "<gray>> 10 Iron");
    public static ItemStack MENU_END_STONE = createItem("End Stone", "MENU_END_STONE", Material.END_STONE, false, true, List.of(), new HashMap<>(), "", "<gray>> 24 Iron");
    public static ItemStack MENU_BLAST_GLASS = createItem("Blast-Proof Glass", "MENU_BLAST_GLASS", Material.GLASS, false, true, List.of(), new HashMap<>(), "", "<gray>> 12 Iron");
    public static ItemStack MENU_OBSIDIAN = createItem("Obsidian", "MENU_OBSIDIAN", Material.OBSIDIAN, false, true, List.of(), new HashMap<>(), "", "<dark_green>> 6 Emeralds");
    public static ItemStack MENU_STONE_SWORD = createItem("Stone Sword", "MENU_STONE_SWORD", Material.STONE_SWORD, false, false, List.of(), new HashMap<>(), "", "<gray>> 10 Iron");
    public static ItemStack MENU_IRON_SWORD = createItem("Iron Sword", "MENU_IRON_SWORD", Material.IRON_SWORD, false, false, List.of(), new HashMap<>(), "", "<gold>> 7 Gold");
    public static ItemStack MENU_DIAMOND_SWORD = createItem("Diamond Sword", "MENU_DIAMOND_SWORD", Material.DIAMOND_SWORD, false, false, List.of(), new HashMap<>(), "", "<dark_green>> 7 Emeralds");
    public static ItemStack MENU_SHIELD = createItem("Shield", "MENU_SHIELD", Material.SHIELD, false, false, List.of(), new HashMap<>(), "", "<gold>> 3 Gold");
    public static ItemStack MENU_BOW_1 = createItem("Bow", "MENU_BOW_1", Material.BOW, false, false, List.of(), new HashMap<>(), "", "<gold>> 10 Gold");
    public static ItemStack MENU_BOW_2 = createItem("Bow", "MENU_BOW_2", Material.BOW, false, false, List.of(), Map.of(Enchantment.POWER, 1), "", "<gold>> 18 Gold");
    public static ItemStack MENU_BOW_3 = createItem("Bow", "MENU_BOW_3", Material.BOW, false, false, List.of(), Map.of(Enchantment.PUNCH, 1, Enchantment.POWER, 2), "", "<dark_green>> 5 Emeralds");
    public static ItemStack MENU_CROSSBOW_1 = createItem("Crossbow", "MENU_CROSSBOW_1", Material.CROSSBOW, false, false, List.of(), new HashMap<>(), "", "<gold>> 12 Gold");
    public static ItemStack MENU_CROSSBOW_2 = createItem("Crossbow", "MENU_CROSSBOW_2", Material.CROSSBOW, false, false, List.of(), Map.of(Enchantment.QUICK_CHARGE, 1), "", "<gold>> 20 Gold");
    public static ItemStack MENU_CROSSBOW_3 = createItem("Crossbow", "MENU_CROSSBOW_3", Material.CROSSBOW, false, false, List.of(), Map.of(Enchantment.QUICK_CHARGE, 2, Enchantment.MULTISHOT, 1), "", "<dark_green>> 8 Emeralds");
    public static ItemStack MENU_TRIDENT_1 = createItem("Trident", "MENU_TRIDENT_1", Material.TRIDENT, false, false, List.of(), Map.of(Enchantment.LOYALTY, 1), "", "<gold>> 12 Gold");
    public static ItemStack MENU_TRIDENT_2 = createItem("Trident", "MENU_TRIDENT_2", Material.TRIDENT, false, false, List.of(), Map.of(Enchantment.RIPTIDE, 1), "", "<dark_green>> 5 Emerald");
    public static ItemStack MENU_TRIDENT_3 = createItem("Trident", "MENU_TRIDENT_3", Material.TRIDENT, false, false, List.of(), Map.of(Enchantment.LOYALTY, 2, Enchantment.CHANNELING, 1), "", "<dark_green>> 5 Emerald");
    public static ItemStack MENU_ARROW = createItem("Arrow x8   ", "MENU_ARROW", Material.ARROW, false, false, List.of(), new HashMap<>(), "", "<gold>> 2 Gold");
    public static ItemStack MENU_NETHERITE_BOOTS = createItem("Permanent Netherite Armor", "NETHERITE_BOOTS", Material.NETHERITE_BOOTS, false, false, List.of(), Map.of(), "<dark_green>> 16 Emeralds");
    public static ItemStack MENU_DIAMOND_BOOTS = createItem("Permanent Diamond Armor", "DIAMOND_BOOTS", Material.DIAMOND_BOOTS, false, false, List.of(), Map.of(), "<dark_green>> 6 Emeralds");
    public static ItemStack
            MENU_IRON_BOOTS = createItem("Permanent Iron Armor", "IRON_BOOTS", Material.IRON_BOOTS, false, false, List.of(), Map.of(), "<gold>> 12 Gold");
    public static ItemStack MENU_CHAINMAIL_BOOTS = createItem("Permanent Chainmail Armor", "CHAINMAIL_BOOTS", Material.CHAINMAIL_BOOTS, false, false, List.of(), Map.of(), "<gray>> 40 Iron");
    public static ItemStack MENU_SHEARS = createItem("Shears", "SHEARS", Material.SHEARS, false, true, List.of(), Map.of(), "", "<gray>> 24 Iron");
    public static ItemStack MENU_WOODEN_AXE = createItem("Wooden Axe", "WOODEN_AXE", Material.WOODEN_AXE, false, true, List.of(), Map.of(), "", "<gray>> 10 Iron");
    public static ItemStack MENU_STONE_AXE = createItem("Stone Axe", "STONE_AXE", Material.STONE_AXE, false, true, List.of(), Map.of(), "", "<gray>> 20 Iron");
    public static ItemStack MENU_IRON_AXE = createItem("Iron Axe", "IRON_AXE", Material.IRON_AXE, false, true, List.of(), Map.of(), "", "<gold>> 6 Gold");
    public static ItemStack MENU_DIAMOND_AXE = createItem("Diamond Axe", "DIAMOND_AXE", Material.DIAMOND_AXE, false, true, List.of(), Map.of(), "", "<dark_green>> 3 Emeralds");
    public static ItemStack MENU_WOODEN_PICKAXE = createItem("Wooden Pickaxe", "WOODEN_PICKAXE", Material.WOODEN_PICKAXE, false, true, List.of(), Map.of(), "", "<gray>> 10 Iron");
    public static ItemStack MENU_STONE_PICKAXE = createItem("Stone Pickaxe", "STONE_PICKAXE", Material.STONE_PICKAXE, false, true, List.of(), Map.of(), "", "<gray>> 20 Iron");
    public static ItemStack MENU_IRON_PICKAXE = createItem("Iron Pickaxe", "IRON_PICKAXE", Material.IRON_PICKAXE, false, true, List.of(), Map.of(), "", "<gold>> 6 Gold");
    public static ItemStack MENU_DIAMOND_PICKAXE = createItem("Diamond Pickaxe", "DIAMOND_PICKAXE", Material.DIAMOND_PICKAXE, false, true, List.of(), Map.of(), "", "<dark_green>> 3 Emeralds");
    public static ItemStack MENU_FIRE_RESISTANCE_POTION = createPotion("Fire Resistance (60s)", "FIRE_RES_POT", PotionEffect.FIRE_RESISTANCE, 1200, 1, "", "<gold>> 6 Gold");
    public static ItemStack MENU_INVISIBILITY_POTION = createPotion("Invisibility (30s)", "INVIS_POT", PotionEffect.INVISIBILITY, 600, 1, "", "<dark_green>> 2 Emeralds");
    public static ItemStack MENU_JUMP_BOOST_POTION = createPotion("Jump Boost (60s)", "JUMP_BOOST_POT", PotionEffect.JUMP_BOOST, 1200, 5, "", "<dark_green>> 1 Emerald");
    public static ItemStack MENU_SPEED_POTION = createPotion("Speed (60s)", "SPEED_POT", PotionEffect.SPEED, 1200, 2, "", "<dark_green>> 1 Emerald");

    // currency items
    public static ItemStack IRON = createItem("Iron", "IRON", Material.IRON_NUGGET, false, false, List.of(), new HashMap<>());
    public static ItemStack GOLD = createItem("Gold", "GOLD", Material.GOLD_NUGGET, false, false, List.of(), new HashMap<>());
    public static ItemStack DIAMOND = createItem("Diamond", "DIAMOND", Material.DIAMOND, false, false, List.of(), new HashMap<>());
    public static ItemStack EMERALD = createItem("Emerald", "EMERALD", Material.EMERALD, false, false, List.of(), new HashMap<>());


    // Armors -- should probably make these dynamically, but not rn
    public static ItemStack BLUE_BOOTS = createArmor("Boots", "BLUE_BOOTS", Material.LEATHER_BOOTS, NamedTextColor.BLUE);
    public static ItemStack BLUE_LEGS = createArmor("Leggings", "BLUE_LEGS", Material.LEATHER_LEGGINGS, NamedTextColor.BLUE);
    public static ItemStack BLUE_CHEST = createArmor("Chestplate", "BLUE_CHEST", Material.LEATHER_CHESTPLATE, NamedTextColor.BLUE);

    public static ItemStack RED_BOOTS = createArmor("Boots", "RED_BOOTS", Material.LEATHER_BOOTS, NamedTextColor.RED);
    public static ItemStack RED_LEGS = createArmor("Leggings", "RED_LEGS", Material.LEATHER_LEGGINGS, NamedTextColor.RED);
    public static ItemStack RED_CHEST = createArmor("Chestplate", "RED_CHEST", Material.LEATHER_CHESTPLATE, NamedTextColor.RED);

    public static ItemStack GREEN_BOOTS = createArmor("Boots", "GREEN_BOOTS", Material.LEATHER_BOOTS, NamedTextColor.GREEN);
    public static ItemStack GREEN_LEGS = createArmor("Leggings", "GREEN_LEGS", Material.LEATHER_LEGGINGS, NamedTextColor.GREEN);
    public static ItemStack GREEN_CHEST = createArmor("Chestplate", "GREEN_CHEST", Material.LEATHER_CHESTPLATE, NamedTextColor.GREEN);

    public static ItemStack YELLOW_BOOTS = createArmor("Boots", "YELLOW_BOOTS", Material.LEATHER_BOOTS, NamedTextColor.YELLOW);
    public static ItemStack YELLOW_LEGS = createArmor("Leggings", "YELLOW_LEGS", Material.LEATHER_LEGGINGS, NamedTextColor.YELLOW);
    public static ItemStack YELLOW_CHEST = createArmor("Chestplate", "YELLOW_CHEST", Material.LEATHER_CHESTPLATE, NamedTextColor.YELLOW);

    public static ItemStack NETHERITE_LEGS = createItem("Netherite Leggings", "NETHERITE_LEGS", Material.NETHERITE_LEGGINGS, true, true, List.of(37), Map.of());
    public static ItemStack DIAMOND_LEGS = createItem("Diamond Leggings", "DIAMOND_LEGS", Material.DIAMOND_LEGGINGS, true, true, List.of(37), Map.of());
    public static ItemStack IRON_LEGS = createItem("Iron Leggings", "IRON_LEGS", Material.IRON_LEGGINGS, true, true, List.of(37), Map.of());
    public static ItemStack CHAINMAIL_LEGS = createItem("Chainmail Leggings", "CHAINMAIL_LEGS", Material.CHAINMAIL_LEGGINGS, true, true, List.of(37), Map.of());

    public static ItemStack NETHERITE_BOOTS = createItem("Netherite Boots", "NETHERITE_BOOTS", Material.NETHERITE_BOOTS, true, true, List.of(36), Map.of());
    public static ItemStack DIAMOND_BOOTS = createItem("Diamond Boots", "DIAMOND_BOOTS", Material.DIAMOND_BOOTS, true, true, List.of(36), Map.of());
    public static ItemStack IRON_BOOTS = createItem("Iron Boots", "IRON_BOOTS", Material.IRON_BOOTS, true, true, List.of(36), Map.of());
    public static ItemStack CHAINMAIL_BOOTS = createItem("Chainmail Boots", "CHAINMAIL_BOOTS", Material.CHAINMAIL_BOOTS, true, true, List.of(36), Map.of());


    // tools

    public static ItemStack SHEARS = createItem("Shears", "SHEARS", Material.SHEARS, false, true, List.of(), Map.of());

    public static ItemStack WOODEN_AXE = createItem("Wooden Axe", "WOODEN_AXE", Material.WOODEN_AXE, false, true, List.of(), Map.of());
    public static ItemStack STONE_AXE = createItem("Stone Axe", "STONE_AXE", Material.STONE_AXE, false, true, List.of(), Map.of());
    public static ItemStack IRON_AXE = createItem("Iron Axe", "IRON_AXE", Material.IRON_AXE, false, true, List.of(), Map.of());
    public static ItemStack DIAMOND_AXE = createItem("Diamond Axe", "DIAMOND_AXE", Material.DIAMOND_AXE, false, true, List.of(), Map.of());

    public static ItemStack WOODEN_PICKAXE = createItem("Wooden Pickaxe", "WOODEN_PICKAXE", Material.WOODEN_PICKAXE, false, true, List.of(), Map.of());
    public static ItemStack STONE_PICKAXE = createItem("Stone Pickaxe", "STONE_PICKAXE", Material.STONE_PICKAXE, false, true, List.of(), Map.of());
    public static ItemStack IRON_PICKAXE = createItem("Iron Pickaxe", "IRON_PICKAXE", Material.IRON_PICKAXE, false, true, List.of(), Map.of());
    public static ItemStack DIAMOND_PICKAXE = createItem("Diamond Pickaxe", "DIAMOND_PICKAXE", Material.DIAMOND_PICKAXE, false, true, List.of(), Map.of());


    // potion types:
    // Fire res, Invis, jump, speed

    public static ItemStack FIRE_RESISTANCE_POTION = createPotion("Fire Resistance (60s)", "FIRE_RES_POT", PotionEffect.FIRE_RESISTANCE, 1200, 1);
    public static ItemStack INVISIBILITY_POTION = createPotion("Invisibility (30s)", "INVIS_POT", PotionEffect.INVISIBILITY, 600, 1);
    public static ItemStack JUMP_BOOST_POTION = createPotion("Jump Boost (60s)", "JUMP_BOOST_POT", PotionEffect.JUMP_BOOST, 1200, 5);
    public static ItemStack SPEED_POTION = createPotion("Speed (60s)", "SPEED_POT", PotionEffect.SPEED, 1200, 2);

    private static ItemStack createItem(String displayName, String id, Material type, boolean noMove, boolean noDrop, List<Integer> allowedSlots, Map<DynamicRegistry.Key<Enchantment>, Integer> enchants, String... lore) {
        List<Component> list = new ArrayList<>();
        for (String s : lore) list.add(Msg.mm(s));
        EnchantmentList enchantmentList = new EnchantmentList(enchants);
        CompoundBinaryTag.Builder builder = CompoundBinaryTag.builder();
        if (noDrop) {
            builder.putBoolean(NO_DROP, true);
        }
        if (noMove) {
            builder.putBoolean(MOVE_BLACKLIST, true);
            builder.putIntArray(ALLOWED_SLOTS, allowedSlots.stream().mapToInt(Integer::intValue).toArray());
        }
        builder.putString(NAMESPACE, id);
        ItemStack item = ItemStack.builder(type)
                .set(DataComponents.ITEM_NAME, Msg.mm(displayName))
                .set(DataComponents.LORE, list)
                .set(DataComponents.UNBREAKABLE)
                .set(DataComponents.TOOLTIP_DISPLAY, new TooltipDisplay(false, Set.of(DataComponents.EQUIPPABLE, DataComponents.UNBREAKABLE)))
                .set(DataComponents.ENCHANTMENTS, enchantmentList)
                .set(DataComponents.CUSTOM_DATA, new CustomData(builder.build()))
                .build();
        itemRegistry.put(id, item);
        return item;
    }

    private static ItemStack createArmor(String name, String id, Material type, NamedTextColor color) {
        CompoundBinaryTag.Builder builder = CompoundBinaryTag.builder();
        builder.putBoolean(NO_DROP, true);
        builder.putBoolean(MOVE_BLACKLIST, true);
        builder.putIntArray(ALLOWED_SLOTS, Stream.of(36, 37, 38, 39).mapToInt(Integer::intValue).toArray());
        builder.putString(NAMESPACE, id);

        ItemStack item = ItemStack.builder(type)
                .set(DataComponents.ITEM_NAME, Msg.mm(name))
                .set(DataComponents.UNBREAKABLE)
                .set(DataComponents.TOOLTIP_DISPLAY, new TooltipDisplay(false, Set.of(DataComponents.EQUIPPABLE, DataComponents.UNBREAKABLE)))
                .set(DataComponents.DYED_COLOR, color)
                .set(DataComponents.CUSTOM_DATA, new CustomData(builder.build()))
                .build();
        itemRegistry.put(id, item);
        return item;
    }

    private static ItemStack createPotion(String name, String id, PotionEffect pot, int duration, int amplifier, String... lore) {
        List<Component> list = new ArrayList<>();
        for (String s : lore) list.add(Msg.mm(s));
        CustomPotionEffect effect = new CustomPotionEffect(pot, Byte.parseByte(String.valueOf(amplifier - 1)), duration, true, true, true);
        PotionContents contents = new PotionContents(effect);
        ItemStack item = ItemStack.builder(Material.POTION)
                .set(DataComponents.POTION_CONTENTS, contents)
                .set(DataComponents.CUSTOM_NAME, Msg.mm(name))
                .set(DataComponents.UNBREAKABLE)
                .set(DataComponents.TOOLTIP_DISPLAY, new TooltipDisplay(false, Set.of(DataComponents.EQUIPPABLE, DataComponents.UNBREAKABLE)))
                .set(DataComponents.LORE, list)
                .set(DataComponents.CUSTOM_DATA, new CustomData(CompoundBinaryTag.builder().putString(NAMESPACE, id).build()))
                .build();
        itemRegistry.put(id, item);
        return item;
    }

    public static ItemStack get(String id) {
        if (id == null) return ItemStack.AIR;
        return itemRegistry.get(id);
    }

    public static Set<String> getItemIDs() {
        return itemRegistry.keySet();
    }

    public static ItemStack getTeamMapped(MappableItem mappableItem, Team team) {
        final String string = team.color().toString().toUpperCase();
        switch (mappableItem) {
            case WOOL -> {
                switch (string.toUpperCase()) {
                    case "RED" -> {
                        return Items.RED_WOOL;
                    }
                    case "GOLD" -> {
                        return Items.ORANGE_WOOL;
                    }
                    case "YELLOW" -> {
                        return Items.YELLOW_WOOL;
                    }
                    case "GREEN" -> {
                        return Items.LIME_WOOL;
                    }
                    case "DARK_GREEN" -> {
                        return Items.GREEN_WOOL;
                    }
                    case "DARK_AQUA" -> {
                        return Items.CYAN_WOOL;
                    }
                    case "AQUA" -> {
                        return Items.LIGHT_BLUE_WOOL;
                    }
                    case "BLUE" -> {
                        return Items.BLUE_WOOL;
                    }
                    case "DARK_PURPLE" -> {
                        return Items.PURPLE_WOOL;
                    }
                    case "LIGHT_PURPLE" -> {
                        return Items.PINK_WOOL;
                    }
                    case "BLACK" -> {
                        return Items.BLACK_WOOL;
                    }
                    case "WHITE" -> {
                        return Items.WHITE_WOOL;
                    }
                    case "GRAY" -> {
                        return Items.LIGHT_GREY_WOOL;
                    }
                    case "DARK_GRAY" -> {
                        return Items.GREY_WOOL;
                    }
                    case "DARK_RED" -> {
                        return Items.MAGENTA_WOOL;
                    }
                    default -> {
                        return Items.SPECTATOR_ARMOR;
                    }
                }
            }
            case GLASS -> {
                switch (string.toUpperCase()) {
                    case "RED" -> {
                        return Items.RED_BLAST_PROOF_GLASS;
                    }
                    case "GOLD" -> {
                        return Items.ORANGE_BLAST_PROOF_GLASS;
                    }
                    case "YELLOW" -> {
                        return Items.YELLOW_BLAST_PROOF_GLASS;
                    }
                    case "GREEN" -> {
                        return Items.LIME_BLAST_PROOF_GLASS;
                    }
                    case "DARK_GREEN" -> {
                        return Items.GREEN_BLAST_PROOF_GLASS;
                    }
                    case "DARK_AQUA" -> {
                        return Items.CYAN_BLAST_PROOF_GLASS;
                    }
                    case "AQUA" -> {
                        return Items.LIGHT_BLUE_BLAST_PROOF_GLASS;
                    }
                    case "BLUE" -> {
                        return Items.BLUE_BLAST_PROOF_GLASS;
                    }
                    case "DARK_PURPLE" -> {
                        return Items.PURPLE_BLAST_PROOF_GLASS;
                    }
                    case "LIGHT_PURPLE" -> {
                        return Items.PINK_BLAST_PROOF_GLASS;
                    }
                    case "BLACK" -> {
                        return Items.BLACK_BLAST_PROOF_GLASS;
                    }
                    case "WHITE" -> {
                        return Items.WHITE_BLAST_PROOF_GLASS;
                    }
                    case "GRAY" -> {
                        return Items.LIGHT_GREY_BLAST_PROOF_GLASS;
                    }
                    case "DARK_GRAY" -> {
                        return Items.GREY_BLAST_PROOF_GLASS;
                    }
                    case "DARK_RED" -> {
                        return Items.MAGENTA_BLAST_PROOF_GLASS;
                    }
                    default -> {
                        return Items.SPECTATOR_ARMOR;
                    }
                }
            }
            case TERRACOTTA -> {
                switch (string.toUpperCase()) {
                    case "RED" -> {
                        return Items.RED_TERRACOTTA;
                    }
                    case "GOLD" -> {
                        return Items.ORANGE_TERRACOTTA;
                    }
                    case "YELLOW" -> {
                        return Items.YELLOW_TERRACOTTA;
                    }
                    case "GREEN" -> {
                        return Items.LIME_TERRACOTTA;
                    }
                    case "DARK_GREEN" -> {
                        return Items.GREEN_TERRACOTTA;
                    }
                    case "DARK_AQUA" -> {
                        return Items.CYAN_TERRACOTTA;
                    }
                    case "AQUA" -> {
                        return Items.LIGHT_BLUE_TERRACOTTA;
                    }
                    case "BLUE" -> {
                        return Items.BLUE_TERRACOTTA;
                    }
                    case "DARK_PURPLE" -> {
                        return Items.PURPLE_TERRACOTTA;
                    }
                    case "LIGHT_PURPLE" -> {
                        return Items.PINK_TERRACOTTA;
                    }
                    case "BLACK" -> {
                        return Items.BLACK_TERRACOTTA;
                    }
                    case "WHITE" -> {
                        return Items.WHITE_TERRACOTTA;
                    }
                    case "GRAY" -> {
                        return Items.LIGHT_GREY_TERRACOTTA;
                    }
                    case "DARK_GRAY" -> {
                        return Items.GREY_TERRACOTTA;
                    }
                    case "DARK_RED" -> {
                        return Items.MAGENTA_TERRACOTTA;
                    }
                    default -> {
                        return Items.SPECTATOR_ARMOR;
                    }
                }
            }
        }
        return Items.SPECTATOR_ARMOR;
    }
}

package net.cytonic.cytonicbedwars.managers;

import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytonicbedwars.utils.Items;
import net.cytonic.cytosis.Cytosis;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemComponent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.component.CustomData;
import net.minestom.server.item.component.HeadProfile;
import net.minestom.server.utils.Unit;
import java.util.List;
import java.util.Objects;
import static net.cytonic.utils.MiniMessageTemplate.MM;

public class MenuManager {

    private static String UUID_KEY;

    public MenuManager() {
        UUID_KEY = "UUID";
    }

    public Inventory getSpectatorSelectorMenu() {
        Inventory inv = new Inventory(InventoryType.CHEST_3_ROW, "Spectate a Player");
        CytonicBedWars.getGameManager().getAlivePlayers().forEach(uuid -> {
            CompoundBinaryTag.Builder builder = CompoundBinaryTag.builder();
            builder.putString(UUID_KEY, uuid.toString());
            ItemStack head = ItemStack.builder(Material.PLAYER_HEAD)
                    .set(ItemComponent.PROFILE, new HeadProfile(Objects.requireNonNull(Cytosis.getPlayer(uuid).orElseThrow().getSkin())))
                    .set(ItemComponent.ITEM_NAME, MM."<GREEN>\{Cytosis.getPlayer(uuid).orElseThrow().getName()}")
                    .set(ItemComponent.LORE, List.of(MM."<GRAY>Click to teleport to \{Cytosis.getPlayer(uuid).orElseThrow().getName()}"))
                    .set(ItemComponent.CUSTOM_DATA, new CustomData(builder.build()))
                    .build();
            inv.addItemStack(head);
        });
        return inv;
    }

    public Inventory getSpectatorSpeedMenu() {
        Inventory menu = new Inventory(InventoryType.CHEST_3_ROW, "Flight Speed");

        ItemStack tenthSpeed = ItemStack.builder(Material.LEATHER_BOOTS)
                .set(ItemComponent.HIDE_TOOLTIP, Unit.INSTANCE)
                .set(ItemComponent.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE)
                .set(ItemComponent.ITEM_NAME, MM."<GREEN>0.1x flight speed")
                .set(ItemComponent.CUSTOM_DATA, new CustomData(CompoundBinaryTag.builder().putString("FLIGHT_SPEED", "0.01f").build()))
                .build();
        menu.setItemStack(11, tenthSpeed);

        ItemStack halfSpeed = ItemStack.builder(Material.CHAINMAIL_BOOTS)
                .set(ItemComponent.HIDE_TOOLTIP, Unit.INSTANCE)
                .set(ItemComponent.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE)
                .set(ItemComponent.ITEM_NAME, MM."<GREEN>.5x flight speed")
                .set(ItemComponent.CUSTOM_DATA, new CustomData(CompoundBinaryTag.builder().putString("FLIGHT_SPEED", "0.05f").build()))
                .build();
        menu.setItemStack(12, halfSpeed);

        ItemStack normalSpeed = ItemStack.builder(Material.IRON_BOOTS)
                .set(ItemComponent.HIDE_TOOLTIP, Unit.INSTANCE)
                .set(ItemComponent.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE)
                .set(ItemComponent.ITEM_NAME, MM."<GREEN>1x flight speed")
                .set(ItemComponent.CUSTOM_DATA, new CustomData(CompoundBinaryTag.builder().putString("FLIGHT_SPEED", "0.1f").build()))
                .build();
        menu.setItemStack(13, normalSpeed);

        ItemStack x2Speed = ItemStack.builder(Material.GOLDEN_BOOTS)
                .set(ItemComponent.HIDE_TOOLTIP, Unit.INSTANCE)
                .set(ItemComponent.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE)
                .set(ItemComponent.ITEM_NAME, MM."<GREEN>2x flight speed")
                .set(ItemComponent.CUSTOM_DATA, new CustomData(CompoundBinaryTag.builder().putString("FLIGHT_SPEED", "0.2f").build()))
                .build();
        menu.setItemStack(14, x2Speed);

        ItemStack x5Speed = ItemStack.builder(Material.DIAMOND_BOOTS)
                .set(ItemComponent.HIDE_TOOLTIP, Unit.INSTANCE)
                .set(ItemComponent.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE)
                .set(ItemComponent.ITEM_NAME, MM."<GREEN>5x flight speed")
                .set(ItemComponent.CUSTOM_DATA, new CustomData(CompoundBinaryTag.builder().putString("FLIGHT_SPEED", "0.5f").build()))
                .build();
        menu.setItemStack(15, x5Speed);
        return menu;
    }

    public Inventory getBlocksShop() {
        Inventory blocks = new Inventory(InventoryType.CHEST_5_ROW, "Item Shop ➜ Building Blocks");

        blocks.setItemStack(1, Items.MENU_BLOCKS);
        blocks.setItemStack(3, Items.MENU_COMBAT);
        blocks.setItemStack(2, Items.MENU_ARMOR);
        blocks.setItemStack(4, Items.MENU_TOOLS);
        blocks.setItemStack(5, Items.MENU_POTIONS);
        blocks.setItemStack(6, Items.MENU_UTILS);
        blocks.setItemStack(7, Items.MENU_CUSTOM_ITEMS);
        blocks.setItemStack(21, Items.MENU_WOOL);
        blocks.setItemStack(22, Items.MENU_BLAST_GLASS);
        blocks.setItemStack(23, Items.MENU_END_STONE);
        blocks.setItemStack(30, Items.MENU_TERRACOTTA);
        blocks.setItemStack(31, Items.MENU_OBSIDIAN);
        blocks.setItemStack(32, Items.MENU_PLANKS);
        blocks.setItemStack(10, Items.MENU_SELECTED_PAGE);

        for (int i = 0; i < 100; i++) blocks.addItemStack(Items.MENU_FILLER);
        return blocks;
    }

    public Inventory getArmorShop() {
        Inventory armor = new Inventory(InventoryType.CHEST_5_ROW, "Item Shop ➜ Armor");
        // header
        armor.setItemStack(1, Items.MENU_BLOCKS);
        armor.setItemStack(3, Items.MENU_COMBAT);
        armor.setItemStack(2, Items.MENU_ARMOR);
        armor.setItemStack(4, Items.MENU_TOOLS);
        armor.setItemStack(5, Items.MENU_POTIONS);
        armor.setItemStack(6, Items.MENU_UTILS);
        armor.setItemStack(7, Items.MENU_CUSTOM_ITEMS);
        armor.setItemStack(22, Items.MENU_CHAINMAIL_BOOTS);
        armor.setItemStack(30, Items.MENU_IRON_BOOTS);
        armor.setItemStack(31, Items.MENU_DIAMOND_BOOTS);
        armor.setItemStack(32, Items.MENU_NETHERITE_BOOTS);
        armor.setItemStack(11, Items.MENU_SELECTED_PAGE);

        for (int i = 0; i < 100; i++) armor.addItemStack(Items.MENU_FILLER);
        return armor;
    }

    public Inventory getCombatShop() {
        Inventory weapons = new Inventory(InventoryType.CHEST_5_ROW, "Item Shop ➜ Weapons");

        weapons.setItemStack(1, Items.MENU_BLOCKS);
        weapons.setItemStack(2, Items.MENU_ARMOR);
        weapons.setItemStack(3, Items.MENU_COMBAT);
        weapons.setItemStack(4, Items.MENU_TOOLS);
        weapons.setItemStack(5, Items.MENU_POTIONS);
        weapons.setItemStack(6, Items.MENU_UTILS);
        weapons.setItemStack(7, Items.MENU_CUSTOM_ITEMS);

        weapons.setItemStack(19, Items.MENU_STONE_SWORD);
        weapons.setItemStack(20, Items.MENU_IRON_SWORD);
        weapons.setItemStack(21, Items.MENU_DIAMOND_SWORD);
        weapons.setItemStack(22, Items.MENU_SHIELD);
        weapons.setItemStack(23, Items.MENU_CROSSBOW_1);
        weapons.setItemStack(24, Items.MENU_CROSSBOW_2);
        weapons.setItemStack(25, Items.MENU_CROSSBOW_3);
        weapons.setItemStack(28, Items.MENU_BOW_1);
        weapons.setItemStack(29, Items.MENU_BOW_2);
        weapons.setItemStack(30, Items.MENU_BOW_3);
        weapons.setItemStack(31, Items.MENU_ARROW);
        weapons.setItemStack(32, Items.MENU_TRIDENT_1);
        weapons.setItemStack(33, Items.MENU_TRIDENT_2);
        weapons.setItemStack(34, Items.MENU_TRIDENT_3);
        weapons.setItemStack(12, Items.MENU_SELECTED_PAGE);

        for (int i = 0; i < 100; i++) weapons.addItemStack(Items.MENU_FILLER);
        return weapons;
    }

    public Inventory getToolShop(Player player) {
        if (!CytonicBedWars.getGameManager().STARTED)
            throw new IllegalStateException("The game must be started to generate a tool shop!");

        Inventory tools = new Inventory(InventoryType.CHEST_5_ROW, "Item Shop ➜ Tools");

        tools.setItemStack(1, Items.MENU_BLOCKS);
        tools.setItemStack(2, Items.MENU_ARMOR);
        tools.setItemStack(3, Items.MENU_COMBAT);
        tools.setItemStack(4, Items.MENU_TOOLS);
        tools.setItemStack(5, Items.MENU_POTIONS);
        tools.setItemStack(6, Items.MENU_UTILS);
        tools.setItemStack(7, Items.MENU_CUSTOM_ITEMS);
        tools.setItemStack(13, Items.MENU_SELECTED_PAGE);

        switch (CytonicBedWars.getGameManager().axes.get(player.getUuid())) {
            case NONE -> tools.setItemStack(30, Items.MENU_WOODEN_AXE);
            case WOODEN -> tools.setItemStack(30, Items.MENU_STONE_AXE);
            case STONE -> tools.setItemStack(30, Items.MENU_IRON_AXE);
            case IRON -> tools.setItemStack(30, Items.MENU_DIAMOND_AXE);
            case DIAMOND -> tools.setItemStack(30, Items.MENU_DIAMOND_AXE.withLore(MM."<green><bold>Already purchased!"));
            default -> tools.setItemStack(30, Items.SPECTATOR_ARMOR);
        }

        switch (CytonicBedWars.getGameManager().pickaxes.get(player.getUuid())) {
            case NONE -> tools.setItemStack(31, Items.MENU_WOODEN_PICKAXE);
            case WOODEN -> tools.setItemStack(31, Items.MENU_STONE_PICKAXE);
            case STONE -> tools.setItemStack(31, Items.MENU_IRON_PICKAXE);
            case IRON -> tools.setItemStack(31, Items.MENU_DIAMOND_PICKAXE);
            case DIAMOND -> tools.setItemStack(31, Items.MENU_DIAMOND_PICKAXE.withLore(MM."<green><bold>Already purchased!"));
            default -> tools.setItemStack(31, Items.SPECTATOR_ARMOR);
        }

        if (CytonicBedWars.getGameManager().shears.get(player.getUuid()))
            tools.setItemStack(32, Items.MENU_SHEARS.withLore(MM."<green><bold>Already purchased!"));
        else tools.setItemStack(32, Items.MENU_SHEARS);

        for (int i = 0; i < 100; i++) tools.addItemStack(Items.MENU_FILLER);
        return tools;
    }

    public Inventory getPotionShop() {
        Inventory potions = new Inventory(InventoryType.CHEST_5_ROW, "Item Shop ➜ Potions");

        potions.setItemStack(1, Items.MENU_BLOCKS);
        potions.setItemStack(2, Items.MENU_ARMOR);
        potions.setItemStack(3, Items.MENU_COMBAT);
        potions.setItemStack(4, Items.MENU_TOOLS);
        potions.setItemStack(5, Items.MENU_POTIONS);
        potions.setItemStack(6, Items.MENU_UTILS);
        potions.setItemStack(7, Items.MENU_CUSTOM_ITEMS);
        potions.setItemStack(14, Items.MENU_SELECTED_PAGE);

        potions.setItemStack(29, Items.MENU_FIRE_RESISTENCE_POTION);
        potions.setItemStack(30, Items.MENU_INVISIBILITY_POTION);
        potions.setItemStack(32, Items.MENU_JUMP_BOOST_POTION);
        potions.setItemStack(33, Items.MENU_SPEED_POTION);

        for (int i = 0; i < 100; i++) potions.addItemStack(Items.MENU_FILLER);
        return potions;
    }

    public Inventory getUtilShop() {
        Inventory util = new Inventory(InventoryType.CHEST_5_ROW, "Item Shop ➜ Utilities");

        util.setItemStack(1, Items.MENU_BLOCKS);
        util.setItemStack(2, Items.MENU_ARMOR);
        util.setItemStack(3, Items.MENU_COMBAT);
        util.setItemStack(4, Items.MENU_TOOLS);
        util.setItemStack(5, Items.MENU_POTIONS);
        util.setItemStack(6, Items.MENU_UTILS);
        util.setItemStack(7, Items.MENU_CUSTOM_ITEMS);
        util.setItemStack(15, Items.MENU_SELECTED_PAGE);

        for (int i = 0; i < 100; i++) util.addItemStack(Items.MENU_FILLER);
        return util;
    }

    public Inventory getRotatingShop() {
        Inventory rotating = new Inventory(InventoryType.CHEST_5_ROW, "Item Shop ➜ Rotating Items");

        rotating.setItemStack(1, Items.MENU_BLOCKS);
        rotating.setItemStack(2, Items.MENU_ARMOR);
        rotating.setItemStack(3, Items.MENU_COMBAT);
        rotating.setItemStack(4, Items.MENU_TOOLS);
        rotating.setItemStack(5, Items.MENU_POTIONS);
        rotating.setItemStack(6, Items.MENU_UTILS);
        rotating.setItemStack(7, Items.MENU_CUSTOM_ITEMS);
        rotating.setItemStack(16, Items.MENU_SELECTED_PAGE);

        for (int i = 0; i < 100; i++) rotating.addItemStack(Items.MENU_FILLER);
        return rotating;
    }
}

package net.cytonic.cytonicbedwars.managers;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytonicbedwars.data.enums.ArmorLevel;
import net.cytonic.cytonicbedwars.data.enums.AxeLevel;
import net.cytonic.cytonicbedwars.data.enums.MappableItem;
import net.cytonic.cytonicbedwars.data.enums.PickaxeLevel;
import net.cytonic.cytonicbedwars.utils.Items;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.utils.MiniMessageTemplate;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.sound.Sound;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemComponent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.component.CustomData;
import net.minestom.server.item.component.HeadProfile;
import net.minestom.server.sound.SoundEvent;
import net.minestom.server.utils.Unit;

import java.util.List;
import java.util.Objects;

import static net.cytonic.utils.MiniMessageTemplate.MM;

@NoArgsConstructor
public class MenuManager {

    private static final String UUID_KEY = "UUID";

    public Inventory getArmorShop() {
        Inventory inventory = new Inventory(InventoryType.CHEST_5_ROW, "Item Shop ➜ Armor");

        inventory.setItemStack(1, Items.MENU_BLOCKS);
        inventory.setItemStack(3, Items.MENU_COMBAT);
        inventory.setItemStack(2, Items.MENU_ARMOR);
        inventory.setItemStack(4, Items.MENU_TOOLS);
        inventory.setItemStack(5, Items.MENU_POTIONS);
        inventory.setItemStack(6, Items.MENU_UTILS);
        inventory.setItemStack(7, Items.MENU_CUSTOM_ITEMS);

        inventory.setItemStack(22, Items.MENU_CHAINMAIL_BOOTS);
        inventory.setItemStack(30, Items.MENU_IRON_BOOTS);
        inventory.setItemStack(31, Items.MENU_DIAMOND_BOOTS);
        inventory.setItemStack(32, Items.MENU_NETHERITE_BOOTS);
        inventory.setItemStack(11, Items.MENU_SELECTED_PAGE);

        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItemStack(i).isAir()) {
                inventory.setItemStack(i, Items.MENU_FILLER);
            }
        }
        inventory.addInventoryCondition((player, _, _, result) -> {
            result.setCancel(true);
            switch (Objects.requireNonNull(result.getClickedItem().get(ItemComponent.CUSTOM_DATA)).nbt().getString(Items.NAMESPACE).toUpperCase()) {
                case "MENU_BLOCK" -> player.openInventory(getBlocksShop());
                case "MENU_ARMOR" -> player.openInventory(getArmorShop());
                case "MENU_COMBAT" -> player.openInventory(getCombatShop());
                case "MENU_TOOLS" -> player.openInventory(getToolShop(player));
                case "MENU_POTIONS" -> player.openInventory(getPotionShop());
                case "MENU_UTILS" -> player.openInventory(getUtilShop());
                case "MENU_CUSTOM" -> player.openInventory(getRotatingShop());

                case "CHAINMAIL_BOOTS" -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("IRON", 40, player)) {
                        player.getInventory().setLeggings(Items.CHAINMAIL_LEGS);
                        player.getInventory().setBoots(Items.CHAINMAIL_BOOTS);
                        CytonicBedWars.getGameManager().setArmor(player.getUuid(), ArmorLevel.CHAINMAIL);
                    } else {
                        player.sendMessage(MM."<red>You need at least 40 iron to buy this!");
                        player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                    }
                }
                case "IRON_BOOTS" -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("GOLD", 12, player)) {
                        player.getInventory().setLeggings(Items.IRON_LEGS);
                        player.getInventory().setBoots(Items.IRON_BOOTS);
                        CytonicBedWars.getGameManager().setArmor(player.getUuid(), ArmorLevel.IRON);
                    } else {
                        player.sendMessage(MM."<red>You need at least 12 Gold to buy this!");
                        player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                    }
                }
                case "DIAMOND_BOOTS" -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("EMERALD", 6, player)) {
                        player.getInventory().setLeggings(Items.DIAMOND_LEGS);
                        player.getInventory().setBoots(Items.DIAMOND_BOOTS);
                        CytonicBedWars.getGameManager().setArmor(player.getUuid(), ArmorLevel.DIAMOND);
                    } else {
                        player.sendMessage(MM."<red>You need at least 6 Emeralds to buy this!");
                        player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                    }
                }
                case "NETHERITE_BOOTS" -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("EMERALD", 16, player)) {
                        player.getInventory().setLeggings(Items.NETHERITE_LEGS);
                        player.getInventory().setBoots(Items.NETHERITE_BOOTS);
                        CytonicBedWars.getGameManager().setArmor(player.getUuid(), ArmorLevel.NETHERITE);
                    } else {
                        player.sendMessage(MM."<red>You need at least 16 Emeralds to buy this!");
                        player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                    }
                }
            }
        });
        return inventory;
    }

    public Inventory getCombatShop() {
        Inventory inventory = new Inventory(InventoryType.CHEST_5_ROW, "Item Shop ➜ Weapons");

        inventory.setItemStack(1, Items.MENU_BLOCKS);
        inventory.setItemStack(2, Items.MENU_ARMOR);
        inventory.setItemStack(3, Items.MENU_COMBAT);
        inventory.setItemStack(4, Items.MENU_TOOLS);
        inventory.setItemStack(5, Items.MENU_POTIONS);
        inventory.setItemStack(6, Items.MENU_UTILS);
        inventory.setItemStack(7, Items.MENU_CUSTOM_ITEMS);

        inventory.setItemStack(19, Items.MENU_STONE_SWORD);
        inventory.setItemStack(20, Items.MENU_IRON_SWORD);
        inventory.setItemStack(21, Items.MENU_DIAMOND_SWORD);
        inventory.setItemStack(22, Items.MENU_SHIELD);
        inventory.setItemStack(23, Items.MENU_CROSSBOW_1);
        inventory.setItemStack(24, Items.MENU_CROSSBOW_2);
        inventory.setItemStack(25, Items.MENU_CROSSBOW_3);
        inventory.setItemStack(28, Items.MENU_BOW_1);
        inventory.setItemStack(29, Items.MENU_BOW_2);
        inventory.setItemStack(30, Items.MENU_BOW_3);
        inventory.setItemStack(31, Items.MENU_ARROW);
        inventory.setItemStack(32, Items.MENU_TRIDENT_1);
        inventory.setItemStack(33, Items.MENU_TRIDENT_2);
        inventory.setItemStack(34, Items.MENU_TRIDENT_3);
        inventory.setItemStack(12, Items.MENU_SELECTED_PAGE);

        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItemStack(i).isAir()) {
                inventory.setItemStack(i, Items.MENU_FILLER);
            }
        }

        inventory.addInventoryCondition((player, _, _, result) -> {
            result.setCancel(true);
            switch (Objects.requireNonNull(result.getClickedItem().get(ItemComponent.CUSTOM_DATA)).nbt().getString(Items.NAMESPACE).toUpperCase()) {
                case "MENU_BLOCK" -> player.openInventory(getBlocksShop());
                case "MENU_ARMOR" -> player.openInventory(getArmorShop());
                case "MENU_COMBAT" -> player.openInventory(getCombatShop());
                case "MENU_TOOLS" -> player.openInventory(getToolShop(player));
                case "MENU_POTIONS" -> player.openInventory(getPotionShop());
                case "MENU_UTILS" -> player.openInventory(getUtilShop());
                case "MENU_CUSTOM" -> player.openInventory(getRotatingShop());

                case "MENU_STONE_SWORD" -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("IRON", 10, player)) {
                            player.getInventory().addItemStack(Items.STONE_SWORD);
                            player.sendMessage(MM."<green>You bought a Stone Sword!");
                            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());
                        } else {
                            player.sendMessage(MM."<red>You need at least 10 iron to buy this!");
                            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        }
                    } else {
                        player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        player.sendMessage(MM."<red>You don't have space in your inventory!");
                    }
                }
                case "MENU_IRON_SWORD" -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("GOLD", 7, player)) {
                            player.getInventory().addItemStack(Items.IRON_SWORD);
                            player.sendMessage(MM."<green>You bought an Iron Sword!");
                            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());
                        } else {
                            player.sendMessage(MM."<red>You need at least 7 gold to buy this!");
                            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        }
                    } else {
                        player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        player.sendMessage(MM."<red>You don't have space in your inventory!");
                    }
                }
                case "MENU_DIAMOND_SWORD" -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("EMERALD", 6, player)) {
                            player.getInventory().addItemStack(Items.DIAMOND_SWORD);
                            player.sendMessage(MM."<green>You bought a Diamond Sword!");
                            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());
                        } else {
                            player.sendMessage(MM."<red>You need at least 6 Emeralds to buy this!");
                            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        }
                    } else {
                        player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        player.sendMessage(MM."<red>You don't have space in your inventory!");
                    }
                }
                case "MENU_SHIELD" -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("GOLD", 3, player)) {
                            player.getInventory().addItemStack(Items.SHIELD);
                            player.sendMessage(MM."<green>You bought a Shield!");
                            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());
                        } else {
                            player.sendMessage(MM."<red>You need at least 3 Gold to buy this!");
                            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        }
                    } else {
                        player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        player.sendMessage(MM."<red>You don't have space in your inventory!");
                    }
                }
                case "MENU_CROSSBOW_1" -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("GOLD", 12, player)) {
                            player.getInventory().addItemStack(Items.CROSSBOW_1);
                            player.sendMessage(MM."<green>You bought a Crossbow!");
                            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());
                        } else {
                            player.sendMessage(MM."<red>You need at least 12 Gold to buy this!");
                            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        }
                    } else {
                        player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        player.sendMessage(MM."<red>You don't have space in your inventory!");
                    }
                }
                case "MENU_CROSSBOW_2" -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("GOLD", 20, player)) {
                            player.getInventory().addItemStack(Items.CROSSBOW_2);
                            player.sendMessage(MM."<green>You bought a Crossbow!");
                            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());
                        } else {
                            player.sendMessage(MM."<red>You need at least 20 Gold to buy this!");
                            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        }
                    } else {
                        player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        player.sendMessage(MM."<red>You don't have space in your inventory!");
                    }
                }
                case "MENU_CROSSBOW_3" -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("EMERALD", 6, player)) {
                            player.getInventory().addItemStack(Items.CROSSBOW_3);
                            player.sendMessage(MM."<green>You bought a Crossbow!");
                            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());
                        } else {
                            player.sendMessage(MM."<red>You need at least 6 Emeralds to buy this!");
                            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        }
                    } else {
                        player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        player.sendMessage(MM."<red>You don't have space in your inventory!");
                    }
                }
                case "MENU_BOW_1" -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("GOLD", 10, player)) {
                            player.getInventory().addItemStack(Items.BOW_1);
                            player.sendMessage(MM."<green>You bought a bow!");
                            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());
                        } else {
                            player.sendMessage(MM."<red>You need at least 10 Gold to buy this!");
                            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        }
                    } else {
                        player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        player.sendMessage(MM."<red>You don't have space in your inventory!");
                    }
                }
                case "MENU_BOW_2" -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("GOLD", 18, player)) {
                            player.getInventory().addItemStack(Items.BOW_2);
                            player.sendMessage(MM."<green>You bought a bow!");
                            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());
                        } else {
                            player.sendMessage(MM."<red>You need at least 18 Gold to buy this!");
                            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        }
                    } else {
                        player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        player.sendMessage(MM."<red>You don't have space in your inventory!");
                    }
                }
                case "MENU_BOW_3" -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("EMERALD", 5, player)) {
                            player.getInventory().addItemStack(Items.BOW_3);
                            player.sendMessage(MM."<green>You bought a bow!");
                            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());
                        } else {
                            player.sendMessage(MM."<red>You need at least 5 Emeralds to buy this!");
                            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        }
                    } else {
                        player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        player.sendMessage(MM."<red>You don't have space in your inventory!");
                    }
                }
                case "MENU_ARROW" -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("GOLD", 2, player)) {
                            ItemStack item = Items.ARROW;
                            item = item.withAmount(8);
                            player.getInventory().addItemStack(item);
                            player.sendMessage(MM."<green>You bought some arrows!");
                            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());
                        } else {
                            player.sendMessage(MM."<red>You need at least 2 Gold to buy this!");
                            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        }
                    } else {
                        player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        player.sendMessage(MM."<red>You don't have space in your inventory!");
                    }
                }
                case "MENU_TRIDENT_1" -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("GOLD", 12, player)) {
                            player.getInventory().addItemStack(Items.TRIDENT_1);
                            player.sendMessage(MM."<green>You bought a trident!");
                            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());
                        } else {
                            player.sendMessage(MM."<red>You need at least 12 Gold to buy this!");
                            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        }
                    } else {
                        player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        player.sendMessage(MM."<red>You don't have space in your inventory!");
                    }
                }
                case "MENU_TRIDENT_2" -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("EMERALD", 5, player)) {
                            player.getInventory().addItemStack(Items.TRIDENT_2);
                            player.sendMessage(MM."<green>You bought a trident!");
                            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());
                        } else {
                            player.sendMessage(MM."<red>You need at least 5 Emeralds to buy this!");
                            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        }
                    } else {
                        player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        player.sendMessage(MM."<red>You don't have space in your inventory!");
                    }
                }
                case "MENU_TRIDENT_3" -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("EMERALD", 5, player)) {
                            player.getInventory().addItemStack(Items.TRIDENT_3);
                            player.sendMessage(MM."<green>You bought a trident!");
                            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());
                        } else {
                            player.sendMessage(MM."<red>You need at least 5 Emeralds to buy this!");
                            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        }
                    } else {
                        player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        player.sendMessage(MM."<red>You don't have space in your inventory!");
                    }
                }
            }
        });

        return inventory;
    }

    public Inventory getToolShop(Player p) {
        if (!CytonicBedWars.getGameManager().STARTED)
            throw new IllegalStateException("The game must be started to generate a tool shop!");

        Inventory inventory = new Inventory(InventoryType.CHEST_5_ROW, "Item Shop ➜ Tools");

        inventory.setItemStack(1, Items.MENU_BLOCKS);
        inventory.setItemStack(2, Items.MENU_ARMOR);
        inventory.setItemStack(3, Items.MENU_COMBAT);
        inventory.setItemStack(4, Items.MENU_TOOLS);
        inventory.setItemStack(5, Items.MENU_POTIONS);
        inventory.setItemStack(6, Items.MENU_UTILS);
        inventory.setItemStack(7, Items.MENU_CUSTOM_ITEMS);
        inventory.setItemStack(13, Items.MENU_SELECTED_PAGE);

        switch (CytonicBedWars.getGameManager().getAxe(p.getUuid())) {
            case NONE -> inventory.setItemStack(30, Items.MENU_WOODEN_AXE);
            case WOODEN -> inventory.setItemStack(30, Items.MENU_STONE_AXE);
            case STONE -> inventory.setItemStack(30, Items.MENU_IRON_AXE);
            case IRON -> inventory.setItemStack(30, Items.MENU_DIAMOND_AXE);
            case DIAMOND ->
                    inventory.setItemStack(30, Items.MENU_DIAMOND_AXE.withLore(MM."<green><bold>Already purchased!"));
            default -> inventory.setItemStack(30, Items.SPECTATOR_ARMOR);
        }

        switch (CytonicBedWars.getGameManager().getPickaxe(p.getUuid())) {
            case NONE -> inventory.setItemStack(31, Items.MENU_WOODEN_PICKAXE);
            case WOODEN -> inventory.setItemStack(31, Items.MENU_STONE_PICKAXE);
            case STONE -> inventory.setItemStack(31, Items.MENU_IRON_PICKAXE);
            case IRON -> inventory.setItemStack(31, Items.MENU_DIAMOND_PICKAXE);
            case DIAMOND ->
                    inventory.setItemStack(31, Items.MENU_DIAMOND_PICKAXE.withLore(MM."<green><bold>Already purchased!"));
            default -> inventory.setItemStack(31, Items.SPECTATOR_ARMOR);
        }

        if (CytonicBedWars.getGameManager().shears.get(p.getUuid()))
            inventory.setItemStack(32, Items.MENU_SHEARS.withLore(MM."<green><bold>Already purchased!"));
        else inventory.setItemStack(32, Items.MENU_SHEARS);

        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItemStack(i).isAir()) {
                inventory.setItemStack(i, Items.MENU_FILLER);
            }
        }
        inventory.addInventoryCondition((player, _, _, result) -> {
            result.setCancel(true);
            switch (Objects.requireNonNull(result.getClickedItem().get(ItemComponent.CUSTOM_DATA)).nbt().getString(Items.NAMESPACE).toUpperCase()) {
                case "MENU_BLOCK" -> player.openInventory(getBlocksShop());
                case "MENU_ARMOR" -> player.openInventory(getArmorShop());
                case "MENU_COMBAT" -> player.openInventory(getCombatShop());
                case "MENU_TOOLS" -> player.openInventory(getToolShop(player));
                case "MENU_POTIONS" -> player.openInventory(getPotionShop());
                case "MENU_UTILS" -> player.openInventory(getUtilShop());
                case "MENU_CUSTOM" -> player.openInventory(getRotatingShop());

                case "WOODEN_AXE" -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("IRON", 10, player)) {
                            CytonicBedWars.getGameManager().setAxe(player.getUuid(), AxeLevel.WOODEN);
                            CytonicBedWars.getGameManager().getPlayerInventoryManager().setAxe(AxeLevel.WOODEN, player);
                            player.sendMessage(MM."<green>You purchased a wooden axe!");
                            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());
                            player.openInventory(getToolShop(player));
                        } else {
                            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                            player.sendMessage(MM."<red>You must have atleast 10 Iron to buy this!");
                        }
                    }
                }
                case "STONE_AXE" -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("IRON", 20, player)) {
                            CytonicBedWars.getGameManager().setAxe(player.getUuid(), AxeLevel.STONE);
                            CytonicBedWars.getGameManager().getPlayerInventoryManager().setAxe(AxeLevel.STONE, player);
                            player.sendMessage(MM."<green>You purchased a stone axe!");
                            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());
                            player.openInventory(getToolShop(player));
                        } else {
                            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                            player.sendMessage(MM."<red>You must have atleast 20 Iron to buy this!");
                        }
                    }
                }
                case "IRON_AXE" -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("GOLD", 6, player)) {
                            CytonicBedWars.getGameManager().setAxe(player.getUuid(), AxeLevel.IRON);
                            CytonicBedWars.getGameManager().getPlayerInventoryManager().setAxe(AxeLevel.IRON, player);
                            player.sendMessage(MM."<green>You purchased an iron axe!");
                            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());
                            player.openInventory(getToolShop(player));
                        } else {
                            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                            player.sendMessage(MM."<red>You must have atleast 6 Gold to buy this!");
                        }
                    }
                }
                case "DIAMOND_AXE" -> {
                    if (CytonicBedWars.getGameManager().getAxe(player.getUuid()).equals(AxeLevel.IRON)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                            if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("EMERALD", 3, player)) {
                                CytonicBedWars.getGameManager().setAxe(player.getUuid(), AxeLevel.DIAMOND);
                                CytonicBedWars.getGameManager().getPlayerInventoryManager().setAxe(AxeLevel.DIAMOND, player);
                                player.sendMessage(MM."<green>You purchased a diamond axe!");
                                player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());
                                player.openInventory(getToolShop(player));
                            } else {
                                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                                player.sendMessage(MM."<red>You must have atleast 3 Emeralds to buy this!");
                            }
                        }
                    } else if (CytonicBedWars.getGameManager().getAxe(player.getUuid()).equals(AxeLevel.DIAMOND)) {
                        player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        player.sendMessage(MM."<red>You already purchased a diamond axe!");
                    }
                }
                case "WOODEN_PICKAXE" -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("IRON", 10, player)) {
                            CytonicBedWars.getGameManager().setPickaxe(player.getUuid(), PickaxeLevel.WOODEN);
                            CytonicBedWars.getGameManager().getPlayerInventoryManager().setPickaxe(PickaxeLevel.WOODEN, player);
                            player.sendMessage(MM."<green>You purchased a wooden pickaxe!");
                            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());
                            player.openInventory(getToolShop(player));
                        } else {
                            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                            player.sendMessage(MM."<red>You must have atleast 10 Iron to buy this!");
                        }
                    }
                }
                case "STONE_PICKAXE" -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("IRON", 20, player)) {
                            CytonicBedWars.getGameManager().setPickaxe(player.getUuid(), PickaxeLevel.STONE);
                            CytonicBedWars.getGameManager().getPlayerInventoryManager().setPickaxe(PickaxeLevel.STONE, player);
                            player.sendMessage(MM."<green>You purchased a stone pickaxe!");
                            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());
                            player.openInventory(getToolShop(player));
                        } else {
                            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                            player.sendMessage(MM."<red>You must have atleast 20 Iron to buy this!");
                        }
                    }
                }
                case "IRON_PICKAXE" -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("GOLD", 6, player)) {
                            CytonicBedWars.getGameManager().setPickaxe(player.getUuid(), PickaxeLevel.IRON);
                            CytonicBedWars.getGameManager().getPlayerInventoryManager().setPickaxe(PickaxeLevel.IRON, player);
                            player.sendMessage(MM."<green>You purchased an iron pickaxe!");
                            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());
                            player.openInventory(getToolShop(player));
                        } else {
                            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                            player.sendMessage(MM."<red>You must have atleast 6 Gold to buy this!");
                        }
                    }
                }
                case "DIAMOND_PICKAXE" -> {
                    if (CytonicBedWars.getGameManager().getPickaxe(player.getUuid()).equals(PickaxeLevel.IRON)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                            if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("EMERALD", 3, player)) {
                                CytonicBedWars.getGameManager().setPickaxe(player.getUuid(), PickaxeLevel.DIAMOND);
                                CytonicBedWars.getGameManager().getPlayerInventoryManager().setPickaxe(PickaxeLevel.DIAMOND, player);
                                player.sendMessage(MM."<green>You purchased a diamond pickaxe!");
                                player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());
                                player.openInventory(getToolShop(player));
                            } else {
                                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                                player.sendMessage(MM."<red>You must have atleast 3 Emeralds to buy this!");
                            }
                        }
                    } else if (CytonicBedWars.getGameManager().getPickaxe(player.getUuid()).equals(PickaxeLevel.DIAMOND)) {
                        player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        player.sendMessage(MM."<red>You already purchased a diamond pickaxe!");
                    }
                }
                case "SHEARS" -> {
                    if (CytonicBedWars.getGameManager().shears.get(player.getUuid())) {
                        player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        player.sendMessage(MM."<red>You already purchased a pair of shears!");
                    } else {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                            if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("IRON", 24, player)) {
                                CytonicBedWars.getGameManager().shears.put(player.getUuid(), true);

                                player.getInventory().addItemStack(Items.SHEARS);
                                player.sendMessage(MM."<green>You purchased a pair of shears!");
                                player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());

                                player.openInventory(getToolShop(player));
                            } else {
                                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                                player.sendMessage(MM."<red>You must have atleast 20 Iron to buy this!");
                            }
                        }
                    }
                }
            }

        });
        return inventory;
    }

    public Inventory getPotionShop() {
        Inventory inventory = new Inventory(InventoryType.CHEST_5_ROW, "Item Shop ➜ Potions");

        inventory.setItemStack(1, Items.MENU_BLOCKS);
        inventory.setItemStack(2, Items.MENU_ARMOR);
        inventory.setItemStack(3, Items.MENU_COMBAT);
        inventory.setItemStack(4, Items.MENU_TOOLS);
        inventory.setItemStack(5, Items.MENU_POTIONS);
        inventory.setItemStack(6, Items.MENU_UTILS);
        inventory.setItemStack(7, Items.MENU_CUSTOM_ITEMS);
        inventory.setItemStack(14, Items.MENU_SELECTED_PAGE);

        inventory.setItemStack(29, Items.MENU_FIRE_RESISTENCE_POTION);
        inventory.setItemStack(30, Items.MENU_INVISIBILITY_POTION);
        inventory.setItemStack(32, Items.MENU_JUMP_BOOST_POTION);
        inventory.setItemStack(33, Items.MENU_SPEED_POTION);

        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItemStack(i).isAir()) {
                inventory.setItemStack(i, Items.MENU_FILLER);
            }
        }
        inventory.addInventoryCondition((player, _, _, result) -> {
            result.setCancel(true);
            switch (Objects.requireNonNull(result.getClickedItem().get(ItemComponent.CUSTOM_DATA)).nbt().getString(Items.NAMESPACE).toUpperCase()) {
                case "MENU_BLOCK" -> player.openInventory(getBlocksShop());
                case "MENU_ARMOR" -> player.openInventory(getArmorShop());
                case "MENU_COMBAT" -> player.openInventory(getCombatShop());
                case "MENU_TOOLS" -> player.openInventory(getToolShop(player));
                case "MENU_POTIONS" -> player.openInventory(getPotionShop());
                case "MENU_UTILS" -> player.openInventory(getUtilShop());
                case "MENU_CUSTOM" -> player.openInventory(getRotatingShop());

                case "FIRE_RES_POT" -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("GOLD", 6, player)) {
                            player.getInventory().addItemStack(Items.FIRE_RESISTENCE_POTION);
                            player.sendMessage(MM."<green>You bought a Fire Resistance potion!");
                            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());
                        } else {
                            player.sendMessage(MM."<red>You need at least 6 Gold to buy this!");
                            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        }
                    } else {
                        player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        player.sendMessage(MM."<red>You don't have space in your inventory!");
                    }
                }
                case "INVIS_POT" -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("EMERALD", 1, player)) {
                            player.getInventory().addItemStack(Items.INVISIBILITY_POTION);
                            player.sendMessage(MM."<green>You bought an Invisibility potion!");
                            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());
                        } else {
                            player.sendMessage(MM."<red>You need at least 1 Emerald to buy this!");
                            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        }
                    } else {
                        player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        player.sendMessage(MM."<red>You don't have space in your inventory!");
                    }
                }
                case "JUMP_BOOST_POT" -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("EMERALD", 1, player)) {
                            player.getInventory().addItemStack(Items.JUMP_BOOST_POTION);
                            player.sendMessage(MM."<green>You bought a Jump Boost potion!");
                            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());
                        } else {
                            player.sendMessage(MM."<red>You need at least 1 Emerald to buy this!");
                            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        }
                    } else {
                        player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        player.sendMessage(MM."<red>You don't have space in your inventory!");
                    }
                }
                case "SPEED_POT" -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("EMERALD", 1, player)) {
                            player.getInventory().addItemStack(Items.SPEED_POTION);
                            player.sendMessage(MM."<green>You bought a Speed potion!");
                            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());
                        } else {
                            player.sendMessage(MM."<red>You need at least 1 Emerald to buy this!");
                            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        }
                    } else {
                        player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        player.sendMessage(MM."<red>You don't have space in your inventory!");
                    }
                }
            }
        });
        return inventory;
    }

    public Inventory getUtilShop() {
        Inventory inventory = new Inventory(InventoryType.CHEST_5_ROW, "Item Shop ➜ Utilities");

        inventory.setItemStack(1, Items.MENU_BLOCKS);
        inventory.setItemStack(2, Items.MENU_ARMOR);
        inventory.setItemStack(3, Items.MENU_COMBAT);
        inventory.setItemStack(4, Items.MENU_TOOLS);
        inventory.setItemStack(5, Items.MENU_POTIONS);
        inventory.setItemStack(6, Items.MENU_UTILS);
        inventory.setItemStack(7, Items.MENU_CUSTOM_ITEMS);
        inventory.setItemStack(15, Items.MENU_SELECTED_PAGE);

        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItemStack(i).isAir()) {
                inventory.setItemStack(i, Items.MENU_FILLER);
            }
        }
        inventory.addInventoryCondition((player, _, _, result) -> {
            result.setCancel(true);
            switch (Objects.requireNonNull(result.getClickedItem().get(ItemComponent.CUSTOM_DATA)).nbt().getString(Items.NAMESPACE).toUpperCase()) {
                case "MENU_BLOCK" -> player.openInventory(getBlocksShop());
                case "MENU_ARMOR" -> player.openInventory(getArmorShop());
                case "MENU_COMBAT" -> player.openInventory(getCombatShop());
                case "MENU_TOOLS" -> player.openInventory(getToolShop(player));
                case "MENU_POTIONS" -> player.openInventory(getPotionShop());
                case "MENU_UTILS" -> player.openInventory(getUtilShop());
                case "MENU_CUSTOM" -> player.openInventory(getRotatingShop());
            }
        });
        return inventory;
    }

    public Inventory getRotatingShop() {
        Inventory inventory = new Inventory(InventoryType.CHEST_5_ROW, "Item Shop ➜ Rotating Items");

        inventory.setItemStack(1, Items.MENU_BLOCKS);
        inventory.setItemStack(2, Items.MENU_ARMOR);
        inventory.setItemStack(3, Items.MENU_COMBAT);
        inventory.setItemStack(4, Items.MENU_TOOLS);
        inventory.setItemStack(5, Items.MENU_POTIONS);
        inventory.setItemStack(6, Items.MENU_UTILS);
        inventory.setItemStack(7, Items.MENU_CUSTOM_ITEMS);
        inventory.setItemStack(16, Items.MENU_SELECTED_PAGE);

        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItemStack(i).isAir()) {
                inventory.setItemStack(i, Items.MENU_FILLER);
            }
        }
        inventory.addInventoryCondition((player, _, _, result) -> {
            result.setCancel(true);
            switch (Objects.requireNonNull(result.getClickedItem().get(ItemComponent.CUSTOM_DATA)).nbt().getString(Items.NAMESPACE).toUpperCase()) {
                case "MENU_BLOCK" -> player.openInventory(getBlocksShop());
                case "MENU_ARMOR" -> player.openInventory(getArmorShop());
                case "MENU_COMBAT" -> player.openInventory(getCombatShop());
                case "MENU_TOOLS" -> player.openInventory(getToolShop(player));
                case "MENU_POTIONS" -> player.openInventory(getPotionShop());
                case "MENU_UTILS" -> player.openInventory(getUtilShop());
                case "MENU_CUSTOM" -> player.openInventory(getRotatingShop());
            }
        });
        return inventory;
    }

    public Inventory getSpectatorSelectorMenu() {
        Inventory inv = new Inventory(InventoryType.CHEST_3_ROW, "Spectate a Player");
        CytonicBedWars.getGameManager().getAlivePlayers().forEach(uuid -> {
            CompoundBinaryTag.Builder builder = CompoundBinaryTag.builder();
            builder.putString(UUID_KEY, uuid.toString());
            ItemStack head = ItemStack.builder(Material.PLAYER_HEAD)
                    .set(ItemComponent.PROFILE, new HeadProfile(Objects.requireNonNull(Cytosis.getPlayer(uuid).orElseThrow().getSkin())))
                    .set(ItemComponent.ITEM_NAME, MM."<GREEN>\{Cytosis.getPlayer(uuid).orElseThrow().getUsername()}")
                    .set(ItemComponent.LORE, List.of(MM."<GRAY>Click to teleport to \{Cytosis.getPlayer(uuid).orElseThrow().getUsername()}"))
                    .set(ItemComponent.CUSTOM_DATA, new CustomData(builder.build()))
                    .build();
            inv.addItemStack(head);
        });
        inv.addInventoryCondition((player, _, _, inventoryConditionResult) -> {
            if (Cytosis.getPlayer(Objects.requireNonNull(inventoryConditionResult.getClickedItem().get(ItemComponent.CUSTOM_DATA)).nbt().getString(UUID_KEY)).isEmpty()) {
                player.sendMessage(MM."<RED>That player is not online.");
                player.openInventory(getSpectatorSelectorMenu());
            } else {
                Player target = Cytosis.getPlayer(Objects.requireNonNull(inventoryConditionResult.getClickedItem().get(ItemComponent.CUSTOM_DATA)).nbt().getString(UUID_KEY)).get();
                player.sendMessage(MM."<green>Teleported you to \{target.getUsername()}!");
                player.teleport(target.getPosition());
            }
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

        menu.addInventoryCondition((player, _, _, result) -> {
            result.setCancel(true);
            ItemStack item = result.getClickedItem();
            String speedString =
                    switch (Objects.requireNonNull(item.get(ItemComponent.CUSTOM_DATA)).nbt().getString("FLIGHT_SPEED")) {
                        case "0.01f" -> "0.1x";
                        case "0.05f" -> "0.5x";
                        case "0.1f" -> "1x";
                        case "0.2f" -> "2x";
                        case "0.5f" -> "5x";
                        default -> "ERROR";
                    };
            float speed = Objects.requireNonNull(item.get(ItemComponent.CUSTOM_DATA)).nbt().getFloat("FLIGHT_SPEED");
            player.setFlyingSpeed(speed);
            player.closeInventory();
            player.sendMessage(MiniMessageTemplate.MM."<GREEN>Your flight speed is now <GOLD>\{speedString}");
        });
        return menu;
    }

    public Inventory getBlocksShop() {
        Inventory inventory = new Inventory(InventoryType.CHEST_5_ROW, "Item Shop ➜ Building Blocks");

        inventory.setItemStack(1, Items.MENU_BLOCKS);
        inventory.setItemStack(2, Items.MENU_ARMOR);
        inventory.setItemStack(3, Items.MENU_COMBAT);
        inventory.setItemStack(4, Items.MENU_TOOLS);
        inventory.setItemStack(5, Items.MENU_POTIONS);
        inventory.setItemStack(6, Items.MENU_UTILS);
        inventory.setItemStack(7, Items.MENU_CUSTOM_ITEMS);

        inventory.setItemStack(21, Items.MENU_WOOL);
        inventory.setItemStack(22, Items.MENU_BLAST_GLASS);
        inventory.setItemStack(23, Items.MENU_END_STONE);
        inventory.setItemStack(30, Items.MENU_TERRACOTTA);
        inventory.setItemStack(31, Items.MENU_OBSIDIAN);
        inventory.setItemStack(32, Items.MENU_PLANKS);
        inventory.setItemStack(10, Items.MENU_SELECTED_PAGE);

        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItemStack(i).isAir()) {
                inventory.setItemStack(i, Items.MENU_FILLER);
            }
        }
        inventory.addInventoryCondition((player, _, _, result) -> {
            result.setCancel(true);
            switch (Objects.requireNonNull(result.getClickedItem().get(ItemComponent.CUSTOM_DATA)).nbt().getString(Items.NAMESPACE).toUpperCase()) {
                case "MENU_BLOCK" -> player.openInventory(getBlocksShop());
                case "MENU_ARMOR" -> player.openInventory(getArmorShop());
                case "MENU_COMBAT" -> player.openInventory(getCombatShop());
                case "MENU_TOOLS" -> player.openInventory(getToolShop(player));
                case "MENU_POTIONS" -> player.openInventory(getPotionShop());
                case "MENU_UTILS" -> player.openInventory(getUtilShop());
                case "MENU_CUSTOM" -> player.openInventory(getRotatingShop());

                case "MENU_WOOL" -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("IRON", 4, player)) {
                            ItemStack itemStack = Items.getTeamMapped(MappableItem.WOOL, CytonicBedWars.getGameManager().getPlayerTeam(player.getUuid()).orElseThrow());
                            itemStack = itemStack.withAmount(16);
                            player.getInventory().addItemStack(itemStack);
                            player.sendMessage(MM."<green>You bought 16 wool!");
                            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());
                        } else {
                            player.sendMessage(MM."<red>You need at least 4 iron to buy this!");
                            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        }
                    } else {
                        Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1);
                        player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        player.sendMessage(MM."<red>You don't have space in your inventory!");
                    }
                }
                case "MENU_BLAST_GLASS" -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("IRON", 12, player)) {
                            ItemStack item = Items.getTeamMapped(MappableItem.GLASS, CytonicBedWars.getGameManager().getPlayerTeam(player.getUuid()).orElseThrow());
                            item = item.withAmount(4);
                            player.getInventory().addItemStack(item);
                            player.sendMessage(MM."<green>You bought 4 Blast-Proof Glass!");
                            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());
                        } else {
                            player.sendMessage(MM."<red>You need at least 12 iron to buy this!");
                            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        }
                    } else {
                        player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        player.sendMessage(MM."<red>You don't have space in your inventory!");
                    }
                }
                case "MENU_END_STONE" -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("IRON", 24, player)) {
                            ItemStack item = Items.END_STONE;
                            item = item.withAmount(12);
                            player.getInventory().addItemStack(item);
                            player.sendMessage(MM."<green>You bought 12 end stone!");
                            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());
                        } else {
                            player.sendMessage(MM."<red>You need at least 24 iron to buy this!");
                            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        }
                    } else {
                        player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        player.sendMessage(MM."<red>You don't have space in your inventory!");
                    }
                }
                case "MENU_TERRACOTTA" -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("IRON", 10, player)) {
                            ItemStack item = Items.getTeamMapped(MappableItem.TERRACOTTA, CytonicBedWars.getGameManager().getPlayerTeam(player.getUuid()).orElseThrow());
                            item = item.withAmount(12);
                            player.getInventory().addItemStack(item);
                            player.sendMessage(MM."<green>You bought 12 terracotta!");
                            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());
                        } else {
                            player.sendMessage(MM."<red>You need at least 10 iron to buy this!");
                            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        }
                    } else {
                        player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        player.sendMessage(MM."<red>You don't have space in your inventory!");
                    }
                }
                case "MENU_OBSIDIAN" -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("EMERALD", 6, player)) {
                            ItemStack item = Items.OBSIDIAN;
                            item = item.withAmount(6);
                            player.getInventory().addItemStack(item);
                            player.sendMessage(MM."<green>You bought 6 obsidian!");
                            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());
                        } else {
                            player.sendMessage(MM."<red>You need at least 6 emeralds to buy this!");
                            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        }
                    } else {
                        player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        player.sendMessage(MM."<red>You don't have space in your inventory!");
                    }
                }
                case "MENU_PLANKS" -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("GOLD", 4, player)) {
                            ItemStack item = Items.PLANKS;
                            item = item.withAmount(8);
                            player.getInventory().addItemStack(item);
                            player.sendMessage(MM."<green>You bought 8 planks!");
                            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());
                        } else {
                            player.sendMessage(MM."<red>You need at least 4 gold to buy this!");
                            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        }
                    } else {
                        player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
                        player.sendMessage(MM."<red>You don't have space in your inventory!");
                    }
                }
            }
        });
        return inventory;
    }
}

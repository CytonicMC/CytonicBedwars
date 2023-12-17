package dev.foxikle.webnetbedwars.managers;

import dev.foxikle.webnetbedwars.WebNetBedWars;
import dev.foxikle.webnetbedwars.data.enums.ArmorLevel;
import dev.foxikle.webnetbedwars.data.enums.MappableItem;
import dev.foxikle.webnetbedwars.utils.Items;
import me.flame.menus.builders.items.ItemBuilder;
import me.flame.menus.items.MenuItem;
import me.flame.menus.menu.ActionResponse;
import me.flame.menus.menu.Menu;
import me.flame.menus.menu.Menus;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class MenuManager {
    private final WebNetBedWars plugin;

    private static NamespacedKey UUID_KEY;

    public MenuManager(WebNetBedWars plugin) {
        this.plugin = plugin;
        UUID_KEY = new NamespacedKey(plugin, "UUID");
    }

    public Menu getSpectatorSelectorMenu() {
        Menu menu = Menus.menu().title("Spectate a Player").rows(2).addAllModifiers().create();

        plugin.getGameManager().getAlivePlayers().forEach(uuid -> {
            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) head.getItemMeta();
            meta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
            meta.getPersistentDataContainer().set(UUID_KEY, PersistentDataType.STRING, uuid.toString());
            meta.setLore(List.of(ChatColor.GRAY + "Click to teleport to " + Bukkit.getPlayer(uuid).getName()));
            meta.setDisplayName(ChatColor.GREEN + Bukkit.getPlayer(uuid).getName());
            head.setItemMeta(meta);
            MenuItem item = ItemBuilder.of(head).buildItem((slot, event) -> {
                Player player = event.getPlayer();
                player.teleport(Bukkit.getPlayer(uuid));
                return ActionResponse.DONE;
            });
            menu.addItem(item);
        });

        return menu;
    }

    public Menu getSpectatorSpeedMenu() {
        Menu menu = Menus.menu().title("Flight Speed").rows(3).addAllModifiers().create();

        ItemStack tenthSpeed = new ItemStack(Material.LEATHER_BOOTS);
        ItemMeta tenthMeta = tenthSpeed.getItemMeta();
        tenthMeta.addItemFlags(ItemFlag.values()); // adds all the flags
        tenthMeta.setDisplayName(ChatColor.GREEN + "0.1x flight speed");
        tenthSpeed.setItemMeta(tenthMeta);

        menu.setItem(11, ItemBuilder.of(tenthSpeed).buildItem((slot, event) -> {
            event.getPlayer().setFlySpeed(0.01f);
            event.getPlayer().closeInventory();
            event.getPlayer().sendMessage(ChatColor.GREEN + "Your flight speed is now " + ChatColor.GOLD + "0.1x");
            return ActionResponse.DONE;
        }));

        ItemStack halfSpeed = new ItemStack(Material.CHAINMAIL_BOOTS);
        ItemMeta halfMeta = halfSpeed.getItemMeta();
        halfMeta.addItemFlags(ItemFlag.values()); // adds all the flags
        halfMeta.setDisplayName(ChatColor.GREEN + ".5x flight speed");
        halfSpeed.setItemMeta(halfMeta);

        menu.setItem(12, ItemBuilder.of(halfSpeed).buildItem((slot, event) -> {
            event.getPlayer().setFlySpeed(0.05f);
            event.getPlayer().closeInventory();
            event.getPlayer().sendMessage(ChatColor.GREEN + "Your flight speed is now " + ChatColor.GOLD + "0.5x");
            return ActionResponse.DONE;
        }));

        ItemStack normalSpeed = new ItemStack(Material.IRON_BOOTS);
        ItemMeta normalMeta = normalSpeed.getItemMeta();
        normalMeta.addItemFlags(ItemFlag.values()); // adds all the flags
        normalMeta.setDisplayName(ChatColor.GREEN + "1x flight speed");
        normalSpeed.setItemMeta(normalMeta);

        menu.setItem(13, ItemBuilder.of(normalSpeed).buildItem((slot, event) -> {
            event.getPlayer().setFlySpeed(0.1f);
            event.getPlayer().closeInventory();
            event.getPlayer().sendMessage(ChatColor.GREEN + "Your flight speed is now " + ChatColor.GOLD + "1x");
            return ActionResponse.DONE;
        }));

        ItemStack x2Speed = new ItemStack(Material.GOLDEN_BOOTS);
        ItemMeta x2Meta = x2Speed.getItemMeta();
        x2Meta.addItemFlags(ItemFlag.values()); // adds all the flags
        x2Meta.setDisplayName(ChatColor.GREEN + "2x flight speed");
        x2Speed.setItemMeta(x2Meta);

        menu.setItem(14, ItemBuilder.of(x2Speed).buildItem((slot, event) -> {
            event.getPlayer().setFlySpeed(0.2f);
            event.getPlayer().closeInventory();
            event.getPlayer().sendMessage(ChatColor.GREEN + "Your flight speed is now " + ChatColor.GOLD + "2x");
            return ActionResponse.DONE;
        }));

        ItemStack x5Speed = new ItemStack(Material.DIAMOND_BOOTS);
        ItemMeta x5Meta = x5Speed.getItemMeta();
        x5Meta.addItemFlags(ItemFlag.values()); // adds all the flags
        x5Meta.setDisplayName(ChatColor.GREEN + "5x flight speed");
        x5Speed.setItemMeta(x5Meta);

        menu.setItem(15, ItemBuilder.of(x5Speed).buildItem((slot, event) -> {
            event.getPlayer().setFlySpeed(0.5f);
            event.getPlayer().closeInventory();
            event.getPlayer().sendMessage(ChatColor.GREEN + "Your flight speed is now " + ChatColor.GOLD + "5x");
            return ActionResponse.DONE;
        }));


        return menu;
    }

    public Menu getBlocksShop() {
        Menu blocks = Menus.menu().title("Item Shop ➜ Building Blocks").rows(5).addAllModifiers().create();

        // header

        blocks.setItem(1, Items.MENU_BLOCKS);
        blocks.setItem(3, ItemBuilder.of(Items.MENU_COMBAT)
                .buildItem((slot, event) -> {
                    getCombatShop().open(event.getPlayer());
                    return ActionResponse.DONE;
                })
        );
        blocks.setItem(2, ItemBuilder.of(Items.MENU_ARMOR)
                .buildItem((slot, event) -> {
                    getArmorShop().open(event.getPlayer());
                    return ActionResponse.DONE;
                })
        );
        blocks.setItem(4, ItemBuilder.of(Items.MENU_TOOLS)
                .buildItem((slot, event) -> {
                    getToolShop().open(event.getPlayer());
                    return ActionResponse.DONE;
                })
        );
        blocks.setItem(5, ItemBuilder.of(Items.MENU_POTIONS)
                .buildItem((slot, event) -> {
                    getPotionShop().open(event.getPlayer());
                    return ActionResponse.DONE;
                })
        );
        blocks.setItem(6, ItemBuilder.of(Items.MENU_UTILS)
                .buildItem((slot, event) -> {
                    getUtilShop().open(event.getPlayer());
                    return ActionResponse.DONE;
                })
        );
        blocks.setItem(7, ItemBuilder.of(Items.MENU_CUSTOM_ITEMS)
                .buildItem((slot, event) -> {
                    getCustomShop().open(event.getPlayer());
                    return ActionResponse.DONE;
                })
        );

        blocks.setItem(21, ItemBuilder.of(Items.MENU_WOOL)
                .buildItem((slot, event) -> {
                    if (plugin.getGameManager().getEconomyManager().takeItem("IRON", 4, event.getPlayer())) {
                        if (plugin.getGameManager().getEconomyManager().hasSpace(event.getPlayer())) {
                            ItemStack clone = Items.getTeamMapped(MappableItem.WOOL, plugin.getGameManager().getPlayerTeam(event.getPlayer().getUniqueId())).clone();
                            clone.setAmount(16);
                            event.getPlayer().getInventory().addItem(clone);
                            event.getPlayer().sendMessage(ChatColor.GREEN + "You bought 16 wool!");
                        } else {
                            event.getPlayer().playSound(event.getPlayer().getEyeLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                            event.getPlayer().sendMessage(ChatColor.RED + "You don't have space in your inventory!");
                        }
                    } else {
                        event.getPlayer().sendMessage(ChatColor.RED + "You need at least 4 iron to buy this!");
                        event.getPlayer().playSound(event.getPlayer().getEyeLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                    }
                    return ActionResponse.DONE;
                })
        );

        blocks.setItem(22, ItemBuilder.of(Items.MENU_BLAST_GLASS)
                .buildItem((slot, event) -> {
                    if (plugin.getGameManager().getEconomyManager().takeItem("IRON", 12, event.getPlayer())) {
                        if (plugin.getGameManager().getEconomyManager().hasSpace(event.getPlayer())) {
                            ItemStack clone = Items.getTeamMapped(MappableItem.GLASS, plugin.getGameManager().getPlayerTeam(event.getPlayer().getUniqueId())).clone();
                            clone.setAmount(4);
                            event.getPlayer().getInventory().addItem(clone);
                            event.getPlayer().sendMessage(ChatColor.GREEN + "You bought 4 Blast-Proof Glass!");
                        } else {
                            event.getPlayer().playSound(event.getPlayer().getEyeLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                            event.getPlayer().sendMessage(ChatColor.RED + "You don't have space in your inventory!");
                        }
                    } else {
                        event.getPlayer().sendMessage(ChatColor.RED + "You need at least 12 iron to buy this!");
                        event.getPlayer().playSound(event.getPlayer().getEyeLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                    }
                    return ActionResponse.DONE;
                })
        );

        blocks.setItem(23, ItemBuilder.of(Items.MENU_END_STONE)
                .buildItem((slot, event) -> {
                    if (plugin.getGameManager().getEconomyManager().takeItem("IRON", 24, event.getPlayer())) {
                        if (plugin.getGameManager().getEconomyManager().hasSpace(event.getPlayer())) {
                            ItemStack clone = Items.END_STONE.clone();
                            clone.setAmount(12);
                            event.getPlayer().getInventory().addItem(clone);
                            event.getPlayer().sendMessage(ChatColor.GREEN + "You bought 12 end stone!");
                        } else {
                            event.getPlayer().playSound(event.getPlayer().getEyeLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                            event.getPlayer().sendMessage(ChatColor.RED + "You don't have space in your inventory!");
                        }
                    } else {
                        event.getPlayer().sendMessage(ChatColor.RED + "You need at least 24 iron to buy this!");
                        event.getPlayer().playSound(event.getPlayer().getEyeLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                    }
                    return ActionResponse.DONE;
                })
        );

        blocks.setItem(30, ItemBuilder.of(Items.MENU_TERRACOTTA)
                .buildItem((slot, event) -> {
                    if (plugin.getGameManager().getEconomyManager().takeItem("IRON", 10, event.getPlayer())) {
                        if (plugin.getGameManager().getEconomyManager().hasSpace(event.getPlayer())) {
                            ItemStack clone = Items.getTeamMapped(MappableItem.TERRACOTTA, plugin.getGameManager().getPlayerTeam(event.getPlayer().getUniqueId())).clone();
                            clone.setAmount(12);
                            event.getPlayer().getInventory().addItem(clone);
                            event.getPlayer().sendMessage(ChatColor.GREEN + "You bought 12 terracotta!");
                        } else {
                            event.getPlayer().playSound(event.getPlayer().getEyeLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                            event.getPlayer().sendMessage(ChatColor.RED + "You don't have space in your inventory!");
                        }
                    } else {
                        event.getPlayer().sendMessage(ChatColor.RED + "You need at least 10 iron to buy this!");
                        event.getPlayer().playSound(event.getPlayer().getEyeLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                    }
                    return ActionResponse.DONE;
                })
        );

        blocks.setItem(31, ItemBuilder.of(Items.MENU_OBSIDIAN)
                .buildItem((slot, event) -> {
                    if (plugin.getGameManager().getEconomyManager().takeItem("EMERALD", 6, event.getPlayer())) {
                        if (plugin.getGameManager().getEconomyManager().hasSpace(event.getPlayer())) {
                            ItemStack clone = Items.OBSIDIAN.clone();
                            clone.setAmount(6);
                            event.getPlayer().getInventory().addItem(clone);
                            event.getPlayer().sendMessage(ChatColor.GREEN + "You bought 6 obsidian!");
                        } else {
                            event.getPlayer().playSound(event.getPlayer().getEyeLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                            event.getPlayer().sendMessage(ChatColor.RED + "You don't have space in your inventory!");
                        }
                    } else {
                        event.getPlayer().sendMessage(ChatColor.RED + "You need at least 6 emeralds to buy this!");
                        event.getPlayer().playSound(event.getPlayer().getEyeLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                    }
                    return ActionResponse.DONE;
                })
        );

        blocks.setItem(32, ItemBuilder.of(Items.MENU_PLANKS)
                .buildItem((slot, event) -> {
                    if (plugin.getGameManager().getEconomyManager().takeItem("GOLD", 4, event.getPlayer())) {
                        if (plugin.getGameManager().getEconomyManager().hasSpace(event.getPlayer())) {
                            ItemStack clone = Items.PLANKS.clone();
                            clone.setAmount(8);
                            event.getPlayer().getInventory().addItem(clone);
                            event.getPlayer().sendMessage(ChatColor.GREEN + "You bought 8 planks!");
                        } else {
                            event.getPlayer().playSound(event.getPlayer().getEyeLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                            event.getPlayer().sendMessage(ChatColor.RED + "You don't have space in your inventory!");
                        }
                    } else {
                        event.getPlayer().sendMessage(ChatColor.RED + "You need at least 4 gold to buy this!");
                        event.getPlayer().playSound(event.getPlayer().getEyeLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                    }
                    return ActionResponse.DONE;
                })
        );

        blocks.setItem(10, Items.MENU_SELECTED_PAGE);


        blocks.getFiller().fill(Items.MENU_FILLER); // fill the border
        return blocks;
    }

    public Menu getArmorShop() {
        Menu blocks = Menus.menu().title("Item Shop ➜ Armor").rows(5).addAllModifiers().create();

        // header

        blocks.setItem(1, ItemBuilder.of(Items.MENU_BLOCKS)
                .buildItem((slot, event) -> {
                    getBlocksShop().open(event.getPlayer());
                    return ActionResponse.DONE;
                })
        );
        blocks.setItem(3, ItemBuilder.of(Items.MENU_COMBAT)
                .buildItem((slot, event) -> {
                    getCombatShop().open(event.getPlayer());
                    return ActionResponse.DONE;
                })
        );
        blocks.setItem(2, Items.MENU_ARMOR);
        blocks.setItem(4, ItemBuilder.of(Items.MENU_TOOLS)
                .buildItem((slot, event) -> {
                    getToolShop().open(event.getPlayer());
                    return ActionResponse.DONE;
                })
        );
        blocks.setItem(5, ItemBuilder.of(Items.MENU_POTIONS)
                .buildItem((slot, event) -> {
                    getPotionShop().open(event.getPlayer());
                    return ActionResponse.DONE;
                })
        );
        blocks.setItem(6, ItemBuilder.of(Items.MENU_UTILS)
                .buildItem((slot, event) -> {
                    getUtilShop().open(event.getPlayer());
                    return ActionResponse.DONE;
                })
        );
        blocks.setItem(7, ItemBuilder.of(Items.MENU_CUSTOM_ITEMS)
                .buildItem((slot, event) -> {
                    getCustomShop().open(event.getPlayer());
                    return ActionResponse.DONE;
                })
        );

        blocks.setItem(22, ItemBuilder.of(Items.MENU_CHAINMAIL_BOOTS)
                .buildItem((slot, event) -> {
                    if (plugin.getGameManager().getEconomyManager().takeItem("IRON", 40, event.getPlayer())) {
                        event.getPlayer().getInventory().setLeggings(Items.CHAINMAIL_LEGS);
                        event.getPlayer().getInventory().setBoots(Items.CHAINMAIL_BOOTS);
                        plugin.getGameManager().setArmor(event.getPlayer().getUniqueId(), ArmorLevel.CHAINMAIL);
                    } else {
                        event.getPlayer().sendMessage(ChatColor.RED + "You need at least 40 iron to buy this!");
                        event.getPlayer().playSound(event.getPlayer().getEyeLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                    }
                    return ActionResponse.DONE;
                })
        );

        blocks.setItem(30, ItemBuilder.of(Items.MENU_IRON_BOOTS)
                .buildItem((slot, event) -> {
                    if (plugin.getGameManager().getEconomyManager().takeItem("GOLD", 12, event.getPlayer())) {
                        event.getPlayer().getInventory().setLeggings(Items.IRON_LEGS);
                        event.getPlayer().getInventory().setBoots(Items.IRON_BOOTS);
                        plugin.getGameManager().setArmor(event.getPlayer().getUniqueId(), ArmorLevel.IRON);
                    } else {
                        event.getPlayer().sendMessage(ChatColor.RED + "You need at least 12 Gold to buy this!");
                        event.getPlayer().playSound(event.getPlayer().getEyeLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                    }
                    return ActionResponse.DONE;
                })
        );

        blocks.setItem(31, ItemBuilder.of(Items.MENU_DIAMOND_BOOTS)
                .buildItem((slot, event) -> {
                    if (plugin.getGameManager().getEconomyManager().takeItem("EMERALD", 6, event.getPlayer())) {
                        event.getPlayer().getInventory().setLeggings(Items.DIAMOND_LEGS);
                        event.getPlayer().getInventory().setBoots(Items.DIAMOND_BOOTS);
                        plugin.getGameManager().setArmor(event.getPlayer().getUniqueId(), ArmorLevel.DIAMOND);
                    } else {
                        event.getPlayer().sendMessage(ChatColor.RED + "You need at least 6 Emeralds to buy this!");
                        event.getPlayer().playSound(event.getPlayer().getEyeLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                    }
                    return ActionResponse.DONE;
                })
        );

        blocks.setItem(32, ItemBuilder.of(Items.MENU_NETHERITE_BOOTS)
                .buildItem((slot, event) -> {
                    if (plugin.getGameManager().getEconomyManager().takeItem("EMERALD", 16, event.getPlayer())) {
                        event.getPlayer().getInventory().setLeggings(Items.NETHERITE_LEGS);
                        event.getPlayer().getInventory().setBoots(Items.NETHERITE_BOOTS);
                        plugin.getGameManager().setArmor(event.getPlayer().getUniqueId(), ArmorLevel.NETHERITE);
                    } else {
                        event.getPlayer().sendMessage(ChatColor.RED + "You need at least 16 Emeralds to buy this!");
                        event.getPlayer().playSound(event.getPlayer().getEyeLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                    }
                    return ActionResponse.DONE;
                })
        );

        blocks.setItem(11, Items.MENU_SELECTED_PAGE);


        blocks.getFiller().fill(Items.MENU_FILLER); // fill the border
        return blocks;
    }

    public Menu getCombatShop() {
        Menu weapons = Menus.menu().title("Item Shop ➜ Weapons").rows(5).addAllModifiers().create();

        // header
        {
            weapons.setItem(1, ItemBuilder.of(Items.MENU_BLOCKS)
                    .buildItem((slot, event) -> {
                        getBlocksShop().open(event.getPlayer());
                        return ActionResponse.DONE;
                    })
            );
            weapons.setItem(2, ItemBuilder.of(Items.MENU_ARMOR)
                    .buildItem((slot, event) -> {
                        getArmorShop().open(event.getPlayer());
                        return ActionResponse.DONE;
                    })
            );
            weapons.setItem(3, Items.MENU_COMBAT);
            weapons.setItem(4, ItemBuilder.of(Items.MENU_TOOLS)
                    .buildItem((slot, event) -> {
                        getToolShop().open(event.getPlayer());
                        return ActionResponse.DONE;
                    })
            );
            weapons.setItem(5, ItemBuilder.of(Items.MENU_POTIONS)
                    .buildItem((slot, event) -> {
                        getPotionShop().open(event.getPlayer());
                        return ActionResponse.DONE;
                    })
            );
            weapons.setItem(6, ItemBuilder.of(Items.MENU_UTILS)
                    .buildItem((slot, event) -> {
                        getUtilShop().open(event.getPlayer());
                        return ActionResponse.DONE;
                    })
            );
            weapons.setItem(7, ItemBuilder.of(Items.MENU_CUSTOM_ITEMS)
                    .buildItem((slot, event) -> {
                        getCustomShop().open(event.getPlayer());
                        return ActionResponse.DONE;
                    })
            );
        }

        weapons.setItem(19, ItemBuilder.of(Items.MENU_STONE_SWORD)
                .buildItem((slot, event) -> {
                    if (plugin.getGameManager().getEconomyManager().takeItem("IRON", 10, event.getPlayer())) {
                        if (plugin.getGameManager().getEconomyManager().hasSpace(event.getPlayer())) {
                            event.getPlayer().getInventory().addItem(Items.STONE_SWORD.clone());
                            event.getPlayer().sendMessage(ChatColor.GREEN + "You bought a Stone Sword!");
                        } else {
                            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1, 1);
                            event.getPlayer().sendMessage(ChatColor.RED + "You don't have space in your inventory!");
                        }
                    } else {
                        event.getPlayer().sendMessage(ChatColor.RED + "You need at least 10 iron to buy this!");
                        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1, 1);
                    }
                    return ActionResponse.DONE;
                })
        );

        weapons.setItem(20, ItemBuilder.of(Items.MENU_IRON_SWORD)
                .buildItem((slot, event) -> {
                    if (plugin.getGameManager().getEconomyManager().takeItem("GOLD", 7, event.getPlayer())) {
                        if (plugin.getGameManager().getEconomyManager().hasSpace(event.getPlayer())) {
                            event.getPlayer().getInventory().addItem(Items.IRON_SWORD.clone());
                            event.getPlayer().sendMessage(ChatColor.GREEN + "You bought an Iron Sword!");
                        } else {
                            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1, 1);
                            event.getPlayer().sendMessage(ChatColor.RED + "You don't have space in your inventory!");
                        }
                    } else {
                        event.getPlayer().sendMessage(ChatColor.RED + "You need at least 7 gold to buy this!");
                        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1, 1);
                    }
                    return ActionResponse.DONE;
                })
        );

        weapons.setItem(21, ItemBuilder.of(Items.MENU_DIAMOND_SWORD)
                .buildItem((slot, event) -> {
                    if (plugin.getGameManager().getEconomyManager().takeItem("EMERALD", 6, event.getPlayer())) {
                        if (plugin.getGameManager().getEconomyManager().hasSpace(event.getPlayer())) {
                            event.getPlayer().getInventory().addItem(Items.DIAMOND_SWORD.clone());
                            event.getPlayer().sendMessage(ChatColor.GREEN + "You bought a Diamond Sword!");
                        } else {
                            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1, 1);
                            event.getPlayer().sendMessage(ChatColor.RED + "You don't have space in your inventory!");
                        }
                    } else {
                        event.getPlayer().sendMessage(ChatColor.RED + "You need at least 6 Emeralds to buy this!");
                        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1, 1);
                    }
                    return ActionResponse.DONE;
                })
        );

        weapons.setItem(22, ItemBuilder.of(Items.MENU_SHIELD)
                .buildItem((slot, event) -> {
                    if (plugin.getGameManager().getEconomyManager().takeItem("GOLD", 3, event.getPlayer())) {
                        if (plugin.getGameManager().getEconomyManager().hasSpace(event.getPlayer())) {
                            event.getPlayer().getInventory().addItem(Items.SHIELD.clone());
                            event.getPlayer().sendMessage(ChatColor.GREEN + "You bought a Shield!");
                        } else {
                            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1, 1);
                            event.getPlayer().sendMessage(ChatColor.RED + "You don't have space in your inventory!");
                        }
                    } else {
                        event.getPlayer().sendMessage(ChatColor.RED + "You need at least 3 Gold to buy this!");
                        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1, 1);
                    }
                    return ActionResponse.DONE;
                })
        );

        weapons.setItem(23, ItemBuilder.of(Items.MENU_CROSSBOW_1)
                .buildItem((slot, event) -> {
                    if (plugin.getGameManager().getEconomyManager().takeItem("GOLD", 12, event.getPlayer())) {
                        if (plugin.getGameManager().getEconomyManager().hasSpace(event.getPlayer())) {
                            event.getPlayer().getInventory().addItem(Items.CROSSBOW_1.clone());
                            event.getPlayer().sendMessage(ChatColor.GREEN + "You bought a Crossbow!");
                        } else {
                            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1, 1);
                            event.getPlayer().sendMessage(ChatColor.RED + "You don't have space in your inventory!");
                        }
                    } else {
                        event.getPlayer().sendMessage(ChatColor.RED + "You need at least 12 Gold to buy this!");
                        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1, 1);
                    }
                    return ActionResponse.DONE;
                })
        );

        weapons.setItem(24, ItemBuilder.of(Items.MENU_CROSSBOW_2)
                .buildItem((slot, event) -> {
                    if (plugin.getGameManager().getEconomyManager().takeItem("GOLD", 20, event.getPlayer())) {
                        if (plugin.getGameManager().getEconomyManager().hasSpace(event.getPlayer())) {
                            event.getPlayer().getInventory().addItem(Items.CROSSBOW_2.clone());
                            event.getPlayer().sendMessage(ChatColor.GREEN + "You bought a Crossbow!");
                        } else {
                            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1, 1);
                            event.getPlayer().sendMessage(ChatColor.RED + "You don't have space in your inventory!");
                        }
                    } else {
                        event.getPlayer().sendMessage(ChatColor.RED + "You need at least 20 Gold to buy this!");
                        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1, 1);
                    }
                    return ActionResponse.DONE;
                })
        );

        weapons.setItem(25, ItemBuilder.of(Items.MENU_CROSSBOW_3)
                .buildItem((slot, event) -> {
                    if (plugin.getGameManager().getEconomyManager().takeItem("EMERALD", 6, event.getPlayer())) {
                        if (plugin.getGameManager().getEconomyManager().hasSpace(event.getPlayer())) {
                            event.getPlayer().getInventory().addItem(Items.CROSSBOW_3.clone());
                            event.getPlayer().sendMessage(ChatColor.GREEN + "You bought a Crossbow!");
                        } else {
                            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1, 1);
                            event.getPlayer().sendMessage(ChatColor.RED + "You don't have space in your inventory!");
                        }
                    } else {
                        event.getPlayer().sendMessage(ChatColor.RED + "You need at least 6 Emeralds to buy this!");
                        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1, 1);
                    }
                    return ActionResponse.DONE;
                })
        );

        weapons.setItem(28, ItemBuilder.of(Items.MENU_BOW_1)
                .buildItem((slot, event) -> {
                    if (plugin.getGameManager().getEconomyManager().takeItem("GOLD", 10, event.getPlayer())) {
                        if (plugin.getGameManager().getEconomyManager().hasSpace(event.getPlayer())) {
                            event.getPlayer().getInventory().addItem(Items.BOW_1.clone());
                            event.getPlayer().sendMessage(ChatColor.GREEN + "You bought a bow!");
                        } else {
                            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1, 1);
                            event.getPlayer().sendMessage(ChatColor.RED + "You don't have space in your inventory!");
                        }
                    } else {
                        event.getPlayer().sendMessage(ChatColor.RED + "You need at least 10 Gold to buy this!");
                        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1, 1);
                    }
                    return ActionResponse.DONE;
                })
        );

        weapons.setItem(29, ItemBuilder.of(Items.MENU_BOW_2)
                .buildItem((slot, event) -> {
                    if (plugin.getGameManager().getEconomyManager().takeItem("GOLD", 18, event.getPlayer())) {
                        if (plugin.getGameManager().getEconomyManager().hasSpace(event.getPlayer())) {
                            event.getPlayer().getInventory().addItem(Items.BOW_2.clone());
                            event.getPlayer().sendMessage(ChatColor.GREEN + "You bought a bow!");
                        } else {
                            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1, 1);
                            event.getPlayer().sendMessage(ChatColor.RED + "You don't have space in your inventory!");
                        }
                    } else {
                        event.getPlayer().sendMessage(ChatColor.RED + "You need at least 18 Gold to buy this!");
                        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1, 1);
                    }
                    return ActionResponse.DONE;
                })
        );

        weapons.setItem(30, ItemBuilder.of(Items.MENU_BOW_3)
                .buildItem((slot, event) -> {
                    if (plugin.getGameManager().getEconomyManager().takeItem("EMERALD", 5, event.getPlayer())) {
                        if (plugin.getGameManager().getEconomyManager().hasSpace(event.getPlayer())) {
                            event.getPlayer().getInventory().addItem(Items.BOW_3.clone());
                            event.getPlayer().sendMessage(ChatColor.GREEN + "You bought a bow!");
                        } else {
                            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1, 1);
                            event.getPlayer().sendMessage(ChatColor.RED + "You don't have space in your inventory!");
                        }
                    } else {
                        event.getPlayer().sendMessage(ChatColor.RED + "You need at least 5 Emeralds to buy this!");
                        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1, 1);
                    }
                    return ActionResponse.DONE;
                })
        );

        weapons.setItem(31, ItemBuilder.of(Items.MENU_ARROW)
                .buildItem((slot, event) -> {
                    if (plugin.getGameManager().getEconomyManager().takeItem("GOLD", 2, event.getPlayer())) {
                        if (plugin.getGameManager().getEconomyManager().hasSpace(event.getPlayer())) {
                            ItemStack clone = Items.ARROW.clone();
                            clone.setAmount(8);
                            event.getPlayer().getInventory().addItem(clone);
                            event.getPlayer().sendMessage(ChatColor.GREEN + "You bought some arrows!");
                        } else {
                            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1, 1);
                            event.getPlayer().sendMessage(ChatColor.RED + "You don't have space in your inventory!");
                        }
                    } else {
                        event.getPlayer().sendMessage(ChatColor.RED + "You need at least 2 Gold to buy this!");
                        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1, 1);
                    }
                    return ActionResponse.DONE;
                })
        );

        weapons.setItem(32, ItemBuilder.of(Items.MENU_TRIDENT_1)
                .buildItem((slot, event) -> {
                    if (plugin.getGameManager().getEconomyManager().takeItem("GOLD", 12, event.getPlayer())) {
                        if (plugin.getGameManager().getEconomyManager().hasSpace(event.getPlayer())) {
                            event.getPlayer().getInventory().addItem(Items.TRIDENT_1.clone());
                            event.getPlayer().sendMessage(ChatColor.GREEN + "You bought a trident!");
                        } else {
                            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1, 1);
                            event.getPlayer().sendMessage(ChatColor.RED + "You don't have space in your inventory!");
                        }
                    } else {
                        event.getPlayer().sendMessage(ChatColor.RED + "You need at least 12 Gold to buy this!");
                        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1, 1);
                    }
                    return ActionResponse.DONE;
                })
        );

        weapons.setItem(33, ItemBuilder.of(Items.MENU_TRIDENT_2)
                .buildItem((slot, event) -> {
                    if (plugin.getGameManager().getEconomyManager().takeItem("EMERALD", 5, event.getPlayer())) {
                        if (plugin.getGameManager().getEconomyManager().hasSpace(event.getPlayer())) {
                            event.getPlayer().getInventory().addItem(Items.TRIDENT_2.clone());
                            event.getPlayer().sendMessage(ChatColor.GREEN + "You bought a trident!");
                        } else {
                            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1, 1);
                            event.getPlayer().sendMessage(ChatColor.RED + "You don't have space in your inventory!");
                        }
                    } else {
                        event.getPlayer().sendMessage(ChatColor.RED + "You need at least 5 Emeralds to buy this!");
                        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1, 1);
                    }
                    return ActionResponse.DONE;
                })
        );

        weapons.setItem(34, ItemBuilder.of(Items.MENU_TRIDENT_3)
                .buildItem((slot, event) -> {
                    if (plugin.getGameManager().getEconomyManager().takeItem("EMERALD", 5, event.getPlayer())) {
                        if (plugin.getGameManager().getEconomyManager().hasSpace(event.getPlayer())) {
                            event.getPlayer().getInventory().addItem(Items.TRIDENT_3.clone());
                            event.getPlayer().sendMessage(ChatColor.GREEN + "You bought a trident!");
                        } else {
                            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1, 1);
                            event.getPlayer().sendMessage(ChatColor.RED + "You don't have space in your inventory!");
                        }
                    } else {
                        event.getPlayer().sendMessage(ChatColor.RED + "You need at least 5 Emeralds to buy this!");
                        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1, 1);
                    }
                    return ActionResponse.DONE;
                })
        );


        weapons.setItem(12, Items.MENU_SELECTED_PAGE);


        weapons.getFiller().fill(Items.MENU_FILLER); // fill the border
        return weapons;
    }

    public Menu getToolShop() {
        Menu blocks = Menus.menu().title("Item Shop ➜ Tools").rows(5).addAllModifiers().create();

        // header

        blocks.setItem(1, ItemBuilder.of(Items.MENU_BLOCKS)
                .buildItem((slot, event) -> {
                    getBlocksShop().open(event.getPlayer());
                    return ActionResponse.DONE;
                })
        );
        blocks.setItem(2, ItemBuilder.of(Items.MENU_ARMOR)
                .buildItem((slot, event) -> {
                    getArmorShop().open(event.getPlayer());
                    return ActionResponse.DONE;
                })
        );
        blocks.setItem(3, ItemBuilder.of(Items.MENU_COMBAT)
                .buildItem((slot, event) -> {
                    getCombatShop().open(event.getPlayer());
                    return ActionResponse.DONE;
                })
        );
        blocks.setItem(4, Items.MENU_TOOLS);
        blocks.setItem(5, ItemBuilder.of(Items.MENU_POTIONS)
                .buildItem((slot, event) -> {
                    getPotionShop().open(event.getPlayer());
                    return ActionResponse.DONE;
                })
        );
        blocks.setItem(6, ItemBuilder.of(Items.MENU_UTILS)
                .buildItem((slot, event) -> {
                    getUtilShop().open(event.getPlayer());
                    return ActionResponse.DONE;
                })
        );
        blocks.setItem(7, ItemBuilder.of(Items.MENU_CUSTOM_ITEMS)
                .buildItem((slot, event) -> {
                    getCustomShop().open(event.getPlayer());
                    return ActionResponse.DONE;
                })
        );

        blocks.setItem(13, Items.MENU_SELECTED_PAGE);


        blocks.getFiller().fill(Items.MENU_FILLER); // fill the border
        return blocks;
    }

    public Menu getPotionShop() {
        Menu blocks = Menus.menu().title("Item Shop ➜ Potions").rows(5).addAllModifiers().create();

        // header

        blocks.setItem(1, ItemBuilder.of(Items.MENU_BLOCKS)
                .buildItem((slot, event) -> {
                    getBlocksShop().open(event.getPlayer());
                    return ActionResponse.DONE;
                })
        );
        blocks.setItem(2, ItemBuilder.of(Items.MENU_ARMOR)
                .buildItem((slot, event) -> {
                    getArmorShop().open(event.getPlayer());
                    return ActionResponse.DONE;
                })
        );
        blocks.setItem(3, ItemBuilder.of(Items.MENU_COMBAT)
                .buildItem((slot, event) -> {
                    getCombatShop().open(event.getPlayer());
                    return ActionResponse.DONE;
                })
        );
        blocks.setItem(4, ItemBuilder.of(Items.MENU_TOOLS)
                .buildItem((slot, event) -> {
                    getToolShop().open(event.getPlayer());
                    return ActionResponse.DONE;
                })
        );
        blocks.setItem(5, Items.MENU_POTIONS);
        blocks.setItem(6, ItemBuilder.of(Items.MENU_UTILS)
                .buildItem((slot, event) -> {
                    getUtilShop().open(event.getPlayer());
                    return ActionResponse.DONE;
                })
        );
        blocks.setItem(7, ItemBuilder.of(Items.MENU_CUSTOM_ITEMS)
                .buildItem((slot, event) -> {
                    getCustomShop().open(event.getPlayer());
                    return ActionResponse.DONE;
                })
        );

        blocks.setItem(14, Items.MENU_SELECTED_PAGE);


        blocks.getFiller().fill(Items.MENU_FILLER); // fill the border
        return blocks;
    }

    public Menu getUtilShop() {
        Menu blocks = Menus.menu().title("Item Shop ➜ Utilities").rows(5).addAllModifiers().create();

        // header

        blocks.setItem(1, ItemBuilder.of(Items.MENU_BLOCKS)
                .buildItem((slot, event) -> {
                    getBlocksShop().open(event.getPlayer());
                    return ActionResponse.DONE;
                })
        );
        blocks.setItem(2, ItemBuilder.of(Items.MENU_ARMOR)
                .buildItem((slot, event) -> {
                    getArmorShop().open(event.getPlayer());
                    return ActionResponse.DONE;
                })
        );
        blocks.setItem(3, ItemBuilder.of(Items.MENU_COMBAT)
                .buildItem((slot, event) -> {
                    getCombatShop().open(event.getPlayer());
                    return ActionResponse.DONE;
                })
        );
        blocks.setItem(4, ItemBuilder.of(Items.MENU_TOOLS)
                .buildItem((slot, event) -> {
                    getToolShop().open(event.getPlayer());
                    return ActionResponse.DONE;
                })
        );
        blocks.setItem(5, ItemBuilder.of(Items.MENU_POTIONS)
                .buildItem((slot, event) -> {
                    getPotionShop().open(event.getPlayer());
                    return ActionResponse.DONE;
                })
        );
        blocks.setItem(6, Items.MENU_UTILS);
        blocks.setItem(7, ItemBuilder.of(Items.MENU_CUSTOM_ITEMS)
                .buildItem((slot, event) -> {
                    getCustomShop().open(event.getPlayer());
                    return ActionResponse.DONE;
                })
        );

        blocks.setItem(15, Items.MENU_SELECTED_PAGE);


        blocks.getFiller().fill(Items.MENU_FILLER); // fill the border
        return blocks;
    }

    public Menu getCustomShop() {
        Menu blocks = Menus.menu().title("Item Shop ➜ Rotating Items").rows(5).addAllModifiers().create();

        // header

        blocks.setItem(1, ItemBuilder.of(Items.MENU_BLOCKS)
                .buildItem((slot, event) -> {
                    getBlocksShop().open(event.getPlayer());
                    return ActionResponse.DONE;
                })
        );
        blocks.setItem(2, ItemBuilder.of(Items.MENU_ARMOR)
                .buildItem((slot, event) -> {
                    getArmorShop().open(event.getPlayer());
                    return ActionResponse.DONE;
                })
        );
        blocks.setItem(3, ItemBuilder.of(Items.MENU_COMBAT)
                .buildItem((slot, event) -> {
                    getCombatShop().open(event.getPlayer());
                    return ActionResponse.DONE;
                })
        );
        blocks.setItem(4, ItemBuilder.of(Items.MENU_TOOLS)
                .buildItem((slot, event) -> {
                    getToolShop().open(event.getPlayer());
                    return ActionResponse.DONE;
                })
        );
        blocks.setItem(5, ItemBuilder.of(Items.MENU_POTIONS)
                .buildItem((slot, event) -> {
                    getPotionShop().open(event.getPlayer());
                    return ActionResponse.DONE;
                })
        );
        blocks.setItem(6, ItemBuilder.of(Items.MENU_UTILS)
                .buildItem((slot, event) -> {
                    getUtilShop().open(event.getPlayer());
                    return ActionResponse.DONE;
                })
        );
        blocks.setItem(7, Items.MENU_CUSTOM_ITEMS);

        blocks.setItem(16, Items.MENU_SELECTED_PAGE);


        blocks.getFiller().fill(Items.MENU_FILLER); // fill the border
        return blocks;
    }
}

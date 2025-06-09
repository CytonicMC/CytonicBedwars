package net.cytonic.cytonicbedwars.menu.itemShop;

import eu.koboo.minestom.stomui.api.PlayerView;
import eu.koboo.minestom.stomui.api.ViewBuilder;
import eu.koboo.minestom.stomui.api.ViewType;
import eu.koboo.minestom.stomui.api.component.ViewProvider;
import eu.koboo.minestom.stomui.api.item.PrebuiltItem;
import eu.koboo.minestom.stomui.api.item.ViewItem;
import eu.koboo.minestom.stomui.api.slots.ViewPattern;
import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytonicbedwars.menu.MenuUtils;
import net.cytonic.cytonicbedwars.utils.Items;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.player.CytosisPlayer;
import net.cytonic.cytosis.utils.Msg;
import net.kyori.adventure.sound.Sound;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;
import net.minestom.server.sound.SoundEvent;
import org.jetbrains.annotations.NotNull;

public class CombatShopMenu extends ViewProvider {

    private static final PrebuiltItem stoneSword = PrebuiltItem.of(Items.MENU_STONE_SWORD, action -> {
        action.getEvent().setCancelled(true);
        CytosisPlayer player = (CytosisPlayer) action.getPlayer();
        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
            if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("IRON", 10, player)) {
                CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("DEFAULT_SWORD", 1, player);
                player.getInventory().addItemStack(Items.STONE_SWORD);
                player.sendMessage(Msg.mm("<green>You bought a Stone Sword!"));
                player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
            } else {
                player.sendMessage(Msg.mm("<red>You need at least 10 iron to buy this!"));
                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            }
        } else {
            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            player.sendMessage(Msg.mm("<red>You don't have space in your inventory!"));
        }
    });
    private static final PrebuiltItem ironSword = PrebuiltItem.of(Items.MENU_IRON_SWORD, action -> {
        action.getEvent().setCancelled(true);
        CytosisPlayer player = (CytosisPlayer) action.getPlayer();
        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
            if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("GOLD", 7, player)) {
                CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("STONE_SWORD", 1, player);
                player.getInventory().addItemStack(Items.IRON_SWORD);
                player.sendMessage(Msg.mm("<green>You bought an Iron Sword!"));
                player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
            } else {
                player.sendMessage(Msg.mm("<red>You need at least 7 gold to buy this!"));
                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            }
        } else {
            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            player.sendMessage(Msg.mm("<red>You don't have space in your inventory!"));
        }
    });

    private static final PrebuiltItem diamondSword = PrebuiltItem.of(Items.MENU_DIAMOND_SWORD, action -> {
        action.getEvent().setCancelled(true);
        CytosisPlayer player = (CytosisPlayer) action.getPlayer();
        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
            if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("EMERALD", 6, player)) {
                CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("IRON_SWORD", 1, player);
                player.getInventory().addItemStack(Items.DIAMOND_SWORD);
                player.sendMessage(Msg.mm("<green>You bought a Diamond Sword!"));
                player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
            } else {
                player.sendMessage(Msg.mm("<red>You need at least 6 Emeralds to buy this!"));
                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            }
        } else {
            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            player.sendMessage(Msg.mm("<red>You don't have space in your inventory!"));
        }
    });

    private static final PrebuiltItem shield = PrebuiltItem.of(Items.MENU_SHIELD, action -> {
        action.getEvent().setCancelled(true);
        CytosisPlayer player = (CytosisPlayer) action.getPlayer();
        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
            if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("GOLD", 3, player)) {
                player.getInventory().addItemStack(Items.SHIELD);
                player.sendMessage(Msg.mm("<green>You bought a Shield!"));
                player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
            } else {
                player.sendMessage(Msg.mm("<red>You need at least 3 Gold to buy this!"));
                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            }
        } else {
            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            player.sendMessage(Msg.mm("<red>You don't have space in your inventory!"));
        }
    });

    private static final PrebuiltItem crossbow1 = PrebuiltItem.of(Items.MENU_CROSSBOW_1, action -> {
        action.getEvent().setCancelled(true);
        CytosisPlayer player = (CytosisPlayer) action.getPlayer();
        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
            if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("GOLD", 12, player)) {
                player.getInventory().addItemStack(Items.CROSSBOW_1);
                player.sendMessage(Msg.mm("<green>You bought a Crossbow!"));
                player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
            } else {
                player.sendMessage(Msg.mm("<red>You need at least 12 Gold to buy this!"));
                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            }
        } else {
            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            player.sendMessage(Msg.mm("<red>You don't have space in your inventory!"));
        }
    });

    private static final PrebuiltItem crossbow2 = PrebuiltItem.of(Items.MENU_CROSSBOW_2, action -> {
        action.getEvent().setCancelled(true);
        CytosisPlayer player = (CytosisPlayer) action.getPlayer();
        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
            if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("GOLD", 20, player)) {
                CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("CROSSBOW_1", 1, player);
                player.getInventory().addItemStack(Items.CROSSBOW_2);
                player.sendMessage(Msg.mm("<green>You bought a Crossbow!"));
                player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
            } else {
                player.sendMessage(Msg.mm("<red>You need at least 20 Gold to buy this!"));
                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            }
        } else {
            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            player.sendMessage(Msg.mm("<red>You don't have space in your inventory!"));
        }
    });

    private static final PrebuiltItem crossbow3 = PrebuiltItem.of(Items.MENU_CROSSBOW_3, action -> {
        action.getEvent().setCancelled(true);
        CytosisPlayer player = (CytosisPlayer) action.getPlayer();
        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
            if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("EMERALD", 6, player)) {
                CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("CROSSBOW_2", 1, player);
                player.getInventory().addItemStack(Items.CROSSBOW_3);
                player.sendMessage(Msg.mm("<green>You bought a Crossbow!"));
                player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
            } else {
                player.sendMessage(Msg.mm("<red>You need at least 6 Emeralds to buy this!"));
                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            }
        } else {
            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            player.sendMessage(Msg.mm("<red>You don't have space in your inventory!"));
        }
    });

    private static final PrebuiltItem bow1 = PrebuiltItem.of(Items.MENU_BOW_1, action -> {
        action.getEvent().setCancelled(true);
        CytosisPlayer player = (CytosisPlayer) action.getPlayer();
        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
            if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("GOLD", 10, player)) {player.getInventory().addItemStack(Items.BOW_1);
                player.sendMessage(Msg.mm("<green>You bought a bow!"));
                player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
            } else {
                player.sendMessage(Msg.mm("<red>You need at least 10 Gold to buy this!"));
                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            }
        } else {
            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            player.sendMessage(Msg.mm("<red>You don't have space in your inventory!"));
        }
    });

    private static final PrebuiltItem bow2 = PrebuiltItem.of(Items.MENU_BOW_2, action -> {
        action.getEvent().setCancelled(true);
        CytosisPlayer player = (CytosisPlayer) action.getPlayer();
        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
            if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("GOLD", 18, player)) {
                CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("BOW_1", 1, player);
                player.getInventory().addItemStack(Items.BOW_2);
                player.sendMessage(Msg.mm("<green>You bought a bow!"));
                player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
            } else {
                player.sendMessage(Msg.mm("<red>You need at least 18 Gold to buy this!"));
                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            }
        } else {
            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            player.sendMessage(Msg.mm("<red>You don't have space in your inventory!"));
        }
    });

    private static final PrebuiltItem bow3 = PrebuiltItem.of(Items.MENU_BOW_3, action -> {
        action.getEvent().setCancelled(true);
        CytosisPlayer player = (CytosisPlayer) action.getPlayer();
        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
            if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("EMERALD", 5, player)) {
                CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("BOW_2", 1, player);
                player.getInventory().addItemStack(Items.BOW_3);
                player.sendMessage(Msg.mm("<green>You bought a bow!"));
                player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
            } else {
                player.sendMessage(Msg.mm("<red>You need at least 5 Emeralds to buy this!"));
                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            }
        } else {
            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            player.sendMessage(Msg.mm("<red>You don't have space in your inventory!"));
        }
    });

    private static final PrebuiltItem arrow = PrebuiltItem.of(Items.MENU_ARROW, action -> {
        action.getEvent().setCancelled(true);
        CytosisPlayer player = (CytosisPlayer) action.getPlayer();
        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
            if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("GOLD", 2, player)) {
                ItemStack item = Items.ARROW;
                item = item.withAmount(8);
                player.getInventory().addItemStack(item);
                player.sendMessage(Msg.mm("<green>You bought some arrows!"));
                player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
            } else {
                player.sendMessage(Msg.mm("<red>You need at least 2 Gold to buy this!"));
                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            }
        } else {
            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            player.sendMessage(Msg.mm("<red>You don't have space in your inventory!"));
        }
    });

    private static final PrebuiltItem trident1 = PrebuiltItem.of(Items.MENU_TRIDENT_1, action -> {
        action.getEvent().setCancelled(true);
        CytosisPlayer player = (CytosisPlayer) action.getPlayer();
        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
            if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("GOLD", 12, player)) {
                player.getInventory().addItemStack(Items.TRIDENT_1);
                player.sendMessage(Msg.mm("<green>You bought a trident!"));
                player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
            } else {
                player.sendMessage(Msg.mm("<red>You need at least 12 Gold to buy this!"));
                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            }
        } else {
            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            player.sendMessage(Msg.mm("<red>You don't have space in your inventory!"));
        }
    });

    private static final PrebuiltItem trident2 = PrebuiltItem.of(Items.MENU_TRIDENT_2, action -> {
        action.getEvent().setCancelled(true);
        CytosisPlayer player = (CytosisPlayer) action.getPlayer();
        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
            if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("EMERALD", 5, player)) {
                CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("TRIDENT_1", 1, player);
                player.getInventory().addItemStack(Items.TRIDENT_2);
                player.sendMessage(Msg.mm("<green>You bought a trident!"));
                player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
            } else {
                player.sendMessage(Msg.mm("<red>You need at least 5 Emeralds to buy this!"));
                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            }
        } else {
            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            player.sendMessage(Msg.mm("<red>You don't have space in your inventory!"));
        }
    });

    private static final PrebuiltItem trident3 = PrebuiltItem.of(Items.MENU_TRIDENT_3, action -> {
        action.getEvent().setCancelled(true);
        CytosisPlayer player = (CytosisPlayer) action.getPlayer();
        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
            if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("EMERALD", 5, player)) {
                CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("TRIDENT_2", 1, player);
                player.getInventory().addItemStack(Items.TRIDENT_3);
                player.sendMessage(Msg.mm("<green>You bought a trident!"));
                player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
            } else {
                player.sendMessage(Msg.mm("<red>You need at least 5 Emeralds to buy this!"));
                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            }
        } else {
            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            player.sendMessage(Msg.mm("<red>You don't have space in your inventory!"));
        }
    });

    public CombatShopMenu() {
        super(Cytosis.VIEW_REGISTRY, ViewBuilder.of(ViewType.SIZE_5_X_9).title(Msg.mm("Item Shop ➜ Combat")));
    }

    @Override
    public void onOpen(@NotNull PlayerView view, @NotNull Player player) {
        ViewPattern pattern = Cytosis.VIEW_REGISTRY.pattern(
                "#bcatpur#",
                "##s######",
                "#########",
                "#########",
                "#########"
        );
        MenuUtils.setItemShopItems(view, pattern);

        ViewItem.bySlot(view, 19).applyPrebuilt(stoneSword);
        ViewItem.bySlot(view, 20).applyPrebuilt(ironSword);
        ViewItem.bySlot(view, 21).applyPrebuilt(diamondSword);
        ViewItem.bySlot(view, 22).applyPrebuilt(shield);
        ViewItem.bySlot(view, 23).applyPrebuilt(crossbow1);
        ViewItem.bySlot(view, 24).applyPrebuilt(crossbow2);
        ViewItem.bySlot(view, 25).applyPrebuilt(crossbow3);
        ViewItem.bySlot(view, 28).applyPrebuilt(bow1);
        ViewItem.bySlot(view, 29).applyPrebuilt(bow2);
        ViewItem.bySlot(view, 30).applyPrebuilt(bow3);
        ViewItem.bySlot(view, 31).applyPrebuilt(arrow);
        ViewItem.bySlot(view, 32).applyPrebuilt(trident1);
        ViewItem.bySlot(view, 33).applyPrebuilt(trident2);
        ViewItem.bySlot(view, 34).applyPrebuilt(trident3);
    }
}

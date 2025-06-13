package net.cytonic.cytonicbedwars.menu.itemShop;

import eu.koboo.minestom.stomui.api.PlayerView;
import eu.koboo.minestom.stomui.api.ViewBuilder;
import eu.koboo.minestom.stomui.api.ViewType;
import eu.koboo.minestom.stomui.api.component.ViewProvider;
import eu.koboo.minestom.stomui.api.item.PrebuiltItem;
import eu.koboo.minestom.stomui.api.item.ViewItem;
import eu.koboo.minestom.stomui.api.slots.ViewPattern;
import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytonicbedwars.data.enums.AxeLevel;
import net.cytonic.cytonicbedwars.data.enums.PickaxeLevel;
import net.cytonic.cytonicbedwars.menu.MenuUtils;
import net.cytonic.cytonicbedwars.utils.Items;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.utils.Msg;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.sound.Sound;
import net.minestom.server.component.DataComponents;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.component.CustomData;
import net.minestom.server.sound.SoundEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ToolShopMenu extends ViewProvider {

    public ToolShopMenu() {
        super(Cytosis.VIEW_REGISTRY, ViewBuilder.of(ViewType.SIZE_5_X_9).title(Msg.mm("Item Shop ➜ Tools")));
    }

    @Override
    public void onOpen(@NotNull PlayerView view, @NotNull Player player) {
        if (!CytonicBedWars.getGameManager().STARTED)
            throw new IllegalStateException("The game must be started to generate a tool shop!");
        ViewPattern pattern = Cytosis.VIEW_REGISTRY.pattern(
                "#bcatpur#",
                "####s####",
                "#########",
                "###APS###",
                "#########"
        );
        MenuUtils.setItemShopItems(view, pattern);
        ItemStack axeItemStack = switch (CytonicBedWars.getGameManager().getAxe(player.getUuid())) {
            case NONE ->
                    Items.MENU_WOODEN_AXE.with(DataComponents.CUSTOM_DATA, new CustomData(CompoundBinaryTag.builder().putString("type", "WOODEN").build()));
            case WOODEN ->
                    Items.MENU_STONE_AXE.with(DataComponents.CUSTOM_DATA, new CustomData(CompoundBinaryTag.builder().putString("type", "STONE").build()));
            case STONE ->
                    Items.MENU_IRON_AXE.with(DataComponents.CUSTOM_DATA, new CustomData(CompoundBinaryTag.builder().putString("type", "IRON").build()));
            case IRON ->
                    Items.MENU_DIAMOND_AXE.with(DataComponents.CUSTOM_DATA, new CustomData(CompoundBinaryTag.builder().putString("type", "DIAMOND").build()));
            case DIAMOND ->
                    Items.MENU_DIAMOND_AXE.withLore(Msg.green("<bold>Already purchased!")).with(DataComponents.CUSTOM_DATA, new CustomData(CompoundBinaryTag.builder().putString("type", "DIAMOND").build()));
        };
        PrebuiltItem axe = PrebuiltItem.of(axeItemStack, action -> {
            action.getEvent().setCancelled(true);
            switch (AxeLevel.valueOf(Objects.requireNonNull(action.getEvent().getClickedItem().get(DataComponents.CUSTOM_DATA)).nbt().getString("type"))) {
                case WOODEN -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("IRON", 10, player)) {
                            CytonicBedWars.getGameManager().setAxe(player.getUuid(), AxeLevel.WOODEN);
                            CytonicBedWars.getGameManager().getPlayerInventoryManager().setAxe(AxeLevel.WOODEN, player);
                            player.sendMessage(Msg.green("You purchased a wooden axe!"));
                            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
                            open(player);
                        } else {
                            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
                            player.sendMessage(Msg.red("You must have at least 10 Iron to buy this!"));
                        }
                    }
                }
                case STONE -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("IRON", 20, player)) {
                            CytonicBedWars.getGameManager().setAxe(player.getUuid(), AxeLevel.STONE);
                            CytonicBedWars.getGameManager().getPlayerInventoryManager().setAxe(AxeLevel.STONE, player);
                            player.sendMessage(Msg.green("You purchased a stone axe!"));
                            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
                            open(player);
                        } else {
                            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
                            player.sendMessage(Msg.red("You must have at least 20 Iron to buy this!"));
                        }
                    }
                }
                case IRON -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("GOLD", 6, player)) {
                            CytonicBedWars.getGameManager().setAxe(player.getUuid(), AxeLevel.IRON);
                            CytonicBedWars.getGameManager().getPlayerInventoryManager().setAxe(AxeLevel.IRON, player);
                            player.sendMessage(Msg.green("You purchased an iron axe!"));
                            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
                            open(player);
                        } else {
                            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
                            player.sendMessage(Msg.red("You must have at least 6 Gold to buy this!"));
                        }
                    }
                }
                case DIAMOND -> {
                    if (CytonicBedWars.getGameManager().getAxe(player.getUuid()).equals(AxeLevel.IRON)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                            if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("EMERALD", 3, player)) {
                                CytonicBedWars.getGameManager().setAxe(player.getUuid(), AxeLevel.DIAMOND);
                                CytonicBedWars.getGameManager().getPlayerInventoryManager().setAxe(AxeLevel.DIAMOND, player);
                                player.sendMessage(Msg.green("You purchased a diamond axe!"));
                                player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
                                open(player);
                            } else {
                                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
                                player.sendMessage(Msg.red("You must have at least 3 Emeralds to buy this!"));
                            }
                        }
                    } else if (CytonicBedWars.getGameManager().getAxe(player.getUuid()).equals(AxeLevel.DIAMOND)) {
                        player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
                        player.sendMessage(Msg.red("You already purchased a diamond axe!"));
                    }
                }
            }
        });

        ViewItem.bySlot(view, pattern.getSlot('A')).applyPrebuilt(axe);

        ItemStack pickaxeItemStack = switch (CytonicBedWars.getGameManager().getPickaxe(player.getUuid())) {
            case NONE ->
                    Items.MENU_WOODEN_PICKAXE.with(DataComponents.CUSTOM_DATA, new CustomData(CompoundBinaryTag.builder().putString("type", "WOODEN").build()));
            case WOODEN ->
                    Items.MENU_STONE_PICKAXE.with(DataComponents.CUSTOM_DATA, new CustomData(CompoundBinaryTag.builder().putString("type", "STONE").build()));
            case STONE ->
                    Items.MENU_IRON_PICKAXE.with(DataComponents.CUSTOM_DATA, new CustomData(CompoundBinaryTag.builder().putString("type", "IRON").build()));
            case IRON ->
                    Items.MENU_DIAMOND_PICKAXE.with(DataComponents.CUSTOM_DATA, new CustomData(CompoundBinaryTag.builder().putString("type", "DIAMOND").build()));
            case DIAMOND ->
                    Items.MENU_DIAMOND_PICKAXE.withLore(Msg.green("<bold>Already purchased!")).with(DataComponents.CUSTOM_DATA, new CustomData(CompoundBinaryTag.builder().putString("type", "DIAMOND").build()));
        };
        PrebuiltItem pickaxe = PrebuiltItem.of(pickaxeItemStack, action -> {
            action.getEvent().setCancelled(true);
            switch (PickaxeLevel.valueOf(Objects.requireNonNull(action.getEvent().getClickedItem().get(DataComponents.CUSTOM_DATA)).nbt().getString("type"))) {
                case WOODEN -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("IRON", 10, player)) {
                            CytonicBedWars.getGameManager().setPickaxe(player.getUuid(), PickaxeLevel.WOODEN);
                            CytonicBedWars.getGameManager().getPlayerInventoryManager().setPickaxe(PickaxeLevel.WOODEN, player);
                            player.sendMessage(Msg.green("You purchased a wooden pickaxe!"));
                            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
                            open(player);
                        } else {
                            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
                            player.sendMessage(Msg.red("You must have at least 10 Iron to buy this!"));
                        }
                    }
                }
                case STONE -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("IRON", 20, player)) {
                            CytonicBedWars.getGameManager().setPickaxe(player.getUuid(), PickaxeLevel.STONE);
                            CytonicBedWars.getGameManager().getPlayerInventoryManager().setPickaxe(PickaxeLevel.STONE, player);
                            player.sendMessage(Msg.green("You purchased a stone pickaxe!"));
                            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
                            open(player);
                        } else {
                            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
                            player.sendMessage(Msg.red("You must have at least 20 Iron to buy this!"));
                        }
                    }
                }
                case IRON -> {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("GOLD", 6, player)) {
                            CytonicBedWars.getGameManager().setPickaxe(player.getUuid(), PickaxeLevel.IRON);
                            CytonicBedWars.getGameManager().getPlayerInventoryManager().setPickaxe(PickaxeLevel.IRON, player);
                            player.sendMessage(Msg.green("You purchased an iron pickaxe!"));
                            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
                            open(player);
                        } else {
                            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
                            player.sendMessage(Msg.red("You must have at least 6 Gold to buy this!"));
                        }
                    }
                }
                case DIAMOND -> {
                    if (CytonicBedWars.getGameManager().getPickaxe(player.getUuid()).equals(PickaxeLevel.IRON)) {
                        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                            if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("EMERALD", 3, player)) {
                                CytonicBedWars.getGameManager().setPickaxe(player.getUuid(), PickaxeLevel.DIAMOND);
                                CytonicBedWars.getGameManager().getPlayerInventoryManager().setPickaxe(PickaxeLevel.DIAMOND, player);
                                player.sendMessage(Msg.green("You purchased a diamond pickaxe!"));
                                player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
                                open(player);
                            } else {
                                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
                                player.sendMessage(Msg.red("You must have at least 3 Emeralds to buy this!"));
                            }
                        }
                    } else if (CytonicBedWars.getGameManager().getPickaxe(player.getUuid()).equals(PickaxeLevel.DIAMOND)) {
                        player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
                        player.sendMessage(Msg.red("You already purchased a diamond pickaxe!"));
                    }
                }
            }
        });
        ViewItem.bySlot(view, pattern.getSlot('P')).applyPrebuilt(pickaxe);

        ItemStack shearsItemStack;
        if (CytonicBedWars.getGameManager().shears.get(player.getUuid())) {
            shearsItemStack = Items.MENU_SHEARS.withLore(Msg.green("<bold>Already purchased!"));
        } else {
            shearsItemStack = Items.MENU_SHEARS;
        }
        PrebuiltItem shears = PrebuiltItem.of(shearsItemStack, action -> {
            action.getEvent().setCancelled(true);
            if (CytonicBedWars.getGameManager().shears.get(player.getUuid())) {
                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
                player.sendMessage(Msg.red("You already purchased a pair of shears!"));
            } else {
                if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
                    if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("IRON", 24, player)) {
                        CytonicBedWars.getGameManager().shears.put(player.getUuid(), true);
                        player.getInventory().addItemStack(Items.SHEARS);
                        player.sendMessage(Msg.green("You purchased a pair of shears!"));
                        player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));

                        open(player);
                    } else {
                        player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
                        player.sendMessage(Msg.red("You must have at least 20 Iron to buy this!"));
                    }
                }
            }
        });
        ViewItem.bySlot(view, pattern.getSlot('S')).applyPrebuilt(shears);
    }
}

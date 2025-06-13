package net.cytonic.cytonicbedwars.menu.itemShop;

import eu.koboo.minestom.stomui.api.PlayerView;
import eu.koboo.minestom.stomui.api.ViewBuilder;
import eu.koboo.minestom.stomui.api.ViewType;
import eu.koboo.minestom.stomui.api.component.ViewProvider;
import eu.koboo.minestom.stomui.api.item.PrebuiltItem;
import eu.koboo.minestom.stomui.api.item.ViewItem;
import eu.koboo.minestom.stomui.api.slots.ViewPattern;
import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytonicbedwars.data.enums.MappableItem;
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

public class BlocksShopMenu extends ViewProvider {

    private static final PrebuiltItem wool = PrebuiltItem.of(Items.MENU_WOOL, action -> {
        action.getEvent().setCancelled(true);
        CytosisPlayer player = (CytosisPlayer) action.getPlayer();
        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
            if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("IRON", 4, player)) {
                ItemStack itemStack = Items.getTeamMapped(MappableItem.WOOL, CytonicBedWars.getGameManager().getPlayerTeam(player.getUuid()).orElseThrow());
                itemStack = itemStack.withAmount(16);
                player.getInventory().addItemStack(itemStack);
                player.sendMessage(Msg.green("You bought 16 wool!"));
                player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
            } else {
                player.sendMessage(Msg.red("You need at least 4 iron to buy this!"));
                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            }
        } else {
            Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1);
            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            player.sendMessage(Msg.red("You don't have space in your inventory!"));
        }
    });

    private static final PrebuiltItem blastGlass = PrebuiltItem.of(Items.MENU_BLAST_GLASS, action -> {
        action.getEvent().setCancelled(true);
        CytosisPlayer player = (CytosisPlayer) action.getPlayer();
        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
            if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("IRON", 12, player)) {
                ItemStack item = Items.getTeamMapped(MappableItem.GLASS, CytonicBedWars.getGameManager().getPlayerTeam(player.getUuid()).orElseThrow());
                item = item.withAmount(4);
                player.getInventory().addItemStack(item);
                player.sendMessage(Msg.green("You bought 4 Blast-Proof Glass!"));
                player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
            } else {
                player.sendMessage(Msg.red("You need at least 12 iron to buy this!"));
                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            }
        } else {
            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            player.sendMessage(Msg.red("You don't have space in your inventory!"));
        }
    });

    private static final PrebuiltItem endStone = PrebuiltItem.of(Items.MENU_END_STONE, action -> {
        action.getEvent().setCancelled(true);
        CytosisPlayer player = (CytosisPlayer) action.getPlayer();
        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
            if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("IRON", 24, player)) {
                ItemStack item = Items.END_STONE;
                item = item.withAmount(12);
                player.getInventory().addItemStack(item);
                player.sendMessage(Msg.green("You bought 12 end stone!"));
                player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
            } else {
                player.sendMessage(Msg.red("You need at least 24 iron to buy this!"));
                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            }
        } else {
            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            player.sendMessage(Msg.red("You don't have space in your inventory!"));
        }
    });

    private static final PrebuiltItem terracotta = PrebuiltItem.of(Items.MENU_TERRACOTTA, action -> {
        action.getEvent().setCancelled(true);
        CytosisPlayer player = (CytosisPlayer) action.getPlayer();
        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
            if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("IRON", 10, player)) {
                ItemStack item = Items.getTeamMapped(MappableItem.TERRACOTTA, CytonicBedWars.getGameManager().getPlayerTeam(player.getUuid()).orElseThrow());
                item = item.withAmount(12);
                player.getInventory().addItemStack(item);
                player.sendMessage(Msg.green("You bought 12 terracotta!"));
                player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
            } else {
                player.sendMessage(Msg.red("You need at least 10 iron to buy this!"));
                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            }
        } else {
            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            player.sendMessage(Msg.red("You don't have space in your inventory!"));
        }
    });

    private static final PrebuiltItem obsidian = PrebuiltItem.of(Items.MENU_OBSIDIAN, action -> {
        action.getEvent().setCancelled(true);
        CytosisPlayer player = (CytosisPlayer) action.getPlayer();
        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
            if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("EMERALD", 6, player)) {
                ItemStack item = Items.OBSIDIAN;
                item = item.withAmount(6);
                player.getInventory().addItemStack(item);
                player.sendMessage(Msg.green("You bought 6 obsidian!"));
                player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
            } else {
                player.sendMessage(Msg.red("You need at least 6 emeralds to buy this!"));
                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            }
        } else {
            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            player.sendMessage(Msg.red("You don't have space in your inventory!"));
        }
    });

    private static final PrebuiltItem planks = PrebuiltItem.of(Items.MENU_PLANKS, action -> {
        action.getEvent().setCancelled(true);
        CytosisPlayer player = (CytosisPlayer) action.getPlayer();
        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
            if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("GOLD", 4, player)) {
                ItemStack item = Items.PLANKS;
                item = item.withAmount(8);
                player.getInventory().addItemStack(item);
                player.sendMessage(Msg.green("You bought 8 planks!"));
                player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
            } else {
                player.sendMessage(Msg.red("You need at least 4 gold to buy this!"));
                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            }
        } else {
            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            player.sendMessage(Msg.red("You don't have space in your inventory!"));
        }
    });

    public BlocksShopMenu() {
        super(Cytosis.VIEW_REGISTRY, ViewBuilder.of(ViewType.SIZE_5_X_9).title(Msg.mm("Item Shop ➜ Blocks")));
    }

    @Override
    public void onOpen(@NotNull PlayerView view, @NotNull Player player) {
        ViewPattern pattern = Cytosis.VIEW_REGISTRY.pattern(
                "#bcatpur#",
                "#s#######",
                "###WBE###",
                "###TOP###",
                "#########"
        );
        MenuUtils.setItemShopItems(view, pattern);

        ViewItem.bySlot(view, pattern.getSlot('W')).applyPrebuilt(wool);
        ViewItem.bySlot(view, pattern.getSlot('B')).applyPrebuilt(blastGlass);
        ViewItem.bySlot(view, pattern.getSlot('E')).applyPrebuilt(endStone);
        ViewItem.bySlot(view, pattern.getSlot('T')).applyPrebuilt(terracotta);
        ViewItem.bySlot(view, pattern.getSlot('O')).applyPrebuilt(obsidian);
        ViewItem.bySlot(view, pattern.getSlot('P')).applyPrebuilt(planks);
    }
}

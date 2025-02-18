package net.cytonic.cytonicbedwars.menu.itemShop;

import eu.koboo.minestom.invue.api.PlayerView;
import eu.koboo.minestom.invue.api.ViewBuilder;
import eu.koboo.minestom.invue.api.ViewType;
import eu.koboo.minestom.invue.api.component.ViewProvider;
import eu.koboo.minestom.invue.api.item.PrebuiltItem;
import eu.koboo.minestom.invue.api.item.ViewItem;
import eu.koboo.minestom.invue.api.slots.ViewPattern;
import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytonicbedwars.menu.MenuUtils;
import net.cytonic.cytonicbedwars.utils.Items;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.player.CytosisPlayer;
import net.cytonic.cytosis.utils.Msg;
import net.kyori.adventure.sound.Sound;
import net.minestom.server.entity.Player;
import net.minestom.server.sound.SoundEvent;
import org.jetbrains.annotations.NotNull;

public class PotionShopMenu extends ViewProvider {

    private static final PrebuiltItem fireResistance = PrebuiltItem.of(Items.MENU_FIRE_RESISTANCE_POTION, action -> {
        action.getEvent().setCancelled(true);
        CytosisPlayer player = (CytosisPlayer) action.getPlayer();
        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
            if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("GOLD", 6, player)) {
                player.getInventory().addItemStack(Items.MENU_FIRE_RESISTANCE_POTION);
                player.sendMessage(Msg.mm("<green>You bought a Fire Resistance potion!"));
                player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());
            } else {
                player.sendMessage(Msg.mm("<red>You need at least 6 Gold to buy this!"));
                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
            }
        } else {
            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
            player.sendMessage(Msg.mm("<red>You don't have space in your inventory!"));
        }
    });

    private static final PrebuiltItem invis = PrebuiltItem.of(Items.MENU_INVISIBILITY_POTION, action -> {
        action.getEvent().setCancelled(true);
        CytosisPlayer player = (CytosisPlayer) action.getPlayer();
        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
            if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("EMERALD", 1, player)) {
                player.getInventory().addItemStack(Items.INVISIBILITY_POTION);
                player.sendMessage(Msg.mm("<green>You bought an Invisibility potion!"));
                player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());
            } else {
                player.sendMessage(Msg.mm("<red>You need at least 1 Emerald to buy this!"));
                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
            }
        } else {
            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
            player.sendMessage(Msg.mm("<red>You don't have space in your inventory!"));
        }
    });

    private static final PrebuiltItem jumpBoost = PrebuiltItem.of(Items.MENU_JUMP_BOOST_POTION, action -> {
        action.getEvent().setCancelled(true);
        CytosisPlayer player = (CytosisPlayer) action.getPlayer();
        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
            if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("EMERALD", 1, player)) {
                player.getInventory().addItemStack(Items.JUMP_BOOST_POTION);
                player.sendMessage(Msg.mm("<green>You bought a Jump Boost potion!"));
                player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());
            } else {
                player.sendMessage(Msg.mm("<red>You need at least 1 Emerald to buy this!"));
                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
            }
        } else {
            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
            player.sendMessage(Msg.mm("<red>You don't have space in your inventory!"));
        }
    });

    private static final PrebuiltItem speed = PrebuiltItem.of(Items.MENU_SPEED_POTION, action -> {
        action.getEvent().setCancelled(true);
        CytosisPlayer player = (CytosisPlayer) action.getPlayer();
        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().hasSpace(player)) {
            if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("EMERALD", 1, player)) {
                player.getInventory().addItemStack(Items.SPEED_POTION);
                player.sendMessage(Msg.mm("<green>You bought a Speed potion!"));
                player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1), player.getPosition());
            } else {
                player.sendMessage(Msg.mm("<red>You need at least 1 Emerald to buy this!"));
                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
            }
        } else {
            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1), player.getPosition());
            player.sendMessage(Msg.mm("<red>You don't have space in your inventory!"));
        }
    });

    public PotionShopMenu() {
        super(Cytosis.VIEW_REGISTRY, ViewBuilder.of(ViewType.SIZE_5_X_9).title(Msg.mm("Item Shop ➜ Potions")));
    }

    @Override
    public void onOpen(@NotNull PlayerView view, @NotNull Player player) {
        ViewPattern pattern = Cytosis.VIEW_REGISTRY.pattern(
                "#bcatpur#",
                "#####s###",
                "#########",
                "##FIJS###",
                "#########"
        );
        MenuUtils.setItemShopItems(view, pattern);

        ViewItem.bySlot(view, pattern.getSlot('F')).applyPrebuilt(fireResistance);
        ViewItem.bySlot(view, pattern.getSlot('I')).applyPrebuilt(invis);
        ViewItem.bySlot(view, pattern.getSlot('J')).applyPrebuilt(jumpBoost);
        ViewItem.bySlot(view, pattern.getSlot('S')).applyPrebuilt(speed);
    }
}

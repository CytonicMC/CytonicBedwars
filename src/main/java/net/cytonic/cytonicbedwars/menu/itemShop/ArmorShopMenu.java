package net.cytonic.cytonicbedwars.menu.itemShop;

import eu.koboo.minestom.stomui.api.PlayerView;
import eu.koboo.minestom.stomui.api.ViewBuilder;
import eu.koboo.minestom.stomui.api.ViewType;
import eu.koboo.minestom.stomui.api.component.ViewProvider;
import eu.koboo.minestom.stomui.api.item.PrebuiltItem;
import eu.koboo.minestom.stomui.api.item.ViewItem;
import eu.koboo.minestom.stomui.api.slots.ViewPattern;
import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytonicbedwars.data.enums.ArmorLevel;
import net.cytonic.cytonicbedwars.menu.MenuUtils;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytonicbedwars.utils.Items;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.utils.Msg;
import net.kyori.adventure.sound.Sound;
import net.minestom.server.entity.EquipmentSlot;
import net.minestom.server.entity.Player;
import net.minestom.server.sound.SoundEvent;
import org.jetbrains.annotations.NotNull;

public class ArmorShopMenu extends ViewProvider {

    private static final PrebuiltItem chainArmor = PrebuiltItem.of(Items.MENU_CHAINMAIL_BOOTS, action -> {
        action.getEvent().setCancelled(true);
        if (!(action.getPlayer() instanceof BedwarsPlayer player)) return;
        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("IRON", 40, player)) {
            player.getInventory().setEquipment(EquipmentSlot.LEGGINGS, player.getHeldSlot(), Items.CHAINMAIL_LEGS);
            player.getInventory().setEquipment(EquipmentSlot.BOOTS, player.getHeldSlot(), Items.CHAINMAIL_BOOTS);
            player.setArmorLevel(ArmorLevel.CHAINMAIL);
            player.sendMessage(Msg.green("You bought permanent chain armor!"));
            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
        } else {
            player.sendMessage(Msg.red("You need at least 40 iron to buy this!"));
            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
        }
    });

    private static final PrebuiltItem ironArmor = PrebuiltItem.of(Items.MENU_IRON_BOOTS, action -> {
        action.getEvent().setCancelled(true);
        if (!(action.getPlayer() instanceof BedwarsPlayer player)) return;
        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("GOLD", 12, player)) {
            player.getInventory().setEquipment(EquipmentSlot.LEGGINGS, player.getHeldSlot(), Items.IRON_LEGS);
            player.getInventory().setEquipment(EquipmentSlot.BOOTS, player.getHeldSlot(), Items.IRON_BOOTS);
            player.setArmorLevel(ArmorLevel.IRON);
            player.sendMessage(Msg.green("You bought permanent iron armor!"));
            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
        } else {
            player.sendMessage(Msg.red("You need at least 12 Gold to buy this!"));
            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
        }
    });

    private static final PrebuiltItem diamondArmor = PrebuiltItem.of(Items.MENU_DIAMOND_BOOTS, action -> {
        action.getEvent().setCancelled(true);
        if (!(action.getPlayer() instanceof BedwarsPlayer player)) return;
        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("EMERALD", 6, player)) {
            player.getInventory().setEquipment(EquipmentSlot.LEGGINGS, player.getHeldSlot(), Items.DIAMOND_LEGS);
            player.getInventory().setEquipment(EquipmentSlot.BOOTS, player.getHeldSlot(), Items.DIAMOND_BOOTS);
            player.setArmorLevel(ArmorLevel.DIAMOND);
            player.sendMessage(Msg.green("You bought permanent diamond armor!"));
            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
        } else {
            player.sendMessage(Msg.red("You need at least 6 Emeralds to buy this!"));
            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
        }
    });

    private static final PrebuiltItem netheriteArmor = PrebuiltItem.of(Items.MENU_NETHERITE_BOOTS, action -> {
        action.getEvent().setCancelled(true);
        if (!(action.getPlayer() instanceof BedwarsPlayer player)) return;
        if (CytonicBedWars.getGameManager().getPlayerInventoryManager().takeItem("EMERALD", 16, player)) {
            player.getInventory().setEquipment(EquipmentSlot.LEGGINGS, player.getHeldSlot(), Items.NETHERITE_LEGS);
            player.getInventory().setEquipment(EquipmentSlot.BOOTS, player.getHeldSlot(), Items.NETHERITE_BOOTS);
            player.setArmorLevel(ArmorLevel.NETHERITE);
            player.sendMessage(Msg.green("You bought permanent netherite armor!"));
            player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
        } else {
            player.sendMessage(Msg.red("You need at least 16 Emeralds to buy this!"));
            player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
        }
    });

    public ArmorShopMenu() {
        super(Cytosis.VIEW_REGISTRY, ViewBuilder.of(ViewType.SIZE_5_X_9).title(Msg.mm("Item Shop ➜ Armor")));
    }

    @Override
    public void onOpen(@NotNull PlayerView view, @NotNull Player player) {
        ViewPattern pattern = Cytosis.VIEW_REGISTRY.pattern(
                "#bcatpur#",
                "###s#####",
                "####C####",
                "###IDN###",
                "#########"
        );
        MenuUtils.setItemShopItems(view, pattern);


        ViewItem.bySlot(view, pattern.getSlot('C')).applyPrebuilt(chainArmor);
        ViewItem.bySlot(view, pattern.getSlot('I')).applyPrebuilt(ironArmor);
        ViewItem.bySlot(view, pattern.getSlot('D')).applyPrebuilt(diamondArmor);
        ViewItem.bySlot(view, pattern.getSlot('N')).applyPrebuilt(netheriteArmor);
    }
}

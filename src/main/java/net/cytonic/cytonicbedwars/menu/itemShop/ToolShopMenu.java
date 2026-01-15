package net.cytonic.cytonicbedwars.menu.itemShop;

import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import me.devnatan.inventoryframework.context.OpenContext;
import me.devnatan.inventoryframework.context.RenderContext;
import me.devnatan.inventoryframework.context.SlotClickContext;
import net.kyori.adventure.sound.Sound;
import net.minestom.server.item.ItemStack;
import net.minestom.server.sound.SoundEvent;
import net.minestom.server.tag.Tag;
import org.jetbrains.annotations.NotNull;

import net.cytonic.cytonicbedwars.data.enums.AxeLevel;
import net.cytonic.cytonicbedwars.data.enums.PickaxeLevel;
import net.cytonic.cytonicbedwars.data.enums.PriceType;
import net.cytonic.cytonicbedwars.managers.GameManager;
import net.cytonic.cytonicbedwars.menu.MenuUtils;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytonicbedwars.utils.Items;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.utils.Msg;

public class ToolShopMenu extends View {

    private static final Tag<@NotNull AxeLevel> AXE_TYPE_TAG = Tag.String("type")
        .map(AxeLevel::valueOf, AxeLevel::name);
    private static final Tag<@NotNull PickaxeLevel> PICKAXE_TYPE_TAG = Tag.String("type")
        .map(PickaxeLevel::valueOf, PickaxeLevel::name);

    @Override
    public void onInit(@NotNull ViewConfigBuilder config) {
        config.layout(
            "#bcatpur#",
            "####s####",
            "#########",
            "###APS###",
            "#########"
        );
        config.title(Msg.mm("Item Shop ➜ Tools"));
        config.cancelInteractions();
        config.size(5);
    }

    @Override
    public void onOpen(@NotNull OpenContext open) {
        if (!Cytosis.CONTEXT.getComponent(GameManager.class).STARTED)
            throw new IllegalStateException("The game must be started to generate a tool shop!");
    }

    @Override
    public void onFirstRender(@NotNull RenderContext context) {
        MenuUtils.setItemShopItems(context);

        axe(context);
        pickaxe(context);
        shears(context);
    }

    private void axe(RenderContext context) {
        context.layoutSlot('A').onRender(slotRenderContext -> {
            BedwarsPlayer player = (BedwarsPlayer) slotRenderContext.getPlayer();

            ItemStack itemStack = switch (player.getAxeLevel()) {
                case NONE -> Items.MENU_WOODEN_AXE.withTag(AXE_TYPE_TAG, AxeLevel.WOODEN);
                case WOODEN -> Items.MENU_STONE_AXE.withTag(AXE_TYPE_TAG, AxeLevel.STONE);
                case STONE -> Items.MENU_IRON_AXE.withTag(AXE_TYPE_TAG, AxeLevel.IRON);
                case IRON -> Items.MENU_DIAMOND_AXE.withTag(AXE_TYPE_TAG, AxeLevel.DIAMOND);
                case DIAMOND -> Items.MENU_DIAMOND_AXE.withTag(AXE_TYPE_TAG, AxeLevel.NONE)
                    .withLore(Msg.green("<bold>Already purchased!"));
            };
            slotRenderContext.setItem(itemStack);
        }).onClick(slotClickContext -> {
            BedwarsPlayer player = (BedwarsPlayer) slotClickContext.getPlayer();
            AxeLevel axeLevel = slotClickContext.getItem().getTag(AXE_TYPE_TAG);
            if (axeLevel == AxeLevel.DIAMOND && player.getAxeLevel().equals(AxeLevel.DIAMOND)) {
                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
                player.sendMessage(Msg.red("You already purchased a diamond axe!"));
                return;
            }

            switch (slotClickContext.getItem().getTag(AXE_TYPE_TAG)) {
                case WOODEN -> giveAxe(slotClickContext, player, axeLevel, PriceType.IRON, 10);
                case STONE -> giveAxe(slotClickContext, player, axeLevel, PriceType.IRON, 20);
                case IRON -> giveAxe(slotClickContext, player, axeLevel, PriceType.GOLD, 6);
                case DIAMOND -> giveAxe(slotClickContext, player, axeLevel, PriceType.EMERALD, 3);
            }
        });
    }

    private void pickaxe(RenderContext context) {
        context.layoutSlot('P').onRender(slotRenderContext -> {
            BedwarsPlayer player = (BedwarsPlayer) slotRenderContext.getPlayer();

            ItemStack itemStack = switch (player.getPickaxeLevel()) {
                case NONE -> Items.MENU_WOODEN_PICKAXE.withTag(PICKAXE_TYPE_TAG, PickaxeLevel.WOODEN);
                case WOODEN -> Items.MENU_DIAMOND_PICKAXE.withTag(PICKAXE_TYPE_TAG, PickaxeLevel.STONE);
                case STONE -> Items.MENU_DIAMOND_PICKAXE.withTag(PICKAXE_TYPE_TAG, PickaxeLevel.IRON);
                case IRON -> Items.MENU_DIAMOND_PICKAXE.withTag(PICKAXE_TYPE_TAG, PickaxeLevel.DIAMOND);
                case DIAMOND -> Items.MENU_DIAMOND_PICKAXE.withTag(PICKAXE_TYPE_TAG, PickaxeLevel.NONE)
                    .withLore(Msg.green("<bold>Already purchased!"));
            };
            slotRenderContext.setItem(itemStack);
        }).onClick(slotClickContext -> {
            BedwarsPlayer player = (BedwarsPlayer) slotClickContext.getPlayer();
            PickaxeLevel pickaxeLevel = slotClickContext.getItem().getTag(PICKAXE_TYPE_TAG);
            if (pickaxeLevel == PickaxeLevel.DIAMOND && player.getPickaxeLevel().equals(PickaxeLevel.DIAMOND)) {
                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
                player.sendMessage(Msg.red("You already purchased a diamond pickaxe!"));
                return;
            }

            switch (slotClickContext.getItem().getTag(PICKAXE_TYPE_TAG)) {
                case WOODEN -> givePickaxe(slotClickContext, player, pickaxeLevel, PriceType.IRON, 10);
                case STONE -> givePickaxe(slotClickContext, player, pickaxeLevel, PriceType.IRON, 20);
                case IRON -> givePickaxe(slotClickContext, player, pickaxeLevel, PriceType.GOLD, 6);
                case DIAMOND -> givePickaxe(slotClickContext, player, pickaxeLevel, PriceType.EMERALD, 3);
            }
        });
    }

    private void shears(RenderContext context) {
        context.layoutSlot('S').onRender(slotRenderContext -> {
            BedwarsPlayer player = (BedwarsPlayer) slotRenderContext.getPlayer();
            if (player.hasShears()) {
                slotRenderContext.setItem(Items.MENU_SHEARS.withLore(Msg.green("<bold>Already purchased!")));
            } else {
                slotRenderContext.setItem(Items.MENU_SHEARS);
            }
        }).onClick(slotClickContext -> {
            BedwarsPlayer player = (BedwarsPlayer) slotClickContext.getPlayer();
            if (player.hasShears()) {
                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
                player.sendMessage(Msg.red("You already purchased a pair of shears!"));
                return;
            }
            if (player.hasSpace()) {
                if (player.takeItem("IRON", 24)) {
                    player.setShears(true);
                    player.getInventory().addItemStack(Items.SHEARS);
                    player.sendMessage(Msg.green("You purchased a pair of shears!"));
                    player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
                    slotClickContext.update();
                } else {
                    player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
                    player.sendMessage(Msg.red("You must have at least 20 Iron to buy this!"));
                }
            }
        });
    }

    private void giveAxe(SlotClickContext slotClickContext, BedwarsPlayer player, AxeLevel axeLevel,
        PriceType priceType, int priceAmount) {
        if (player.hasSpace()) {
            if (player.takeItem(priceType.name(), priceAmount)) {
                player.setAxeLevel(axeLevel);
                player.sendMessage(Msg.green("You purchased a %s axe!", axeLevel.name().toLowerCase()));
                player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
                slotClickContext.update();
            } else {
                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
                player.sendMessage(
                    Msg.red("You must have at least %d %s to buy this!", priceAmount, priceType.getName()));
            }
        }
    }

    private void givePickaxe(SlotClickContext slotClickContext, BedwarsPlayer player, PickaxeLevel pickaxeLevel,
        PriceType priceType, int priceAmount) {
        if (player.hasSpace()) {
            if (player.takeItem(priceType.name(), priceAmount)) {
                player.setPickaxe(pickaxeLevel);
                player.sendMessage(Msg.green("You purchased a %s pickaxe!", pickaxeLevel.name().toLowerCase()));
                player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
                slotClickContext.update();
            } else {
                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
                player.sendMessage(
                    Msg.red("You must have at least %d %s to buy this!", priceAmount, priceType.getName()));
            }
        }
    }
}

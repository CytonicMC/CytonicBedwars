package net.cytonic.cytonicbedwars.menu;

import java.util.function.Consumer;

import lombok.experimental.UtilityClass;
import me.devnatan.inventoryframework.context.RenderContext;
import net.kyori.adventure.sound.Sound;
import net.minestom.server.component.DataComponents;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.sound.SoundEvent;

import net.cytonic.cytonicbedwars.data.enums.PriceType;
import net.cytonic.cytonicbedwars.menu.itemShop.ArmorShopMenu;
import net.cytonic.cytonicbedwars.menu.itemShop.BlocksShopMenu;
import net.cytonic.cytonicbedwars.menu.itemShop.CombatShopMenu;
import net.cytonic.cytonicbedwars.menu.itemShop.PotionShopMenu;
import net.cytonic.cytonicbedwars.menu.itemShop.RotatingShopMenu;
import net.cytonic.cytonicbedwars.menu.itemShop.ToolShopMenu;
import net.cytonic.cytonicbedwars.menu.itemShop.UtilsShopMenu;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytosis.utils.Msg;

@UtilityClass
public class MenuUtils {

    public static final ItemStack filler = ItemStack.builder(Material.GRAY_STAINED_GLASS_PANE).hideExtraTooltip()
        .build();
    public static final ItemStack selectedPage = ItemStack.builder(Material.LIME_STAINED_GLASS_PANE).build();

    public static void blocksMenu(RenderContext context, char layoutSlot) {
        context.layoutSlot(layoutSlot).onRender(slotRenderContext -> {
            ItemStack.Builder builder = ItemStack.builder(Material.BRICKS);
            builder.set(DataComponents.ITEM_NAME, Msg.green("Building Blocks"));
            builder.lore(Msg.grey("Click to browse!"));
            slotRenderContext.setItem(builder.build());
        }).onClick(slotClickContext -> slotClickContext.openForPlayer(BlocksShopMenu.class));
    }

    public static void armorMenu(RenderContext context, char layoutSlot) {
        context.layoutSlot(layoutSlot).onRender(slotRenderContext -> {
            ItemStack.Builder builder = ItemStack.builder(Material.DIAMOND_CHESTPLATE);
            builder.set(DataComponents.ITEM_NAME, Msg.green("Armor"));
            builder.lore(Msg.grey("Click to browse!"));
            slotRenderContext.setItem(builder.build());
        }).onClick(slotClickContext -> slotClickContext.openForPlayer(ArmorShopMenu.class));
    }

    public static void combatMenu(RenderContext context, char layoutSlot) {
        context.layoutSlot(layoutSlot).onRender(slotRenderContext -> {
            ItemStack.Builder builder = ItemStack.builder(Material.DIAMOND_SWORD);
            builder.set(DataComponents.ITEM_NAME, Msg.green("Weapons"));
            builder.lore(Msg.grey("Click to browse!"));
            slotRenderContext.setItem(builder.build());
        }).onClick(slotClickContext -> slotClickContext.openForPlayer(CombatShopMenu.class));
    }

    public static void toolMenu(RenderContext context, char layoutSlot) {
        context.layoutSlot(layoutSlot).onRender(slotRenderContext -> {
            ItemStack.Builder builder = ItemStack.builder(Material.DIAMOND_PICKAXE);
            builder.set(DataComponents.ITEM_NAME, Msg.green("Tools"));
            builder.lore(Msg.grey("Click to browse!"));
            slotRenderContext.setItem(builder.build());
        }).onClick(slotClickContext -> slotClickContext.openForPlayer(ToolShopMenu.class));
    }

    public static void potionMenu(RenderContext context, char layoutSlot) {
        context.layoutSlot(layoutSlot).onRender(slotRenderContext -> {
            ItemStack.Builder builder = ItemStack.builder(Material.BREWING_STAND);
            builder.set(DataComponents.ITEM_NAME, Msg.green("Potions"));
            builder.lore(Msg.grey("Click to browse!"));
            slotRenderContext.setItem(builder.build());
        }).onClick(slotClickContext -> slotClickContext.openForPlayer(PotionShopMenu.class));
    }

    public static void utilsMenu(RenderContext context, char layoutSlot) {
        context.layoutSlot(layoutSlot).onRender(slotRenderContext -> {
            ItemStack.Builder builder = ItemStack.builder(Material.FIRE_CHARGE);
            builder.set(DataComponents.ITEM_NAME, Msg.green("Utilities"));
            builder.lore(Msg.grey("Click to browse!"));
            slotRenderContext.setItem(builder.build());
        }).onClick(slotClickContext -> slotClickContext.openForPlayer(UtilsShopMenu.class));
    }

    public static void rotatingMenu(RenderContext context, char layoutSlot) {
        context.layoutSlot(layoutSlot).onRender(slotRenderContext -> {
            ItemStack.Builder builder = ItemStack.builder(Material.ENDERMAN_SPAWN_EGG);
            builder.set(DataComponents.ITEM_NAME, Msg.red("<b>Coming Soon"));
            slotRenderContext.setItem(builder.build());
        }).onClick(slotClickContext -> slotClickContext.openForPlayer(RotatingShopMenu.class));
    }

    public static void setItemShopItems(RenderContext context) {
        context.layoutSlot('#', filler);

        blocksMenu(context, 'b');
        combatMenu(context, 'c');
        armorMenu(context, 'a');
        toolMenu(context, 't');
        potionMenu(context, 'p');
        utilsMenu(context, 'u');
        rotatingMenu(context, 'r');
        context.layoutSlot('s', selectedPage);
    }

    public static void setItem(RenderContext context, char layoutSlot, Material material, String name, PriceType price,
        int priceAmount, Consumer<BedwarsPlayer> onPurchase) {
        context.layoutSlot(layoutSlot).onRender(slotRenderContext -> {
            ItemStack.Builder builder = ItemStack.builder(material);
            builder.set(DataComponents.ITEM_NAME, Msg.mm(name));
            builder.lore(Msg.mm("> %d %s", priceAmount, price.getName()));
            slotRenderContext.setItem(builder.build());
        }).onClick(slotClickContext -> {
            if (!(slotClickContext.getPlayer() instanceof BedwarsPlayer player)) return;
            if (player.takeItem(price.name(), priceAmount)) {
                onPurchase.accept(player);
                player.sendMessage(Msg.green("You bought %s!", name.toLowerCase()));
                player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
            } else {
                player.sendMessage(Msg.red("You need at least %d %s to buy this!", priceAmount, name.toLowerCase()));
                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
            }
        });
    }

    public static void setItem(RenderContext context, char layoutSlot, Material material, String name, PriceType price,
        int priceAmount, int amount, Consumer<BedwarsPlayer> onPurchase) {
        context.layoutSlot(layoutSlot).onRender(slotRenderContext -> {
            ItemStack.Builder builder = ItemStack.builder(material);
            builder.set(DataComponents.ITEM_NAME, Msg.mm(name));
            builder.lore(Msg.mm("> %d %s", priceAmount, price.getName()));
            slotRenderContext.setItem(builder.build());
        }).onClick(slotClickContext -> {
            if (!(slotClickContext.getPlayer() instanceof BedwarsPlayer player)) return;
            if (player.hasSpace()) {
                if (player.takeItem(price.name(), priceAmount)) {
                    onPurchase.accept(player);
                    player.sendMessage(Msg.green("You bought %d %s!", amount, name.toLowerCase()));
                    player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
                } else {
                    player.sendMessage(
                        Msg.red("You need at least %d %s to buy this!", priceAmount, name.toLowerCase()));
                    player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
                }
            } else {
                Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1);
                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
                player.sendMessage(Msg.red("You don't have space in your inventory!"));
            }
        });
    }

    public static void setItem(RenderContext context, int slot, Material material, String name, PriceType price,
        int priceAmount, int amount, Consumer<BedwarsPlayer> onPurchase) {
        context.slot(slot).onRender(slotRenderContext -> {
            ItemStack.Builder builder = ItemStack.builder(material);
            builder.set(DataComponents.ITEM_NAME, Msg.mm(name));
            builder.lore(Msg.mm("> %d %s", priceAmount, price.getName()));
            slotRenderContext.setItem(builder.build());
        }).onClick(slotClickContext -> {
            if (!(slotClickContext.getPlayer() instanceof BedwarsPlayer player)) return;
            if (player.hasSpace()) {
                if (player.takeItem(price.name(), priceAmount)) {
                    onPurchase.accept(player);
                    if (amount == 1) {
                        player.sendMessage(Msg.green("You bought a %s!", name.toLowerCase()));
                    } else {
                        player.sendMessage(Msg.green("You bought %d %s!", amount, name.toLowerCase()));
                    }
                    player.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.MASTER, 1, 1));
                } else {
                    player.sendMessage(
                        Msg.red("You need at least %d %s to buy this!", priceAmount, name.toLowerCase()));
                    player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
                }
            } else {
                Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1);
                player.playSound(Sound.sound(SoundEvent.ENTITY_VILLAGER_NO, Sound.Source.MASTER, 1, 1));
                player.sendMessage(Msg.red("You don't have space in your inventory!"));
            }
        });
    }
}

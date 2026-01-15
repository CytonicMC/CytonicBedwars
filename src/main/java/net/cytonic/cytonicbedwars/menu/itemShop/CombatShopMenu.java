package net.cytonic.cytonicbedwars.menu.itemShop;

import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import me.devnatan.inventoryframework.context.RenderContext;
import net.minestom.server.item.Material;
import org.jetbrains.annotations.NotNull;

import net.cytonic.cytonicbedwars.data.enums.PriceType;
import net.cytonic.cytonicbedwars.menu.MenuUtils;
import net.cytonic.cytonicbedwars.utils.Items;
import net.cytonic.cytosis.utils.Msg;

public class CombatShopMenu extends View {

    @Override
    public void onInit(@NotNull ViewConfigBuilder config) {
        config.layout(
            "#bcatpur#",
            "##s######",
            "#########",
            "#########",
            "#########"
        );
        config.title(Msg.mm("Item Shop ➜ Combat"));
        config.cancelInteractions();
        config.size(5);
    }

    @Override
    public void onFirstRender(@NotNull RenderContext context) {
        MenuUtils.setItemShopItems(context);

        MenuUtils.setItem(context, 19, Material.STONE_SWORD, "Stone Sword", PriceType.IRON, 10, 1,
            player -> player.setSword(Items.STONE_SWORD));

        MenuUtils.setItem(context, 20, Material.IRON_SWORD, "Iron Sword", PriceType.GOLD, 7, 1,
            player -> player.setSword(Items.IRON_SWORD));

        MenuUtils.setItem(context, 21, Material.STONE_SWORD, "Diamond Sword", PriceType.EMERALD, 6, 1,
            player -> player.setSword(Items.DIAMOND_SWORD));

        MenuUtils.setItem(context, 22, Material.SHIELD, "Shield", PriceType.GOLD, 3, 1,
            player -> player.getInventory().addItemStack(Items.SHIELD));

        MenuUtils.setItem(context, 23, Material.CROSSBOW, "Crossbow", PriceType.GOLD, 12, 1,
            player -> player.getInventory().addItemStack(Items.CROSSBOW_1));

        MenuUtils.setItem(context, 24, Material.CROSSBOW, "Crossbow", PriceType.GOLD, 20, 1, player -> {
            player.takeItem("CROSSBOW_1", 1);
            player.getInventory().addItemStack(Items.CROSSBOW_2);
        });

        MenuUtils.setItem(context, 25, Material.CROSSBOW, "Crossbow", PriceType.EMERALD, 6, 1, player -> {
            player.takeItem("CROSSBOW_1", 1);
            player.takeItem("CROSSBOW_2", 1);
            player.getInventory().addItemStack(Items.CROSSBOW_3);
        });

        MenuUtils.setItem(context, 28, Material.BOW, "Bow", PriceType.GOLD, 10, 1,
            player -> player.getInventory().addItemStack(Items.BOW_1));

        MenuUtils.setItem(context, 29, Material.BOW, "Bow", PriceType.GOLD, 18, 1,
            player -> player.getInventory().addItemStack(Items.BOW_2));

        MenuUtils.setItem(context, 30, Material.BOW, "Bow", PriceType.GOLD, 12, 1,
            player -> player.getInventory().addItemStack(Items.BOW_3));

        MenuUtils.setItem(context, 31, Material.ARROW, "Arrow", PriceType.GOLD, 2, 8,
            player -> player.getInventory().addItemStack(Items.ARROW.withAmount(8)));

        MenuUtils.setItem(context, 31, Material.TRIDENT, "Trident", PriceType.GOLD, 12, 1,
            player -> player.getInventory().addItemStack(Items.TRIDENT_1));

        MenuUtils.setItem(context, 31, Material.TRIDENT, "Trident", PriceType.EMERALD, 5, 1, player -> {
            player.getInventory().addItemStack(Items.TRIDENT_2);
            player.takeItem("TRIDENT_1", 1);
        });

        MenuUtils.setItem(context, 31, Material.TRIDENT, "Trident", PriceType.GOLD, 5, 1, player -> {
            player.getInventory().addItemStack(Items.TRIDENT_3);
            player.takeItem("TRIDENT_1", 1);
            player.takeItem("TRIDENT_2", 1);
        });
    }
}

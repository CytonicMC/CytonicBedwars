package net.cytonic.cytonicbedwars.menu.itemShop;

import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import me.devnatan.inventoryframework.context.RenderContext;
import net.cytonic.cytonicbedwars.data.enums.MappableItem;
import net.cytonic.cytonicbedwars.data.enums.PriceType;
import net.cytonic.cytonicbedwars.managers.GameManager;
import net.cytonic.cytonicbedwars.menu.MenuUtils;
import net.cytonic.cytonicbedwars.utils.Items;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.utils.Msg;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import org.jetbrains.annotations.NotNull;

public class BlocksShopMenu extends View {

    @Override
    public void onInit(@NotNull ViewConfigBuilder config) {
        config.layout(
                "#bcatpur#",
                "#s#######",
                "###WBE###",
                "###TOP###",
                "#########"
        );
        config.title(Msg.mm("Item Shop ➜ Blocks"));
        config.cancelInteractions();
        config.size(5);
    }

    @Override
    public void onFirstRender(@NotNull RenderContext context) {
        MenuUtils.setItemShopItems(context);

        MenuUtils.setItem(context, 'W', Material.WHITE_WOOL, "Wool", PriceType.IRON, 4, 16, player -> {
            ItemStack itemStack = Items.getTeamMapped(MappableItem.WOOL, Cytosis.CONTEXT.getComponent(GameManager.class).getPlayerTeam(player).orElseThrow());
            itemStack = itemStack.withAmount(16);
            player.getInventory().addItemStack(itemStack);
        });

        MenuUtils.setItem(context, 'B', Material.GLASS, "Blast-Proof Glass", PriceType.IRON, 12, 4, player -> {
            ItemStack item = Items.getTeamMapped(MappableItem.GLASS, Cytosis.CONTEXT.getComponent(GameManager.class).getPlayerTeam(player).orElseThrow());
            item = item.withAmount(4);
            player.getInventory().addItemStack(item);
        });

        MenuUtils.setItem(context, 'E', Material.END_STONE, "End Stone", PriceType.IRON, 24, 12, player -> {
            ItemStack item = Items.END_STONE;
            item = item.withAmount(12);
            player.getInventory().addItemStack(item);
        });

        MenuUtils.setItem(context, 'T', Material.END_STONE, "Terracotta", PriceType.IRON, 10, 12, player -> {
            ItemStack item = Items.getTeamMapped(MappableItem.TERRACOTTA, Cytosis.CONTEXT.getComponent(GameManager.class).getPlayerTeam(player).orElseThrow());
            item = item.withAmount(12);
            player.getInventory().addItemStack(item);
        });

        MenuUtils.setItem(context, 'O', Material.END_STONE, "Obsidian", PriceType.EMERALD, 6, 6, player -> {
            ItemStack item = Items.OBSIDIAN;
            item = item.withAmount(6);
            player.getInventory().addItemStack(item);
        });

        MenuUtils.setItem(context, 'P', Material.OAK_PLANKS, "Planks", PriceType.GOLD, 4, 8, player -> {
            ItemStack item = Items.PLANKS;
            item = item.withAmount(8);
            player.getInventory().addItemStack(item);
        });
    }
}

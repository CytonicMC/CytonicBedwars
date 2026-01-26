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

public class PotionShopMenu extends View {

    @Override
    public void onInit(@NotNull ViewConfigBuilder config) {
        config.layout(
            "#bcatpur#",
            "#####s###",
            "#########",
            "##FIJS###",
            "#########"
        );
        config.title(Msg.mm("Item Shop ➜ Potions"));
        config.cancelInteractions();
        config.size(5);
    }

    @Override
    public void onFirstRender(@NotNull RenderContext context) {
        MenuUtils.setItemShopItems(context);

        MenuUtils.setItem(context, 'F', Material.POTION, "Fire Resistance (60s)", PriceType.GOLD, 6, 1, player -> {
            player.getInventory().addItemStack(Items.FIRE_RESISTANCE_POTION);
        });

        MenuUtils.setItem(context, 'I', Material.POTION, "Invisibility (30s)", PriceType.EMERALD, 1, 1, player -> {
            player.getInventory().addItemStack(Items.INVISIBILITY_POTION);
        });

        MenuUtils.setItem(context, 'J', Material.POTION, "Jump Boost (60s)", PriceType.EMERALD, 1, 1, player -> {
            player.getInventory().addItemStack(Items.JUMP_BOOST_POTION);
        });

        MenuUtils.setItem(context, 'S', Material.POTION, "Speed (60s)", PriceType.EMERALD, 1, 1, player -> {
            player.getInventory().addItemStack(Items.SPEED_POTION);
        });
    }
}

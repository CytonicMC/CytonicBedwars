package net.cytonic.cytonicbedwars.menu.itemShop;

import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import me.devnatan.inventoryframework.context.RenderContext;
import org.jetbrains.annotations.NotNull;

import net.cytonic.cytonicbedwars.menu.MenuUtils;
import net.cytonic.cytosis.utils.Msg;

public class RotatingShopMenu extends View {

    @Override
    public void onInit(@NotNull ViewConfigBuilder config) {
        config.layout(
            "#bcatpur#",
            "#######s#",
            "#########",
            "#########",
            "#########"
        );
        config.title(Msg.mm("Item Shop ➜ Rotating Items"));
        config.cancelInteractions();
        config.size(5);
    }

    @Override
    public void onFirstRender(@NotNull RenderContext context) {
        MenuUtils.setItemShopItems(context);
    }
}

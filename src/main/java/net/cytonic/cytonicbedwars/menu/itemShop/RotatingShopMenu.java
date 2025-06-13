package net.cytonic.cytonicbedwars.menu.itemShop;

import eu.koboo.minestom.stomui.api.PlayerView;
import eu.koboo.minestom.stomui.api.ViewBuilder;
import eu.koboo.minestom.stomui.api.ViewType;
import eu.koboo.minestom.stomui.api.component.ViewProvider;
import eu.koboo.minestom.stomui.api.slots.ViewPattern;
import net.cytonic.cytonicbedwars.menu.MenuUtils;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.utils.Msg;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RotatingShopMenu extends ViewProvider {

    public RotatingShopMenu() {
        super(Cytosis.VIEW_REGISTRY, ViewBuilder.of(ViewType.SIZE_5_X_9).title(Msg.mm("Item Shop ➜ Rotating Items")));
    }

    @Override
    public void onOpen(@NotNull PlayerView view, @NotNull Player player) {
        ViewPattern pattern = Cytosis.VIEW_REGISTRY.pattern(
                "#bcatpur#",
                "#######s#",
                "#########",
                "#########",
                "#########"
        );
        MenuUtils.setItemShopItems(view, pattern);
    }
}

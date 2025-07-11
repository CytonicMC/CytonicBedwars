package net.cytonic.cytonicbedwars.menu.spectators;

import eu.koboo.minestom.stomui.api.PlayerView;
import eu.koboo.minestom.stomui.api.ViewBuilder;
import eu.koboo.minestom.stomui.api.ViewType;
import eu.koboo.minestom.stomui.api.component.ViewProvider;
import eu.koboo.minestom.stomui.api.item.PrebuiltItem;
import eu.koboo.minestom.stomui.api.item.ViewItem;
import eu.koboo.minestom.stomui.api.slots.ViewPattern;
import net.cytonic.cytonicbedwars.menu.MenuUtils;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.utils.Msg;
import net.minestom.server.component.DataComponents;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.component.TooltipDisplay;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class SpectatorSpeedMenu extends ViewProvider {

    private static final PrebuiltItem tenthSpeed = PrebuiltItem.of(
            ItemStack.builder(Material.LEATHER_BOOTS)
                    .set(DataComponents.TOOLTIP_DISPLAY, new TooltipDisplay(false, Set.of(DataComponents.EQUIPPABLE)))
                    .set(DataComponents.ITEM_NAME, Msg.green("0.1x flight speed"))
                    .build(), action -> {
                action.getEvent().setCancelled(true);
                action.getPlayer().setFlyingSpeed(0.01f);
                action.getPlayer().sendMessage(Msg.success("Your flight speed is now <gold>0.1x"));
            });

    private static final PrebuiltItem halfSpeed = PrebuiltItem.of(
            ItemStack.builder(Material.CHAINMAIL_BOOTS)
                    .set(DataComponents.TOOLTIP_DISPLAY, new TooltipDisplay(false, Set.of(DataComponents.EQUIPPABLE, DataComponents.UNBREAKABLE)))
                    .set(DataComponents.ITEM_NAME, Msg.green("0.5x flight speed"))
                    .build(), action -> {
                action.getEvent().setCancelled(true);
                action.getPlayer().setFlyingSpeed(0.05f);
                action.getPlayer().sendMessage(Msg.success("Your flight speed is now <gold>0.5x"));
            });

    private static final PrebuiltItem normalSpeed = PrebuiltItem.of(
            ItemStack.builder(Material.IRON_BOOTS)
                    .set(DataComponents.TOOLTIP_DISPLAY, new TooltipDisplay(false, Set.of(DataComponents.EQUIPPABLE, DataComponents.UNBREAKABLE)))
                    .set(DataComponents.ITEM_NAME, Msg.green("1x flight speed"))
                    .build(), action -> {
                action.getEvent().setCancelled(true);
                action.getPlayer().setFlyingSpeed(0.1f);
                action.getPlayer().sendMessage(Msg.success("Your flight speed is now <gold>1x"));
            });

    private static final PrebuiltItem x2Speed = PrebuiltItem.of(
            ItemStack.builder(Material.GOLDEN_BOOTS)
                    .set(DataComponents.TOOLTIP_DISPLAY, new TooltipDisplay(false, Set.of(DataComponents.EQUIPPABLE, DataComponents.UNBREAKABLE)))
                    .set(DataComponents.ITEM_NAME, Msg.green("2x flight speed"))
                    .build(), action -> {
                action.getEvent().setCancelled(true);
                action.getPlayer().setFlyingSpeed(0.2f);
                action.getPlayer().sendMessage(Msg.success("Your flight speed is now <gold>2x"));
            });

    private static final PrebuiltItem x5Speed = PrebuiltItem.of(
            ItemStack.builder(Material.DIAMOND_BOOTS)
                    .set(DataComponents.TOOLTIP_DISPLAY, new TooltipDisplay(false, Set.of(DataComponents.EQUIPPABLE, DataComponents.UNBREAKABLE)))
                    .set(DataComponents.ITEM_NAME, Msg.green("5x flight speed"))
                    .build(), action -> {
                action.getEvent().setCancelled(true);
                action.getPlayer().setFlyingSpeed(0.5f);
                action.getPlayer().sendMessage(Msg.success("Your flight speed is now <gold>5x"));
            });

    public SpectatorSpeedMenu() {
        super(Cytosis.VIEW_REGISTRY, ViewBuilder.of(ViewType.SIZE_3_X_9).title(Msg.mm("Flight Speed")));
    }

    @Override
    public void onOpen(@NotNull PlayerView view, @NotNull Player player) {
        ViewPattern pattern = Cytosis.VIEW_REGISTRY.pattern(
                "#########",
                "##THN25##",
                "#########"
        );
        for (int slot : pattern.getSlots('#')) {
            ViewItem.bySlot(view, slot).applyPrebuilt(MenuUtils.filler);
        }
        ViewItem.bySlot(view, pattern.getSlot('T')).applyPrebuilt(tenthSpeed);
        ViewItem.bySlot(view, pattern.getSlot('H')).applyPrebuilt(halfSpeed);
        ViewItem.bySlot(view, pattern.getSlot('N')).applyPrebuilt(normalSpeed);
        ViewItem.bySlot(view, pattern.getSlot('2')).applyPrebuilt(x2Speed);
        ViewItem.bySlot(view, pattern.getSlot('5')).applyPrebuilt(x5Speed);
    }
}

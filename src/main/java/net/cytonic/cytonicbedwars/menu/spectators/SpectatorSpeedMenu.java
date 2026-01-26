package net.cytonic.cytonicbedwars.menu.spectators;

import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import me.devnatan.inventoryframework.context.RenderContext;
import net.minestom.server.component.DataComponents;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import org.jetbrains.annotations.NotNull;

import net.cytonic.cytonicbedwars.menu.MenuUtils;
import net.cytonic.cytosis.utils.Msg;

public class SpectatorSpeedMenu extends View {

    @Override
    public void onInit(@NotNull ViewConfigBuilder config) {
        config.title(Msg.mm("Flight Speed"));
        config.layout(
            "#########",
            "##THN25##",
            "#########"
        );
    }

    @Override
    public void onFirstRender(@NotNull RenderContext context) {
        context.layoutSlot('#', MenuUtils.filler);
        createSpeedItem(context, 'T', 0.01f, Material.LEATHER_BOOTS);
        createSpeedItem(context, 'H', 0.05f, Material.CHAINMAIL_BOOTS);
        createSpeedItem(context, 'N', 0.1f, Material.IRON_BOOTS);
        createSpeedItem(context, '2', 0.2f, Material.GOLDEN_BOOTS);
        createSpeedItem(context, '5', 0.5f, Material.DIAMOND_BOOTS);
    }

    private void createSpeedItem(RenderContext context, char slot, float speed, Material material) {
        context.layoutSlot(slot).onRender(slotRenderContext -> {
            ItemStack.Builder builder = ItemStack.builder(material);
            builder.hideExtraTooltip();
            builder.set(DataComponents.ITEM_NAME, Msg.green("%sx flight speed", speed));
            slotRenderContext.setItem(builder.build());
        }).onClick(slotClickContext -> {
            slotClickContext.getPlayer().setFlyingSpeed(speed);
            slotClickContext.getPlayer().sendMessage(Msg.success("Your flight speed is now <gold>%sx", speed));
        });
    }
}

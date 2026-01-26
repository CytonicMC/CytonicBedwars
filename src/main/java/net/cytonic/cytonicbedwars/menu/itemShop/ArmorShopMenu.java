package net.cytonic.cytonicbedwars.menu.itemShop;

import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import me.devnatan.inventoryframework.context.RenderContext;
import net.cytonic.cytonicbedwars.data.enums.ArmorLevel;
import net.cytonic.cytonicbedwars.data.enums.PriceType;
import net.cytonic.cytonicbedwars.menu.MenuUtils;
import net.cytonic.cytonicbedwars.utils.Items;
import net.cytonic.cytosis.utils.Msg;
import net.minestom.server.entity.EquipmentSlot;
import net.minestom.server.item.Material;
import org.jetbrains.annotations.NotNull;

public class ArmorShopMenu extends View {
    @Override
    public void onInit(@NotNull ViewConfigBuilder config) {
        config.layout(
                "#bcatpur#",
                "###s#####",
                "####C####",
                "###IDN###",
                "#########"
        );
        config.title(Msg.mm("Item Shop ➜ Armor"));
        config.cancelInteractions();
        config.size(5);
    }

    @Override
    public void onFirstRender(@NotNull RenderContext context) {
        MenuUtils.setItemShopItems(context);

        MenuUtils.setItem(context, 'C', Material.CHAINMAIL_BOOTS, "Permanent Chain Armor", PriceType.IRON, 40, player -> {
            player.getInventory().setEquipment(EquipmentSlot.LEGGINGS, player.getHeldSlot(), Items.CHAINMAIL_LEGS);
            player.getInventory().setEquipment(EquipmentSlot.BOOTS, player.getHeldSlot(), Items.CHAINMAIL_BOOTS);
            player.setArmorLevel(ArmorLevel.CHAINMAIL);
        });

        MenuUtils.setItem(context, 'I', Material.IRON_BOOTS, "Permanent Iron Armor", PriceType.GOLD, 12, player -> {
            player.getInventory().setEquipment(EquipmentSlot.LEGGINGS, player.getHeldSlot(), Items.IRON_LEGS);
            player.getInventory().setEquipment(EquipmentSlot.BOOTS, player.getHeldSlot(), Items.IRON_BOOTS);
            player.setArmorLevel(ArmorLevel.IRON);
        });

        MenuUtils.setItem(context, 'D', Material.DIAMOND_BOOTS, "Permanent Diamond Armor", PriceType.EMERALD, 6, player -> {
            player.getInventory().setEquipment(EquipmentSlot.LEGGINGS, player.getHeldSlot(), Items.DIAMOND_LEGS);
            player.getInventory().setEquipment(EquipmentSlot.BOOTS, player.getHeldSlot(), Items.DIAMOND_BOOTS);
            player.setArmorLevel(ArmorLevel.DIAMOND);
        });

        MenuUtils.setItem(context, 'N', Material.NETHERITE_BOOTS, "Permanent Netherite Armor", PriceType.EMERALD, 16, player -> {
            player.getInventory().setEquipment(EquipmentSlot.LEGGINGS, player.getHeldSlot(), Items.NETHERITE_LEGS);
            player.getInventory().setEquipment(EquipmentSlot.BOOTS, player.getHeldSlot(), Items.NETHERITE_BOOTS);
            player.setArmorLevel(ArmorLevel.NETHERITE);
        });
    }
}

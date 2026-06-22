package net.cytonic.cytonicbedwars.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import org.jetbrains.annotations.Nullable;

import net.cytonic.cytonicbedwars.utils.Items;
import net.cytonic.cytosis.utils.Msg;

@AllArgsConstructor
@Getter
public enum GeneratorType {
    IRON(ItemStack.of(Material.IRON_INGOT), null, null),
    GOLD(Items.get("GOLD"), null, null),
    DIAMOND(Items.get("DIAMOND"), Msg.aqua("<bold>Diamond Generator"), ItemStack.of(Material.DIAMOND_BLOCK)),
    EMERALD(Items.get("EMERALD"), Msg.green("<bold>Emerald Generator"), ItemStack.of(Material.EMERALD_BLOCK));
    private final ItemStack item;
    @Nullable
    private final Component name;
    @Nullable
    private final ItemStack visualItem;
}

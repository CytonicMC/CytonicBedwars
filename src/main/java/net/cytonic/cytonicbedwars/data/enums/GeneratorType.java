package net.cytonic.cytonicbedwars.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.cytonic.cytonicbedwars.utils.Items;
import net.cytonic.cytosis.utils.Msg;
import net.kyori.adventure.text.Component;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.ItemStack;

@AllArgsConstructor
@Getter
public enum GeneratorType {
    IRON(Items.get("IRON"), null, null),
    GOLD(Items.get("GOLD"), null, null),
    DIAMOND(Items.get("DIAMOND"), Msg.mm("<aqua><bold>Diamond Generator"), Block.DIAMOND_BLOCK),
    EMERALD(Items.get("EMERALD"), Msg.mm("<green><bold>Emerald Generator"), Block.EMERALD_BLOCK);
    private final ItemStack item;
    private final Component name;
    private final Block visualBlock;
}

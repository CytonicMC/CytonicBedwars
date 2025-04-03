package net.cytonic.cytonicbedwars.data.objects;

import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.block.Block;

public record Team(String displayName,
                   String prefix,
                   NamedTextColor color,
                   Block bedType,
                   Pos spawnLocation,
                   Pos generatorLocation,
                   Pos itemShopLocation,
                   Pos teamShopLocation,
                   Pos chestLocation,
                   Pos bedLocation,
                   Block woolType,
                   Block glassType,
                   Block terracottaType) {
}

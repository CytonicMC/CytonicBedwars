package net.cytonic.cytonicbedwars.config;


import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.minestom.server.codec.Codec;
import net.minestom.server.instance.block.Block;

import net.cytonic.cytosis.utils.Msg;

@Getter
@AllArgsConstructor
public enum TeamColor {
    RED("<red>[R]</red>", "<red>[RED]</red>", Block.RED_BED, Block.RED_WOOL, Block.RED_STAINED_GLASS,
        Block.RED_TERRACOTTA),
    GREEN("<green>[G]</green>", "<green>[GREEN]</green>", Block.GREEN_BED, Block.GREEN_WOOL,
        Block.GREEN_STAINED_GLASS, Block.GREEN_TERRACOTTA),
    BLUE("<blue>[B]</blue>", "<blue>[BLUE]</blue>", Block.BLUE_BED, Block.BLUE_WOOL, Block.BLUE_STAINED_GLASS,
        Block.BLUE_TERRACOTTA),
    YELLOW("<yellow>[Y]</yellow>", "<yellow>[YELLOW]</yellow>", Block.YELLOW_BED, Block.YELLOW_WOOL,
        Block.YELLOW_STAINED_GLASS,
        Block.YELLOW_TERRACOTTA);
    public static final Codec<TeamColor> CODEC = Codec.Enum(TeamColor.class);
    private final Component prefix;
    private final Component name;
    private final Block bedBlock;
    private final Block woolBlock;
    private final Block glassBlock;
    private final Block terracottaType;

    TeamColor(String prefix, String name, Block bedBlock, Block woolBlock, Block glassBlock, Block terracottaType) {
        this.prefix = Msg.mm(prefix);
        this.name = Msg.mm(name);
        this.bedBlock = bedBlock;
        this.woolBlock = woolBlock;
        this.glassBlock = glassBlock;
        this.terracottaType = terracottaType;
    }
}

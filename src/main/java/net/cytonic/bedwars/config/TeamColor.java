package net.cytonic.bedwars.config;


import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.minestom.server.codec.Codec;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.Material;

import net.cytonic.cytosis.utils.Msg;

@Getter
@AllArgsConstructor
public enum TeamColor {
    RED("<red>[R]</red>", "<red>[RED]</red>", Block.RED_BED, Material.RED_WOOL, Material.RED_STAINED_GLASS,
        Material.RED_TERRACOTTA),
    GREEN("<green>[G]</green>", "<green>[GREEN]</green>", Block.LIME_BED, Material.LIME_WOOL,
        Material.LIME_STAINED_GLASS, Material.LIME_TERRACOTTA),
    BLUE("<blue>[B]</blue>", "<blue>[BLUE]</blue>", Block.BLUE_BED, Material.BLUE_WOOL, Material.BLUE_STAINED_GLASS,
        Material.BLUE_TERRACOTTA),
    YELLOW("<yellow>[Y]</yellow>", "<yellow>[YELLOW]</yellow>", Block.YELLOW_BED, Material.YELLOW_WOOL,
        Material.YELLOW_STAINED_GLASS, Material.YELLOW_TERRACOTTA);
    public static final Codec<TeamColor> CODEC = Codec.Enum(TeamColor.class);
    private final Component prefix;
    private final Component name;
    private final Block bedBlock;
    private final Material woolBlock;
    private final Material glassBlock;
    private final Material terracottaType;

    TeamColor(String prefix, String name, Block bedBlock, Material woolBlock, Material glassBlock,
        Material terracottaType) {
        this.prefix = Msg.mm(prefix);
        this.name = Msg.mm(name);
        this.bedBlock = bedBlock;
        this.woolBlock = woolBlock;
        this.glassBlock = glassBlock;
        this.terracottaType = terracottaType;
    }
}

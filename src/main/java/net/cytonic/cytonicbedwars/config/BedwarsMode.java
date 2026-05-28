package net.cytonic.cytonicbedwars.config;

import java.util.Arrays;
import java.util.List;

import net.minestom.server.codec.Codec;

public enum BedwarsMode {
    NORMAL;
    public static final Codec<BedwarsMode> CODEC = Codec.Enum(BedwarsMode.class);

    public List<BedwarsMap> getMaps() {
        return Arrays.stream(BedwarsMap.values())
            .filter(it -> it.getSupportedModes().contains(this)).toList();
    }
}

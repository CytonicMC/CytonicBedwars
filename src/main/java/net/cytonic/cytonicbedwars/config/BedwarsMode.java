package net.cytonic.cytonicbedwars.config;

import java.util.Arrays;
import java.util.List;

import net.minestom.server.codec.Codec;

import net.cytonic.cytosis.utils.Utils;

public enum BedwarsMode {
    NORMAL;
    public static final Codec<BedwarsMode> CODEC = Codec.Enum(BedwarsMode.class);

    public String getHumanName() {
        return Utils.captializeFirstLetters(name().toLowerCase().replace("_", " "));
    }

    public List<BedwarsMap> getMaps() {
        return Arrays.stream(BedwarsMap.values())
            .filter(it -> it.getSupportedModes().contains(this)).toList();
    }
}

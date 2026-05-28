package net.cytonic.cytonicbedwars.config;

import net.minestom.server.codec.Codec;

public enum TeamSize {
    SOLOS,
    DOUBLES;
    public static final Codec<TeamSize> CODEC = Codec.Enum(TeamSize.class);
}

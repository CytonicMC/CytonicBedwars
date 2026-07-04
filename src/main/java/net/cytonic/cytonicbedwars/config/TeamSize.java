package net.cytonic.cytonicbedwars.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minestom.server.codec.Codec;

@Getter
@AllArgsConstructor
public enum TeamSize {
    SOLOS(1),
    DOUBLES(2);
    public static final Codec<TeamSize> CODEC = Codec.Enum(TeamSize.class);
    private final int playersPerTeam;
}

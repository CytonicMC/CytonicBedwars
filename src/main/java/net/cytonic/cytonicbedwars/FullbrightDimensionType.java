package net.cytonic.cytonicbedwars;

import net.minestom.server.MinecraftServer;
import net.minestom.server.color.Color;
import net.minestom.server.registry.RegistryKey;
import net.minestom.server.world.DimensionType;
import net.minestom.server.world.attribute.EnvironmentAttribute;

public class FullbrightDimensionType {

    public static final RegistryKey<DimensionType> INSTANCE;

    static {
        INSTANCE = MinecraftServer.getDimensionTypeRegistry()
            .register("cytonic:bedwars",
                DimensionType.builder()
                    .setAttribute(EnvironmentAttribute.AMBIENT_LIGHT_COLOR, new Color(255, 255, 255))
                    .build()
            );
    }

    public static void init() {
    }
}

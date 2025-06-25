package net.cytonic.cytonicbedwars;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.data.enums.GeneratorType;
import net.cytonic.cytonicbedwars.data.objects.Team;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.logging.Logger;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.block.Block;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This class is used to store all the cached configuration values.
 */
@NoArgsConstructor
public final class Config {

    public static String worldName = "NULL";
    public static String mapName = "NULL";
    public static String mode = "NULL";
    public static int minPlayers = 0;
    public static int maxPlayers = 0;
    public static int bridgeEggBlockDespawn = 0;
    public static int bridgeEggBlockLimit = 0;
    public static Pos spawnPlatformCenter = new Pos(0, 0, 0, 180, 0);
    public static Map<String, Team> teams = new HashMap<>();
    public static Map<GeneratorType, Integer> generatorsWaitTimeTicks = new HashMap<>();
    public static Map<GeneratorType, Integer> generatorsItemLimit = new HashMap<>();
    public static Map<GeneratorType, List<Pos>> generators = new HashMap<>();

    /**
     * Loads the config from a string
     *
     * @param data the data
     */
    public static void importConfig(String data) {
        long time = System.currentTimeMillis();
        Logger.info("Importing bedwars config!");
        ConfigurationNode node = null;
        try {
            node = Cytosis.GSON_CONFIGURATION_LOADER.buildAndLoadString(data);
        } catch (ConfigurateException e) {
            Logger.error("Could not import config!", e);
        }
        if (node == null) {
            Logger.error("Configuration node is null!");
            return;
        }
        worldName = node.node("world_name").getString();
        mapName = node.node("map_name").getString();
        mode = node.node("mode").getString();
        minPlayers = node.node("min_players").getInt();
        maxPlayers = node.node("max_players").getInt();
        bridgeEggBlockDespawn = node.node("bridge_egg_block_despawn").getInt();
        bridgeEggBlockLimit = node.node("bridge_egg_block_limit").getInt();
        try {
            spawnPlatformCenter = node.node("spawn_platform_center").get(Pos.class);
        } catch (SerializationException e) {
            Logger.error("Could not import spawn platform center!", e);
        }
        node.node("teams").childrenMap().forEach((key, teamNode) -> {
            try {
                Team team = new Team(
                        String.valueOf(key),
                        teamNode.node("tab_prefix").getString(),
                        NamedTextColor.NAMES.value(Objects.requireNonNull(teamNode.node("team_color").getString())),
                        Objects.requireNonNull(teamNode.node("spawn_location").get(Pos.class)),
                        teamNode.node("generation_location").get(Pos.class),
                        teamNode.node("item_shop_location").get(Pos.class),
                        teamNode.node("team_shop_location").get(Pos.class),
                        teamNode.node("team_chest_location").get(Pos.class),
                        teamNode.node("bed_location").get(Pos.class),
                        Block.fromKey(Objects.requireNonNull(teamNode.node("bed_item").get(Key.class))),
                        Block.fromKey(Objects.requireNonNull(teamNode.node("wool_item").get(Key.class))),
                        Block.fromKey(Objects.requireNonNull(teamNode.node("glass_item").get(Key.class))),
                        Block.fromKey(Objects.requireNonNull(teamNode.node("terracotta_item").get(Key.class)))
                );
                teams.put(String.valueOf(key), team);
            } catch (SerializationException e) {
                Logger.error("Could not import team!", e);
            }
        });
        node.node("generators_wait_time_ticks").childrenMap().forEach((key, value) -> generatorsWaitTimeTicks.put(GeneratorType.valueOf(String.valueOf(key).toUpperCase()), value.getInt()));
        node.node("generators_item_limit").childrenMap().forEach((key, value) -> generatorsItemLimit.put(GeneratorType.valueOf(String.valueOf(key).toUpperCase()), value.getInt()));
        node.node("generators").childrenMap().forEach((key, value) -> {
            try {
                generators.put(GeneratorType.valueOf(String.valueOf(key).toUpperCase()), value.getList(Pos.class));
            } catch (SerializationException e) {
                Logger.error("Could not import generators!", e);
            }
        });
        Logger.info("Finished importing bedwars config in %sms!", (System.currentTimeMillis() - time));
    }
}

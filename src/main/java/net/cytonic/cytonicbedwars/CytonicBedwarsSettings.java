package net.cytonic.cytonicbedwars;

import com.google.gson.JsonObject;
import net.cytonic.cytonicbedwars.data.objects.Team;
import net.cytonic.cytonicbedwars.utils.Utils;
import net.cytonic.cytosis.logging.Logger;
import net.cytonic.cytosis.utils.PosSerializer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.block.Block;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to store all the cached configuration values.
 */
public final class CytonicBedwarsSettings {

    public static String worldName = "";
    public static String mapName = "";
    public static String mode = "";
    public static int minPlayers;
    public static int maxPlayers = 0;
    public static int bridgeEggBlockDespawn = 0;
    public static int bridgeEggBlockLimit = 0;
    public static Pos spawnPlatformCenter = new Pos(0, 0, 0, 180, 0);
    public static Map<String, Team> teams = new HashMap<>();

    /**
     * Defualt constructor
     */
    private CytonicBedwarsSettings() {
    }

    /**
     * Loads the config from a config map
     *
     * @param jsonObject The config json object
     */
    public static void importConfig(JsonObject jsonObject) {
        Logger.info("Importing bedwars config!");
        jsonObject.asMap().forEach((key, value) -> {
            try {
                switch (key) {
                    case "world_name" -> worldName = value.getAsString();
                    case "map_name" -> mapName = value.getAsString();
                    case "mode" -> mode = value.getAsString();
                    case "min_players" -> minPlayers = value.getAsInt();
                    case "max_players" -> maxPlayers = value.getAsInt();
                    case "bridge_egg_block_despawn" -> bridgeEggBlockDespawn = value.getAsInt();
                    case "bridge_egg_block_limit" -> bridgeEggBlockLimit = value.getAsInt();
                    case "spawn_platform_center" ->
                            spawnPlatformCenter = PosSerializer.deserialize(value.getAsString());
                    case "teams" -> {
                        JsonObject teams = value.getAsJsonObject();
                        teams.asMap().forEach((key1, value1) -> {
                            JsonObject obj1 = value1.getAsJsonObject();
                            Logger.debug(STR."key = \{key1}");
                            Team t = new Team(
                                    key1,
                                    obj1.get("tab_prefix").getAsString(),
                                    Utils.getColor(obj1.get("team_color").getAsString()),
                                    Block.fromNamespaceId(obj1.get("bed_item").getAsString()),
                                    PosSerializer.deserialize(obj1.get("spawn_location").getAsString()),
                                    PosSerializer.deserialize(obj1.get("generation_location").getAsString()),
                                    PosSerializer.deserialize(obj1.get("item_shop_location").getAsString()),
                                    PosSerializer.deserialize(obj1.get("team_shop_location").getAsString()),
                                    PosSerializer.deserialize(obj1.get("team_chest_location").getAsString()),
                                    Block.fromNamespaceId(obj1.get("wool_item").getAsString()),
                                    Block.fromNamespaceId(obj1.get("glass_item").getAsString()),
                                    Block.fromNamespaceId(obj1.get("terracotta_item").getAsString())
                            );
                            CytonicBedwarsSettings.teams.put(key1, t);
                            Logger.debug("After team put");
                        });
                    }
                    default -> { /*Do nothing*/ }
                }
            } catch (ClassCastException e) {
                Logger.error(STR."Could not import config key: \{key}", e);
            }
        });
        Logger.info("Bedwars config imported!");
    }
}

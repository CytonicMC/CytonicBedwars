package net.cytonic.cytonicbedwars.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.google.gson.JsonParser;
import lombok.Getter;
import net.hollowcube.polar.PolarWorld;
import net.kyori.adventure.key.Key;
import net.minestom.server.codec.Transcoder;

import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.managers.WorldManager;

@Getter
public enum BedwarsMap {
    FARM("farm.json", "farm", TeamSize.SOLOS, BedwarsMode.NORMAL),
    LUSH_RUSH("farm.json", "lush-rush", TeamSize.DOUBLES, BedwarsMode.NORMAL);
    private final String file;
    private final String worldName;
    private final PolarWorld world;
    private final TeamSize supportedTeamSize;
    private final List<BedwarsMode> supportedModes;
    private final BedwarsMapConfig config;

    BedwarsMap(String file, String worldName, TeamSize supportedTeamSize, BedwarsMode... supportedModes) {
        this.file = file;
        this.worldName = worldName;
        this.world = readWorld();
        this.supportedTeamSize = supportedTeamSize;
        this.supportedModes = List.of(supportedModes);
        this.config = readConfig();
    }

    private PolarWorld readWorld() {
        try {
            return Cytosis.get(WorldManager.class).loadWorld(Key.key("bedwars", worldName)).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private BedwarsMapConfig readConfig() {
        try (InputStream stream = this.getClass().getResourceAsStream("/maps/" + file)) {
            if (stream == null) throw new IllegalStateException("Map config file not found for " + file);

            return BedwarsMapConfig.CODEC.decode(Transcoder.JSON,
                    JsonParser.parseReader(new InputStreamReader(stream)))
                .orElseThrow("Failed to decode map config");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

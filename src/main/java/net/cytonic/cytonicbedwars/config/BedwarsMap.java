package net.cytonic.cytonicbedwars.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.google.gson.JsonParser;
import lombok.Getter;
import net.hollowcube.polar.PolarReader;
import net.hollowcube.polar.PolarWorld;
import net.minestom.server.codec.Codec;
import net.minestom.server.codec.Transcoder;

@Getter
public enum BedwarsMap {
    FARM("farm.json", "farm.polar", TeamSize.SOLOS, BedwarsMode.NORMAL),
    LUSH_RUSH("farm.json", "farm.polar", TeamSize.DOUBLES, BedwarsMode.NORMAL);
    private final String file;
    private final String worldFile;
    private final PolarWorld world;
    private final TeamSize supportedTeamSize;
    private final List<BedwarsMode> supportedModes;
    private final BedwarsMapConfig config;
    public static final Codec<BedwarsMap> CODEC = Codec.Enum(BedwarsMap.class);

    BedwarsMap(String file, String worldFile, TeamSize supportedTeamSize, BedwarsMode... supportedModes) {
        this.file = file;
        this.worldFile = worldFile;
        this.world = readWorld();
        this.supportedTeamSize = supportedTeamSize;
        this.supportedModes = List.of(supportedModes);
        this.config = readConfig();
    }

    private PolarWorld readWorld() {
        try (InputStream stream = this.getClass().getResourceAsStream("/worlds/" + worldFile)) {
            if (stream == null) throw new IllegalStateException("World file not found for " + worldFile);
            return PolarReader.read(stream.readAllBytes());
        } catch (IOException e) {
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

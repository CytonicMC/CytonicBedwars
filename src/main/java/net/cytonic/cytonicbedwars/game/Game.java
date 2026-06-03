package net.cytonic.cytonicbedwars.game;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import net.cytonic.cytonicbedwars.config.BedwarsMap;
import net.cytonic.cytonicbedwars.config.BedwarsMapConfig.TeamConfig;
import net.cytonic.cytonicbedwars.config.BedwarsMode;
import net.cytonic.cytonicbedwars.config.TeamColor;
import net.cytonic.cytonicbedwars.data.objects.Team;

public class Game {

    @Getter
    private final UUID id = UUID.randomUUID();
    @Getter
    private final BedwarsMap map;
    private final BedwarsMode mode;
    @Getter
    private final GameWorld world;
    private final Map<TeamColor, Team> teams = new HashMap<>();
    @Getter
    @Setter
    private boolean started;

    public Game(BedwarsMap map, BedwarsMode mode) {
        this.map = map;
        this.mode = mode;
        this.world = new GameWorld(id, map);
        for (TeamConfig config : map.getConfig().teams()) {
            teams.put(config.color(), new Team(
                config.color(),
                config.spawnPos(),
                config.itemShopPos(),
                config.teamShopPos(),
                config.teamChestPos(),
                config.generatorPos()
            ));
        }
    }

    @Nullable
    public Team getTeam(TeamColor teamColor) {
        return teams.get(teamColor);
    }
}

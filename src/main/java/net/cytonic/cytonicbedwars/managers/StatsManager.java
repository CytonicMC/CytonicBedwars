package net.cytonic.cytonicbedwars.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lombok.Getter;

import net.cytonic.cytonicbedwars.data.objects.Stats;
import net.cytonic.cytosis.bootstrap.annotations.CytosisComponent;

@Getter
@CytosisComponent
public class StatsManager {

    private final Map<UUID, Stats> stats = new HashMap<>();

    public void addPlayer(UUID uuid) {
        stats.put(uuid, new Stats(0, 0, 0, 0, 0, 0));
    }

    public Stats getStats(UUID uuid) {
        return stats.get(uuid);
    }
}

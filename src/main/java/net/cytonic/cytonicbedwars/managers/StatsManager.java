package net.cytonic.cytonicbedwars.managers;

import lombok.Getter;
import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.data.objects.Stats;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class StatsManager {
    private final Map<UUID, Stats> stats = new HashMap<>();

    public void addPlayer(UUID uuid) {
        stats.put(uuid, new Stats(0, 0, 0, 0, 0, 0));
    }

    public Stats getStats(UUID uuid) {
        return stats.get(uuid);
    }
}

package net.cytonic.cytonicbedwars.listeners;

import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytonicbedwars.CytonicBedwarsSettings;
import net.cytonic.cytosis.Cytosis;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;

public class JoinListener {

    public JoinListener() {
    }

    public void onJoin(AsyncPlayerConfigurationEvent event) {
        Player player = event.getPlayer();
        event.setSpawningInstance(Cytosis.getDefaultInstance());
        Pos pos = CytonicBedwarsSettings.spawnPlatformCenter;
        pos = pos.withY(pos.y() + 1);
        player.setRespawnPoint(pos);
        if (CytonicBedWars.getGameManager().STARTED) {
            CytonicBedWars.getGameManager().getScoreboardManager().addScoreboard(event.getPlayer());
            if (CytonicBedWars.getGameManager().getPlayerTeam(event.getPlayer().getUuid()) == null) {
                event.getPlayer().setGameMode(GameMode.SPECTATOR);
            }
        }
    }

    public void onJoin(PlayerSpawnEvent event) {
        if (CytonicBedWars.getGameManager().STARTED) {
            CytonicBedWars.getGameManager().getScoreboardManager().addScoreboard(event.getPlayer());
            if (CytonicBedWars.getGameManager().getPlayerTeam(event.getPlayer().getUuid()) == null) {
                event.getPlayer().setGameMode(GameMode.SPECTATOR);
            }
        }
    }
}

package net.cytonic.cytonicbedwars.listeners;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytonicbedwars.CytonicBedwarsSettings;
import net.cytonic.cytonicbedwars.data.enums.GameState;
import net.cytonic.cytonicbedwars.runnables.WaitingRunnable;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.player.CytosisPlayer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;

@NoArgsConstructor
public class JoinListener {

    public void onJoin(AsyncPlayerConfigurationEvent event) {
        CytosisPlayer player = (CytosisPlayer) event.getPlayer();
        event.setSpawningInstance(Cytosis.getDefaultInstance());
        Pos pos = CytonicBedwarsSettings.spawnPlatformCenter;
        pos = pos.withY(pos.y() + 1);
        player.setRespawnPoint(pos);
    }

    public void onJoin(PlayerSpawnEvent event) {
        if (!CytonicBedWars.getGameManager().STARTED) {
            if (Cytosis.getOnlinePlayers().size() >= 2) {
                CytonicBedWars.getGameManager().setGameState(GameState.STARTING);
                CytonicBedWars.getGameManager().setWaitingRunnable(new WaitingRunnable());
            }
        }
        if (CytonicBedWars.getGameManager().STARTED) {
            if (CytonicBedWars.getGameManager().getPlayerTeam(event.getPlayer().getUuid()).isEmpty()) {
                event.getPlayer().setGameMode(GameMode.SPECTATOR);
            }
        }
    }
}

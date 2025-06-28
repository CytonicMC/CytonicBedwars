package net.cytonic.cytonicbedwars.listeners;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.Config;
import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytonicbedwars.data.enums.GameState;
import net.cytonic.cytonicbedwars.runnables.WaitingRunnable;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.events.api.Listener;
import net.minestom.server.entity.GameMode;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;

@NoArgsConstructor
@SuppressWarnings("unused")
public class JoinListener {

    @Listener
    public void onJoin(AsyncPlayerConfigurationEvent event) {
        event.setSpawningInstance(Cytosis.getDefaultInstance());
        event.getPlayer().setRespawnPoint(Config.spawnPlatformCenter.add(0, 1, 0));
    }

    @Listener
    public void onJoin(PlayerSpawnEvent event) {
        if (!CytonicBedWars.getGameManager().STARTED) {
            if (CytonicBedWars.getGameManager().getWaitingRunnable() == null) {
                if (Cytosis.getOnlinePlayers().size() >= Config.minPlayers) {
                    CytonicBedWars.getGameManager().setGameState(GameState.STARTING);
                    CytonicBedWars.getGameManager().setWaitingRunnable(new WaitingRunnable());
                }
            }
        }
        if (CytonicBedWars.getGameManager().STARTED) {
            if (CytonicBedWars.getGameManager().getPlayerTeam(event.getPlayer().getUuid()).isEmpty()) {
                event.getPlayer().setGameMode(GameMode.SPECTATOR);
            }
        }
    }
}

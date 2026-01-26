package net.cytonic.cytonicbedwars.listeners;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.Config;
import net.cytonic.cytonicbedwars.data.enums.GameState;
import net.cytonic.cytonicbedwars.managers.GameManager;
import net.cytonic.cytonicbedwars.runnables.WaitingRunnable;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.events.api.Listener;
import net.minestom.server.entity.GameMode;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.instance.InstanceContainer;

@NoArgsConstructor
@SuppressWarnings("unused")
public class JoinListener {

    @Listener
    public void onJoin(AsyncPlayerConfigurationEvent event) {
        event.setSpawningInstance(Cytosis.CONTEXT.getComponent(InstanceContainer.class));
        event.getPlayer().setRespawnPoint(Config.spawnPlatformCenter.add(0, 1, 0));
    }

    @Listener
    public void onJoin(PlayerSpawnEvent event) {
        if (!Cytosis.CONTEXT.getComponent(GameManager.class).STARTED) {
            if (Cytosis.CONTEXT.getComponent(GameManager.class).getWaitingRunnable() == null) {
                if (Cytosis.getOnlinePlayers().size() >= Config.minPlayers) {
                    Cytosis.CONTEXT.getComponent(GameManager.class).setGameState(GameState.STARTING);
                    Cytosis.CONTEXT.getComponent(GameManager.class).setWaitingRunnable(new WaitingRunnable());
                }
            }
        }
        if (Cytosis.CONTEXT.getComponent(GameManager.class).STARTED) {
            if (Cytosis.CONTEXT.getComponent(GameManager.class).getPlayerTeam(event.getPlayer().getUuid()).isEmpty()) {
                event.getPlayer().setGameMode(GameMode.SPECTATOR);
            }
        }
    }
}

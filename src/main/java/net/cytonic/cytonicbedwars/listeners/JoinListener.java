package net.cytonic.cytonicbedwars.listeners;

import lombok.NoArgsConstructor;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;

@NoArgsConstructor
@SuppressWarnings("unused")
public class JoinListener {

    //todo @Listener
    public void onJoin(AsyncPlayerConfigurationEvent event) {
//        event.setSpawningInstance(Cytosis.CONTEXT.getComponent(InstanceContainer.class));
//        event.getPlayer().setRespawnPoint(BedwarsConfig.spawnPlatformCenter.add(0, 1, 0));
    }

    //todo @Listener
    public void onJoin(PlayerSpawnEvent event) {
//        if (!Cytosis.CONTEXT.getComponent(GameManager.class).STARTED) {
//            if (Cytosis.CONTEXT.getComponent(GameManager.class).getWaitingRunnable() == null) {
//                if (Cytosis.getOnlinePlayers().size() >= BedwarsConfig.minPlayers) {
//                    Cytosis.CONTEXT.getComponent(GameManager.class).setGameState(GameState.STARTING);
//                    Cytosis.CONTEXT.getComponent(GameManager.class).setWaitingRunnable(new WaitingRunnable());
//                }
//            }
//        }
//        if (Cytosis.CONTEXT.getComponent(GameManager.class).STARTED) {
//            if (Cytosis.CONTEXT.getComponent(GameManager.class).getPlayerTeam(event.getPlayer().getUuid()).isEmpty()) {
//                event.getPlayer().setGameMode(GameMode.SPECTATOR);
//            }
//        }
    }
}

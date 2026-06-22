package net.cytonic.cytonicbedwars.listeners;

import lombok.NoArgsConstructor;

import net.cytonic.cytonicbedwars.managers.GameManager;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.events.api.Listener;

import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerDisconnectEvent;

@NoArgsConstructor
@SuppressWarnings("unused")
public class LeaveListener {

    //todo @Listener
    public void onLeave(PlayerDisconnectEvent event) {
        if (Cytosis.CONTEXT.getComponent(GameManager.class).STARTED) {
            if (Cytosis.CONTEXT.getComponent(GameManager.class).getPlayerTeam(event.getPlayer().getUuid()).isEmpty()) {
                // they are a spectator
                Player player = event.getPlayer();
                player.clearEffects();
                Cytosis.CONTEXT.getComponent(GameManager.class).getSpectators().remove(player.getUuid());
            }
        } else {
//            if (Cytosis.getOnlinePlayers().size() < BedwarsConfig.minPlayers) {
//                if (Cytosis.CONTEXT.getComponent(GameManager.class).getWaitingRunnable() != null) {
//                    Cytosis.CONTEXT.getComponent(GameManager.class).getWaitingRunnable().stop();
//                    Cytosis.CONTEXT.getComponent(GameManager.class).setWaitingRunnable(null);
//                    Cytosis.CONTEXT.getComponent(GameManager.class).setGameState(GameState.WAITING);
//                    Cytosis.getOnlinePlayers().forEach(player -> player.sendMessage(Msg.redSplash("START CANCELLED!", "There are not enough players to start the game!")));
//                }
//            }
        }
    }
}
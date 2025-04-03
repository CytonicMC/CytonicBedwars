package net.cytonic.cytonicbedwars.listeners;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytonicbedwars.CytonicBedwarsSettings;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.utils.Msg;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerDisconnectEvent;

@NoArgsConstructor
public class LeaveListener {

    public void onLeave(PlayerDisconnectEvent event) {
        if (CytonicBedWars.getGameManager().STARTED) {
            if (CytonicBedWars.getGameManager().getPlayerTeam(event.getPlayer().getUuid()).isEmpty()) {
                // they are a spectator
                Player player = event.getPlayer();
                player.clearEffects();
                CytonicBedWars.getGameManager().spectators.remove(player.getUuid());
            }
        } else {
            if (Cytosis.getOnlinePlayers().size() < CytonicBedwarsSettings.minPlayers) {
                if (CytonicBedWars.getGameManager().getWaitingRunnable() != null) {
                    CytonicBedWars.getGameManager().getWaitingRunnable().stop();
                    Cytosis.getOnlinePlayers().forEach(player -> {
                        player.sendMessage(Msg.mm("not enough players"));
                    });
                }
            }
        }
    }
}
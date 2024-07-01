package net.cytonic.cytonicbedwars.listeners;

import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerDisconnectEvent;

public class LeaveListener {

    private final CytonicBedWars plugin;
    public LeaveListener(CytonicBedWars plugin) {
        this.plugin = plugin;
    }

    public void onLeave(PlayerDisconnectEvent event) {
        if (plugin.getGameManager().STARTED) {
            plugin.getGameManager().getScoreboardManager().removeScoreboard(event.getPlayer().getUuid());
            if (plugin.getGameManager().getPlayerTeam(event.getPlayer().getUuid()) == null) {
                // they are a spectator
                Player player = event.getPlayer();
                player.clearEffects();
                plugin.getGameManager().spectators.remove(player.getUuid());
            }
        }
    }
}

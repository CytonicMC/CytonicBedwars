package net.cytonic.cytonicbedwars.listeners;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.cytonic.cytonicbedwars.WebNetBedWars;

public class JoinListener implements Listener {
    private final WebNetBedWars plugin;

    public JoinListener(WebNetBedWars plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event){
        if(plugin.getGameManager().STARTED){
            plugin.getGameManager().getScoreboardManager().addScoreboard(event.getPlayer());
            if(plugin.getGameManager().getPlayerTeam(event.getPlayer().getUniqueId()) == null){
                event.getPlayer().setGameMode(GameMode.SPECTATOR);
            }
        }
    }
}

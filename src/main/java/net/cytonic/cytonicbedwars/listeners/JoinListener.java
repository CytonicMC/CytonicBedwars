package net.cytonic.cytonicbedwars.listeners;

import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytosis.Cytosis;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;

public class JoinListener {

    private final CytonicBedWars plugin;
    public JoinListener(CytonicBedWars plugin) {
        this.plugin = plugin;
    }

    public void onJoin(AsyncPlayerConfigurationEvent event) {
        Player player = event.getPlayer();
        //todo get the values from a config file
        int x = 0;
        int y = 100;
        int z = 0;
        Pos location = new Pos(x, y, z);
        player.sendMessage("we got to this");
        event.setSpawningInstance(Cytosis.getDefaultInstance());
        player.setRespawnPoint(location);
        if (plugin.getGameManager().STARTED) {
            plugin.getGameManager().getScoreboardManager().addScoreboard(event.getPlayer());
            if (plugin.getGameManager().getPlayerTeam(event.getPlayer().getUuid()) == null) {
                event.getPlayer().setGameMode(GameMode.SPECTATOR);
            }
        }
    }

    public void onJoin(PlayerSpawnEvent event) {
        if (plugin.getGameManager().STARTED) {
            plugin.getGameManager().getScoreboardManager().addScoreboard(event.getPlayer());
            if (plugin.getGameManager().getPlayerTeam(event.getPlayer().getUuid()) == null) {
                event.getPlayer().setGameMode(GameMode.SPECTATOR);
            }
        }
    }
}

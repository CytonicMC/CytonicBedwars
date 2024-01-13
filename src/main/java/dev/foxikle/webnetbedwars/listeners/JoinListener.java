package dev.foxikle.webnetbedwars.listeners;

import dev.foxikle.webnetbedwars.WebNetBedWars;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    private final WebNetBedWars plugin;

    public JoinListener(WebNetBedWars plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event){

        Player p = event.getPlayer();
        String worldName = plugin.getConfig().getString("Map");
        World mapSlime = Bukkit.getWorld(worldName);

        int x = Integer.parseInt(plugin.getConfig().getString("x"));
        int y = Integer.parseInt(plugin.getConfig().getString("y"));
        int z = Integer.parseInt(plugin.getConfig().getString("z"));

        Location location = new Location(mapSlime, x, y, z);
        p.sendMessage("we got to this");
        p.teleport(location);

        if(plugin.getGameManager().STARTED){
            plugin.getGameManager().getScoreboardManager().addScoreboard(event.getPlayer());
            if(plugin.getGameManager().getPlayerTeam(event.getPlayer().getUniqueId()) == null){
                event.getPlayer().setGameMode(GameMode.SPECTATOR);
            }
        }
    }
}

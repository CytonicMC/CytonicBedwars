package dev.foxikle.webnetbedwars.listeners;

import dev.foxikle.webnetbedwars.WebNetBedWars;
import dev.foxikle.webnetbedwars.runnables.RespawnRunnable;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
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

        Location location = plugin.getLocation("SpawnPlatformCenter");
        location.add(.5, 2, 0.5);
        p.teleport(location);

        if(plugin.getGameManager().STARTED) {
            if(plugin.getGameManager().getPlayerTeam(p.getUniqueId()) != null) {
                p.sendMessage(ChatColor.YELLOW + "Hey, Welcome back! You'll be respawned in 10 seconds.");
                p.getInventory().clear();
                p.setGameMode(GameMode.SPECTATOR);
                p.setInvulnerable(true);
                p.clearActivePotionEffects();
                new RespawnRunnable(plugin, 10, p).runTaskTimer(plugin, 0, 20);
            }
            plugin.getGameManager().getScoreboardManager().addScoreboard(event.getPlayer());
            if(plugin.getGameManager().getPlayerTeam(event.getPlayer().getUniqueId()) == null){
                event.getPlayer().setGameMode(GameMode.SPECTATOR);
            }
        } else {
            p.getInventory().clear();
            p.setGameMode(GameMode.ADVENTURE);
            p.setInvulnerable(true);
            p.clearActivePotionEffects();
        }
    }
}

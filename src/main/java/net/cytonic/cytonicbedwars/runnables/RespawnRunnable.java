package net.cytonic.cytonicbedwars.runnables;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.cytonic.cytonicbedwars.WebNetBedWars;

public class RespawnRunnable extends BukkitRunnable {
    private final WebNetBedWars plugin;
    private int timeLeft;
    private Player player;

    public RespawnRunnable(WebNetBedWars plugin, int timeLeft, Player player) {
        this.plugin = plugin;
        this.timeLeft = timeLeft;
        this.player = player;
    }


    @Override
    public void run() {
        timeLeft--;
        if(timeLeft <= 0){
            cancel();
            plugin.getGameManager().respawnPlayer(player);
            return;
        }

        switch (timeLeft) {
            case 1, 2, 3 -> {
                player.sendTitle(ChatColor.YELLOW + "Respawning in " + timeLeft, "", 5, 5, 5);
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK,.8f, 1f);
            }
        }
    }
}

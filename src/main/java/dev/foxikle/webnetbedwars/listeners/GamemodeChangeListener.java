package dev.foxikle.webnetbedwars.listeners;

import dev.foxikle.webnetbedwars.WebNetBedWars;
import dev.foxikle.webnetbedwars.utils.Items;
import net.kyori.adventure.util.TriState;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GamemodeChangeListener implements Listener {
    private final WebNetBedWars plugin;

    public GamemodeChangeListener(WebNetBedWars plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onGamemodeChange(PlayerGameModeChangeEvent event){
        Player player = event.getPlayer();
        if(event.getNewGameMode() == GameMode.SPECTATOR) {

            player.getInventory().clear();

            player.getInventory().setBoots(Items.SPECTATOR_ARMOR);
            player.getInventory().setLeggings(Items.SPECTATOR_ARMOR);
            player.getInventory().setChestplate(Items.SPECTATOR_ARMOR);
            player.setHealth(player.getMaxHealth());
            event.setCancelled(true);
            player.setGameMode(GameMode.ADVENTURE);
            Bukkit.getOnlinePlayers().forEach(p ->{
                if(p != player) p.hidePlayer(plugin, player);
            });
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, true, false, false));
            player.setAllowFlight(true);
            player.setFlyingFallDamage(TriState.FALSE);
            player.setFlying(true);
            plugin.getGameManager().spectators.add(player.getUniqueId());

            player.getInventory().setItem(0, Items.SPECTATOR_TARGET_SELECTOR);
            player.getInventory().setItem(4, Items.SPECTATOR_SPEED_SELECTOR);
            player.getInventory().setItem(8, Items.SPECTATOR_LOBBY_REQEST);
        } else {
            // un-ivisafy if they are respawning, etc.
            plugin.getGameManager().spectators.remove(player.getUniqueId());
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
            Bukkit.getOnlinePlayers().forEach(p -> {
                if(p != player) p.showPlayer(plugin, player);
            });
            if(event.getNewGameMode() != GameMode.CREATIVE){
                player.setAllowFlight(false);
                player.setFlying(false);
                player.setFlyingFallDamage(TriState.TRUE);
            }
        }
    }
}

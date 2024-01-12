package dev.foxikle.webnetbedwars.runnables;

import dev.foxikle.webnetbedwars.WebNetBedWars;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

public class BridgeEggRunnable extends BukkitRunnable {

    private final WebNetBedWars plugin;
    private final Egg egg;
    private final ItemStack blockToPlace;
    private final Player thrower;

    public BridgeEggRunnable(WebNetBedWars plugin, Egg egg, ItemStack blockToPlace, Player thrower) {
        this.plugin = plugin;
        this.egg = egg;
        this.blockToPlace = blockToPlace;
        this.thrower = thrower;
    }

    @Override
    public void run() {
        if(egg.isDead()) {
            cancel();
            return;
        }
        Location location = egg.getLocation();
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            Block block = location.getBlock();
            block.setType(blockToPlace.getType());
            block.setMetadata("blockdata", new FixedMetadataValue(plugin, true));
            thrower.playSound(thrower.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1F, 1F);
        }, 10);
    }
}

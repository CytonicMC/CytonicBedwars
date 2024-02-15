package dev.foxikle.webnetbedwars.runnables;

import dev.foxikle.webnetbedwars.WebNetBedWars;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitRunnable;

public class GeneratorVisualRunnable extends BukkitRunnable {

    private final ArmorStand as;
    private final WebNetBedWars plugin;
    private double rots = 0.0;

    public GeneratorVisualRunnable(ArmorStand as, WebNetBedWars plugin) {
        this.as = as;
        this.plugin = plugin;
    }

    @Override
    public void run() {
        rots += (Math.PI/12);
        Location loc = as.getLocation().subtract(0, Math.sin(rots)/24, 0);
        loc.setYaw(as.getYaw() + 10.5F);
        as.teleport(loc);
    }

    public void start() {
       runTaskTimer(plugin, 0, 1);
    }
}

package net.cytonic.cytonicbedwars.runnables;

import lombok.Getter;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.timer.Task;

import java.time.Duration;

public class GeneratorVisualRunnable {

    private final Entity as;
    @Getter
    private final Task task;
    private double rots = 0.0;

    public GeneratorVisualRunnable(Entity as) {
        this.as = as;
        task = MinecraftServer.getSchedulerManager().buildTask(this::run).repeat(Duration.ofMillis(20)).schedule();
    }

    private void run() {
        rots += (Math.PI / 12);
        Pos loc = as.getPosition().sub(0, Math.sin(rots) / 24, 0);
        loc = loc.withYaw(as.getPosition().yaw() + 10.5F);
        as.teleport(loc);
    }
}

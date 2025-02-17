package net.cytonic.cytonicbedwars.runnables;

import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytosis.utils.Msg;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.sound.SoundEvent;
import net.minestom.server.timer.Task;

import java.time.Duration;

public class RespawnRunnable {
    private final Player player;
    private final Task task;
    private int timeLeft;

    public RespawnRunnable(int timeLeft, Player player) {
        this.timeLeft = timeLeft;
        this.player = player;
        task = MinecraftServer.getSchedulerManager().buildTask(this::run).repeat(Duration.ofSeconds(1)).schedule();
    }


    public void run() {
        timeLeft--;
        if (timeLeft <= 0) {
            task.cancel();
            CytonicBedWars.getGameManager().respawnPlayer(player);
            return;
        }

        switch (timeLeft) {
            case 1, 2, 3 -> {
                player.showTitle(Title.title(Msg.mm("<YELLOW>Respawning in " + timeLeft), Component.text(""), Title.Times.times(Duration.ofMillis(0), Duration.ofMillis(1150), Duration.ofSeconds(1))));
                player.playSound(Sound.sound(SoundEvent.UI_BUTTON_CLICK, Sound.Source.AMBIENT, .8f, 1f));
            }
        }
    }
}

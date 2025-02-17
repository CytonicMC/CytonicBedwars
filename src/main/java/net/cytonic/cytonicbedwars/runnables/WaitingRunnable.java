package net.cytonic.cytonicbedwars.runnables;


import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.utils.Msg;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.title.Title;
import net.minestom.server.MinecraftServer;
import net.minestom.server.sound.SoundEvent;
import net.minestom.server.timer.Task;

import java.time.Duration;

public class WaitingRunnable {
    private final Task task;
    public static int timeLeft;

    public WaitingRunnable(int timeLeft) {
        WaitingRunnable.timeLeft = timeLeft;
        task = MinecraftServer.getSchedulerManager().buildTask(this::run).repeat(Duration.ofSeconds(1)).schedule();
    }


    public void run() {
        timeLeft--;
        if (timeLeft <= 0) {
            task.cancel();
            CytonicBedWars.getGameManager().start();
            return;
        }

        switch (timeLeft) {
            case 1, 2, 3, 4, 5 -> {
                Cytosis.getOnlinePlayers().forEach(player -> {
                    player.sendMessage(Msg.mm("<yellow>The game starts in <red>" + timeLeft + " <yellow>seconds!"));
                    player.showTitle(Title.title(Msg.mm("<red>" + timeLeft), Msg.mm(""), Title.Times.times(Duration.ofMillis(0), Duration.ofSeconds(1), Duration.ofMillis(0))));
                    player.playSound(Sound.sound(SoundEvent.UI_BUTTON_CLICK, Sound.Source.AMBIENT, .8f, 1f));
                });
            }
        }
    }

    public void stop() {
        task.cancel();
    }
}

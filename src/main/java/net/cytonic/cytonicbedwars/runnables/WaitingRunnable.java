package net.cytonic.cytonicbedwars.runnables;


import lombok.Getter;
import net.cytonic.cytonicbedwars.managers.GameManager;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.managers.SideboardManager;
import net.cytonic.cytosis.utils.Msg;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.title.Title;
import net.minestom.server.MinecraftServer;
import net.minestom.server.sound.SoundEvent;
import net.minestom.server.timer.Task;

import java.time.Duration;

public class WaitingRunnable {
    @Getter
    private static int timeLeft = 6;
    private final Task task;

    public WaitingRunnable() {
        task = MinecraftServer.getSchedulerManager().buildTask(this::run).repeat(Duration.ofSeconds(1)).schedule();
    }

    public void run() {
        timeLeft--;
        if (timeLeft <= 0) {
            task.cancel();
            Cytosis.CONTEXT.getComponent(GameManager.class).start();
            return;
        }

        switch (timeLeft) {
            case 1, 2, 3, 4, 5 -> {
                Cytosis.CONTEXT.getComponent(SideboardManager.class).updatePlayersNow();
                Cytosis.getOnlinePlayers().forEach(player -> {
                    player.sendMessage(Msg.yellow("The game starts in <red>%s <yellow>seconds!", timeLeft));
                    player.showTitle(Title.title(Msg.red(String.valueOf(timeLeft)), Msg.mm(""), Title.Times.times(Duration.ofMillis(0), Duration.ofSeconds(1), Duration.ofMillis(0))));
                    player.playSound(Sound.sound(SoundEvent.UI_BUTTON_CLICK, Sound.Source.AMBIENT, .8f, 1f));
                });
            }
        }
    }

    public void stop() {
        task.cancel();
    }
}

package net.cytonic.bedwars.runnables;

import java.time.Duration;

import lombok.Getter;
import net.minestom.server.MinecraftServer;
import net.minestom.server.timer.Task;

import net.cytonic.bedwars.data.enums.GameState;
import net.cytonic.bedwars.managers.GameManager;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.utils.Msg;

public class GameRunnable {

    @Getter
    private static int timeLeft = GameState.PLAY.getDuration() + 1;
    private final Task task;

    public GameRunnable() {
        task = MinecraftServer.getSchedulerManager().buildTask(this::run).repeat(Duration.ofSeconds(1)).schedule();
    }

    public static String getFormattedTimeLeft() {
        Duration duration = Duration.ofSeconds(timeLeft);
        long minutes = duration.toMinutes();
        long seconds = duration.minusMinutes(minutes).getSeconds();
        return minutes + ":" + (seconds < 10 ? "0" + seconds : seconds);
    }

    public void run() {
        timeLeft--;
        if (timeLeft <= -1) {
            timeLeft = Cytosis.CONTEXT.getComponent(GameManager.class).nextGameState().getDuration();
        }
        if (Cytosis.CONTEXT.getComponent(GameManager.class).getGameState() == GameState.BED_DESTRUCTION
            && timeLeft == 59) {
            Cytosis.getOnlinePlayers()
                .forEach(player -> player.sendMessage(Msg.yellow("All beds will be destroyed in <red>60 seconds!")));
        }
    }

    public void stop() {
        task.cancel();
    }
}

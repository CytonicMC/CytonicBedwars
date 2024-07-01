package net.cytonic.cytonicbedwars.runnables;

import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytosis.utils.MiniMessageTemplate;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.sound.SoundEvent;
import net.minestom.server.timer.SchedulerManager;
import net.minestom.server.timer.Task;

import java.time.Duration;

public class RespawnRunnable {
    private final CytonicBedWars plugin;
    private int timeLeft;
    private final Player player;
    private final Task task;

    public RespawnRunnable(CytonicBedWars plugin, int timeLeft, Player player) {
        this.plugin = plugin;
        this.timeLeft = timeLeft;
        this.player = player;
        task = MinecraftServer.getSchedulerManager().buildTask(this::run).repeat(Duration.ofMillis(200)).schedule();
    }


    public void run() {
        timeLeft--;
        if(timeLeft <= 0){
            task.cancel();
            plugin.getGameManager().respawnPlayer(player);
            return;
        }

        switch (timeLeft) {
            case 1, 2, 3 -> {
                player.showTitle(Title.title(MiniMessageTemplate.MM."<YELLOW>Respawning in \{timeLeft}", Component.text(""), Title.Times.times(Duration.ofMillis(0), Duration.ofMillis(4200), Duration.ofMillis(0))));
                player.playSound(Sound.sound(SoundEvent.UI_BUTTON_CLICK, Sound.Source.AMBIENT, .8f, 1f));
            }
        }
    }
}

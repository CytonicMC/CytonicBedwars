package net.cytonic.cytonicbedwars.listeners;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.Config;
import net.cytonic.cytonicbedwars.managers.GameManager;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.events.api.Listener;
import net.cytonic.cytosis.utils.Msg;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.damage.DamageType;
import net.minestom.server.event.player.PlayerMoveEvent;

@NoArgsConstructor
@SuppressWarnings("unused")
public class MoveListener {

    @Listener
    public static void onMove(PlayerMoveEvent event) {
        if (!(event.getPlayer() instanceof BedwarsPlayer player)) return;
        if (!Cytosis.CONTEXT.getComponent(GameManager.class).STARTED) return;
        if (event.getNewPosition().y() <= -40) {
            if (Cytosis.CONTEXT.getComponent(GameManager.class).getSpectators().contains(player.getUuid())) {
                player.teleport(Config.spawnPlatformCenter);
                event.setCancelled(true);
            } else {
                Cytosis.CONTEXT.getComponent(GameManager.class).kill(player, null, DamageType.OUT_OF_WORLD);
                event.setCancelled(true);
            }
        }
        if (player.getGameMode() == GameMode.CREATIVE) return;
        Pos spawn = Config.spawnPlatformCenter;
        if (distance(event.getNewPosition().x(), spawn.x(), event.getNewPosition().z(), spawn.z()) > 11025 || event.getNewPosition().y() >= 50) {
            event.setCancelled(true);
            player.sendMessage(Msg.whoops("You cannot travel too far from the map!"));
        }
    }

    private static double distance(double x1, double x2, double z1, double z2) {
        return Math.pow(x1 - x2, 2) + Math.pow(z1 - z2, 2);
    }
}

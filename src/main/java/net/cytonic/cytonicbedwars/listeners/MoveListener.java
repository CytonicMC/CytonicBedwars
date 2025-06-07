package net.cytonic.cytonicbedwars.listeners;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytonicbedwars.CytonicBedwarsSettings;
import net.cytonic.cytosis.events.api.Listener;
import net.cytonic.cytosis.player.CytosisPlayer;
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
        if (!CytonicBedWars.getGameManager().STARTED) return;
        if (CytonicBedWars.getGameManager().spectators.contains(event.getPlayer().getUuid())) {
            event.getPlayer().teleport(CytonicBedwarsSettings.spawnPlatformCenter);
            event.setCancelled(true);
        } else {
            if (event.getNewPosition().y() <= -40) {
                CytonicBedWars.getGameManager().kill((CytosisPlayer) event.getPlayer(), null, DamageType.OUT_OF_WORLD);
                event.setCancelled(true);
            }
        }
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        Pos spawn = CytonicBedwarsSettings.spawnPlatformCenter;
        if (distance(event.getNewPosition().x(), spawn.x(), event.getNewPosition().z(), spawn.z()) > 105.0 || event.getNewPosition().y() >= 50) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Msg.whoops("You cannot travel too far from the map!"));
        }
    }

    private static double distance(double x1, double x2, double z1, double z2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(z1 - z2, 2));
    }
}

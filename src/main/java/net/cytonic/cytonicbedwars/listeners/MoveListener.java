package net.cytonic.cytonicbedwars.listeners;

import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.damage.DamageType;
import net.minestom.server.event.player.PlayerMoveEvent;

public class MoveListener {

    private final CytonicBedWars plugin;
    public MoveListener(CytonicBedWars plugin) {
        this.plugin = plugin;
    }

    public void onMove(PlayerMoveEvent event){
        if(!plugin.getGameManager().STARTED) return;
        if(event.getNewPosition().y() <= -40){
                plugin.getGameManager().kill(event.getPlayer(), null, DamageType.OUT_OF_WORLD);
            event.setCancelled(true);
        }
        //todo get values from config
        Pos spawn = new Pos(0, 100, 0);
        if(distance(event.getNewPosition().x(), spawn.x(), event.getNewPosition().z(), spawn.z()) > 105.0) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Component.text("You cannot travel too far from the map!", NamedTextColor.RED));
        }
    }

    private double distance (double x1, double x2, double z1, double z2) {
        return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(z1-z2, 2));
    }
}

package net.cytonic.cytonicbedwars.listeners;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.entity.GameMode;
import net.minestom.server.event.player.PlayerBlockBreakEvent;

@NoArgsConstructor
public class BlockBreakListener {

    public void onBlockBreak(PlayerBlockBreakEvent e) {
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        if (CytonicBedWars.getGameManager().spectators.contains(e.getPlayer().getUuid())) {
            e.getPlayer().sendMessage(Component.text("You cannot do this as a spectator!", NamedTextColor.RED));
            e.setCancelled(true);
            return;
        }
        if (e.getBlock().name().contains("bed")) {
            CytonicBedWars.getGameManager().getTeamlist().forEach(team -> {
                if (e.getBlock().name().equals(team.bedType().name())) {
                    CytonicBedWars.getGameManager().breakBed(e.getPlayer(), team);
                }
            });
            return;
        }
        if (!e.getBlock().hasNbt()) {
            e.getPlayer().sendMessage(Component.text("You can only break blocks placed by players!", NamedTextColor.RED));
        } else if (e.getBlock().nbt() != null && !e.getBlock().nbt().getBoolean("placedByPlayer")) {
            e.setCancelled(true);
            e.setResultBlock(e.getBlock());
        }
    }
}

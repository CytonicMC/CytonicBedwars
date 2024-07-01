package net.cytonic.cytonicbedwars.listeners;

import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.entity.GameMode;
import net.minestom.server.event.player.PlayerBlockBreakEvent;

public class BlockBreakListener {

    private final CytonicBedWars plugin;

    public BlockBreakListener(CytonicBedWars plugin) {
        this.plugin = plugin;
    }

    public void onBlockBreak(PlayerBlockBreakEvent e) {
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        if (plugin.getGameManager().spectators.contains(e.getPlayer().getUuid())) {
            e.getPlayer().sendMessage(Component.text("You cannot do this as a spectator!", NamedTextColor.RED));
            e.setCancelled(true);
            return;
        }
        if (e.getBlock().name().contains("BED")) {
            plugin.getGameManager().getTeamlist().forEach(team -> {
                if (e.getBlock() == team.bedType()) {
                    plugin.getGameManager().breakBed(e.getPlayer(), team);
                }
            });
            return;
        }
        if (e.getBlock().hasNbt())
            if (!e.getBlock().nbt().getBoolean("placedByPlayer")) {
                e.getPlayer().sendMessage(Component.text("You can only break blocks placed by players!", NamedTextColor.RED));
                e.setCancelled(true);
            }
    }
}

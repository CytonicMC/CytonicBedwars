package dev.foxikle.webnetbedwars.listeners;

import dev.foxikle.webnetbedwars.WebNetBedWars;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.metadata.MetadataValue;

public class BlockBreakListener implements Listener {
    private final WebNetBedWars plugin;

    public BlockBreakListener(WebNetBedWars plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        for (MetadataValue mv : e.getBlock().getMetadata("blockdata")) {
            if(mv.asBoolean() && mv.getOwningPlugin() instanceof WebNetBedWars){
                return;
            }
        }
        if(e.getPlayer().getGameMode() != GameMode.CREATIVE) {
            e.getPlayer().sendMessage(Component.text("You can only break blocks placed by players!", NamedTextColor.RED));
            e.setCancelled(true);
        }
    }
}

package dev.foxikle.webnetbedwars.commands;

import dev.foxikle.webnetbedwars.WebNetBedWars;
import dev.foxikle.webnetbedwars.data.objects.Team;
import dev.foxikle.webnetbedwars.managers.MenuManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
public class ItemShopCommand implements CommandExecutor {

    private final WebNetBedWars plugin;

    public ItemShopCommand(WebNetBedWars plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
            for (Team t : plugin.getGameManager().getTeamlist())
                if (plugin.getGameManager().STARTED) {
                    if (player.getLocation().distance(t.itemShopLocation()) <= 5) {
                        plugin.getGameManager().getMenuManager().getBlocksShop().open(player);
                    }
        }
        return true;
    }
}
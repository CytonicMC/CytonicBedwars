package dev.foxikle.webnetbedwars;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DebugCommand implements CommandExecutor, TabCompleter {
    private final WebNetBedWars plugin;

    public DebugCommand(WebNetBedWars plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player){
            if(player.hasPermission("webnet.bedwars.debug")) {
                if(args.length >= 1){
                    switch (args[0].toLowerCase()){
                        case "start" -> {
                            player.sendMessage(ChatColor.GREEN + "Starting game!");
                            plugin.getGameManager().start();
                        }
                        case "end" -> {
                            player.sendMessage(ChatColor.GREEN + "ending game!");
                            plugin.getGameManager().cleanup();
                        }
                        case "listteams" -> {
                            plugin.getGameManager().getTeamlist().forEach(team -> player.sendMessage(team.prefix() + team.displayName()));
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return List.of("end", "listteams", "start");
    }
}

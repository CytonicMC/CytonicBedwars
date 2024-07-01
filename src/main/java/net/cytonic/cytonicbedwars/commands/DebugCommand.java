package net.cytonic.cytonicbedwars.commands;

import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytonicbedwars.data.enums.GameState;
import net.cytonic.cytosis.Cytosis;
import static net.cytonic.cytosis.utils.MiniMessageTemplate.MM;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
import net.minestom.server.entity.Player;

public class DebugCommand extends Command {

    private final CytonicBedWars plugin;

    public DebugCommand(CytonicBedWars plugin) {
        super("debug");
        this.plugin = plugin;

        setCondition((sender, _) -> sender.hasPermission("cytonic..bedwars.commands.debug"));
        setDefaultExecutor((sender, _) -> sender.sendMessage(Component.text("You must specify a command!", NamedTextColor.RED)));

        var debugArgument = ArgumentType.Word("debug").from("start", "end", "listteams", "freeze", "f", "itemshop", "bedbug");
        debugArgument.setCallback((sender, exception) -> sender.sendMessage(Component.text(STR."The command \{exception.getInput()} is invalid!", NamedTextColor.RED)));
        debugArgument.setSuggestionCallback((_, _, suggestion) -> {
            suggestion.addEntry(new SuggestionEntry("start", Component.text("Starts the game!")));
            suggestion.addEntry(new SuggestionEntry("end", Component.text("Ends the game!")));
            suggestion.addEntry(new SuggestionEntry("listteams", Component.text("Lists all the teams!")));
            suggestion.addEntry(new SuggestionEntry("freeze", Component.text("Freezes the game!")));
            suggestion.addEntry(new SuggestionEntry("f", Component.text("Freezes the game!")));
            suggestion.addEntry(new SuggestionEntry("itemshop", Component.text("Opens the item shop!")));
            suggestion.addEntry(new SuggestionEntry("bedbug", Component.text("Spawns a bed bug!")));
        });

        addSyntax(((sender, context) -> {
            if (sender instanceof Player player) {
                if (player.hasPermission("cytonic.bedwars.commands.debug")) {
                    String command = context.get(debugArgument);

                    switch (command.toLowerCase()) {
                        case "start" -> {
                            if (plugin.getGameManager().STARTED) {
                                player.sendMessage(Component.text("The game has already been started! Use '/debug stop' to end it!", NamedTextColor.RED));
                            }
                            player.sendMessage(Component.text("Starting game!", NamedTextColor.GREEN));
                            plugin.getGameManager().start();
                        }
                        case "end" -> {
                            player.sendMessage(Component.text("Ending game!", NamedTextColor.GREEN));
                            plugin.getGameManager().cleanup();
                        }
                        case "listteams" -> plugin.getGameManager().getTeamlist().forEach(team -> player.sendMessage(team.prefix() + team.displayName()));
                        case "freeze", "f" -> {
                            if (plugin.getGameManager().getGameState() != GameState.FROZEN) {
                                Cytosis.getOnlinePlayers().forEach((player1) -> player1.sendMessage(MM."<yellow>The game is now <aqua><bold>FROZEN<reset><yellow>!"));
                                plugin.getGameManager().freeze();
                            } else {
                                Cytosis.getOnlinePlayers().forEach((player1) -> player1.sendMessage(MM."<yellow>The game is now <gold><bold>THAWED<reset><yellow>!"));
                                plugin.getGameManager().thaw();
                            }
                        }
                        case "itemshop" -> {
                            if (!plugin.getGameManager().STARTED) {
                                player.sendMessage(MM."<RED><BOLD>!! WARNING !!<RESET> <RED> The game has not been started. Some shop pages may not work!");
                            }

                            //plugin.getGameManager().getMenuManager().getBlocksShop().open(player);
                        }
                       // case "bedbug" -> new BedBug(plugin.getGameManager().getPlayerTeam(player.getUuid()), player.getPosition());
                    }
                }
            }

        }));
    }
}

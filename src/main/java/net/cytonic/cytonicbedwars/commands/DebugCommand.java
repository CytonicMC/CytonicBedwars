package net.cytonic.cytonicbedwars.commands;

import net.cytonic.cytonicbedwars.data.enums.GameState;
import net.cytonic.cytosis.Cytosis;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
import net.minestom.server.entity.Player;
import net.cytonic.cytonicbedwars.CytonicBedWars;

import static net.cytonic.utils.MiniMessageTemplate.MM;

public class DebugCommand extends Command {

    public DebugCommand() {
        super("debug");

        setCondition((sender, _) -> sender.hasPermission("cytonic..bedwars.commands.debug"));
        setDefaultExecutor((sender, _) -> sender.sendMessage(Component.text("You must specify a command!", NamedTextColor.RED)));

        var debugArgument = ArgumentType.Word("debug").from("start", "end", "listteams", "freeze", "f", "itemshop");
        debugArgument.setCallback((sender, exception) -> sender.sendMessage(Component.text(STR."The command \{exception.getInput()} is invalid!", NamedTextColor.RED)));
        debugArgument.setSuggestionCallback((_, _, suggestion) -> {
            suggestion.addEntry(new SuggestionEntry("start", Component.text("Starts the game!")));
            suggestion.addEntry(new SuggestionEntry("end", Component.text("Ends the game!")));
            suggestion.addEntry(new SuggestionEntry("listteams", Component.text("Lists all the teams!")));
            suggestion.addEntry(new SuggestionEntry("freeze", Component.text("Freezes the game!")));
            suggestion.addEntry(new SuggestionEntry("f", Component.text("Freezes the game!")));
            suggestion.addEntry(new SuggestionEntry("itemshop", Component.text("Opens the item shop!")));
        });

        addSyntax((sender, context) -> {
            if (sender instanceof Player player) {
                if (player.hasPermission("cytonic.bedwars.commands.debug")) {
                    String command = context.get(debugArgument);

                    switch (command.toLowerCase()) {
                        case "start" -> {
                            if (CytonicBedWars.getGameManager().STARTED) {
                                player.sendMessage(Component.text("The game has already been started! Use '/debug stop' to end it!", NamedTextColor.RED));
                            }
                            player.sendMessage(Component.text("Starting game!", NamedTextColor.GREEN));
                            CytonicBedWars.getGameManager().start();
                        }
                        case "end" -> {
                            player.sendMessage(Component.text("Ending game!", NamedTextColor.GREEN));
                            CytonicBedWars.getGameManager().cleanup();
                        }
                        case "listteams" ->
                                CytonicBedWars.getGameManager().getTeamlist().forEach(team -> player.sendMessage(MM."\{team.prefix()}\{team.displayName()}"));
                        case "freeze", "f" -> {
                            if (CytonicBedWars.getGameManager().getGameState() != GameState.FROZEN) {
                                Cytosis.getOnlinePlayers().forEach((player1) -> player1.sendMessage(MM."<yellow>The game is now <aqua><bold>FROZEN<reset><yellow>!"));
                                CytonicBedWars.getGameManager().freeze();
                            } else {
                                Cytosis.getOnlinePlayers().forEach((player1) -> player1.sendMessage(MM."<yellow>The game is now <gold><bold>THAWED<reset><yellow>!"));
                                CytonicBedWars.getGameManager().thaw();
                            }
                        }
                        case "itemshop" -> {
                            if (!CytonicBedWars.getGameManager().STARTED) {
                                player.sendMessage(MM."<RED><BOLD>!! WARNING !!<RESET> <RED> The game has not been started. Some shop pages may not work!");
                            }
                            player.openInventory(CytonicBedWars.getGameManager().getMenuManager().getBlocksShop());
                        }
                    }
                }
            }

        }, debugArgument);
    }
}

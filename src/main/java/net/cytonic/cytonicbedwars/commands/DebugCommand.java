package net.cytonic.cytonicbedwars.commands;

import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytonicbedwars.data.enums.GameState;
import net.cytonic.cytonicbedwars.menu.itemShop.BlocksShopMenu;
import net.cytonic.cytonicbedwars.runnables.WaitingRunnable;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.commands.utils.CommandUtils;
import net.cytonic.cytosis.commands.utils.CytosisCommand;
import net.cytonic.cytosis.player.CytosisPlayer;
import net.cytonic.cytosis.utils.Msg;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;

public class DebugCommand extends CytosisCommand {

    public DebugCommand() {
        super("debug");

        setCondition(CommandUtils.IS_ADMIN);
        setDefaultExecutor((sender, context) -> sender.sendMessage(Msg.whoops("You must specify a command!")));

        var debugArgument = ArgumentType.Word("debug").from("start", "forceStart", "end", "cleanup", "listteams", "freeze", "f", "itemshop");
        debugArgument.setCallback((sender, exception) -> sender.sendMessage(Msg.whoops("The command " + exception.getInput() + " is invalid!")));
        debugArgument.setSuggestionCallback((commandSender, commandContext, suggestion) -> {
            suggestion.addEntry(new SuggestionEntry("start", Msg.mm("Starts the game!")));
            suggestion.addEntry(new SuggestionEntry("forceStart", Msg.mm("Force starts the game!")));
            suggestion.addEntry(new SuggestionEntry("end", Msg.mm("Ends the game!")));
            suggestion.addEntry(new SuggestionEntry("cleanup", Msg.mm("Cleans up the game!")));
            suggestion.addEntry(new SuggestionEntry("listteams", Msg.mm("Lists all the teams!")));
            suggestion.addEntry(new SuggestionEntry("freeze", Msg.mm("Freezes the game!")));
            suggestion.addEntry(new SuggestionEntry("f", Msg.mm("Freezes the game!")));
            suggestion.addEntry(new SuggestionEntry("itemshop", Msg.mm("Opens the item shop!")));
        });

        addSyntax((sender, context) -> {
            if (sender instanceof CytosisPlayer player) {
                String command = context.get(debugArgument);

                switch (command.toLowerCase()) {
                    case "start" -> {
                        if (CytonicBedWars.getGameManager().STARTED) {
                            player.sendMessage(Msg.mm("The game has already been started! Use '/debug stop' to end it!", NamedTextColor.RED));
                        }
                        CytonicBedWars.getGameManager().setGameState(GameState.STARTING);
                        CytonicBedWars.getGameManager().setWaitingRunnable(new WaitingRunnable());
                    }
                    case "forcestart" -> {
                        if (CytonicBedWars.getGameManager().STARTED) {
                            player.sendMessage(Msg.mm("The game has already been started! Use '/debug stop' to end it!", NamedTextColor.RED));
                        }
                        CytonicBedWars.getGameManager().start();
                    }
                    case "end" -> {
                        player.sendMessage(Msg.mm("Ending game!", NamedTextColor.GREEN));
                        CytonicBedWars.getGameManager().end();
                    }
                    case "cleanup" -> {
                        player.sendMessage(Msg.mm("Cleaning up game!", NamedTextColor.GREEN));
                        CytonicBedWars.getGameManager().cleanup();
                    }
                    case "listteams" ->
                            CytonicBedWars.getGameManager().getTeamlist().forEach(team -> player.sendMessage(Msg.mm(team.prefix() + team.displayName())));
                    case "freeze", "f" -> {
                        if (CytonicBedWars.getGameManager().getGameState() != GameState.FROZEN) {
                            Cytosis.getOnlinePlayers().forEach((player1) -> player1.sendMessage(Msg.mm("<yellow>The game is now <aqua><bold>FROZEN<reset><yellow>!")));
                            CytonicBedWars.getGameManager().freeze();
                        } else {
                            Cytosis.getOnlinePlayers().forEach((player1) -> player1.sendMessage(Msg.mm("<yellow>The game is now <gold><bold>THAWED<reset><yellow>!")));
                            CytonicBedWars.getGameManager().thaw();
                        }
                    }
                    case "itemshop" -> {
                        if (!CytonicBedWars.getGameManager().STARTED) {
                            player.sendMessage(Msg.mm("<RED><BOLD>!! WARNING !!<RESET> <RED> The game has not been started. Some shop pages may not work!"));
                        }
                        new BlocksShopMenu().open(player);
                    }
                }
            }
        }, debugArgument);
    }
}

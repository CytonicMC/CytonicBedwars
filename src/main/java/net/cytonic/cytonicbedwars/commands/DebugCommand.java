package net.cytonic.cytonicbedwars.commands;

import net.kyori.adventure.text.Component;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.ArgumentWord;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;

import net.cytonic.cytonicbedwars.game.Game;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytonicbedwars.server.BedwarsServer;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.commands.utils.CommandUtils;
import net.cytonic.cytosis.commands.utils.CytosisCommand;
import net.cytonic.cytosis.utils.Msg;
import net.cytonic.cytosis.utils.Utils;

public class DebugCommand extends CytosisCommand {

    public DebugCommand() {
        super("debug");

        setCondition(CommandUtils.IS_ADMIN);
        setDefaultExecutor((sender, _) -> sender.sendMessage(Msg.whoops("You must specify a command!")));

        ArgumentWord debugArgument = ArgumentType.Word("debug")
            .from("start", "forceStart", "end", "gameinfo", "listgames");
        debugArgument.setCallback((sender, exception) -> sender.sendMessage(
            Msg.whoops("The command " + exception.getInput() + " is invalid!")));
        debugArgument.setSuggestionCallback((_, _, suggestion) -> {
            suggestion.addEntry(new SuggestionEntry("start", Msg.green("Starts the game!")));
            suggestion.addEntry(new SuggestionEntry("forceStart", Msg.green("Force starts the game!")));
            suggestion.addEntry(new SuggestionEntry("end", Msg.green("Ends the game!")));
            suggestion.addEntry(new SuggestionEntry("gameinfo", Msg.green("Shows info about the game you are in!")));
            suggestion.addEntry(new SuggestionEntry("listgames", Msg.green("Lists the running on THIS server!")));
        });

        addSyntax((sender, context) -> {
            if (!(sender instanceof BedwarsPlayer player)) return;
            String command = context.get(debugArgument);

            switch (command.toLowerCase()) {
                case "start" -> {
                    if (player.getGame().isStarted()) {
                        player.sendMessage(
                            Msg.red("The game has already been started! Use '/debug stop' to end it!"));
                        return;
                    }
                    player.getGame().waitStart();
                }
                case "forcestart" -> {
                    if (player.getGame().isStarted()) {
                        player.sendMessage(
                            Msg.red("The game has already been started! Use '/debug stop' to end it!"));
                        return;
                    }
                    player.getGame().start();
                }
                case "end" -> {
                    player.sendMessage(Msg.green("Ending game!"));
                    player.getGame().end();
                }
                case "gameinfo" -> sendGameInfo(player, player.getGame());
                case "listgames" -> {
                    BedwarsServer server = Cytosis.getServer();
                    player.sendMessage(Msg.green("Games running on this server: %s", server.getGames().size()));
                    server.getGames().forEach((_, game) -> sendGameInfo(player, game));
                }
            }
        }, debugArgument);
    }

    private void sendGameInfo(BedwarsPlayer player, Game game) {
        player.sendMessage(Msg.mm(" ID: <click:copy_to_clipboard:%s>%s", game.getId(), game.getId()));
        player.sendMessage(Msg.mm("     Map: %s",
            Utils.captializeFirstLetters(game.getMap().name().toLowerCase().replace("_", " "))));
        player.sendMessage(Msg.mm("     Started: ").append(formatBool(game.isStarted())));
        player.sendMessage(
            Msg.mm("     State: <gold>%s", Utils.captializeFirstLetters(game.getState().name().toLowerCase())));
        player.sendMessage(Msg.mm("     Teams:"));
        game.getTeams().forEach((color, team) -> {
            player.sendMessage(Msg.mm("         <%s>%s:", color.name().toLowerCase(),
                Utils.captializeFirstLetters(color.name().toLowerCase())));
            player.sendMessage(Msg.mm("             Alive: ").append(formatBool(team.isAlive())));
            player.sendMessage(Msg.mm("             Has bed: ").append(formatBool(team.hasBed())));
            player.sendMessage(Msg.mm("             Players:"));
            for (BedwarsPlayer teamPlayer : team.getPlayers()) {
                player.sendMessage(Msg.mm("                %s:", teamPlayer.getUsername()));
                player.sendMessage(Msg.mm("                 Is Alive: ").append(formatBool(teamPlayer.isAlive())));
                player.sendMessage(
                    Msg.mm("                 Is Respawning: ").append(formatBool(teamPlayer.isRespawning())));
                player.sendMessage(Msg.mm("                 Armor Level: <gold>%s",
                    Utils.captializeFirstLetters(teamPlayer.getArmorLevel().name().toLowerCase())));
                player.sendMessage(Msg.mm("                 Axe Level: <gold>%s",
                    Utils.captializeFirstLetters(teamPlayer.getAxeLevel().name().toLowerCase())));
                player.sendMessage(Msg.mm("                 Pickaxe Level: <gold>%s",
                    Utils.captializeFirstLetters(teamPlayer.getPickaxeLevel().name().toLowerCase())));
                player.sendMessage(Msg.mm("                 Has Shears: ").append(formatBool(teamPlayer.hasShears())));
            }
        });
    }

    private Component formatBool(boolean bool) {
        if (bool) {
            return Msg.green("Yes");
        }
        return Msg.red("No");
    }
}

package net.cytonic.cytonicbedwars.commands;

import me.devnatan.inventoryframework.ViewFrame;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;

import net.cytonic.cytonicbedwars.game.Game;
import net.cytonic.cytonicbedwars.managers.GameManager;
import net.cytonic.cytonicbedwars.menu.ItemShopMenu;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytonicbedwars.server.BedwarsServer;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.commands.utils.CommandUtils;
import net.cytonic.cytosis.commands.utils.CytosisCommand;
import net.cytonic.cytosis.utils.Msg;

public class DebugCommand extends CytosisCommand {

    public DebugCommand() {
        super("debug");

        setCondition(CommandUtils.IS_ADMIN);
        setDefaultExecutor((sender, _) -> sender.sendMessage(Msg.whoops("You must specify a command!")));

        var debugArgument = ArgumentType.Word("debug")
            .from("start", "forceStart", "end", "listteams", "itemshop", "teaminfo", "listgames",
                "gameinfo");
        debugArgument.setCallback((sender, exception) -> sender.sendMessage(
            Msg.whoops("The command " + exception.getInput() + " is invalid!")));
        debugArgument.setSuggestionCallback((_, _, suggestion) -> {
            suggestion.addEntry(new SuggestionEntry("start", Msg.green("Starts the game!")));
            suggestion.addEntry(new SuggestionEntry("forceStart", Msg.green("Force starts the game!")));
            suggestion.addEntry(new SuggestionEntry("end", Msg.green("Ends the game!")));
            suggestion.addEntry(new SuggestionEntry("listteams", Msg.green("Lists all the teams!")));
            suggestion.addEntry(new SuggestionEntry("itemshop", Msg.green("Opens the item shop!")));
            suggestion.addEntry(new SuggestionEntry("teaminfo", Msg.green("Shows information about the teams!")));
            suggestion.addEntry(new SuggestionEntry("listgames", Msg.green("Lists the running on THIS server!")));
            suggestion.addEntry(new SuggestionEntry("gameinfo", Msg.green("Shows info about the game you are in!")));
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
                case "test" -> {
                    player.sendToLobby();
//                    player.sendMessage(Component.text("Click to copy").clickEvent(ClickEvent.copyToClipboard("""
//                        {
//                          "x": %d,
//                          "y": %d,
//                          "z": %d
//                        }
//                        """)));
                }
                case "itemshop" -> {
                    if (!Cytosis.CONTEXT.getComponent(GameManager.class).STARTED) {
                        player.sendMessage(Msg.redSplash("!! WARNING !!",
                            "The game has not been started. Some shop pages may not work!"));
                    }
                    Cytosis.CONTEXT.getComponent(ViewFrame.class).open(ItemShopMenu.class, player);
                }
                case "listgames" -> {
                    BedwarsServer server = Cytosis.getServer();
                    player.sendMessage(Msg.green("Games running on this server: %s", server.getGames().size()));
                    server.getGames().forEach((_, game) -> sendGameInfo(player, game));
                }
                case "gameinfo" -> sendGameInfo(player, player.getGame());
            }
        }, debugArgument);
    }

    private void sendGameInfo(BedwarsPlayer player, Game game) {
        player.sendMessage(Msg.mm(" ID: <click:copy_to_clipboard:%s>%s", game.getId(), game.getId()));
        player.sendMessage(Msg.mm("     Map: %s", game.getMap().name().toLowerCase()));
        player.sendMessage(Msg.mm("     Started: %s", game.isStarted()));
        player.sendMessage(Msg.mm("     State: %s", game.getState().name().toLowerCase()));
        player.sendMessage(Msg.mm("     Teams:"));
        game.getTeams().forEach((color, team) -> {
            player.sendMessage(Msg.mm("         %s:", color.name().toLowerCase()));
            player.sendMessage(Msg.mm("             Alive: %s", team.isAlive()));
            player.sendMessage(Msg.mm("             Has bed: %s", team.hasBed()));
            player.sendMessage(Msg.mm("             Players:"));
            for (BedwarsPlayer teamPlayer : team.getPlayers()) {
                player.sendMessage(Msg.mm("                %s:", teamPlayer.getUsername()));
                player.sendMessage(Msg.mm("                 Is Alive: %s", teamPlayer.isAlive()));
                player.sendMessage(Msg.mm("                 Is Respawning: %s", teamPlayer.isRespawning()));
                player.sendMessage(Msg.mm("                 Armor Level: %s",
                    teamPlayer.getArmorLevel().name().toLowerCase()));
                player.sendMessage(Msg.mm("                 Axe Level: %s",
                    teamPlayer.getAxeLevel().name().toLowerCase()));
                player.sendMessage(Msg.mm("                 Pickaxe Level: %s",
                    teamPlayer.getPickaxeLevel().name().toLowerCase()));
                player.sendMessage(Msg.mm("                 Has Shears: %s", teamPlayer.hasShears()));
            }
        });
    }
}

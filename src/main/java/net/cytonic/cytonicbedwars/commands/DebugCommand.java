package net.cytonic.cytonicbedwars.commands;

import net.cytonic.cytonicbedwars.data.enums.GameState;
import net.cytonic.cytonicbedwars.menu.itemShop.BlocksShopMenu;
import net.cytonic.cytonicbedwars.runnables.WaitingRunnable;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.commands.CommandUtils;
import net.cytonic.cytosis.logging.Logger;
import net.cytonic.cytosis.player.CytosisPlayer;
import net.cytonic.cytosis.utils.Msg;
import net.hollowcube.polar.*;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.minestom.server.coordinate.Pos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class DebugCommand extends Command {

    public DebugCommand() {
        super("debug");

        setCondition(CommandUtils.IS_ADMIN);
        setDefaultExecutor((sender, context) -> sender.sendMessage(Msg.whoops("You must specify a command!")));

        var debugArgument = ArgumentType.Word("debug").from("start", "forceStart", "end", "cleanup", "listteams", "freeze", "f", "itemshop", "convertWorld", "addWorld");
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
            suggestion.addEntry(new SuggestionEntry("convertWorld", Msg.mm("Coverts the world to a polar world!")));
            suggestion.addEntry(new SuggestionEntry("addWorld", Msg.mm("Adds a world to the database!")));
        });

        var worldNameArgument = ArgumentType.Word("worldName");
        var worldTypeArgument = ArgumentType.Word("worldType");
        var worldPathArgument = ArgumentType.Word("worldPath");
        var worldSpawnxArgument = ArgumentType.Integer("worldSpawnx");
        var worldSpawnyArgument = ArgumentType.Integer("worldSpawny");
        var worldSpawnzArgument = ArgumentType.Integer("worldSpawnz");

        addSyntax((sender, context) -> {
            if (sender instanceof CytosisPlayer player) {
                String command = context.get(debugArgument);

                switch (command.toLowerCase()) {
                    case "start" -> {
                        if (CytonicBedWars.getGameManager().STARTED) {
                            player.sendMessage(Msg.mm("The game has already been started! Use '/debug stop' to end it!", NamedTextColor.RED));
                        }
                        CytonicBedWars.getGameManager().setGameState(GameState.STARTING);
                        CytonicBedWars.getGameManager().setWaitingRunnable(new WaitingRunnable(7));
                        CytonicBedWars.getGameManager().getWaitingRunnable().run();
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

        addSyntax((sender, context) -> {
            if (sender instanceof CytosisPlayer player) {
                String command = context.get(debugArgument);
                if (command.equalsIgnoreCase("convertWorld")) {
                    String worldPath = context.get(worldPathArgument);
                    try {
                        Path path = Path.of(worldPath);
                        PolarWorld polarWorld = AnvilPolar.anvilToPolar(path);
                        new File("worlds").mkdir();
                        File file = new File("worlds/world.polar");
                        file.createNewFile();
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        fileOutputStream.write(PolarWriter.write(polarWorld));
                        fileOutputStream.close();
                        player.sendMessage(Msg.success("World converted!\nyou can find it in the worlds folder!"));
                    } catch (IOException e) {
                        Logger.error("error", e);
                        player.sendMessage(Msg.serverError("An error occurred!"));
                    }
                }
            }
        }, debugArgument, worldPathArgument);

        addSyntax((sender, context) -> {
            if (sender instanceof CytosisPlayer player) {
                String command = context.get(debugArgument);
                if (command.equalsIgnoreCase("addWorld")) {
                    String worldPath = context.get(worldPathArgument);
                    String worldName = context.get(worldNameArgument);
                    String worldType = context.get(worldTypeArgument);
                    Pos pos = new Pos(context.get(worldSpawnxArgument), context.get(worldSpawnyArgument), context.get(worldSpawnzArgument));
                    try {
                        File path = new File(worldPath);
                        FileInputStream inputStream = new FileInputStream(path);
                        PolarWorld world = PolarReader.read(inputStream.readAllBytes());
                        inputStream.close();
                        Cytosis.getDatabaseManager().getMysqlDatabase().addWorld(worldName, worldType, world, pos, UUID.randomUUID());
                        player.sendMessage(Msg.success("World with name: " + worldName + " and type: " + worldType + " has been added to the database!"));
                    } catch (Exception e) {
                        Logger.error("error", e);
                        player.sendMessage(Msg.serverError("An error occurred!"));
                    }
                }
            }
        }, debugArgument, worldNameArgument, worldTypeArgument, worldPathArgument, worldSpawnxArgument, worldSpawnyArgument, worldSpawnzArgument);
    }
}

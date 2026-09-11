package net.cytonic.bedwars.commands;

import java.util.UUID;

import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.minecraft.ArgumentUUID;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;

import net.cytonic.bedwars.player.BedwarsPlayer;
import net.cytonic.bedwars.server.BedwarsServer;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.commands.utils.CommandUtils;
import net.cytonic.cytosis.commands.utils.CytosisCommand;
import net.cytonic.cytosis.utils.Msg;

public class SwitchGameCommand extends CytosisCommand {

    public SwitchGameCommand() {
        super("switchgame");

        setCondition(CommandUtils.IS_ADMIN);

        setDefaultExecutor((sender, _) -> sender.sendMessage(Msg.whoops("Usage: /switchgame <game id>")));

        ArgumentUUID gameArg = ArgumentType.UUID("game");
        gameArg.setSuggestionCallback((_, _, suggestion) -> {
            BedwarsServer server = Cytosis.getServer();
            server.games().forEach((id, game) ->
                suggestion.addEntry(new SuggestionEntry(id.toString(),
                    Msg.mm("Map: %s", game.getMap().name().toLowerCase()))));
        });

        addSyntax((sender, context) -> {
            if (!(sender instanceof BedwarsPlayer player)) return;
            BedwarsServer server = Cytosis.getServer();
            UUID game = context.get(gameArg);
            if (!server.games().containsKey(game)) {
                player.sendMessage(Msg.whoops("The game with id '%s' does not exist!", game));
                return;
            }
            server.swapGame(player, server.getGame(game));
        }, gameArg);
    }
}

package net.cytonic.cytonicbedwars.server.chat;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import net.cytonic.cytonicbedwars.data.objects.Team;
import net.cytonic.cytonicbedwars.game.Game;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytosis.server.chat.ChatService;
import net.cytonic.cytosis.utils.Msg;

public class ChatServiceImpl implements ChatService<BedwarsPlayer> {

    @Override
    public void handleAllChat(BedwarsPlayer player, String originalMessage) {
        Team team = player.getBedwarsTeam();
        Game game = player.getGame();
        if (team == null || game == null) return;

        Component message = team.getColor().getName()
            .appendSpace()
            .append(player.formattedName())
            .append(Msg.white(":"))
            .appendSpace()
            .append(Component.text(originalMessage).color(NamedTextColor.WHITE));

        game.getWorld().sendMessage(message);
    }
}

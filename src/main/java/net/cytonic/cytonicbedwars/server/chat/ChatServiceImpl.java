package net.cytonic.cytonicbedwars.server.chat;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytosis.server.chat.ChatService;
import net.cytonic.cytosis.utils.Msg;

public class ChatServiceImpl implements ChatService<BedwarsPlayer> {

    @Override
    public void handleAllChat(BedwarsPlayer player, String originalMessage) {
        Component message = player.getBedwarsFormattedName()
            .append(Msg.white(":"))
            .appendSpace()
            .append(Component.text(originalMessage).color(NamedTextColor.WHITE));

        player.getGame().getWorld().sendMessage(message);
    }
}

package net.cytonic.cytonicbedwars.data.objects;

import net.cytonic.cytosis.player.CytosisPlayer;
import net.cytonic.cytosis.playerlist.Column;
import net.cytonic.cytosis.playerlist.PlayerlistCreator;
import net.cytonic.cytosis.utils.Msg;
import net.kyori.adventure.text.Component;

import java.util.List;

public class PlayerList implements PlayerlistCreator {
    @Override
    public List<Column> createColumns(CytosisPlayer player) {
        return List.of();
    }

    @Override
    public Component header(CytosisPlayer player) {
        return Msg.mm("<yellow><b>Cytonic Bedwars");
    }

    @Override
    public Component footer(CytosisPlayer player) {
        return Msg.mm("<yellow><b>Playing on <red>mc.cytonic.net");
    }

    @Override
    public int getColumnCount() {
        return 0;
    }
}

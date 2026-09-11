package net.cytonic.bedwars.npcs;

import java.util.List;

import net.kyori.adventure.text.Component;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.instance.Instance;

import net.cytonic.cytosis.entity.npc.NPC;
import net.cytonic.cytosis.entity.npc.configuration.NPCConfiguration;
import net.cytonic.cytosis.events.npcs.NPCInteractEvent;
import net.cytonic.cytosis.player.CytosisPlayer;
import net.cytonic.cytosis.utils.Msg;
import net.cytonic.protocol.utils.ExcludeFromIndex;

@ExcludeFromIndex
public class TeamShopNPC extends NPC {

    public TeamShopNPC(Pos pos, Instance instance, PlayerSkin skin) {
        super(new NPCConfiguration() {
            @Override
            public List<Component> holograms(CytosisPlayer player) {
                return List.of(Msg.red("Coming soon"));
            }

            @Override
            public Pos position(CytosisPlayer player) {
                return pos;
            }

            @Override
            public Instance instance() {
                return instance;
            }

            @Override
            public PlayerSkin skin(CytosisPlayer player) {
                return skin;
            }

            @Override
            public boolean looking(CytosisPlayer player) {
                return true;
            }
        });
    }

    @Override
    public void onClick(NPCInteractEvent event) {
        event.player().sendMessage(Msg.red("Coming soon"));
    }
}

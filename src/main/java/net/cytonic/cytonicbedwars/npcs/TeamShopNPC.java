package net.cytonic.cytonicbedwars.npcs;

import java.util.List;

import net.kyori.adventure.text.Component;
import net.minestom.server.coordinate.Pos;

import net.cytonic.cytonicbedwars.Config;
import net.cytonic.cytosis.entity.npc.NPC;
import net.cytonic.cytosis.entity.npc.configuration.NPCConfiguration;
import net.cytonic.cytosis.events.npcs.NPCInteractEvent;
import net.cytonic.cytosis.player.CytosisPlayer;
import net.cytonic.cytosis.utils.Msg;
import net.cytonic.protocol.ExcludeFromClassGraph;

@ExcludeFromClassGraph
public class TeamShopNPC extends NPC {

    public TeamShopNPC(Pos pos) {
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
            public String texture(CytosisPlayer player) {
                return Config.teamShopSkin.textures();
            }

            @Override
            public String signature(CytosisPlayer player) {
                return Config.teamShopSkin.signature();
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

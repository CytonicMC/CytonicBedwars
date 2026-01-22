package net.cytonic.cytonicbedwars.npcs;

import java.util.List;

import me.devnatan.inventoryframework.ViewFrame;
import net.kyori.adventure.text.Component;
import net.minestom.server.coordinate.Pos;

import net.cytonic.cytonicbedwars.Config;
import net.cytonic.cytonicbedwars.menu.itemShop.BlocksShopMenu;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.entity.npc.NPC;
import net.cytonic.cytosis.entity.npc.configuration.NPCConfiguration;
import net.cytonic.cytosis.events.npcs.NPCInteractEvent;
import net.cytonic.cytosis.player.CytosisPlayer;
import net.cytonic.cytosis.utils.Msg;
import net.cytonic.protocol.utils.ExcludeFromClassGraph;

@ExcludeFromClassGraph
public class ItemShopNPC extends NPC {

    public ItemShopNPC(Pos pos) {
        super(new NPCConfiguration() {
            @Override
            public List<Component> holograms(CytosisPlayer player) {
                return List.of(Msg.gold("<b>ITEM SHOP"));
            }

            @Override
            public Pos position(CytosisPlayer player) {
                return pos;
            }

            @Override
            public String texture(CytosisPlayer player) {
                return Config.itemShopSkin.textures();
            }

            @Override
            public String signature(CytosisPlayer player) {
                return Config.itemShopSkin.signature();
            }

            @Override
            public boolean looking(CytosisPlayer player) {
                return true;
            }
        });
    }

    @Override
    public void onClick(NPCInteractEvent event) {
        Cytosis.CONTEXT.getComponent(ViewFrame.class).open(BlocksShopMenu.class, event.player());
    }
}

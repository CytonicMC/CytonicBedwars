package net.cytonic.cytonicbedwars.npcs;

import java.util.List;

import me.devnatan.inventoryframework.ViewFrame;
import net.kyori.adventure.text.Component;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.instance.Instance;
import org.jetbrains.annotations.Nullable;

import net.cytonic.cytonicbedwars.menu.ItemShopMenu;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.entity.npc.NPC;
import net.cytonic.cytosis.entity.npc.configuration.NPCConfiguration;
import net.cytonic.cytosis.events.npcs.NPCInteractEvent;
import net.cytonic.cytosis.player.CytosisPlayer;
import net.cytonic.cytosis.utils.Msg;
import net.cytonic.protocol.utils.ExcludeFromIndex;

@ExcludeFromIndex
public class ItemShopNPC extends NPC {

    public ItemShopNPC(Pos pos, Instance instance) {
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
            public Instance instance() {
                return instance;
            }

            @Override
            public @Nullable PlayerSkin skin(CytosisPlayer player) {
                //todo figure out skin
                return null;
            }

            @Override
            public boolean looking(CytosisPlayer player) {
                return true;
            }
        });
    }

    @Override
    public void onClick(NPCInteractEvent event) {
        Cytosis.CONTEXT.getComponent(ViewFrame.class).open(ItemShopMenu.class, event.player());
    }
}

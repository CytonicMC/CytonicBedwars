package net.cytonic.cytonicbedwars.itemAbility;

import me.devnatan.inventoryframework.ViewFrame;
import net.minestom.server.event.player.PlayerUseItemEvent;

import net.cytonic.cytonicbedwars.game.BedwarsWorld;
import net.cytonic.cytonicbedwars.menu.spectators.SpectatorSelectMenu;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytosis.Cytosis;

public class SpectatorCompassItemAbility implements ItemAbility {

    @Override
    public void use(BedwarsPlayer player, BedwarsWorld world, PlayerUseItemEvent event) {
        Cytosis.get(ViewFrame.class).open(SpectatorSelectMenu.class, player);
    }
}

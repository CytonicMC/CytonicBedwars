package net.cytonic.bedwars.server.sideboard;

import org.jspecify.annotations.Nullable;

import net.cytonic.bedwars.player.BedwarsPlayer;
import net.cytonic.cytosis.server.sideboard.SideboardService;
import net.cytonic.cytosis.sideboard.SideboardCreator;

public class SideboardServiceImpl implements SideboardService<BedwarsPlayer> {

    @Override
    public boolean supportsSideboard() {
        return true;
    }

    @Override
    public @Nullable SideboardCreator<BedwarsPlayer> sideboardCreator() {
        return new SideboardImpl();
    }
}

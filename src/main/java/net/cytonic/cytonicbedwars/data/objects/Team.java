package net.cytonic.cytonicbedwars.data.objects;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.block.Block;

import java.util.List;

@Getter
@Setter
public class Team {
    @Getter(AccessLevel.NONE)
    private boolean bed = false;
    private net.minestom.server.scoreboard.Team mcTeam;
    private List<BedwarsPlayer> players;
    private boolean alive = true;
    private final String displayName;
    private final String name;
    private final String prefix;
    private final NamedTextColor color;
    private final Pos spawnLocation;
    private final Pos generatorLocation;
    private final Pos itemShopLocation;
    private final Pos teamShopLocation;
    private final Pos chestLocation;
    private final Pos bedLocation;
    private final Block bedType;
    private final Block woolType;
    private final Block glassType;
    private final Block terracottaType;

    public Team(String displayName, String prefix, NamedTextColor color, Pos spawnLocation, Pos generatorLocation,
                Pos itemShopLocation, Pos teamShopLocation, Pos chestLocation, Pos bedLocation, Block bedType, Block woolType, Block glassType, Block terracottaType) {
        this.displayName = displayName;
        this.name = displayName.split(" ")[0];
        this.prefix = prefix;
        this.color = color;
        this.spawnLocation = spawnLocation;
        this.generatorLocation = generatorLocation;
        this.itemShopLocation = itemShopLocation;
        this.teamShopLocation = teamShopLocation;
        this.chestLocation = chestLocation;
        this.bedLocation = bedLocation;
        this.bedType = bedType;
        this.woolType = woolType;
        this.glassType = glassType;
        this.terracottaType = terracottaType;
    }

    public boolean hasBed() {
        return bed;
    }
}

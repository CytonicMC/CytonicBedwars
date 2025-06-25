package net.cytonic.cytonicbedwars.data.objects;

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
    private boolean bed = false;
    private net.minestom.server.scoreboard.Team mcTeam;
    private List<BedwarsPlayer> players;
    private boolean alive = true;
    private String displayName;
    private String prefix;
    private NamedTextColor color;
    private Pos spawnLocation;
    private Pos generatorLocation;
    private Pos itemShopLocation;
    private Pos teamShopLocation;
    private Pos chestLocation;
    private Pos bedLocation;
    private Block bedType;
    private Block woolType;
    private Block glassType;
    private Block terracottaType;


    public Team(String displayName, String prefix, NamedTextColor color, Pos spawnLocation, Pos generatorLocation, Pos itemShopLocation, Pos teamShopLocation, Pos chestLocation, Pos bedLocation, Block bedType, Block woolType, Block glassType, Block terracottaType) {
        this.displayName = displayName;
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
}

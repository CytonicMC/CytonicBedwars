package net.cytonic.cytonicbedwars.data.objects;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.block.Block;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;

import net.cytonic.cytonicbedwars.config.TeamColor;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;

@Getter
@Setter
public class Team {

    private final TeamColor color;
    private final Component prefix;
    private final Pos spawnPos;
    private final Pos itemShopPos;
    private final Pos teamShopPos;
    private final BlockVec teamChestPos;
    private final Pos generatorPos;
    private final Block bedType;
    private final Block woolType;
    private final Block glassType;
    private final Block terracottaType;
    @Getter(AccessLevel.NONE)
    private boolean bed = false;
    private net.minestom.server.scoreboard.Team mcTeam;
    private List<BedwarsPlayer> players;
    private boolean alive = true;
    private Inventory teamChest = new Inventory(InventoryType.CHEST_3_ROW, "Team Chest");

    public Team(
        TeamColor color,
        Pos spawnPos,
        Pos itemShopPos,
        Pos teamShopPos,
        BlockVec teamChestPos,
        Pos generatorPos
    ) {
        this.color = color;
        this.prefix = color.getPrefix();
        this.spawnPos = spawnPos;
        this.itemShopPos = itemShopPos;
        this.teamShopPos = teamShopPos;
        this.teamChestPos = teamChestPos;
        this.generatorPos = generatorPos;
        this.bedType = color.getBedBlock();
        this.woolType = color.getWoolBlock();
        this.glassType = color.getGlassBlock();
        this.terracottaType = color.getTerracottaType();
    }

    public boolean hasBed() {
        return bed;
    }

    public List<BedwarsPlayer> getAlivePlayers() {
        return players.stream().filter(BedwarsPlayer::isAlive).toList();
    }
}

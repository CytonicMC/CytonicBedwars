package net.cytonic.bedwars.game;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.inventory.InventoryCloseEvent;
import net.minestom.server.instance.block.Block;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.Material;
import net.minestom.server.network.packet.server.play.BlockActionPacket;
import net.minestom.server.network.packet.server.play.TeamsPacket.CollisionRule;
import net.minestom.server.sound.SoundEvent;

import net.cytonic.bedwars.config.BedwarsMapConfig;
import net.cytonic.bedwars.config.TeamColor;
import net.cytonic.bedwars.data.enums.GeneratorType;
import net.cytonic.bedwars.npcs.ItemShopNPC;
import net.cytonic.bedwars.npcs.TeamShopNPC;
import net.cytonic.bedwars.player.BedwarsPlayer;
import net.cytonic.cytosis.utils.Msg;

@Getter
@Setter
public class Team {

    private final TeamColor color;
    private final Component name;
    private final Component prefix;
    private final Pos spawnPos;
    private final Pos itemShopPos;
    private final Pos teamShopPos;
    private final BlockVec teamChestPos;
    private final Pos generatorPos;
    private final BlockVec bedPos;
    private final Block bedType;
    private final Material woolType;
    private final Material glassType;
    private final Material terracottaType;
    private final Generator ironGenerator;
    private final Generator goldGenerator;
    private ItemShopNPC itemShopNpc;
    private TeamShopNPC teamShopNpc;
    @Getter(AccessLevel.NONE)
    private boolean bed = true;
    private net.minestom.server.scoreboard.Team mcTeam;
    private List<BedwarsPlayer> players = new ArrayList<>();
    private boolean alive = true;
    private Inventory teamChest;

    public Team(
        TeamColor color,
        Pos spawnPos,
        Pos itemShopPos,
        Pos teamShopPos,
        BlockVec teamChestPos,
        Pos generatorPos,
        Duration ironGeneratorTime,
        int ironGeneratorLimit,
        Duration goldGeneratorTime,
        int goldGeneratorLimit,
        BlockVec bedPos
    ) {
        this.color = color;
        this.name = color.getName();
        this.prefix = color.getPrefix();
        this.spawnPos = spawnPos;
        this.itemShopPos = itemShopPos;
        this.teamShopPos = teamShopPos;
        this.teamChestPos = teamChestPos;
        this.generatorPos = generatorPos.add(0, 1.5, 0);
        this.bedPos = bedPos;
        this.bedType = color.getBedBlock();
        this.woolType = color.getWoolBlock();
        this.glassType = color.getGlassBlock();
        this.terracottaType = color.getTerracottaType();
        this.ironGenerator = new Generator(GeneratorType.IRON, generatorPos, ironGeneratorTime, ironGeneratorLimit);
        this.goldGenerator = new Generator(GeneratorType.GOLD, generatorPos, goldGeneratorTime, goldGeneratorLimit);
        this.mcTeam = MinecraftServer.getTeamManager().createBuilder(color.name().toLowerCase())
            .collisionRule(CollisionRule.PUSH_OTHER_TEAMS)
            .prefix(this.prefix.appendSpace())
            .seeInvisiblePlayers()
            .build();
        this.teamChest = new Inventory(InventoryType.CHEST_3_ROW, Msg.mm("Team Chest"));
        teamChest.eventNode()
            .addListener(EventListener.of(InventoryCloseEvent.class, event -> {
                event.getInstance()
                    .sendGroupedPacket(new BlockActionPacket(teamChestPos, (byte) 1, (byte) 0, Block.CHEST));

                event.getInstance().playSound(
                    Sound.sound(SoundEvent.BLOCK_CHEST_CLOSE, Sound.Source.MASTER, 0.5f,
                        new Random().nextFloat() * 0.1F + 0.9F), teamChestPos);
            }));
    }

    public void addPlayer(BedwarsPlayer player) {
        players.add(player);
        mcTeam.addMember(player.getUsername());
        player.UNSAFE_joinTeam(color);
    }

    public boolean hasBed() {
        return bed;
    }

    public List<BedwarsPlayer> getAlivePlayers() {
        return players.stream().filter(BedwarsPlayer::isAlive).toList();
    }

    public void start(BedwarsWorld world, BedwarsMapConfig config) {
        ironGenerator.start(world);
        goldGenerator.start(world);

        itemShopNpc = new ItemShopNPC(itemShopPos, world, config.itemShopSkin());
        itemShopNpc.register();
        teamShopNpc = new TeamShopNPC(teamShopPos, world, config.teamShopSkin());
        teamShopNpc.register();

        for (BedwarsPlayer player : players) {
            player.teleport(spawnPos);
            player.setGameMode(GameMode.SURVIVAL);

            player.applyItems();
        }
    }

    public void end() {
        if (!players.isEmpty()) {
            ironGenerator.stop();
            goldGenerator.stop();

            itemShopNpc.remove();
            teamShopNpc.remove();
        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Team team && team.color == color;
    }
}

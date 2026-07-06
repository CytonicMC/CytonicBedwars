package net.cytonic.cytonicbedwars.game;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import io.github.togar2.pvp.feature.CombatFeatureSet;
import io.github.togar2.pvp.feature.FeatureType;
import net.hollowcube.polar.PolarLoader;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.instance.ExplosionSupplier;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockFace;
import org.apache.commons.lang3.function.Consumers;

import net.cytonic.cytonicbedwars.FullbrightDimensionType;
import net.cytonic.cytonicbedwars.config.BedwarsMap;
import net.cytonic.cytonicbedwars.config.BedwarsMapConfig;
import net.cytonic.cytonicbedwars.config.BedwarsMapConfig.TeamConfig;
import net.cytonic.cytonicbedwars.server.BedwarsServer;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.world.AbstractWorld;

public class BedwarsWorld extends AbstractWorld {

    public BedwarsWorld(UUID uuid, BedwarsMap map) {
        super(uuid, FullbrightDimensionType.INSTANCE);
        setChunkLoader(new PolarLoader(map.getWorld()));
        MinecraftServer.getInstanceManager().registerInstance(this);
        if (Cytosis.isDev() && Cytosis.isStandalone()) {
//            spawnDebugMarkers(map.getConfig());
        }
        placeSpawnPlatform();

        ExplosionSupplier explosionSupplier = Cytosis.get(CombatFeatureSet.class).get(FeatureType.EXPLOSION)
            .getExplosionSupplier();
        setExplosionSupplier(explosionSupplier);
    }

    private void spawnDebugMarkers(BedwarsMapConfig config) {
        for (Pos pos : config.diamondGenerators().positions()) {
            new Entity(EntityType.ARMADILLO).setInstance(this, pos);
        }
        for (Pos pos : config.emeraldGenerators().positions()) {
            new Entity(EntityType.ALLAY).setInstance(this, pos);
        }

        for (TeamConfig team : config.teams()) {
            new Entity(EntityType.CHICKEN).setInstance(this, team.spawnPos());
            new Entity(EntityType.BAT).setInstance(this, team.itemShopPos());
            new Entity(EntityType.SALMON).setInstance(this, team.teamShopPos());
            new Entity(EntityType.COD).setInstance(this, team.teamChestPos().add(0, 1, 0));
            new Entity(EntityType.CAT).setInstance(this, team.generatorPos());
        }
    }

    public void placeSpawnPlatform() {
        CompletableFuture.allOf(
            loadChunk(0, 0),
            loadChunk(-1, 0),
            loadChunk(0, -1),
            loadChunk(-1, -1)).join();
        BedwarsServer.SPAWN_PLATFORM.createBatch().toAbsoluteBatch(0, 40, 0).apply(this, Consumers.nop());
    }

    public void removeSpawnPlatform() {
        CompletableFuture.allOf(
            loadChunk(0, 0),
            loadChunk(-1, 0),
            loadChunk(0, -1),
            loadChunk(-1, -1)).join();

        BedwarsServer.SPAWN_PLATFORM.forEachBlock((pos, _) ->
            setBlock(pos.add(0, 40, 0), Block.AIR));
    }

    public void breakBed(Team team) {
        BlockVec pos = team.getBedPos();
        Block block = getBlock(pos);
        if (block.key().equals(team.getBedType().key())) {
            setBlock(pos, Block.AIR);
            BlockFace facing = BlockFace.valueOf(Objects.requireNonNull(block.getProperty("facing")).toUpperCase());
            if (Objects.requireNonNull(block.getProperty("part")).equals("head")) {
                facing = facing.getOppositeFace();
            }
            setBlock(pos.relative(facing), Block.AIR);
        }
    }
}


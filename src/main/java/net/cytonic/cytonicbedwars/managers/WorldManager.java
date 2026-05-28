package net.cytonic.cytonicbedwars.managers;

import net.hollowcube.polar.PolarLoader;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockFace;
import net.minestom.server.world.clock.WorldClock;

import net.cytonic.cytonicbedwars.BedwarsConfig;
import net.cytonic.cytonicbedwars.data.objects.Team;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.bootstrap.annotations.CytosisComponent;
import net.cytonic.cytosis.data.EnvironmentDatabase;
import net.cytonic.cytosis.logging.Logger;

@CytosisComponent(dependsOn = InstanceContainer.class)
public class WorldManager {

    public void breakBed(Team team) {
//        InstanceContainer instance = Cytosis.CONTEXT.getComponent(InstanceContainer.class);
//        Block block = instance.getBlock(team.getBedLocation());
//        BlockVec blockPos = new BlockVec(team.getBedLocation());
//        if (block.key().equals(team.getBedType().key())) {
//            instance.setBlock(blockPos, Block.AIR);
//            BlockFace facing = BlockFace.valueOf(block.getProperty("facing").toUpperCase());
//            if (block.getProperty("part").equals("head")) {
//                facing = facing.getOppositeFace();
//            }
//            instance.setBlock(blockPos.relative(facing), Block.AIR);
//        }
    }

    public void loadWorld() {
        try {
            //fixme
//            var dimKey = MinecraftServer.getDimensionTypeRegistry().register("bedwars:" + Config.worldName, DimensionType.builder().ambientLight(100).build());
            Cytosis.CONTEXT.registerComponent(MinecraftServer.getInstanceManager().createInstanceContainer());
//            Cytosis.CONTEXT.getComponent(EnvironmentDatabase.class)
//                .getWorld(BedwarsConfig.worldName, "bedwars_map_" + BedwarsConfig.mode.toLowerCase())
//                .whenComplete((world, throwable) -> {
//                    if (throwable != null) {
//                        Logger.error("error", throwable);
//                    } else {
//                        Cytosis.CONTEXT.registerComponent(
//                            MinecraftServer.getInstanceManager().createInstanceContainer());
//                        InstanceContainer instance = Cytosis.CONTEXT.getComponent(InstanceContainer.class);
//                        instance.setChunkLoader(new PolarLoader(world));
//                        instance.clock(WorldClock.OVERWORLD).pause();
//                        instance.setTime(6000);
//                        Logger.info("loading spawn platform");
//                        createSpawnPlatform();
//                    }
//                });

        } catch (Exception e) {
            Logger.error("error", e);
        }
    }

    public void redoWorld() {
        loadWorld();
//        createSpawnPlatform();
    }
}
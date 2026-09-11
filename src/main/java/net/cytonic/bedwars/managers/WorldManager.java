package net.cytonic.bedwars.managers;

import net.minestom.server.MinecraftServer;

import net.cytonic.bedwars.game.Team;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.logging.Logger;

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
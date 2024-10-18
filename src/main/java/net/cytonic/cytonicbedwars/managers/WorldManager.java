package net.cytonic.cytonicbedwars.managers;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.CytonicBedwarsSettings;
import net.cytonic.cytosis.Cytosis;
import net.hollowcube.polar.PolarLoader;
import net.hollowcube.polar.PolarWorld;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.block.Block;

import java.util.concurrent.ExecutionException;

@NoArgsConstructor
public class WorldManager {

    public void loadWorld() {
        try {
            PolarWorld world = Cytosis.getDatabaseManager().getMysqlDatabase().getWorld(CytonicBedwarsSettings.worldName, STR."bedwars_map_\{CytonicBedwarsSettings.mode}").get();
            Cytosis.getDefaultInstance().setChunkLoader(new PolarLoader(world));
            Cytosis.getDefaultInstance().setChunkSupplier(LightingChunk::new);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void createSpawnPlatform() {
        Pos loc = CytonicBedwarsSettings.spawnPlatformCenter;
        int x = (int) loc.x();
        int y = (int) loc.y();
        int z = (int) loc.z();
        Cytosis.getDefaultInstance().setBlock(x, y, z, Block.BEACON);
        Cytosis.getDefaultInstance().setBlock(x + 1, y, z, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 2, y, z, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 3, y, z, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 4, y, z, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 5, y, z, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 1, y, z, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 2, y, z, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 3, y, z, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 4, y, z, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 5, y, z, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x, y, z + 1, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 1, y, z + 1, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 2, y, z + 1, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 3, y, z + 1, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 4, y, z + 1, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 5, y, z + 1, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 1, y, z + 1, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 2, y, z + 1, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 3, y, z + 1, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 4, y, z + 1, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 5, y, z + 1, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x, y, z - 1, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 1, y, z - 1, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 2, y, z - 1, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 3, y, z - 1, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 4, y, z - 1, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 5, y, z - 1, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 1, y, z - 1, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 2, y, z - 1, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 3, y, z - 1, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 4, y, z - 1, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 5, y, z - 1, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x, y, z - 2, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 1, y, z - 2, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 2, y, z - 2, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 3, y, z - 2, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 4, y, z - 2, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 1, y, z - 2, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 2, y, z - 2, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 3, y, z - 2, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 4, y, z - 2, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x, y, z - 3, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 1, y, z - 3, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 2, y, z - 3, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 3, y, z - 3, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 4, y, z - 3, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 1, y, z - 3, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 2, y, z - 3, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 3, y, z - 3, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 4, y, z - 3, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x, y, z + 2, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 1, y, z + 2, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 2, y, z + 2, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 3, y, z + 2, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 4, y, z + 2, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 1, y, z + 2, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 2, y, z + 2, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 3, y, z + 2, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 4, y, z + 2, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x, y, z + 3, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 1, y, z + 3, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 2, y, z + 3, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 3, y, z + 3, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 4, y, z + 3, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 1, y, z + 3, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 2, y, z + 3, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 3, y, z + 3, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 4, y, z + 3, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x, y, z - 4, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 1, y, z - 4, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 2, y, z - 4, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 3, y, z - 4, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 1, y, z - 4, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 2, y, z - 4, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 3, y, z - 4, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x, y, z + 4, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 1, y, z + 4, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 2, y, z + 4, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 3, y, z + 4, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 1, y, z + 4, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 2, y, z + 4, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 3, y, z + 4, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x, y, z - 5, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 1, y, z - 5, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 1, y, z - 5, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x, y, z + 5, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 1, y, z + 5, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x - 1, y, z + 5, Block.GRAY_STAINED_GLASS);
        Cytosis.getDefaultInstance().setBlock(x + 1, y + 1, z - 5, Block.WHITE_STAINED_GLASS_PANE); // WALLS V LAYER 1
        Cytosis.getDefaultInstance().setBlock(x + 1, y + 1, z - 4, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x + 2, y + 1, z - 4, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x + 3, y + 1, z - 4, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x + 3, y + 1, z - 3, Block.COBBLED_DEEPSLATE_WALL);
        Cytosis.getDefaultInstance().setBlock(x + 4, y + 1, z - 3, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x + 4, y + 1, z - 2, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x + 4, y + 1, z - 1, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x + 5, y + 1, z - 1, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x - 1, y + 1, z - 5, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x - 1, y + 1, z - 4, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x - 2, y + 1, z - 4, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x - 3, y + 1, z - 4, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x - 3, y + 1, z - 3, Block.COBBLED_DEEPSLATE_WALL);
        Cytosis.getDefaultInstance().setBlock(x - 4, y + 1, z - 3, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x - 4, y + 1, z - 2, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x - 4, y + 1, z - 1, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x - 5, y + 1, z - 1, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x + 1, y + 1, z + 5, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x + 1, y + 1, z + 4, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x + 2, y + 1, z + 4, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x + 3, y + 1, z + 4, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x + 3, y + 1, z + 3, Block.COBBLED_DEEPSLATE_WALL);
        Cytosis.getDefaultInstance().setBlock(x + 4, y + 1, z + 3, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x + 4, y + 1, z + 2, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x + 4, y + 1, z + 1, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x + 5, y + 1, z + 1, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x - 1, y + 1, z + 5, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x - 1, y + 1, z + 4, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x - 2, y + 1, z + 4, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x - 3, y + 1, z + 4, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x - 3, y + 1, z + 3, Block.COBBLED_DEEPSLATE_WALL);
        Cytosis.getDefaultInstance().setBlock(x - 4, y + 1, z + 3, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x - 4, y + 1, z + 2, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x - 4, y + 1, z + 1, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x - 5, y + 1, z + 1, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x - 5, y + 1, z, Block.WHITE_STAINED_GLASS_PANE, true);
        Cytosis.getDefaultInstance().setBlock(x + 5, y + 1, z, Block.WHITE_STAINED_GLASS_PANE, true);
        Cytosis.getDefaultInstance().setBlock(x, y + 1, z + 5, Block.WHITE_STAINED_GLASS_PANE, true);
        Cytosis.getDefaultInstance().setBlock(x, y + 1, z - 5, Block.WHITE_STAINED_GLASS_PANE, true);
        Cytosis.getDefaultInstance().setBlock(x + 1, y + 2, z - 5, Block.WHITE_STAINED_GLASS_PANE); // WALLS LAYER 2 V
        Cytosis.getDefaultInstance().setBlock(x + 1, y + 2, z - 4, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x + 2, y + 2, z - 4, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x + 3, y + 2, z - 4, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x + 3, y + 2, z - 3, Block.COBBLED_DEEPSLATE_WALL);
        Cytosis.getDefaultInstance().setBlock(x + 4, y + 2, z - 3, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x + 4, y + 2, z - 2, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x + 4, y + 2, z - 1, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x + 5, y + 2, z - 1, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x - 1, y + 2, z - 5, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x - 1, y + 2, z - 4, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x - 2, y + 2, z - 4, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x - 3, y + 2, z - 4, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x - 3, y + 2, z - 3, Block.COBBLED_DEEPSLATE_WALL);
        Cytosis.getDefaultInstance().setBlock(x - 4, y + 2, z - 3, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x - 4, y + 2, z - 2, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x - 4, y + 2, z - 1, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x - 5, y + 2, z - 1, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x + 1, y + 2, z + 5, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x + 1, y + 2, z + 4, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x + 2, y + 2, z + 4, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x + 3, y + 2, z + 4, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x + 3, y + 2, z + 3, Block.COBBLED_DEEPSLATE_WALL);
        Cytosis.getDefaultInstance().setBlock(x + 4, y + 2, z + 3, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x + 4, y + 2, z + 2, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x + 4, y + 2, z + 1, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x + 5, y + 2, z + 1, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x - 1, y + 2, z + 5, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x - 1, y + 2, z + 4, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x - 2, y + 2, z + 4, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x - 3, y + 2, z + 4, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x - 3, y + 2, z + 3, Block.COBBLED_DEEPSLATE_WALL);
        Cytosis.getDefaultInstance().setBlock(x - 4, y + 2, z + 3, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x - 4, y + 2, z + 2, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x - 4, y + 2, z + 1, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x - 5, y + 2, z + 1, Block.WHITE_STAINED_GLASS_PANE);
        Cytosis.getDefaultInstance().setBlock(x - 5, y + 2, z, Block.WHITE_STAINED_GLASS_PANE, true);
        Cytosis.getDefaultInstance().setBlock(x + 5, y + 2, z, Block.WHITE_STAINED_GLASS_PANE, true);
        Cytosis.getDefaultInstance().setBlock(x, y + 2, z + 5, Block.WHITE_STAINED_GLASS_PANE, true);
        Cytosis.getDefaultInstance().setBlock(x, y + 2, z - 5, Block.WHITE_STAINED_GLASS_PANE, true);
        Cytosis.getDefaultInstance().setBlock(x - 3, y + 3, z + 2, Block.DARK_PRISMARINE_SLAB);// ROOF V
        Cytosis.getDefaultInstance().setBlock(x - 3, y + 3, z + 1, Block.DARK_PRISMARINE_SLAB);
        Cytosis.getDefaultInstance().setBlock(x - 2, y + 3, z + 3, Block.DARK_PRISMARINE_SLAB);
        Cytosis.getDefaultInstance().setBlock(x - 1, y + 3, z + 3, Block.DARK_PRISMARINE_SLAB);
        Cytosis.getDefaultInstance().setBlock(x - 3, y + 3, z - 2, Block.DARK_PRISMARINE_SLAB);
        Cytosis.getDefaultInstance().setBlock(x - 3, y + 3, z - 1, Block.DARK_PRISMARINE_SLAB);
        Cytosis.getDefaultInstance().setBlock(x - 2, y + 3, z - 3, Block.DARK_PRISMARINE_SLAB);
        Cytosis.getDefaultInstance().setBlock(x - 1, y + 3, z - 3, Block.DARK_PRISMARINE_SLAB);
        Cytosis.getDefaultInstance().setBlock(x + 3, y + 3, z + 2, Block.DARK_PRISMARINE_SLAB);
        Cytosis.getDefaultInstance().setBlock(x + 3, y + 3, z + 1, Block.DARK_PRISMARINE_SLAB);
        Cytosis.getDefaultInstance().setBlock(x + 2, y + 3, z + 3, Block.DARK_PRISMARINE_SLAB);
        Cytosis.getDefaultInstance().setBlock(x + 1, y + 3, z + 3, Block.DARK_PRISMARINE_SLAB);
        Cytosis.getDefaultInstance().setBlock(x + 3, y + 3, z - 2, Block.DARK_PRISMARINE_SLAB);
        Cytosis.getDefaultInstance().setBlock(x + 3, y + 3, z - 1, Block.DARK_PRISMARINE_SLAB);
        Cytosis.getDefaultInstance().setBlock(x + 2, y + 3, z - 3, Block.DARK_PRISMARINE_SLAB);
        Cytosis.getDefaultInstance().setBlock(x + 1, y + 3, z - 3, Block.DARK_PRISMARINE_SLAB);
        Cytosis.getDefaultInstance().setBlock(x + 4, y + 3, z, Block.DARK_PRISMARINE_SLAB);
        Cytosis.getDefaultInstance().setBlock(x - 4, y + 3, z, Block.DARK_PRISMARINE_SLAB);
        Cytosis.getDefaultInstance().setBlock(x, y + 3, z + 4, Block.DARK_PRISMARINE_SLAB);
        Cytosis.getDefaultInstance().setBlock(x, y + 3, z - 4, Block.DARK_PRISMARINE_SLAB);
    }

    //fixme use a for loop or recusion to simplify
    public void removeSpawnPlatform() {
        Pos loc = CytonicBedwarsSettings.spawnPlatformCenter;
        int x = (int) loc.x();
        int y = (int) loc.y();
        int z = (int) loc.z();
        Cytosis.getDefaultInstance().setBlock(x, y, z, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 1, y, z, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 2, y, z, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 3, y, z, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 4, y, z, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 5, y, z, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 1, y, z, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 2, y, z, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 3, y, z, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 4, y, z, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 5, y, z, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x, y, z + 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 1, y, z + 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 2, y, z + 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 3, y, z + 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 4, y, z + 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 5, y, z + 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 1, y, z + 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 2, y, z + 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 3, y, z + 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 4, y, z + 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 5, y, z + 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x, y, z - 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 1, y, z - 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 2, y, z - 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 3, y, z - 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 4, y, z - 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 5, y, z - 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 1, y, z - 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 2, y, z - 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 3, y, z - 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 4, y, z - 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 5, y, z - 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x, y, z - 2, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 1, y, z - 2, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 2, y, z - 2, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 3, y, z - 2, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 4, y, z - 2, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 1, y, z - 2, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 2, y, z - 2, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 3, y, z - 2, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 4, y, z - 2, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x, y, z - 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 1, y, z - 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 2, y, z - 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 3, y, z - 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 4, y, z - 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 1, y, z - 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 2, y, z - 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 3, y, z - 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 4, y, z - 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x, y, z + 2, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 1, y, z + 2, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 2, y, z + 2, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 3, y, z + 2, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 4, y, z + 2, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 1, y, z + 2, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 2, y, z + 2, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 3, y, z + 2, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 4, y, z + 2, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x, y, z + 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 1, y, z + 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 2, y, z + 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 3, y, z + 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 4, y, z + 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 1, y, z + 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 2, y, z + 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 3, y, z + 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 4, y, z + 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x, y, z - 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 1, y, z - 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 2, y, z - 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 3, y, z - 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 1, y, z - 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 2, y, z - 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 3, y, z - 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x, y, z + 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 1, y, z + 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 2, y, z + 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 3, y, z + 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 1, y, z + 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 2, y, z + 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 3, y, z + 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x, y, z - 5, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 1, y, z - 5, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 1, y, z - 5, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x, y, z + 5, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 1, y, z + 5, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 1, y, z + 5, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 1, y + 1, z - 5, Block.AIR); // WALLS V LAYER 1
        Cytosis.getDefaultInstance().setBlock(x + 1, y + 1, z - 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 2, y + 1, z - 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 3, y + 1, z - 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 3, y + 1, z - 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 4, y + 1, z - 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 4, y + 1, z - 2, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 4, y + 1, z - 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 5, y + 1, z - 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 1, y + 1, z - 5, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 1, y + 1, z - 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 2, y + 1, z - 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 3, y + 1, z - 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 3, y + 1, z - 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 4, y + 1, z - 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 4, y + 1, z - 2, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 4, y + 1, z - 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 5, y + 1, z - 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 1, y + 1, z + 5, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 1, y + 1, z + 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 2, y + 1, z + 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 3, y + 1, z + 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 3, y + 1, z + 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 4, y + 1, z + 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 4, y + 1, z + 2, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 4, y + 1, z + 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 5, y + 1, z + 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 1, y + 1, z + 5, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 1, y + 1, z + 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 2, y + 1, z + 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 3, y + 1, z + 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 3, y + 1, z + 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 4, y + 1, z + 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 4, y + 1, z + 2, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 4, y + 1, z + 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 5, y + 1, z + 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 5, y + 1, z, Block.AIR, true);
        Cytosis.getDefaultInstance().setBlock(x + 5, y + 1, z, Block.AIR, true);
        Cytosis.getDefaultInstance().setBlock(x, y + 1, z + 5, Block.AIR, true);
        Cytosis.getDefaultInstance().setBlock(x, y + 1, z - 5, Block.AIR, true);
        Cytosis.getDefaultInstance().setBlock(x + 1, y + 2, z - 5, Block.AIR); // WALLS LAYER 2 V
        Cytosis.getDefaultInstance().setBlock(x + 1, y + 2, z - 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 2, y + 2, z - 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 3, y + 2, z - 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 3, y + 2, z - 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 4, y + 2, z - 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 4, y + 2, z - 2, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 4, y + 2, z - 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 5, y + 2, z - 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 1, y + 2, z - 5, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 1, y + 2, z - 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 2, y + 2, z - 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 3, y + 2, z - 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 3, y + 2, z - 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 4, y + 2, z - 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 4, y + 2, z - 2, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 4, y + 2, z - 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 5, y + 2, z - 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 1, y + 2, z + 5, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 1, y + 2, z + 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 2, y + 2, z + 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 3, y + 2, z + 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 3, y + 2, z + 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 4, y + 2, z + 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 4, y + 2, z + 2, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 4, y + 2, z + 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 5, y + 2, z + 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 1, y + 2, z + 5, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 1, y + 2, z + 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 2, y + 2, z + 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 3, y + 2, z + 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 3, y + 2, z + 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 4, y + 2, z + 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 4, y + 2, z + 2, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 4, y + 2, z + 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 5, y + 2, z + 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 5, y + 2, z, Block.AIR, true);
        Cytosis.getDefaultInstance().setBlock(x + 5, y + 2, z, Block.AIR, true);
        Cytosis.getDefaultInstance().setBlock(x, y + 2, z + 5, Block.AIR, true);
        Cytosis.getDefaultInstance().setBlock(x, y + 2, z - 5, Block.AIR, true);
        Cytosis.getDefaultInstance().setBlock(x - 3, y + 3, z + 2, Block.AIR);// ROOF V
        Cytosis.getDefaultInstance().setBlock(x - 3, y + 3, z + 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 2, y + 3, z + 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 1, y + 3, z + 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 3, y + 3, z - 2, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 3, y + 3, z - 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 2, y + 3, z - 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 1, y + 3, z - 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 3, y + 3, z + 2, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 3, y + 3, z + 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 2, y + 3, z + 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 1, y + 3, z + 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 3, y + 3, z - 2, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 3, y + 3, z - 1, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 2, y + 3, z - 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 1, y + 3, z - 3, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x + 4, y + 3, z, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x - 4, y + 3, z, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x, y + 3, z + 4, Block.AIR);
        Cytosis.getDefaultInstance().setBlock(x, y + 3, z - 4, Block.AIR);
    }
}
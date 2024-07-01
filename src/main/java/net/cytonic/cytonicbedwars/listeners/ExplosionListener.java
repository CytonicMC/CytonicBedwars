//package net.cytonic.cytonicbedwars.listeners;
// todo explosions
//import net.cytonic.cytonicbedwars.CytonicBedWars;
//import net.minestom.server.coordinate.Pos;
//import net.minestom.server.entity.EntityType;
//import net.minestom.server.instance.Explosion;
//import net.minestom.server.item.Material;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class ExplosionListener {
//
//    private final CytonicBedWars plugin;
//    public ExplosionListener(CytonicBedWars plugin) {
//        this.plugin = plugin;
//    }
//
//    public void onExplode(Explosion event){
//        if(event.getEntityType() == EntityType.TNT || event.getEntityType() == EntityType.FIREBALL){
//            event.setYield(0f);
//            event.setCancelled(false);
//
//            Map<Pos, Material> testing = new HashMap<>();
//
//            event.blockList().forEach(block -> testing.put(block.get(), block.getType()));
//
//            Bukkit.getScheduler().runTaskLater(plugin, () -> event.blockList().forEach(block -> {
//                if(!block.hasMetadata("blockdata")) {
//                    block.setType(testing.get(block.getLocation()));
//                }
//            }), 0);
//        }
//    }
//
//    @EventHandler
//    public void onExplode(BlockExp event){
//        event.setCancelled(true);
//        event.blockList().forEach(block -> block.getMetadata("blockdata").forEach(metadataValue -> {
//            if(!metadataValue.asBoolean()){
//                block.breakNaturally();
//            }
//        }));
//    }
//}

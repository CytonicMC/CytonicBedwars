package net.cytonic.cytonicbedwars.listeners;

import io.github.togar2.pvp.events.ExplosionEvent;
import net.cytonic.cytosis.events.api.Listener;
import net.minestom.server.entity.EntityType;
import net.minestom.server.instance.block.Block;

import java.util.Objects;

@SuppressWarnings("unused")
//this does not currently work because minestom pvp explosions are broken
public class ExplosionListener {

    @Listener
    public void onExplode(ExplosionEvent event) {
        if (event.getDamageObject().getAttacker() == null) return;
        if (event.getDamageObject().getAttacker().getEntityType().equals(EntityType.TNT) || event.getDamageObject().getAttacker().getEntityType().equals(EntityType.FIREBALL)) {
            event.getDamageObject().setAmount(0f);
            event.setCancelled(false);
            event.getAffectedBlocks().forEach(point -> {
                Block block = event.getInstance().getBlock(point);
                if (block.hasNbt() && Objects.requireNonNull(block.nbt()).getBoolean("placedByPlayer")) return;
                event.getAffectedBlocks().remove(point);
            });
        }
    }
}

package net.cytonic.cytonicbedwars.itemAbility.fireball;

import io.github.togar2.pvp.entity.projectile.CustomEntityProjectile;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.minestom.server.collision.Aerodynamics;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.metadata.item.FireballMeta;

import net.cytonic.cytonicbedwars.player.BedwarsPlayer;

public class FireballEntity extends CustomEntityProjectile {

    private static final double ACCELERATION_POWER = 0.1; // blocks/tick^2
    private static final double INERTIA = 0.95; // air inertia only
    private static final int TICKS_PER_SECOND = 20;
    private final BedwarsPlayer shooter;

    public FireballEntity(BedwarsPlayer shooter) {
        super(shooter, EntityType.FIREBALL);
        this.shooter = shooter;

        setNoGravity(true);

        this.setAerodynamics(new Aerodynamics(0.0, 1.0, 1.0));

        editEntityMeta(FireballMeta.class, meta -> meta.setShooter(shooter));
    }

    @Override
    public void tick(long time) {
        applyInertia();
        super.tick(time);
    }

    public void shoot(Vec direction, double speedBlocksPerTick) {
        this.setVelocity(direction.normalize().mul(speedBlocksPerTick * TICKS_PER_SECOND));
    }

    @Override
    public boolean onStuck() {
        explode();
        return true;
    }

    @Override
    public boolean onHit(Entity entity) {
        explode();
        return false;
    }

    private void explode() {
        instance.explode(
            (float) position.x(),
            (float) (position.y() + boundingBox.height() * 0.0625),
            (float) position.z(),
            3.0f,
            CompoundBinaryTag.builder()
                .putString("causingEntity", shooter.getUuid().toString())
                .build());
    }

    private void applyInertia() {
        Vec deltaMovement = this.getVelocity().div(TICKS_PER_SECOND);

        Vec newDeltaMovement = deltaMovement
            .add(deltaMovement.normalize().mul(ACCELERATION_POWER))
            .mul(INERTIA);

        this.setVelocity(newDeltaMovement.mul(TICKS_PER_SECOND));
    }
}

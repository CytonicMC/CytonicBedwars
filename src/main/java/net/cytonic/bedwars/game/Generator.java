package net.cytonic.bedwars.game;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.ItemEntity;
import net.minestom.server.entity.metadata.display.AbstractDisplayMeta.BillboardConstraints;
import net.minestom.server.entity.metadata.display.ItemDisplayMeta;
import net.minestom.server.entity.metadata.display.TextDisplayMeta;
import net.minestom.server.timer.Task;
import net.minestom.server.timer.TaskSchedule;
import net.minestom.server.utils.time.Tick;
import org.jetbrains.annotations.UnknownNullability;

import net.cytonic.bedwars.data.enums.GeneratorType;
import net.cytonic.cytosis.utils.Msg;

public class Generator {

    private final GeneratorType type;
    private final Pos pos;
    private final int itemLimit;
    @Setter
    private int waitTime;
    @UnknownNullability
    private Entity visual;
    private double visualRotation;
    @UnknownNullability
    private Task visualTask;
    @UnknownNullability
    private Entity name;
    @UnknownNullability
    private Entity countdown;
    @UnknownNullability
    private Task countdownTask;
    private int countdownTime;
    @UnknownNullability
    private Task task;
    private final List<ItemEntity> spawnedItems = new ArrayList<>();

    public Generator(GeneratorType type, Pos pos, Duration waitTime, int itemLimit) {
        this.type = type;
        this.pos = pos;
        this.waitTime = Tick.SERVER_TICKS.fromDuration(waitTime);
        this.itemLimit = itemLimit;
    }

    public void start(BedwarsWorld world) {
        if (type.getName() != null) {
            visual = new Entity(EntityType.ITEM_DISPLAY);
            visual.setInstance(world, pos.add(0, 4, 0));
            visual.editEntityMeta(ItemDisplayMeta.class, meta -> {
                assert type.getVisualItem() != null;
                meta.setItemStack(type.getVisualItem());
                meta.setHasNoGravity(true);
                meta.setPosRotInterpolationDuration(10);
            });

            visualTask = MinecraftServer.getSchedulerManager().scheduleTask(() -> {
                visualRotation += (Math.PI / 12);
                Pos pos = visual.getPosition().sub(0, Math.sin(visualRotation) / 24, 0);
                pos = pos.withYaw(visual.getPosition().yaw() + 10.5F);
                visual.teleport(pos);
            }, TaskSchedule.immediate(), TaskSchedule.millis(5));

            name = new Entity(EntityType.TEXT_DISPLAY);
            name.setInstance(world, pos.add(0, 2.5, 0));
            name.editEntityMeta(TextDisplayMeta.class, meta -> {
                meta.setText(type.getName());
                meta.setBillboardRenderConstraints(BillboardConstraints.CENTER);
                meta.setHasNoGravity(true);
            });

            countdown = new Entity(EntityType.TEXT_DISPLAY);
            countdown.setInstance(world, pos.add(0, 2.2, 0));
            countdown.editEntityMeta(TextDisplayMeta.class, meta -> {
                meta.setBillboardRenderConstraints(BillboardConstraints.CENTER);
                meta.setHasNoGravity(true);
            });

            countdownTask = MinecraftServer.getSchedulerManager().scheduleTask(() -> {
                float toNextF = ((waitTime - countdownTime) / 20.0F);
                countdown.editEntityMeta(TextDisplayMeta.class, meta -> meta.setText(
                    Msg.yellow("Spawns in <red>%.1f <yellow>seconds", toNextF)));
                if (countdownTime == waitTime) {
                    countdownTime = 0;
                    return;
                }
                countdownTime++;
            }, TaskSchedule.immediate(), TaskSchedule.millis(15));
        }

        task = MinecraftServer.getSchedulerManager().scheduleTask(() -> {
            spawnedItems.removeIf(Entity::isRemoved);
            if (spawnedItems.size() >= itemLimit) return;
            ItemEntity itemEntity = new ItemEntity(type.getItem());
            itemEntity.setInstance(world, pos.add(0, .2, 0));
            itemEntity.setVelocity(new Vec(0, 0, 0));
            spawnedItems.add(itemEntity);
        }, TaskSchedule.immediate(), TaskSchedule.tick(waitTime));
    }

    public void stop() {
        if (visualTask != null) {
            visualTask.cancel();
            visual.remove();

            name.remove();

            countdownTask.cancel();
            countdown.remove();

        }

        task.cancel();

        spawnedItems.forEach(Entity::remove);
        spawnedItems.clear();
    }
}

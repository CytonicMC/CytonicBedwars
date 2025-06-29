package net.cytonic.cytonicbedwars.data.objects;

import lombok.Setter;
import net.cytonic.cytonicbedwars.data.enums.GeneratorType;
import net.cytonic.cytonicbedwars.runnables.GeneratorVisualRunnable;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.utils.Msg;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.ItemEntity;
import net.minestom.server.entity.metadata.display.AbstractDisplayMeta;
import net.minestom.server.entity.metadata.display.ItemDisplayMeta;
import net.minestom.server.entity.metadata.display.TextDisplayMeta;
import net.minestom.server.tag.Tag;
import net.minestom.server.timer.Task;
import net.minestom.server.timer.TaskSchedule;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Generator {

    public static String SPLIT_KEY = "split_key";

    private final List<ItemEntity> spawnedItems = new ArrayList<>();
    private Task runnable;
    private Entity visual;
    private GeneratorVisualRunnable visualRunnable;
    private Entity countDown;
    private Entity name;
    private Task countdownRunnable;
    private final GeneratorType generatorType;
    @Setter
    private int waitTime;
    private final int itemLimit;
    private final Pos spawnLoc;
    private final boolean hasVisual;
    private final boolean splittable;

    private int toNext = 0; // in seconds

    //todo: Gen split by checking the players nearby and adding items to their inventory too

    public Generator(GeneratorType generatorType, int waitTime, int itemLimit, Pos spawnLoc, boolean hasVisual, boolean splittable) {
        this.generatorType = generatorType;
        this.waitTime = waitTime;
        this.itemLimit = itemLimit;
        this.spawnLoc = spawnLoc;
        this.hasVisual = hasVisual;
        this.splittable = splittable;
    }

    public void start() {
        if (hasVisual) {
            visual = new Entity(EntityType.ITEM_DISPLAY);
            visual.setInstance(Cytosis.getDefaultInstance(), spawnLoc.add(0, 4, 0));
            visual.editEntityMeta(ItemDisplayMeta.class, meta -> {
                meta.setItemStack(generatorType.getVisualItem());
                meta.setHasNoGravity(true);
            });
            visualRunnable = new GeneratorVisualRunnable(visual);

            name = new Entity(EntityType.TEXT_DISPLAY);
            name.setInstance(Cytosis.getDefaultInstance(), spawnLoc.add(0, 2.5, 0));
            name.editEntityMeta(TextDisplayMeta.class, meta -> {
                meta.setText(Objects.requireNonNull(generatorType.getName()));
                meta.setBillboardRenderConstraints(AbstractDisplayMeta.BillboardConstraints.CENTER);
                meta.setHasNoGravity(true);
            });

            countDown = new Entity(EntityType.TEXT_DISPLAY);
            countDown.setInstance(Cytosis.getDefaultInstance(), spawnLoc.add(0, 2.2, 0));
            countDown.editEntityMeta(TextDisplayMeta.class, meta -> {
                meta.setBillboardRenderConstraints(AbstractDisplayMeta.BillboardConstraints.CENTER);
                meta.setHasNoGravity(true);
            });

            countdownRunnable = MinecraftServer.getSchedulerManager().buildTask(() -> {
                toNext++;
                float toNextF = ((waitTime - toNext) / 20.0F);
                countDown.editEntityMeta(TextDisplayMeta.class, meta -> meta.setText(Msg.yellow("Spawns in <red>%.1f <yellow>seconds", toNextF)));
                if (toNext == waitTime) {
                    toNext = 0;
                }
            }).repeat(Duration.ofMillis(15)).schedule();
        }

        runnable = MinecraftServer.getSchedulerManager().buildTask(() -> {
            spawnedItems.removeIf(itemEntity -> itemEntity != null && itemEntity.isRemoved());
            if (spawnedItems.size() > itemLimit) return;
            ItemEntity itemEntity = new ItemEntity(generatorType.getItem());
            itemEntity.setInstance(Cytosis.getDefaultInstance(), spawnLoc.add(0, .5, 0));
            itemEntity.setVelocity(new Vec(0, 0, 0));
            if (splittable) {
                itemEntity.tagHandler().setTag(Tag.Boolean(SPLIT_KEY), true);
            }
            spawnedItems.add(itemEntity);
        }).repeat(TaskSchedule.tick(waitTime)).schedule();
    }

    public void stop() {
        if (hasVisual) {
            visualRunnable.getTask().cancel();
            visual.remove();
        }
        if (countDown != null) {
            countDown.remove();
            countdownRunnable.cancel();
        }
        if (name != null) {
            name.remove();
        }
        runnable.cancel();
        toNext = 0;
    }
}

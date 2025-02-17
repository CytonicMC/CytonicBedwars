package net.cytonic.cytonicbedwars.data.objects;

import lombok.Setter;
import net.cytonic.cytonicbedwars.data.enums.GeneratorType;
import net.cytonic.cytonicbedwars.data.types.GeneratorItems;
import net.cytonic.cytonicbedwars.runnables.GeneratorVisualRunnable;
import net.cytonic.cytonicbedwars.utils.Items;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.utils.Msg;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.ItemEntity;
import net.minestom.server.entity.metadata.display.TextDisplayMeta;
import net.minestom.server.tag.Tag;
import net.minestom.server.timer.Task;

import java.time.Duration;
import java.util.*;

public class Generator {

    public static String SPLIT_KEY = "split_key";

    private final GeneratorType type;
    private final GeneratorItems<String, Integer, Integer> items;
    private final Pos spawnLoc;
    private final boolean hasVisual;
    private final boolean splitable;
    private final Map<String, List<ItemEntity>> spawnedItems = new HashMap<>();
    private final Map<String, Task> runnables = new HashMap<>();
    private final int countDownDuration; // in seconds
    @Setter
    private Entity visual;
    private GeneratorVisualRunnable visualRunnable;
    private Entity countDown;
    @Setter
    private Entity name;
    private Task coundownRunnable;
    private String countDownFormat;
    private int toNext = 0; // in seconds

    //todo: Gensplit by checking the players nearby and adding items to their inventory too

    public Generator(GeneratorType type, GeneratorItems<String, Integer, Integer> items, Pos spawnLoc, boolean hasVisual, boolean splitable, int countDownDuration) {
        this.type = type;
        this.items = items;
        this.spawnLoc = spawnLoc;
        this.hasVisual = hasVisual;
        this.splitable = splitable;
        this.countDownDuration = countDownDuration;
        items.getKeyset().forEach(s -> {
            spawnedItems.put(s, new ArrayList<>());

            final int maxItems = items.getSecondValue(s);
            runnables.put(s, MinecraftServer.getSchedulerManager().buildTask(() -> {
                new ArrayList<>(spawnedItems.get(s)).forEach(item -> {
                    if (item != null && item.isRemoved()) {
                        spawnedItems.get(s).remove(item);
                    }
                });
                if (spawnedItems.get(s).size() >= maxItems) return;
                ItemEntity i = new ItemEntity(Items.get(s));
                i.setInstance(Cytosis.getDefaultInstance(), spawnLoc.add(0, .5, 0));
                i.setVelocity(new Vec(0, 0, 0));
                i.tagHandler().setTag(Tag.String(Items.NAMESPACE), UUID.randomUUID().toString());
                if (splitable)
                    i.tagHandler().setTag(Tag.Boolean(SPLIT_KEY), true);
                spawnedItems.get(s).add(i);
            }).repeat(Duration.ofSeconds(items.getFirstValue(s))).schedule());
        });
    }

    public void start() {
        if (countDown != null) {
            coundownRunnable = MinecraftServer.getSchedulerManager().buildTask(() -> {
                toNext++;
                float toNextF = ((countDownDuration - toNext) / 20.0F);
                String text = String.format(countDownFormat, toNextF);
                countDown.editEntityMeta(TextDisplayMeta.class, meta -> meta.setText(Msg.mm(text)));
                if (toNext == countDownDuration)
                    toNext = 0;
            }).repeat(Duration.ofMillis(15)).schedule();
        }
        if (hasVisual) {
            if (visual == null) throw new NullPointerException("The visual cannot be null!");
            visualRunnable = new GeneratorVisualRunnable(visual);
        }
    }

    public void stop() {
        if (hasVisual) {
            visualRunnable.getTask().cancel();
            visual.remove();
        }
        if (countDown != null) {
            countDown.remove();
            coundownRunnable.cancel();
        }
        if (name != null) {
            name.remove();
        }
        runnables.values().forEach(Task::cancel);
    }

    public void setCountDown(Entity display, String format) {
        this.countDown = display;
        this.countDownFormat = format;
    }
}

package net.cytonic.cytonicbedwars.managers;


import lombok.Getter;
import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytonicbedwars.CytonicBedwarsSettings;
import net.cytonic.cytonicbedwars.data.enums.GeneratorType;
import net.cytonic.cytonicbedwars.data.objects.Generator;
import net.cytonic.cytonicbedwars.data.objects.Team;
import net.cytonic.cytonicbedwars.data.types.GeneratorItems;
import net.cytonic.cytonicbedwars.data.types.Pair;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.utils.Msg;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.metadata.display.AbstractDisplayMeta;
import net.minestom.server.entity.metadata.display.BlockDisplayMeta;
import net.minestom.server.entity.metadata.display.TextDisplayMeta;
import net.minestom.server.instance.block.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class GeneratorManager {
    private final Map<Team, Generator> ironGenerators = new HashMap<>();
    private final Map<Team, Generator> goldGenerators = new HashMap<>();
    private final List<Generator> diamondGenerators = new ArrayList<>();
    private final List<Generator> emeraldGenerators = new ArrayList<>();

    public void registerTeamGenerators() {
        for (Team t : CytonicBedWars.getGameManager().getTeamlist()) {
            Pos loc = t.generatorLocation();
            Generator ironGenerator = new Generator(
                    GeneratorType.IRON,
                    new GeneratorItems<>("IRON", new Pair<>(CytonicBedwarsSettings.generatorsWaitTime.get(GeneratorType.IRON), CytonicBedwarsSettings.generatorsItemLimit.get(GeneratorType.IRON))),
                    loc,
                    false,
                    true,
                    CytonicBedwarsSettings.generatorsWaitTimeTicks.get(GeneratorType.IRON));
            ironGenerator.start();
            ironGenerators.put(t, ironGenerator);
            Generator goldGenerator = new Generator(
                    GeneratorType.GOLD,
                    new GeneratorItems<>("GOLD", new Pair<>(CytonicBedwarsSettings.generatorsWaitTime.get(GeneratorType.GOLD), CytonicBedwarsSettings.generatorsItemLimit.get(GeneratorType.GOLD))),
                    loc,
                    false,
                    true,
                    CytonicBedwarsSettings.generatorsWaitTimeTicks.get(GeneratorType.GOLD));
            goldGenerator.start();
            goldGenerators.put(t, goldGenerator);
        }
    }

    public void registerDiamondGenerators() {
        List<Pos> diamondGeneratorsPositions = CytonicBedwarsSettings.generators.get(GeneratorType.DIAMOND);
        for (Pos diamondGeneratorsPosition : diamondGeneratorsPositions) {
            Generator generator = new Generator(
                    GeneratorType.DIAMOND,
                    new GeneratorItems<>("DIAMOND", new Pair<>(CytonicBedwarsSettings.generatorsWaitTime.get(GeneratorType.DIAMOND), CytonicBedwarsSettings.generatorsItemLimit.get(GeneratorType.DIAMOND))),
                    diamondGeneratorsPosition,
                    true,
                    false,
                    CytonicBedwarsSettings.generatorsWaitTimeTicks.get(GeneratorType.DIAMOND)
            );
            Entity visual = new Entity(EntityType.BLOCK_DISPLAY);
            visual.setInstance(Cytosis.getDefaultInstance(), diamondGeneratorsPosition.add(0, 4, 0).sub(.5, 0, .5));
            visual.editEntityMeta(BlockDisplayMeta.class, meta -> {
                meta.setBlockState(Block.DIAMOND_BLOCK);
                meta.setHasNoGravity(true);
            });
            generator.setVisual(visual);

            Entity name = new Entity(EntityType.TEXT_DISPLAY);
            name.setInstance(Cytosis.getDefaultInstance(), diamondGeneratorsPosition.add(0, 2.5, 0));
            name.editEntityMeta(TextDisplayMeta.class, meta -> {
                meta.setText(Msg.mm("<aqua><bold>Diamond Generator"));
                meta.setBillboardRenderConstraints(AbstractDisplayMeta.BillboardConstraints.CENTER);
                meta.setHasNoGravity(true);
            });
            generator.setName(name);
            String nextSpawnStr = "<yellow>Spawns in <red>%.2f<yellow> seconds";
            Entity nextSpawn = new Entity(EntityType.TEXT_DISPLAY);
            nextSpawn.setInstance(Cytosis.getDefaultInstance(), diamondGeneratorsPosition.add(0, 2.2, 0));
            nextSpawn.editEntityMeta(TextDisplayMeta.class, meta -> {
                int countDownDuration = CytonicBedwarsSettings.generatorsWaitTimeTicks.get(GeneratorType.DIAMOND);
                float toNextF = ((countDownDuration) / 20.0F);
                meta.setText(Msg.mm(nextSpawnStr, toNextF));
                meta.setBillboardRenderConstraints(AbstractDisplayMeta.BillboardConstraints.CENTER);
                meta.setHasNoGravity(true);
            });
            generator.setCountDown(nextSpawn, nextSpawnStr);
            generator.start();
            diamondGenerators.add(generator);
        }
    }

    public void registerEmeraldGenerators() {
        List<Pos> posList = CytonicBedwarsSettings.generators.get(GeneratorType.EMERALD);
        for (Pos pos : posList) {
            Generator generator = new Generator(
                    GeneratorType.EMERALD,
                    new GeneratorItems<>("EMERALD", new Pair<>(CytonicBedwarsSettings.generatorsWaitTime.get(GeneratorType.EMERALD), CytonicBedwarsSettings.generatorsItemLimit.get(GeneratorType.EMERALD))),
                    pos,
                    true,
                    false,
                    CytonicBedwarsSettings.generatorsWaitTimeTicks.get(GeneratorType.EMERALD)
            );

            Entity visual = new Entity(EntityType.BLOCK_DISPLAY);
            visual.setInstance(Cytosis.getDefaultInstance(), pos.add(0, 4, 0).sub(.5, 0, .5));
            visual.editEntityMeta(BlockDisplayMeta.class, meta -> {
                meta.setBlockState(Block.EMERALD_BLOCK);
                meta.setHasNoGravity(true);
            });
            generator.setVisual(visual);

            Entity name = new Entity(EntityType.TEXT_DISPLAY);
            name.setInstance(Cytosis.getDefaultInstance(), pos.add(0, 2.5, 0));
            name.editEntityMeta(TextDisplayMeta.class, meta -> {
                meta.setText(Msg.mm("<green><bold>Emerald Generator"));
                meta.setBillboardRenderConstraints(AbstractDisplayMeta.BillboardConstraints.CENTER);
                meta.setHasNoGravity(true);
            });
            generator.setName(name);
            String nextSpawnStr = "<yellow>Spawns in <red>%.2f <yellow>seconds";
            Entity nextSpawn = new Entity(EntityType.TEXT_DISPLAY);
            nextSpawn.setInstance(Cytosis.getDefaultInstance(), pos.add(0, 2.2, 0));
            nextSpawn.editEntityMeta(TextDisplayMeta.class, meta -> {
                int countDownDuration = CytonicBedwarsSettings.generatorsWaitTimeTicks.get(GeneratorType.EMERALD);
                float toNextF = ((countDownDuration) / 20.0F);
                meta.setText(Msg.mm(nextSpawnStr, toNextF));
                meta.setBillboardRenderConstraints(AbstractDisplayMeta.BillboardConstraints.CENTER);
                meta.setHasNoGravity(true);
            });
            generator.setCountDown(nextSpawn, nextSpawnStr);
            generator.start();
            emeraldGenerators.add(generator);
        }
    }

    public void removeGenerators() {
        for (Generator ironGenerator : ironGenerators.values()) {
            ironGenerator.stop();
        }
        for (Generator goldGenerator : goldGenerators.values()) {
            goldGenerator.stop();
        }
        for (Generator diamondGenerator : diamondGenerators) {
            diamondGenerator.stop();
        }
        for (Generator emeraldGenerator : emeraldGenerators) {
            emeraldGenerator.stop();
        }
        ironGenerators.clear();
        goldGenerators.clear();
        diamondGenerators.clear();
        emeraldGenerators.clear();
    }
}


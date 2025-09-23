package net.cytonic.cytonicbedwars.managers;


import lombok.Getter;
import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.Config;
import net.cytonic.cytonicbedwars.data.enums.GeneratorType;
import net.cytonic.cytonicbedwars.data.objects.Generator;
import net.cytonic.cytonicbedwars.data.objects.Team;
import net.cytonic.cytosis.Cytosis;
import net.minestom.server.coordinate.Pos;

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
        for (Team t : Cytosis.CONTEXT.getComponent(GameManager.class).getTeams()) {
            Pos loc = t.getGeneratorLocation();
            Generator ironGenerator = new Generator(
                    GeneratorType.IRON,
                    Config.generatorsWaitTimeTicks.get(GeneratorType.IRON), Config.generatorsItemLimit.get(GeneratorType.IRON),
                    loc,
                    false,
                    true
            );
            ironGenerators.put(t, ironGenerator);
            Generator goldGenerator = new Generator(
                    GeneratorType.GOLD,
                    Config.generatorsWaitTimeTicks.get(GeneratorType.GOLD), Config.generatorsItemLimit.get(GeneratorType.GOLD),
                    loc,
                    false,
                    true
            );
            goldGenerators.put(t, goldGenerator);
            ironGenerator.start();
            goldGenerator.start();
        }
    }

    public void registerDiamondGenerators() {
        List<Pos> diamondGeneratorsPositions = Config.generators.get(GeneratorType.DIAMOND);
        for (Pos diamondGeneratorsPosition : diamondGeneratorsPositions) {
            Generator generator = new Generator(
                    GeneratorType.DIAMOND,
                    Config.generatorsWaitTimeTicks.get(GeneratorType.DIAMOND), Config.generatorsItemLimit.get(GeneratorType.DIAMOND),
                    diamondGeneratorsPosition,
                    true,
                    false
            );
            diamondGenerators.add(generator);
            generator.start();
        }
    }

    public void increaseDiamondsSpawnSpeed(int time) {
        for (Generator diamondGenerator : diamondGenerators) {
            diamondGenerator.stop();
            diamondGenerator.setWaitTime(time);
            diamondGenerator.start();
        }
    }

    public void registerEmeraldGenerators() {
        List<Pos> posList = Config.generators.get(GeneratorType.EMERALD);
        for (Pos pos : posList) {
            Generator generator = new Generator(
                    GeneratorType.EMERALD,
                    Config.generatorsWaitTimeTicks.get(GeneratorType.EMERALD), Config.generatorsItemLimit.get(GeneratorType.EMERALD),
                    pos,
                    true,
                    false
            );
            emeraldGenerators.add(generator);
            generator.start();
        }
    }

    public void increaseEmeraldsSpawnSpeed(int time) {
        for (Generator emeraldGenerator : emeraldGenerators) {
            emeraldGenerator.stop();
            emeraldGenerator.setWaitTime(time);
            emeraldGenerator.start();
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


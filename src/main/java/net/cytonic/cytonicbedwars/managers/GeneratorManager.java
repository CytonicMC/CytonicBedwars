package net.cytonic.cytonicbedwars.managers;


import lombok.Getter;
import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytonicbedwars.CytonicBedwarsSettings;
import net.cytonic.cytonicbedwars.data.enums.GeneratorType;
import net.cytonic.cytonicbedwars.data.objects.Generator;
import net.cytonic.cytonicbedwars.data.objects.Team;
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
        for (Team t : CytonicBedWars.getGameManager().getTeamlist()) {
            Pos loc = t.generatorLocation();
            Generator ironGenerator = new Generator(
                    GeneratorType.IRON,
                    CytonicBedwarsSettings.generatorsWaitTimeTicks.get(GeneratorType.IRON), CytonicBedwarsSettings.generatorsItemLimit.get(GeneratorType.IRON),
                    loc,
                    false,
                    true
            );
            ironGenerators.put(t, ironGenerator);
            Generator goldGenerator = new Generator(
                    GeneratorType.GOLD,
                    CytonicBedwarsSettings.generatorsWaitTimeTicks.get(GeneratorType.GOLD), CytonicBedwarsSettings.generatorsItemLimit.get(GeneratorType.GOLD),
                    loc,
                    false,
                    true
            );
            goldGenerators.put(t, goldGenerator);
        }
    }

    public void registerDiamondGenerators() {
        List<Pos> diamondGeneratorsPositions = CytonicBedwarsSettings.generators.get(GeneratorType.DIAMOND);
        for (Pos diamondGeneratorsPosition : diamondGeneratorsPositions) {
            Generator generator = new Generator(
                    GeneratorType.DIAMOND,
                    CytonicBedwarsSettings.generatorsWaitTimeTicks.get(GeneratorType.DIAMOND), CytonicBedwarsSettings.generatorsItemLimit.get(GeneratorType.DIAMOND),
                    diamondGeneratorsPosition,
                    true,
                    false
            );
            diamondGenerators.add(generator);
        }
    }

    public void registerEmeraldGenerators() {
        List<Pos> posList = CytonicBedwarsSettings.generators.get(GeneratorType.EMERALD);
        for (Pos pos : posList) {
            Generator generator = new Generator(
                    GeneratorType.EMERALD,
                    CytonicBedwarsSettings.generatorsWaitTimeTicks.get(GeneratorType.EMERALD), CytonicBedwarsSettings.generatorsItemLimit.get(GeneratorType.EMERALD),
                    pos,
                    true,
                    false
            );
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


package net.cytonic.cytonicbedwars;

import io.github.togar2.pvp.MinestomPvP;
import io.github.togar2.pvp.feature.CombatFeatureSet;
import io.github.togar2.pvp.feature.CombatFeatures;
import lombok.Getter;
import net.minestom.server.MinecraftServer;

@Getter
public final class CytonicBedWars {

    //    @Override
    public void initialize() {
//        Cytosis.CONTEXT.setServerGroup(new ServerGroup("bedwars", gameType));
        initPvp();
//        Cytosis.CONTEXT.registerComponent(new WorldManager());
//        Cytosis.CONTEXT.registerComponent(new GameManager());
//        Cytosis.CONTEXT.registerComponent(new GeneratorManager());
//        Cytosis.CONTEXT.registerComponent(new ItemAbilityDispatcher());
    }

    private void initPvp() {
        MinestomPvP.init(false, true);

        CombatFeatureSet modernVanilla = CombatFeatures.modernVanilla();
        MinecraftServer.getGlobalEventHandler().addChild(modernVanilla.createNode());
    }
}

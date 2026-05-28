package net.cytonic.cytonicbedwars;

import java.util.Map;

import dev.minestomunited.entrypoint.EntryPoint;
import dev.minestomunited.entrypoint.config.format.JsonCodecConfigFormat;
import dev.minestomunited.entrypoint.config.source.EnvironmentVariableConfigSource;

import net.cytonic.cytonicbedwars.server.BedwarsServer;
import net.cytonic.cytosis.Cytosis;

public class BedwarsMain {

    static void main(String[] args) {
        EntryPoint.Builder<BedwarsServer> builder = EntryPoint.<BedwarsServer>builder()
            .registerConfig(BedwarsConfig.class)
            .configSource(new EnvironmentVariableConfigSource("bedwars_"))
            .configFormat(new JsonCodecConfigFormat(Map.of(
                BedwarsConfig.class, BedwarsConfig.CODEC
            )))
            .server(BedwarsServer::new)
            .afterSetup(BedwarsServer::afterSetup);

        Cytosis.applyToBuilder(builder).run(args);
    }
}

package net.cytonic.bedwars;

import java.util.Map;

import dev.minestomunited.common.config.format.JsonCodecConfigFormat;
import dev.minestomunited.entrypoint.EntryPoint;

import net.cytonic.bedwars.config.BedwarsConfig;
import net.cytonic.bedwars.server.BedwarsServer;
import net.cytonic.cytosis.Cytosis;

public class BedwarsMain {

    static void main(String[] args) {
        EntryPoint.Builder<BedwarsServer> builder = EntryPoint.<BedwarsServer>builder()
            .registerConfig(BedwarsConfig.class)
            .addConfigFormat(new JsonCodecConfigFormat(Map.of(
                BedwarsConfig.class, BedwarsConfig.CODEC
            )))
            .server(BedwarsServer::new)
            .afterSetup(BedwarsServer::afterSetup);

        Cytosis.applyToBuilder(builder).run(args);
    }
}

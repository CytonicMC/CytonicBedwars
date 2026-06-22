package net.cytonic.cytonicbedwars.config;

import java.time.Duration;
import java.util.List;

import dev.minestomunited.common.codecUtils.CodecUtils;
import net.minestom.server.codec.Codec;
import net.minestom.server.codec.StructCodec;
import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.PlayerSkin;

import net.cytonic.cytosis.utils.DurationParser;

public interface BedwarsMapConfig {

    Codec<BedwarsMapConfig> CODEC = BedwarsMode.CODEC.unionType(
        "mode", mode -> switch (mode) {
            case NORMAL -> Normal.CODEC;
            default -> throw new IllegalStateException("Unexpected value: " + mode);
        },
        BedwarsMapConfig::mode
    );

    BedwarsMode mode();

    TeamSize teamSize();
    //todo use teams list size to determine max players

    PlayerSkin itemShopSkin();

    PlayerSkin teamShopSkin();

    List<? extends TeamConfig> teams();
    //todo figure out generator stuff

    Duration ironGeneratorTime();

    int ironGeneratorLimit();

    int goldGeneratorLimit();

    Duration goldGeneratorTime();

    GeneratorConfig diamondGenerators();

    GeneratorConfig emeraldGenerators();

    record GeneratorConfig(List<Pos> positions, Duration time, int limit) {

        public static final Codec<GeneratorConfig> CODEC = StructCodec.struct(
            "positions", CodecUtils.CENTERED_POS.list(), GeneratorConfig::positions,
            "time", Codec.STRING.transform(DurationParser::parse, it -> DurationParser.unparse(it, " ")),
            GeneratorConfig::time,
            "limit", Codec.INT, GeneratorConfig::limit,
            GeneratorConfig::new
        );
    }

    interface TeamConfig {

        TeamColor color();

        Pos spawnPos();

        Pos itemShopPos();

        Pos teamShopPos();

        BlockVec teamChestPos();

        Pos generatorPos();
        //todo use block type to determine the bed broken

        BlockVec bedPos();
    }

    record Normal(
        BedwarsMode mode,
        TeamSize teamSize,
        PlayerSkin itemShopSkin,
        PlayerSkin teamShopSkin,
        List<TeamConfig> teams,
        Duration ironGeneratorTime,
        int ironGeneratorLimit,
        Duration goldGeneratorTime,
        int goldGeneratorLimit,
        GeneratorConfig diamondGenerators,
        GeneratorConfig emeraldGenerators
    ) implements BedwarsMapConfig {

        public static final StructCodec<Normal> CODEC = StructCodec.struct(
            "mode", BedwarsMode.CODEC, Normal::mode,
            "team_size", TeamSize.CODEC, Normal::teamSize,
            "item_shop_skin", CodecUtils.PLAYER_SKIN, Normal::itemShopSkin,
            "team_shop_skin", CodecUtils.PLAYER_SKIN, Normal::teamShopSkin,
            "teams", TeamConfig.CODEC.list(), Normal::teams,
            "iron_generator_time", Codec.STRING.transform(DurationParser::parse, it -> DurationParser.unparse(it, " ")),
            Normal::ironGeneratorTime,
            "iron_generator_limit", Codec.INT, Normal::ironGeneratorLimit,
            "gold_generator_time", Codec.STRING.transform(DurationParser::parse, it -> DurationParser.unparse(it, " ")),
            Normal::goldGeneratorTime,
            "gold_generator_limit", Codec.INT, Normal::goldGeneratorLimit,
            "diamond_generators", GeneratorConfig.CODEC, Normal::diamondGenerators,
            "emerald_generators", GeneratorConfig.CODEC, Normal::emeraldGenerators,
            Normal::new
        );

        record TeamConfig(
            TeamColor color,
            Pos spawnPos,
            Pos itemShopPos,
            Pos teamShopPos,
            BlockVec teamChestPos,
            Pos generatorPos,
            BlockVec bedPos
        ) implements BedwarsMapConfig.TeamConfig {

            public static final Codec<TeamConfig> CODEC = StructCodec.struct(
                "color", TeamColor.CODEC, BedwarsMapConfig.TeamConfig::color,
                "spawn_pos", CodecUtils.CENTERED_POS, BedwarsMapConfig.TeamConfig::spawnPos,
                "item_shop_pos", CodecUtils.CENTERED_POS, BedwarsMapConfig.TeamConfig::itemShopPos,
                "team_shop_pos", CodecUtils.CENTERED_POS, BedwarsMapConfig.TeamConfig::teamShopPos,
                "team_chest_pos", CodecUtils.BLOCK_VEC, BedwarsMapConfig.TeamConfig::teamChestPos,
                "generator_pos", CodecUtils.CENTERED_POS, BedwarsMapConfig.TeamConfig::generatorPos,
                "bed_pos", CodecUtils.BLOCK_VEC, TeamConfig::bedPos,
                TeamConfig::new
            );
        }
    }
}

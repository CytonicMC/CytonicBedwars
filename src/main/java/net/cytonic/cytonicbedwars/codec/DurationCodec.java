package net.cytonic.cytonicbedwars.codec;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minestom.server.codec.Codec;

/**
 * Format: *s*m*h*d
 * Example: 1d5h
 * Example: 5m20s
 */
public class DurationCodec {
    public static final Codec<Duration> INSTANCE =
            Codec.STRING.transform(DurationCodec::deserialize, DurationCodec::serialize);
    private static final Pattern PATTERN = Pattern.compile(
            "(?:(\\d+)\\s*d)?\\s*" +
                    "(?:(\\d+)\\s*h)?\\s*" +
                    "(?:(\\d+)\\s*m)?\\s*" +
                    "(?:(\\d+)\\s*s)?",
            Pattern.CASE_INSENSITIVE
    );

    private static String serialize(Duration value) {
        return "d" + value.toDays() +
                "h" + value.toHours() +
                "m" + value.toMinutes() +
                "s" + value.toSeconds();
    }

    private static Duration deserialize(String value) {
        Matcher matcher = PATTERN.matcher(value.trim());
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid duration: " + value);
        }

        long days = matcher.group(1) != null ? Long.parseLong(matcher.group(1)) : 0;
        long hours = matcher.group(2) != null ? Long.parseLong(matcher.group(2)) : 0;
        long minutes = matcher.group(3) != null ? Long.parseLong(matcher.group(3)) : 0;
        long seconds = matcher.group(4) != null ? Long.parseLong(matcher.group(4)) : 0;

        return Duration.ofDays(days)
                .plusHours(hours)
                .plusMinutes(minutes)
                .plusSeconds(seconds);
    }
}

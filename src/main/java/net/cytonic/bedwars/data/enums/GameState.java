package net.cytonic.bedwars.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GameState {
    WAITING("Waiting", -1),
    STARTING("Starting", -1),
    PLAY("Play", 360), // 6 minutes
    DIAMOND_2("Diamond II", 360),
    EMERALD_2("Emerald II", 360),
    DIAMOND_3("Diamond III", 360),
    EMERALD_3("Emerald III", 360),
    BED_DESTRUCTION("Bed Destruction", 360),
    SUDDEN_DEATH("Sudden Death", 600), // 10 minutes
    ENDED("Ended", -1);
    private final String displayName;
    // in seconds
    private final int duration;

    public GameState getNext() {
        return values()[ordinal() + 1];
    }
}

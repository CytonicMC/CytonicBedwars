package net.cytonic.cytonicbedwars.data.enums;

import lombok.Getter;

@Getter
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
    ENDED("Ending", -1),
    CLEANUP("Cleanup", -1),
    FROZEN("Frozen", -1);

    // in seconds
    private final int duration;
    private final String displayName;

    GameState(String displayName, int duration) {
        this.duration = duration;
        this.displayName = displayName;
    }

    public GameState getNext() {
        return values()[ordinal() + 1];
    }
}

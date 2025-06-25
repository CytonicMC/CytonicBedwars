package net.cytonic.cytonicbedwars.player;

import lombok.Getter;
import lombok.Setter;
import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytonicbedwars.data.enums.ArmorLevel;
import net.cytonic.cytonicbedwars.data.enums.AxeLevel;
import net.cytonic.cytonicbedwars.data.enums.PickaxeLevel;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.player.CytosisPlayer;
import net.minestom.server.network.player.GameProfile;
import net.minestom.server.network.player.PlayerConnection;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Getter
@Setter
public class BedwarsPlayer extends CytosisPlayer {
    private ArmorLevel armorLevel = ArmorLevel.NONE;
    private AxeLevel axeLevel = AxeLevel.NONE;
    private PickaxeLevel pickaxeLevel = PickaxeLevel.NONE;
    private boolean shears = false;
    private boolean alive = true;
    private boolean respawning = false;

    public BedwarsPlayer(@NotNull PlayerConnection playerConnection, GameProfile gameProfile) {
        super(playerConnection, gameProfile);
        load();
    }

    public BedwarsPlayer(@NotNull UUID uuid, @NotNull String username, @NotNull PlayerConnection playerConnection) {
        super(uuid, username, playerConnection);
        load();
    }

    public void load() {
        BedwarsPlayer player = CytonicBedWars.getGameManager().getPlayer(getUuid()).orElse(null);
        if (player == null) {
            return;
        }
        this.armorLevel = player.getArmorLevel();
        this.axeLevel = player.getAxeLevel();
        this.pickaxeLevel = player.getPickaxeLevel();
        this.shears = player.isShears();
        this.alive = player.isAlive();
        this.respawning = player.isRespawning();
    }

    public void sendToLobby() {
        Cytosis.getNatsManager().sendPlayerToGenericServer(this.getUuid(), "cytonic", "lobby", "The Lobby");
    }
}

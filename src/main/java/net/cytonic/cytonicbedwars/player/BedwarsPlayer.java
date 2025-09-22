package net.cytonic.cytonicbedwars.player;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.cytonic.cytonicbedwars.data.enums.ArmorLevel;
import net.cytonic.cytonicbedwars.data.enums.AxeLevel;
import net.cytonic.cytonicbedwars.data.enums.PickaxeLevel;
import net.cytonic.cytonicbedwars.managers.GameManager;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.messaging.NatsManager;
import net.cytonic.cytosis.player.CytosisPlayer;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
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
    @Getter(AccessLevel.NONE)
    private boolean shears = false;
    private boolean alive = true;
    private boolean respawning = false;
    private Inventory enderChest = new Inventory(InventoryType.CHEST_3_ROW, "Ender Chest");

    public BedwarsPlayer(@NotNull PlayerConnection playerConnection, GameProfile gameProfile) {
        super(playerConnection, gameProfile);
        load();
    }

    public BedwarsPlayer(@NotNull UUID uuid, @NotNull String username, @NotNull PlayerConnection playerConnection) {
        super(uuid, username, playerConnection);
        load();
    }

    public void load() {
        Cytosis.CONTEXT.getComponent(GameManager.class).getPlayer(getUuid()).ifPresent(player -> {
            this.armorLevel = player.getArmorLevel();
            this.axeLevel = player.getAxeLevel();
            this.pickaxeLevel = player.getPickaxeLevel();
            this.shears = player.hasShears();
            this.alive = player.isAlive();
            this.respawning = player.isRespawning();
            this.enderChest = player.getEnderChest();
        });
    }

    public void sendToLobby() {
        Cytosis.CONTEXT.getComponent(NatsManager.class).sendPlayerToGenericServer(this.getUuid(), "cytonic", "lobby", "The Lobby");
    }

    public boolean hasShears() {
        return shears;
    }

    public void openEnderChest() {
        openInventory(enderChest);
    }
}

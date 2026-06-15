package net.cytonic.cytonicbedwars.player;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.key.Key;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.network.player.GameProfile;
import net.minestom.server.network.player.PlayerConnection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.cytonic.cytonicbedwars.config.TeamColor;
import net.cytonic.cytonicbedwars.data.enums.ArmorLevel;
import net.cytonic.cytonicbedwars.data.enums.AxeLevel;
import net.cytonic.cytonicbedwars.data.enums.PickaxeLevel;
import net.cytonic.cytonicbedwars.data.objects.PlayerStats;
import net.cytonic.cytonicbedwars.data.objects.Team;
import net.cytonic.cytonicbedwars.game.Game;
import net.cytonic.cytonicbedwars.managers.GameManager;
import net.cytonic.cytonicbedwars.server.BedwarsServer;
import net.cytonic.cytonicbedwars.utils.Items;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.player.CytosisPlayer;
import net.cytonic.cytosis.protocol.publishers.SendPlayerToServerPacketPublisher;

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
    private PlayerStats stats = null;
    @Getter(AccessLevel.NONE)
    private UUID gameId;
    private TeamColor teamColor;

    public BedwarsPlayer(@NotNull PlayerConnection playerConnection, GameProfile gameProfile) {
        super(playerConnection, gameProfile);
//        load();
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
            this.stats = player.getStats();
        });
    }

    public void UNSAFE_joinGame(UUID gameId, TeamColor teamColor) {
        this.gameId = gameId;
        this.teamColor = teamColor;
    }

    public void sendToLobby() {
        Cytosis.get(SendPlayerToServerPacketPublisher.class)
            .sendPlayerToGenericServer(getUuid(), Key.key("lobby", "lobby"), "The Lobby");
    }

    public boolean hasShears() {
        return shears;
    }

    public void openEnderChest() {
        openInventory(enderChest);
    }

    public void removeItems(Material material, int amount) {
        ItemStack[] slots = inventory.getItemStacks();
        int remaining = amount;

        for (int i = 0; i < slots.length && remaining > 0; i++) {
            var stack = slots[i];
            if (stack.material() == material) {
                int remove = Math.min(stack.amount(), remaining);
                int updated = stack.amount() - remove;
                inventory.setItemStack(i,
                    updated > 0 ? stack.withAmount(updated) : ItemStack.AIR);
                remaining -= remove;
            }
        }
    }

    public int itemCount(String id) {
        int count = 0;
        for (ItemStack stack : inventory.getItemStacks()) {
            if (!stack.hasTag(Items.NAMESPACE)) continue;
            if (stack.getTag(Items.NAMESPACE).equals(id)) {
                count += stack.amount();
            }
        }
        return count;
    }

    @Nullable
    public Team getBedwarsTeam() {
        if (getGame() == null) return null;
        return getGame().getTeam(teamColor);
    }

    @Nullable
    public Game getGame() {
        return Cytosis.get(BedwarsServer.class).getGame(gameId);
    }

    public void giveAxe() {
        if (axeLevel == AxeLevel.NONE) return;
        inventory.addItemStack(Items.get(axeLevel.getItemID()));
    }

    public void givePickaxe() {
        if (pickaxeLevel == PickaxeLevel.NONE) return;
        inventory.addItemStack(Items.get(pickaxeLevel.getItemID()));
    }
}

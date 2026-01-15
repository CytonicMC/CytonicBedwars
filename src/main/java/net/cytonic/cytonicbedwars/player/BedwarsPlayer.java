package net.cytonic.cytonicbedwars.player;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;
import net.minestom.server.network.player.GameProfile;
import net.minestom.server.network.player.PlayerConnection;
import org.jetbrains.annotations.NotNull;

import net.cytonic.cytonicbedwars.data.enums.ArmorLevel;
import net.cytonic.cytonicbedwars.data.enums.AxeLevel;
import net.cytonic.cytonicbedwars.data.enums.PickaxeLevel;
import net.cytonic.cytonicbedwars.managers.GameManager;
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
        Cytosis.get(SendPlayerToServerPacketPublisher.class)
            .sendPlayerToGenericServer(getUuid(), "cytonic", "lobby", "The Lobby");
    }

    public boolean hasShears() {
        return shears;
    }

    public void openEnderChest() {
        openInventory(enderChest);
    }

    public void setAxeLevel(AxeLevel level) {
        this.axeLevel = level;
        setAxe(level);
    }

    public boolean takeItem(String id, int quantity) {
        int toRemove = quantity;
        if (itemCount(id) >= quantity) {
            int i = 0;
            for (ItemStack stack : inventory.getItemStacks()) {
                if (stack.hasTag(Items.NAMESPACE)) {
                    if (stack.amount() <= toRemove) { // go ahead to remove the entire stack
                        if (stack.getTag(Items.NAMESPACE).equals(id)) {
                            toRemove -= stack.amount();
                            inventory.setItemStack(i, stack.withAmount(0));
                        }

                        if (toRemove == 0) {
                            return true;
                        }
                    } else { // only remove some
                        if (stack.getTag(Items.NAMESPACE).equals(id)) {
                            inventory.setItemStack(i, stack.withAmount(stack.amount() - toRemove));
                            return true;
                        }
                    }
                }
                i++;
            }
        } else {
            return false;
        }
        return false;
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

    public boolean hasSpace() {
        for (ItemStack stack : inventory.getItemStacks()) {
            if (stack.isAir()) {
                return true;
            }
        }
        return false;
    }

    public void setAxe(AxeLevel level) {
        if (level == AxeLevel.WOODEN) { // doesn't have an axe to remove
            inventory.addItemStack(Items.WOODEN_AXE);
            return;
        }
        ItemStack[] items = inventory.getItemStacks();
        for (int i = 0; i < inventory.getItemStacks().length; i++) {
            if (items[i].hasTag(Items.NAMESPACE)) {
                String oldID = AxeLevel.getOrdered(level, -1).getItemID();
                String id = items[i].getTag(Items.NAMESPACE);
                if (id.equals(oldID)) {
                    inventory.setItemStack(i, Items.get(level.getItemID()));
                    return;
                }
            }
        }
    }

    public void setPickaxe(PickaxeLevel level) {
        if (level == PickaxeLevel.WOODEN) { // doesn't have an axe to remove
            inventory.addItemStack(Items.WOODEN_PICKAXE);
            return;
        }
        ItemStack[] items = inventory.getItemStacks();
        for (int i = 0; i < inventory.getItemStacks().length; i++) {
            if (items[1].hasTag(Items.NAMESPACE)) {
                String oldID = PickaxeLevel.getOrdered(level, -1).getItemID();
                String id = items[i].getTag(Items.NAMESPACE);
                if (id.equals(oldID)) {
                    inventory.setItemStack(i, Items.get(level.getItemID()));
                    return;
                }
            }
        }
    }

    public void setSword(ItemStack sword) {
        takeItem("DEFAULT_SWORD", 1);
        inventory.addItemStack(sword);
    }
}

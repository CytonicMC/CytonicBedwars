package net.cytonic.cytonicbedwars.player;

import java.util.Random;
import java.util.UUID;

import io.github.togar2.pvp.utils.PotionFlags;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.minestom.server.coordinate.Point;
import net.minestom.server.entity.EquipmentSlot;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.inventory.InventoryCloseEvent;
import net.minestom.server.instance.block.Block;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.network.packet.server.play.BlockActionPacket;
import net.minestom.server.network.player.GameProfile;
import net.minestom.server.network.player.PlayerConnection;
import net.minestom.server.potion.Potion;
import net.minestom.server.potion.PotionEffect;
import net.minestom.server.sound.SoundEvent;
import org.jetbrains.annotations.UnknownNullability;

import net.cytonic.cytonicbedwars.config.TeamColor;
import net.cytonic.cytonicbedwars.data.enums.ArmorLevel;
import net.cytonic.cytonicbedwars.data.enums.AxeLevel;
import net.cytonic.cytonicbedwars.data.enums.PickaxeLevel;
import net.cytonic.cytonicbedwars.data.objects.PlayerStats;
import net.cytonic.cytonicbedwars.game.Game;
import net.cytonic.cytonicbedwars.game.Team;
import net.cytonic.cytonicbedwars.managers.GameManager;
import net.cytonic.cytonicbedwars.server.BedwarsServer;
import net.cytonic.cytonicbedwars.utils.Items;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.player.CytosisPlayer;
import net.cytonic.cytosis.utils.Msg;

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
    private int respawnTime;
    private Inventory enderChest = new Inventory(InventoryType.CHEST_3_ROW, "Ender Chest");
    @UnknownNullability
    private Point enderChestPos;
    private PlayerStats stats = null;
    @Getter(AccessLevel.NONE)
    private UUID gameId;
    private TeamColor teamColor;

    public BedwarsPlayer(PlayerConnection playerConnection, GameProfile gameProfile) {
        super(playerConnection, gameProfile);

        enderChest.eventNode().addListener(EventListener.of(InventoryCloseEvent.class, _ -> {
            instance.sendGroupedPacket(
                new BlockActionPacket(enderChestPos, (byte) 1, (byte) 0, Block.ENDER_CHEST));
            instance.playSound(Sound.sound(SoundEvent.BLOCK_ENDER_CHEST_CLOSE, Sound.Source.MASTER, 0.5f,
                new Random().nextFloat() * 0.1F + 0.9F), enderChestPos);
        }));
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

    public void UNSAFE_joinGame(UUID gameId) {
        this.gameId = gameId;
    }

    public void UNSAFE_joinTeam(TeamColor color) {
        this.teamColor = color;
    }

    public void sendToLobby() {
        if (Cytosis.isStandalone()) {
            kickInternal(Msg.mm("Sent to lobby"));
            return;
        }
        sendToGenericServer(Key.key("lobby:lobby"), " The Lobby");
    }

    public boolean hasShears() {
        return shears;
    }

    public int itemCount(Material material) {
        int count = 0;
        for (ItemStack itemStack : inventory.getItemStacks()) {
            if (itemStack.material() == material) {
                count += itemStack.amount();
            }
        }
        return count;
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

    public Component getBedwarsFormattedName() {
        return getBedwarsTeam().getName()
            .appendSpace()
            .append(formattedName());
    }

    public Game getGame() {
        return Cytosis.get(BedwarsServer.class).getGame(gameId);
    }

    public Team getBedwarsTeam() {
        Team team = getGame().getTeam(teamColor);
        if (team == null) {
            throw new IllegalStateException("Team with id " + teamColor + " does not exist on game " + gameId);
        }
        return team;
    }

    public boolean isSpectator() {
        return getGame().isSpectator(this);
    }

    public void applyItems() {
        inventory.setItemStack(0, ItemStack.of(Material.WOODEN_SWORD));

        inventory.setEquipment(EquipmentSlot.HELMET, getHeldSlot(), armorLevel.getHead());
        inventory.setEquipment(EquipmentSlot.CHESTPLATE, getHeldSlot(), armorLevel.getChest());
        inventory.setEquipment(EquipmentSlot.LEGGINGS, getHeldSlot(), ItemStack.of(Material.LEATHER_LEGGINGS));
        inventory.setEquipment(EquipmentSlot.BOOTS, getHeldSlot(), ItemStack.of(Material.LEATHER_BOOTS));

        inventory.addItemStack(axeLevel.getItemStack());
        inventory.addItemStack(pickaxeLevel.getItemStack());
        if (shears) {
            inventory.addItemStack(ItemStack.of(Material.SHEARS));
        }
    }

    public void applyInvisibility() {
        addEffect(
            new Potion(PotionEffect.INVISIBILITY, PotionFlags.create(false, false, true), Potion.INFINITE_DURATION));
    }

    public void removeInvisibility() {
        removeEffect(PotionEffect.INVISIBILITY);
    }
}

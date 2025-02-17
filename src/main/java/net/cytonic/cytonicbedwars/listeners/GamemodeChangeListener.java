package net.cytonic.cytonicbedwars.listeners;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytonicbedwars.utils.Items;
import net.minestom.server.entity.EquipmentSlot;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerGameModeChangeEvent;
import net.minestom.server.potion.Potion;
import net.minestom.server.potion.PotionEffect;

@NoArgsConstructor
public class GamemodeChangeListener {

    public void onGamemodeChange(PlayerGameModeChangeEvent event) {
        Player player = event.getPlayer();
        if (event.getNewGameMode() == GameMode.SPECTATOR) {
            player.getInventory().clear();
            player.getInventory().setEquipment(EquipmentSlot.BOOTS, player.getHeldSlot(), Items.SPECTATOR_ARMOR);
            player.getInventory().setEquipment(EquipmentSlot.LEGGINGS, player.getHeldSlot(), Items.SPECTATOR_ARMOR);
            player.getInventory().setEquipment(EquipmentSlot.CHESTPLATE, player.getHeldSlot(), Items.SPECTATOR_ARMOR);
            player.setHealth(20);
            event.setCancelled(true);
            player.setGameMode(GameMode.ADVENTURE);
            player.addEffect(new Potion(PotionEffect.INVISIBILITY, (byte) 1, -1));
            player.setAllowFlying(true);
            player.setFlying(true);
            CytonicBedWars.getGameManager().spectators.add(player.getUuid());
            player.getInventory().setItemStack(0, Items.SPECTATOR_TARGET_SELECTOR);
            player.getInventory().setItemStack(4, Items.SPECTATOR_SPEED_SELECTOR);
            player.getInventory().setItemStack(8, Items.SPECTATOR_LOBBY_REQUEST);
        } else {
            // un-ivisafy if they are respawning, etc.
            if (event.getPlayer().getGameMode() == GameMode.ADVENTURE)
                player.getInventory().clear(); // only clear the inventory if they are coming from a spectator
            CytonicBedWars.getGameManager().spectators.remove(player.getUuid());
            player.removeEffect(PotionEffect.INVISIBILITY);
            if (event.getNewGameMode() != GameMode.CREATIVE) {
                player.setAllowFlying(false);
                player.setFlying(false);
            }
        }
    }
}

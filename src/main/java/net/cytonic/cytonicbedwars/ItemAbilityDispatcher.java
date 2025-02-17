package net.cytonic.cytonicbedwars;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.menu.spectators.SpectatorSelectMenu;
import net.cytonic.cytonicbedwars.menu.spectators.SpectatorSpeedMenu;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.logging.Logger;
import net.cytonic.cytosis.player.CytosisPlayer;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.metadata.item.FireballMeta;
import net.minestom.server.event.player.PlayerBlockInteractEvent;
import net.minestom.server.event.player.PlayerEntityInteractEvent;
import net.minestom.server.item.ItemStack;

@NoArgsConstructor
public class ItemAbilityDispatcher {

    public void dispatch(String abilityKey, CytosisPlayer user, PlayerBlockInteractEvent event) {
        Logger.debug("test key = " + abilityKey);
        switch (abilityKey) {
            case "FIREBALL" -> {
                ItemStack item = user.getItemInHand(event.getHand());
                user.setItemInHand(event.getHand(), item.withAmount(item.amount() - 1));
                event.setCancelled(true);
                Entity entity = new Entity(EntityType.FIREBALL);
                FireballMeta fb = (FireballMeta) entity.getEntityMeta();
                fb.setShooter(user);
                entity.setInstance(Cytosis.getDefaultInstance(), user.getPosition());
            }
            case "TNT" -> {
                event.setCancelled(true);
                ItemStack item = user.getItemInHand(event.getHand());
                user.setItemInHand(event.getHand(), item.withAmount(item.amount() - 1));
                Entity entity = new Entity(EntityType.TNT);
                entity.setInstance(Cytosis.getDefaultInstance(), event.getCursorPosition());
            }
            case "SPECTATOR_COMPASS" -> new SpectatorSelectMenu().open(user);
            case "SPECTATOR_SPEED_SELECTOR" -> new SpectatorSpeedMenu().open(user);
            case "LOBBY_REQUEST" -> CytonicBedWars.getGameManager().sendPlayerToLobby(user);
            default -> { // not an ability
                return;
            }
        }
        event.setCancelled(true);
    }

    public void dispatch(String abilityKey, CytosisPlayer user, PlayerEntityInteractEvent event) {
        Logger.debug("test key = " + abilityKey);
        switch (abilityKey) {
            case "FIREBALL" -> {
                ItemStack item = user.getItemInHand(event.getHand());
                user.setItemInHand(event.getHand(), item.withAmount(item.amount() - 1));
                Entity entity = new Entity(EntityType.FIREBALL);
                FireballMeta fb = (FireballMeta) entity.getEntityMeta();
                fb.setShooter(user);
                entity.setInstance(Cytosis.getDefaultInstance(), user.getPosition());
            }
            case "TNT" -> {
                ItemStack item = user.getItemInHand(event.getHand());
                user.setItemInHand(event.getHand(), item.withAmount(item.amount() - 1));
                Entity entity = new Entity(EntityType.TNT);
                entity.setInstance(Cytosis.getDefaultInstance(), event.getInteractPosition());
            }
            case "SPECTATOR_COMPASS" -> new SpectatorSelectMenu().open(user);
            case "SPECTATOR_SPEED_SELECTOR" -> new SpectatorSpeedMenu().open(user);
            case "LOBBY_REQUEST" -> CytonicBedWars.getGameManager().sendPlayerToLobby(user);
            default -> { // not an ability
            }
        }
    }
}

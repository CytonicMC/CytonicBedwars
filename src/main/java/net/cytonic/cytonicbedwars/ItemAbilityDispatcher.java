package net.cytonic.cytonicbedwars;

import net.cytonic.cytosis.Cytosis;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.metadata.item.FireballMeta;
import net.minestom.server.event.player.PlayerBlockInteractEvent;
import net.minestom.server.event.player.PlayerEntityInteractEvent;
import net.minestom.server.item.ItemStack;

public class ItemAbilityDispatcher {

    public ItemAbilityDispatcher() {
    }

    public void dispatch(String abilityKey, Player user, PlayerBlockInteractEvent event) {
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
            case "SPECTATOR_COMPASS" -> user.openInventory(CytonicBedWars.getGameManager().getMenuManager().getSpectatorSelectorMenu());
            case "LOBBY_REQUEST" -> user.sendMessage("No leaving >:)");
            case "SPECTATOR_SPEED_SELECTOR" -> user.openInventory(CytonicBedWars.getGameManager().getMenuManager().getSpectatorSpeedMenu());
            default -> { // not an ability
                return;
            }
        }
        event.setCancelled(true);
    }

    public void dispatch(String abilityKey, Player user, PlayerEntityInteractEvent event) {
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
            case "SPECTATOR_COMPASS" -> user.openInventory(CytonicBedWars.getGameManager().getMenuManager().getSpectatorSelectorMenu());
            case "LOBBY_REQUEST" -> user.sendMessage("No leaving >:)");
            case "SPECTATOR_SPEED_SELECTOR" -> user.openInventory(CytonicBedWars.getGameManager().getMenuManager().getSpectatorSpeedMenu());
            default -> { // not an ability
            }
        }
    }
}

package net.cytonic.cytonicbedwars;

import lombok.NoArgsConstructor;
import me.devnatan.inventoryframework.ViewFrame;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.metadata.item.FireballMeta;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.item.ItemStack;

import net.cytonic.cytonicbedwars.menu.spectators.SpectatorSelectMenu;
import net.cytonic.cytonicbedwars.menu.spectators.SpectatorSpeedMenu;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytosis.Cytosis;

@NoArgsConstructor
public class ItemAbilityDispatcher {

    public void dispatch(String ability, BedwarsPlayer player, PlayerUseItemEvent event) {
        switch (ability) {
            case "FIREBALL" -> {
                ItemStack item = player.getItemInHand(event.getHand());
                player.setItemInHand(event.getHand(), item.withAmount(item.amount() - 1));
                Entity entity = new Entity(EntityType.FIREBALL);
                FireballMeta fb = (FireballMeta) entity.getEntityMeta();
                fb.setShooter(player);
                entity.setInstance(Cytosis.CONTEXT.getComponent(InstanceContainer.class), player.getPosition());
            }
            case "SPECTATOR_COMPASS" ->
                Cytosis.CONTEXT.getComponent(ViewFrame.class).open(SpectatorSelectMenu.class, player);
            case "SPECTATOR_SPEED_SELECTOR" -> Cytosis.CONTEXT.getComponent(ViewFrame.class).open(SpectatorSpeedMenu.class, player);
            case "LOBBY_REQUEST" -> player.sendToLobby();
            default -> { // not an ability
            }
        }
    }
}

package net.cytonic.cytonicbedwars;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.menu.spectators.SpectatorSelectMenu;
import net.cytonic.cytonicbedwars.menu.spectators.SpectatorSpeedMenu;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytosis.Cytosis;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.metadata.item.FireballMeta;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.item.ItemStack;

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
                entity.setInstance(Cytosis.getDefaultInstance(), player.getPosition());
            }
            case "SPECTATOR_COMPASS" -> new SpectatorSelectMenu().open(player);
            case "SPECTATOR_SPEED_SELECTOR" -> new SpectatorSpeedMenu().open(player);
            case "LOBBY_REQUEST" -> player.sendToLobby();
            default -> { // not an ability
            }
        }
    }
}

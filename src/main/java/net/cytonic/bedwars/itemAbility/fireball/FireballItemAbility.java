package net.cytonic.bedwars.itemAbility.fireball;

import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.GameMode;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.item.ItemStack;

import net.cytonic.bedwars.game.BedwarsWorld;
import net.cytonic.bedwars.itemAbility.ItemAbility;
import net.cytonic.bedwars.player.BedwarsPlayer;

public class FireballItemAbility implements ItemAbility {

    @Override
    public void use(BedwarsPlayer player, BedwarsWorld world, PlayerUseItemEvent event) {
        if (player.getGameMode() != GameMode.CREATIVE) {
            ItemStack itemStack = player.getItemInHand(event.getHand());
            player.setItemInHand(event.getHand(), itemStack.withAmount(itemStack.amount() - 1));
        }

        FireballEntity fireball = new FireballEntity(player);
        Vec direction = player.getPosition().direction();
        fireball.shoot(direction, 1.25); // blocks/tick initial speed

        fireball.setInstance(world, player.getEyeLocation());
    }
}

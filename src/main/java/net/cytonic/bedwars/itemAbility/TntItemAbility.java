package net.cytonic.bedwars.itemAbility;

import io.github.togar2.pvp.feature.CombatFeatureSet;
import io.github.togar2.pvp.feature.FeatureType;
import io.github.togar2.pvp.feature.explosion.ExplosionFeature.IgnitionCause.ByPlayer;
import net.minestom.server.entity.GameMode;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.ItemStack;

import net.cytonic.bedwars.game.BedwarsWorld;
import net.cytonic.bedwars.player.BedwarsPlayer;
import net.cytonic.cytosis.Cytosis;

public class TntItemAbility implements ItemAbility {

    @Override
    public void place(BedwarsPlayer player, BedwarsWorld world, PlayerBlockPlaceEvent event) {
        if (player.getGameMode() != GameMode.CREATIVE) {
            ItemStack itemStack = player.getItemInHand(event.getHand());
            player.setItemInHand(event.getHand(), itemStack.withAmount(itemStack.amount() - 1));
        }

        Cytosis.get(CombatFeatureSet.class).get(FeatureType.EXPLOSION)
            .primeExplosive(event.getInstance(), event.getBlockPosition(), new ByPlayer(player), 80);
        event.getInstance().setBlock(event.getBlockPosition(), Block.AIR);
        event.setCancelled(true);
    }
}

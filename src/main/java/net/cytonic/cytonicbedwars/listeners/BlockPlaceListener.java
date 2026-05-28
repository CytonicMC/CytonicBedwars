package net.cytonic.cytonicbedwars.listeners;

import lombok.NoArgsConstructor;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.tag.Tag;
import org.jetbrains.annotations.NotNull;

import net.cytonic.cytonicbedwars.BedwarsConfig;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytosis.events.api.Listener;
import net.cytonic.cytosis.utils.Msg;

@NoArgsConstructor
@SuppressWarnings("unused")
public class BlockPlaceListener {

    public static final Tag<@NotNull Boolean> PLACED_BY_PLAYER_TAG = Tag.Boolean("placed_by_player");
    public static final Tag<@NotNull String> BW_ID = Tag.String("bw_id");

    private static double distance(double x1, double x2, double z1, double z2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(z1 - z2, 2));
    }

    @Listener
    public void onBlockPlace(PlayerBlockPlaceEvent event) {
        if (!(event.getPlayer() instanceof BedwarsPlayer player)) return;
        if (player.getGameMode() == GameMode.CREATIVE) return;
        Pos spawn = new Pos(0,0,0);
        //todo make distance configurable
        if (distance(event.getBlockPosition().x(), spawn.x(), event.getBlockPosition().z(), spawn.z()) > 105.0
            || event.getBlockPosition().y() >= 50) {
            event.setCancelled(true);
            player.sendMessage(Msg.whoops("You cannot place blocks that far from the map!"));
        }

        event.setBlock(event.getBlock().withTag(PLACED_BY_PLAYER_TAG, true));
        ItemStack itemStack = event.getPlayer().getItemInHand(event.getHand());
        if (!itemStack.hasTag(BW_ID)) return;
        event.setBlock(event.getBlock().withTag(BW_ID, itemStack.getTag(BW_ID)));
    }
}

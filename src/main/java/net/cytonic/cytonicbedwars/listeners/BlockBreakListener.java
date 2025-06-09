package net.cytonic.cytonicbedwars.listeners;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytonicbedwars.utils.Items;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.events.api.Listener;
import net.cytonic.cytosis.player.CytosisPlayer;
import net.cytonic.cytosis.utils.Msg;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.ItemEntity;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.item.ItemStack;

import java.util.Objects;

@NoArgsConstructor
@SuppressWarnings("unused")
public class BlockBreakListener {

    @Listener
    public void onBlockBreak(PlayerBlockBreakEvent e) {
        CytosisPlayer player = (CytosisPlayer) e.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE) return;
        if (CytonicBedWars.getGameManager().spectators.contains(player.getUuid())) {
            player.sendMessage(Msg.whoops("You cannot do this as a spectator!"));
            e.setCancelled(true);
            return;
        }
        if (e.getBlock().name().contains("bed")) {
            if (CytonicBedWars.getGameManager().getPlayerTeam(player.getUuid()).isPresent()) {
                if (CytonicBedWars.getGameManager().getPlayerTeam(player.getUuid()).get().bedType().key().equals(e.getBlock().key())) {
                    e.setCancelled(true);
                    player.sendMessage(Msg.whoops("You cannot break your own bed!"));
                    return;
                }
            }
            CytonicBedWars.getGameManager().getTeamlist().forEach(team -> {
                if (e.getBlock().name().equals(team.bedType().name())) {
                    CytonicBedWars.getGameManager().breakBed(player, team);
                    CytonicBedWars.getGameManager().getWorldManager().breakBed(e.getBlock(), e.getBlockPosition());
                }
            });
            return;
        }

        if (e.getBlock().hasNbt()) {
            if (!Objects.requireNonNull(e.getBlock().nbt()).getBoolean("placedByPlayer")) {
                player.sendMessage(Msg.whoops("You can only break blocks placed by players!"));
                e.setCancelled(true);
                return;
            }
        } else {
            player.sendMessage(Msg.whoops("You can only break blocks placed by players!"));
            e.setCancelled(true);
            return;
        }
        ItemStack stack = Items.get(e.getBlock().nbt().getString("bwID"));
        ItemEntity item = new ItemEntity(stack);
        item.setInstance(Cytosis.getDefaultInstance(), e.getBlockPosition());
        item.spawn();
    }
}

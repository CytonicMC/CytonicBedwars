package net.cytonic.cytonicbedwars.listeners;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.managers.GameManager;
import net.cytonic.cytonicbedwars.managers.WorldManager;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytonicbedwars.utils.Items;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.events.api.Listener;
import net.cytonic.cytosis.utils.Msg;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.ItemEntity;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.item.ItemStack;

import java.util.Objects;

@NoArgsConstructor
@SuppressWarnings("unused")
public class BlockBreakListener {

    @Listener
    public void onBlockBreak(PlayerBlockBreakEvent e) {
        if (!(e.getPlayer() instanceof BedwarsPlayer player)) return;
        if (player.getGameMode() == GameMode.CREATIVE) return;
        if (Cytosis.CONTEXT.getComponent(GameManager.class).getSpectators().contains(player.getUuid())) {
            player.sendMessage(Msg.whoops("You cannot do this as a spectator!"));
            e.setCancelled(true);
            return;
        }
        if (e.getBlock().name().contains("bed")) {
            if (Cytosis.CONTEXT.getComponent(GameManager.class).getPlayerTeam(player).isPresent()) {
                if (Cytosis.CONTEXT.getComponent(GameManager.class).getPlayerTeam(player).get().getBedType().key().equals(e.getBlock().key())) {
                    e.setCancelled(true);
                    player.sendMessage(Msg.whoops("You cannot break your own bed!"));
                    return;
                }
            }
            Cytosis.CONTEXT.getComponent(GameManager.class).getTeams().forEach(team -> {
                if (e.getBlock().name().equals(team.getBedType().name())) {
                    Cytosis.CONTEXT.getComponent(GameManager.class).breakBed(player, team);
                    Cytosis.CONTEXT.getComponent(WorldManager.class).breakBed(team);
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
        item.setInstance(Cytosis.CONTEXT.getComponent(InstanceContainer.class), e.getBlockPosition());
        item.spawn();
    }
}

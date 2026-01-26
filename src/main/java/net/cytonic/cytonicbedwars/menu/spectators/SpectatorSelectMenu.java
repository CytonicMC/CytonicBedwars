package net.cytonic.cytonicbedwars.menu.spectators;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import me.devnatan.inventoryframework.context.RenderContext;
import net.minestom.server.component.DataComponents;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.network.player.ResolvableProfile;
import net.minestom.server.tag.Tag;
import org.jetbrains.annotations.NotNull;

import net.cytonic.cytonicbedwars.data.objects.Team;
import net.cytonic.cytonicbedwars.managers.GameManager;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.utils.Msg;

public class SpectatorSelectMenu extends View {

    private static final Tag<@NotNull UUID> UUID_TAG = Tag.UUID("uuid");

    @Override
    public void onInit(@NotNull ViewConfigBuilder config) {
        config.title(Msg.mm("Spectate a player"));
    }

    @Override
    public void onFirstRender(@NotNull RenderContext context) {
        AtomicInteger slot = new AtomicInteger();
        List<BedwarsPlayer> players = Cytosis.CONTEXT.getComponent(GameManager.class).getTeams().stream()
            .map(Team::getPlayers).flatMap(List::stream).toList();

        for (BedwarsPlayer player : players) {
            ItemStack itemStack = ItemStack.builder(Material.PLAYER_HEAD)
                .set(DataComponents.PROFILE, new ResolvableProfile(Objects.requireNonNull(player.getSkin())))
                .hideExtraTooltip()
                .set(DataComponents.ITEM_NAME, Msg.green(player.getUsername()))
                .lore(Msg.grey("Click to teleport to %s", player.getUsername()))
                .set(UUID_TAG, player.getUuid())
                .build();
            context.firstSlot(itemStack).onClick(slotClickContext -> {
                UUID uuid = slotClickContext.getItem().getTag(UUID_TAG);
                if (Cytosis.getPlayer(uuid).isEmpty()) {
                    slotClickContext.getPlayer().sendMessage(Msg.whoops("That player is not online"));
                    slotClickContext.openForPlayer(SpectatorSelectMenu.class);
                    return;
                }
                Player target = Cytosis.getPlayer(uuid).get();
                slotClickContext.getPlayer().sendMessage(Msg.success("Teleported you to %s!", target.getUsername()));
                slotClickContext.getPlayer().teleport(target.getPosition());
            });
        }
    }
}

package net.cytonic.cytonicbedwars.menu.spectators;

import eu.koboo.minestom.invue.api.PlayerView;
import eu.koboo.minestom.invue.api.ViewBuilder;
import eu.koboo.minestom.invue.api.ViewType;
import eu.koboo.minestom.invue.api.component.ViewProvider;
import eu.koboo.minestom.invue.api.item.PrebuiltItem;
import eu.koboo.minestom.invue.api.item.ViewItem;
import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.utils.Msg;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemComponent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.component.CustomData;
import net.minestom.server.item.component.HeadProfile;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class SpectatorSelectMenu extends ViewProvider {

    public SpectatorSelectMenu() {
        super(Cytosis.VIEW_REGISTRY, ViewBuilder.of(ViewType.SIZE_3_X_9).title(Msg.mm("Spectate a player")));
    }

    @Override
    public void onOpen(@NotNull PlayerView view, @NotNull Player player) {
        AtomicInteger i = new AtomicInteger(0);
        CytonicBedWars.getGameManager().getAlivePlayers().forEach(uuid -> {
            ItemStack stack = ItemStack.builder(Material.PLAYER_HEAD)
                    .set(ItemComponent.PROFILE, new HeadProfile(Objects.requireNonNull(Cytosis.getPlayer(uuid).orElseThrow().getSkin())))
                    .set(ItemComponent.ITEM_NAME, Msg.mm("<green>" + Cytosis.getPlayer(uuid).orElseThrow().getUsername()))
                    .set(ItemComponent.LORE, List.of((Msg.mm("<gray>Click to teleport to " + Cytosis.getPlayer(uuid).orElseThrow().getUsername()))))
                    .set(ItemComponent.CUSTOM_DATA, new CustomData(CompoundBinaryTag.builder().putString("uuid", uuid.toString()).build()))
                    .build();
            PrebuiltItem item = PrebuiltItem.of(stack, action -> {
                action.getEvent().setCancelled(true);
                if (Cytosis.getPlayer(UUID.fromString(Objects.requireNonNull(action.getEvent().getClickedItem().get(ItemComponent.CUSTOM_DATA)).nbt().getString("uuid"))).isEmpty()) {
                    player.sendMessage(Msg.whoops("That player is not online."));
                    open(player);
                } else {
                    Player target = Cytosis.getPlayer(Objects.requireNonNull(action.getEvent().getClickedItem().get(ItemComponent.CUSTOM_DATA)).nbt().getString("uuid")).orElseThrow();
                    player.sendMessage(Msg.success("<green>Teleported you to " + target.getUsername() + "!"));
                    player.teleport(target.getPosition());
                }
            });
            ViewItem.bySlot(view, i.getAndIncrement()).applyPrebuilt(item);
        });
    }
}

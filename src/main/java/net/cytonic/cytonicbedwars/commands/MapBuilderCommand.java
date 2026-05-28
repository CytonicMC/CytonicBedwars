package net.cytonic.cytonicbedwars.commands;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;

import net.cytonic.cytonicbedwars.game.GameWorld;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytonicbedwars.utils.Events;

public class MapBuilderCommand extends Command {

    private final Set<UUID> ENABLED = new HashSet<>();

    public MapBuilderCommand() {
        super("test");
        setDefaultExecutor((sender, _) -> {
            BedwarsPlayer player = (BedwarsPlayer) sender;
            if (ENABLED.contains(player.getUuid())) {
                ENABLED.remove(player.getUuid());
                if (player.getInstance() instanceof GameWorld world) {
                    world.removeSpawnPlatform();
                    Objects.requireNonNull(player.getGame()).setStarted(true);
                    System.out.println("REMOVE");
                }
            } else {
                ENABLED.add(player.getUuid());
                if (player.getInstance() instanceof GameWorld world) {
                    world.placeSpawnPlatform();
                    Objects.requireNonNull(player.getGame()).setStarted(false);
                    System.out.println("PLACE");
                }
            }
        });

        Events.onPlayerBlockBreak(event -> {
            if (ENABLED.contains(event.getPlayer().getUuid())) {
                event.getPlayer().sendMessage(Component.text("Click to copy").clickEvent(ClickEvent.copyToClipboard("""
                    {
                      "x": %d,
                      "y": %d,
                      "z": %d
                    }
                    """.formatted(event.getBlockPosition().blockX(), event.getBlockPosition().blockY(),
                    event.getBlockPosition().blockZ()))));
                event.setCancelled(true);
            }
        });


    }
}

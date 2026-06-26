package net.cytonic.cytonicbedwars.events;

import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.Title.Times;
import net.kyori.adventure.util.Ticks;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.damage.DamageType;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.sound.SoundEvent;
import net.minestom.server.tag.Tag;

import net.cytonic.cytonicbedwars.config.BedwarsMap;
import net.cytonic.cytonicbedwars.game.Game;
import net.cytonic.cytonicbedwars.game.Team;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytonicbedwars.server.BedwarsServer;
import net.cytonic.cytonicbedwars.utils.Events;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.utils.Msg;

public class BedwarsListeners {

    public static final Tag<Boolean> PLACED_BY_PLAYER_TAG = Tag.Boolean("placed_by_player");

    public static void init(BedwarsServer server) {
        Events.onPlayerGameModeRequest(event -> event.getPlayer().setGameMode(event.getRequestedGameMode()));
        Events.onAsyncPlayerConfiguration(
            event -> {
                event.getPlayer().setPermissionLevel(4);
                Game game = server.getGames().values().stream().filter(it -> it.getMap() == BedwarsMap.FARM)
                    .findFirst().orElseThrow();

                event.setSpawningInstance(game.getWorld());
                event.getPlayer().setRespawnPoint(Game.SPAWN_POS);
                ((BedwarsPlayer) event.getPlayer()).UNSAFE_joinGame(game.getId());
            });
        Events.onEntityItemMerge(event -> event.setCancelled(true));
        Events.onPickupItem(event -> {
            if (!(event.getEntity() instanceof BedwarsPlayer player)) return;

            if (player.getGame().getSpectators().contains(player.getUuid())) {
                event.setCancelled(true);
                return;
            }

            //todo sword stuff
            ItemStack itemStack = event.getItemStack();
            event.setCancelled(!player.getInventory().addItemStack(itemStack));
        });

        Events.onPlayerBlockBreak(event -> {
            if (!(event.getPlayer() instanceof BedwarsPlayer player)) return;
            if (player.getGameMode() == GameMode.CREATIVE) return;

            Game game = player.getGame();
            if (game.isSpectator(player)) {
                event.setCancelled(true);
                return;
            }

            if (event.getBlock().key().value().contains("bed")) {
                if (player.getBedwarsTeam().getBedType().key().equals(event.getBlock().key())) {
                    player.sendMessage(Msg.whoops("You can't break your own bed!"));
                    event.setCancelled(true);
                    return;
                }

                Team team = game.getTeams().values().stream()
                    .filter(it -> it.getBedType().key().equals(event.getBlock().key())).findFirst().orElseThrow();

                Component message =
                    Msg.mm("<newline><red><b>BED DESTRUCTION!</b></red> ")
                        .append(team.getName())
                        .append(Msg.mm("<grey>'s Bed was <red><b>destroyed<b></red> by "))
                        .append(player.getBedwarsFormattedName())
                        .append(Msg.mm("<gray>!<newline>"));
                Cytosis.getOnlinePlayers().forEach(p -> {
                    player.playSound(Sound.sound(SoundEvent.ENTITY_GENERIC_EXPLODE, Sound.Source.PLAYER, 1f, 100f));
                    p.sendMessage(message);
                });
                for (BedwarsPlayer p : team.getPlayers()) {
                    Title title = Title.title(Msg.red("<b>BED DESTROYED!"), Msg.white("You will no longer respawn!"),
                        Times.times(Ticks.duration(10L), Ticks.duration(100L), Ticks.duration(20L)));
                    p.showTitle(title);
                }
                team.setBed(false);
                game.getWorld().breakBed(team);
                return;
            }

            if (!event.getBlock().hasTag(PLACED_BY_PLAYER_TAG)) {
                player.sendMessage(Msg.whoops("You can only break blocks placed by players!"));
                event.setCancelled(true);
            }

            //todo spawn dropped item
        });

        Events.onPlayerBlockPlace(event -> {
            if (!(event.getPlayer() instanceof BedwarsPlayer player)) return;
            if (player.getGameMode() == GameMode.CREATIVE) return;

            event.setBlock(event.getBlock().withTag(PLACED_BY_PLAYER_TAG, true));
        });

        Events.onEntityPreDeath(event -> {
            if (!(event.getEntity() instanceof BedwarsPlayer player)) return;
            if (!(event.getDamage().getAttacker() instanceof BedwarsPlayer attacker)) return;
            event.setCancelDeath(true);

            player.getGame().kill(player, attacker, event.getDamage().getType());
        });

        Events.onFinalDamage(event -> {
            if (!(event.getEntity() instanceof BedwarsPlayer player)) return;

            if (player.isSpectator() || !player.getGame().isStarted()) {
                event.setCancelled(true);
                return;
            }

            if (event.getDamage().getAttacker() instanceof BedwarsPlayer attacker && attacker.isInvulnerable()) {
                player.sendMessage(Msg.grey("You attacked someone and lost your invincibility!"));
                player.setInvulnerable(false);
            }
        });

        Events.onPlayerMove(event -> {
            if (!(event.getPlayer() instanceof BedwarsPlayer player)) return;
            if (!player.getGame().isStarted()) return;

            if (event.getNewPosition().y() <= -40) {
                event.setCancelled(true);

                if (player.isSpectator()) {
                    player.teleport(Game.SPAWN_POS);
                    return;
                }
                player.getGame().kill(player, null, DamageType.OUT_OF_WORLD);
            }

            if (player.getGameMode() == GameMode.CREATIVE) return;

            Pos spawn = Game.SPAWN_POS;
            if (distance(event.getNewPosition().x(), spawn.x(), event.getNewPosition().z(), spawn.z()) > 11025
                || event.getNewPosition().y() >= 50) {
                event.setCancelled(true);
                player.sendMessage(Msg.whoops("You cannot travel too far from the map!"));
            }
        });

        Events.onInventoryPreClick(event -> {
            if (!(event.getPlayer() instanceof BedwarsPlayer player)) return;
            if (player.getGameMode() == GameMode.CREATIVE) return;
            if (event.getClickedItem().material() == Material.WOODEN_SWORD) {
                event.setCancelled(true);
            }
        });
    }

    private static double distance(double x1, double x2, double z1, double z2) {
        return Math.pow(x1 - x2, 2) + Math.pow(z1 - z2, 2);
    }
}

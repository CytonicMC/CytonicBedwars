package dev.foxikle.webnetbedwars.managers;

import dev.foxikle.webnetbedwars.WebNetBedWars;
import me.flame.menus.builders.items.ItemBuilder;
import me.flame.menus.items.MenuItem;
import me.flame.menus.menu.ActionResponse;
import me.flame.menus.menu.Menu;
import me.flame.menus.menu.Menus;
import me.flame.menus.menu.fillers.Filler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class MenuManager {
    private final WebNetBedWars plugin;

    private static NamespacedKey UUID_KEY;

    public MenuManager(WebNetBedWars plugin) {
        this.plugin = plugin;
        UUID_KEY = new NamespacedKey(plugin, "UUID");
    }

    public Menu getSpectatorSelectorMenu() {
        Menu menu = Menus.menu().title("Spectate a Player").rows(2).addAllModifiers().create();

        plugin.getGameManager().getAlivePlayers().forEach(uuid -> {
            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) head.getItemMeta();
            meta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
            meta.getPersistentDataContainer().set(UUID_KEY, PersistentDataType.STRING, uuid.toString());
            meta.setLore(List.of(ChatColor.GRAY + "Click to teleport to " + Bukkit.getPlayer(uuid).getName()));
            meta.setDisplayName(ChatColor.GREEN + Bukkit.getPlayer(uuid).getName());
            head.setItemMeta(meta);
            MenuItem item = ItemBuilder.of(head).buildItem((slot, event) -> {
                Player player = event.getPlayer();
                player.teleport(Bukkit.getPlayer(uuid));
                return ActionResponse.DONE;
            });
            menu.addItem(item);
        });

        return menu;
    }

    public Menu getSpectatorSpeedMenu() {
        Menu menu = Menus.menu().title("Flight Speed").rows(3).addAllModifiers().create();

        ItemStack tenthSpeed = new ItemStack(Material.LEATHER_BOOTS);
        ItemMeta tenthMeta = tenthSpeed.getItemMeta();
        tenthMeta.addItemFlags(ItemFlag.values()); // adds all the flags
        tenthMeta.setDisplayName(ChatColor.GREEN + "0.1x flight speed");
        tenthSpeed.setItemMeta(tenthMeta);

        menu.setItem(11, ItemBuilder.of(tenthSpeed).buildItem((slot, event) -> {
            event.getPlayer().setFlySpeed(0.01f);
            event.getPlayer().closeInventory();
            event.getPlayer().sendMessage(ChatColor.GREEN + "Your flight speed is now " + ChatColor.GOLD + "0.1x");
            return ActionResponse.DONE;
        }));

        ItemStack halfSpeed = new ItemStack(Material.CHAINMAIL_BOOTS);
        ItemMeta halfMeta = halfSpeed.getItemMeta();
        halfMeta.addItemFlags(ItemFlag.values()); // adds all the flags
        halfMeta.setDisplayName(ChatColor.GREEN + ".5x flight speed");
        halfSpeed.setItemMeta(halfMeta);

        menu.setItem(12, ItemBuilder.of(halfSpeed).buildItem((slot, event) -> {
            event.getPlayer().setFlySpeed(0.05f);
            event.getPlayer().closeInventory();
            event.getPlayer().sendMessage(ChatColor.GREEN + "Your flight speed is now " + ChatColor.GOLD + "0.5x");
            return ActionResponse.DONE;
        }));

        ItemStack normalSpeed = new ItemStack(Material.IRON_BOOTS);
        ItemMeta normalMeta = normalSpeed.getItemMeta();
        normalMeta.addItemFlags(ItemFlag.values()); // adds all the flags
        normalMeta.setDisplayName(ChatColor.GREEN + "1x flight speed");
        normalSpeed.setItemMeta(normalMeta);

        menu.setItem(13, ItemBuilder.of(normalSpeed).buildItem((slot, event) -> {
            event.getPlayer().setFlySpeed(0.1f);
            event.getPlayer().closeInventory();
            event.getPlayer().sendMessage(ChatColor.GREEN + "Your flight speed is now " + ChatColor.GOLD + "1x");
            return ActionResponse.DONE;
        }));

        ItemStack x2Speed = new ItemStack(Material.GOLDEN_BOOTS);
        ItemMeta x2Meta = x2Speed.getItemMeta();
        x2Meta.addItemFlags(ItemFlag.values()); // adds all the flags
        x2Meta.setDisplayName(ChatColor.GREEN + "2x flight speed");
        x2Speed.setItemMeta(x2Meta);

        menu.setItem(14, ItemBuilder.of(x2Speed).buildItem((slot, event) -> {
            event.getPlayer().setFlySpeed(0.2f);
            event.getPlayer().closeInventory();
            event.getPlayer().sendMessage(ChatColor.GREEN + "Your flight speed is now " + ChatColor.GOLD + "2x");
            return ActionResponse.DONE;
        }));

        ItemStack x5Speed = new ItemStack(Material.DIAMOND_BOOTS);
        ItemMeta x5Meta = x5Speed.getItemMeta();
        x5Meta.addItemFlags(ItemFlag.values()); // adds all the flags
        x5Meta.setDisplayName(ChatColor.GREEN + "5x flight speed");
        x5Speed.setItemMeta(x5Meta);

        menu.setItem(15, ItemBuilder.of(x5Speed).buildItem((slot, event) -> {
            event.getPlayer().setFlySpeed(0.5f);
            event.getPlayer().closeInventory();
            event.getPlayer().sendMessage(ChatColor.GREEN + "Your flight speed is now " + ChatColor.GOLD + "5x");
            return ActionResponse.DONE;
        }));



        return menu;
    }
}

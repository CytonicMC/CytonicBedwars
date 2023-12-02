package dev.foxikle.webnetbedwars.managers;

import dev.foxikle.webnetbedwars.WebNetBedWars;
import me.flame.menus.builders.items.ItemBuilder;
import me.flame.menus.items.MenuItem;
import me.flame.menus.menu.ActionResponse;
import me.flame.menus.menu.Menu;
import me.flame.menus.menu.Menus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
        Menu menu = Menus.menu().title("Spectator Menu?").rows(2).addAllModifiers().create();

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
}

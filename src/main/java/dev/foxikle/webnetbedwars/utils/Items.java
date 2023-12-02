package dev.foxikle.webnetbedwars.utils;

import dev.foxikle.webnetbedwars.WebNetBedWars;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Items {
    private static final WebNetBedWars plugin = WebNetBedWars.INSTANCE;
    private static final Map<String, ItemStack> itemRegistry = new HashMap<>();
    public static NamespacedKey NAMESPACE = new NamespacedKey(plugin, "bwID");

    // item constants

    // spectator items
    public static ItemStack SPECTATOR_TARGET_SELECTOR = createItem(ChatColor.GREEN + "Target Selector", "SPECTATOR_COMPASS", Material.COMPASS, ChatColor.GRAY + "Right click to teleport to a player!");
    public static ItemStack SPECTATOR_SPEED_SELECTOR = createItem(ChatColor.AQUA + "Speed Selector", "SPECTATOR_BOOTS", Material.DIAMOND_BOOTS, ChatColor.GRAY + "Right click to change flight speed.");
    public static ItemStack SPECTATOR_LOBBY_REQEST = createItem(ChatColor.RED + "Go to Lobby", "LOBBY_REQUEST", Material.RED_BED, ChatColor.GOLD + "To the lobby!");

    // shop items
    public static ItemStack FIREBALL = createItem("Fireball", "FIREBALL", Material.FIRE_CHARGE);
    public static ItemStack TNT = createItem("TNT", "TNT", Material.TNT);

    private static ItemStack createItem(String displayname, String id, Material type, String... lore){
        ItemStack item = new ItemStack(type);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(displayname);
        meta.setLore(List.of(lore));
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(NAMESPACE, PersistentDataType.STRING, id);

        item.setItemMeta(meta);
        itemRegistry.put(id, item);
        return item;
    }

    @Nullable
    public static ItemStack get(String id){
        return itemRegistry.get(id);
    }

    public static Set<String> getItemIDs(){
        return itemRegistry.keySet();
    }
}

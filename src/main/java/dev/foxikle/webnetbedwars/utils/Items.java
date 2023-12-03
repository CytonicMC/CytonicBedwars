package dev.foxikle.webnetbedwars.utils;

import dev.foxikle.webnetbedwars.WebNetBedWars;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
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
    public static NamespacedKey MOVE_BLACKLIST = new NamespacedKey(plugin, "move_blacklist");
    public static NamespacedKey ALLOWED_SLOTS = new NamespacedKey(plugin, "allowed_slots");
    public static NamespacedKey NO_DROP = new NamespacedKey(plugin, "no_drop");

    // item constants

    // spectator items
    public static ItemStack SPECTATOR_TARGET_SELECTOR = createItem(ChatColor.GREEN + "Target Selector", "SPECTATOR_COMPASS", Material.COMPASS, true, true, List.of(0), ChatColor.GRAY + "Right click to teleport to a player!");
    public static ItemStack SPECTATOR_SPEED_SELECTOR = createItem(ChatColor.AQUA + "Speed Selector", "SPECTATOR_BOOTS", Material.HEART_OF_THE_SEA, true, true, List.of(4), ChatColor.GRAY + "Right click to change flight speed.");
    public static ItemStack SPECTATOR_LOBBY_REQEST = createItem(ChatColor.RED + "Go to Lobby", "LOBBY_REQUEST", Material.RED_BED, true, true, List.of(8), ChatColor.GOLD + "To the lobby!");
    public static ItemStack SPECTATOR_ARMOR = createItem("", "SPECTATOR_ARMOR", Material.BARRIER, true, true, List.of(36, 37, 38, 39));

    // shop items
    public static ItemStack FIREBALL = createItem("Fireball", "FIREBALL", Material.FIRE_CHARGE, false, false, List.of());
    public static ItemStack TNT = createItem("TNT", "TNT", Material.TNT, false, false, List.of());

    private static ItemStack createItem(String displayname, String id, Material type, boolean noMove, boolean noDrop, List<Integer> allowedSlots, String... lore){
        ItemStack item = new ItemStack(type);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(displayname);
        meta.setLore(List.of(lore));
        meta.addItemFlags(ItemFlag.values());
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        if(noDrop) {
            pdc.set(NO_DROP, PersistentDataType.BOOLEAN, true);
        }
        if(noMove) {
            pdc.set(MOVE_BLACKLIST, PersistentDataType.BOOLEAN, true);
            pdc.set(ALLOWED_SLOTS, PersistentDataType.INTEGER_ARRAY, allowedSlots.stream().mapToInt(Integer::intValue).toArray());
        }
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

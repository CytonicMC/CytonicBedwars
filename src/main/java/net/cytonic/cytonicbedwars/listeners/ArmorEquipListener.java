package net.cytonic.cytonicbedwars.listeners;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.utils.Items;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.GameMode;
import net.minestom.server.event.inventory.PlayerInventoryItemChangeEvent;
import net.minestom.server.item.Material;

import java.time.Duration;

@NoArgsConstructor
public class ArmorEquipListener {

    public void onArmorEquip(PlayerInventoryItemChangeEvent event) {
        if (event.getPreviousItem().material() == Material.BARRIER && event.getPlayer().getGameMode() == GameMode.ADVENTURE) {
            MinecraftServer.getSchedulerManager().buildTask(() -> {
                event.getPlayer().getInventory().setBoots(Items.SPECTATOR_ARMOR);
                event.getPlayer().getInventory().setLeggings(Items.SPECTATOR_ARMOR);
                event.getPlayer().getInventory().setChestplate(Items.SPECTATOR_ARMOR);
            }).delay(Duration.ofMillis(100));
        }
    }
}

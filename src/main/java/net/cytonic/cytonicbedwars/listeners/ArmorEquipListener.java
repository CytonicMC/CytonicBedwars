package net.cytonic.cytonicbedwars.listeners;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.utils.Items;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.EquipmentSlot;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.inventory.InventoryItemChangeEvent;
import net.minestom.server.inventory.PlayerInventory;
import net.minestom.server.item.Material;

import java.time.Duration;

@NoArgsConstructor
public class ArmorEquipListener {

    public static void onArmorEquip(InventoryItemChangeEvent event) {
        if (!(event.getInventory() instanceof PlayerInventory playerInventory)) return;
        for (Player player : playerInventory.getViewers()) {
            if (event.getPreviousItem().material() == Material.BARRIER && player.getGameMode() == GameMode.ADVENTURE) {
                MinecraftServer.getSchedulerManager().buildTask(() -> {
                    playerInventory.setEquipment(EquipmentSlot.BOOTS, player.getHeldSlot(), Items.SPECTATOR_ARMOR);
                    playerInventory.setEquipment(EquipmentSlot.LEGGINGS, player.getHeldSlot(), Items.SPECTATOR_ARMOR);
                    playerInventory.setEquipment(EquipmentSlot.CHESTPLATE, player.getHeldSlot(), Items.SPECTATOR_ARMOR);
                }).delay(Duration.ofMillis(100));
            }
        }
    }
}

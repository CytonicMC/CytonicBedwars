package net.cytonic.cytonicbedwars.listeners;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytonicbedwars.managers.GameManager;
import net.cytonic.cytonicbedwars.utils.Items;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.events.api.Listener;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.EquipmentSlot;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.inventory.InventoryItemChangeEvent;
import net.minestom.server.inventory.PlayerInventory;
import net.minestom.server.item.Material;

import java.time.Duration;

@NoArgsConstructor
@SuppressWarnings("unused")
public class ArmorEquipListener {

    @Listener
    public void onArmorEquip(InventoryItemChangeEvent event) {
        if (!(event.getInventory() instanceof PlayerInventory playerInventory)) return;
        for (Player player : playerInventory.getViewers()) {
            if (event.getPreviousItem().material() == Material.BARRIER && player.getGameMode() == GameMode.ADVENTURE && !Cytosis.CONTEXT.getComponent(GameManager.class).getSpectators().contains(player.getUuid())) {
                MinecraftServer.getSchedulerManager().buildTask(() -> {
                    playerInventory.setEquipment(EquipmentSlot.BOOTS, player.getHeldSlot(), Items.SPECTATOR_ARMOR);
                    playerInventory.setEquipment(EquipmentSlot.LEGGINGS, player.getHeldSlot(), Items.SPECTATOR_ARMOR);
                    playerInventory.setEquipment(EquipmentSlot.CHESTPLATE, player.getHeldSlot(), Items.SPECTATOR_ARMOR);
                }).delay(Duration.ofMillis(100)).schedule();
            }
        }
    }
}

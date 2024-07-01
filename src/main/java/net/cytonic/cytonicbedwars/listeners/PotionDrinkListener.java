package net.cytonic.cytonicbedwars.listeners;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.player.PlayerEatEvent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import java.time.Duration;

public class PotionDrinkListener {

    public void onPotionDrink(PlayerEatEvent e) {
        if(e.getItemStack().material() == Material.POTION || e.getItemStack().material() == Material.GLASS_BOTTLE) {
            MinecraftServer.getSchedulerManager().buildTask(() -> e.getPlayer().setItemInHand(e.getHand(), ItemStack.AIR)).delay(Duration.ofMillis(100));
        }
    }
}

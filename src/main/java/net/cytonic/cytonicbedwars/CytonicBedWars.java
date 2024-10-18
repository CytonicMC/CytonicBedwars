package net.cytonic.cytonicbedwars;

import com.google.gson.JsonObject;
import lombok.Getter;
import net.cytonic.cytonicbedwars.commands.DebugCommand;
import net.cytonic.cytonicbedwars.commands.ItemCommand;
import net.cytonic.cytonicbedwars.listeners.*;
import net.cytonic.cytonicbedwars.managers.GameManager;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.events.EventListener;
import net.cytonic.cytosis.logging.Logger;
import net.cytonic.cytosis.plugins.CytosisPlugin;
import net.minestom.server.event.entity.EntityDamageEvent;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.event.inventory.PlayerInventoryItemChangeEvent;
import net.minestom.server.event.item.ItemDropEvent;
import net.minestom.server.event.player.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@Getter
public final class CytonicBedWars implements CytosisPlugin {

    public static final String version = "0.1";
    @Getter
    private static GameManager gameManager;
    @Getter
    private static ItemAbilityDispatcher itemAbilityDispatcher;

    @Override
    public void initialize() {
        createConfig();
        gameManager = new GameManager();
        gameManager.setup();
        registerCommands();
        registerListeners();

        itemAbilityDispatcher = new ItemAbilityDispatcher();
    }

    @Override
    public void shutdown() {
        gameManager.cleanup();
    }

    private void registerListeners() {
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:BlockBreakEvent", false, 2, PlayerBlockBreakEvent.class, event -> new BlockBreakListener().onBlockBreak(event)));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:PlayerEatEvent", false, 2, PlayerEatEvent.class, (event -> new PotionDrinkListener().onPotionDrink(event))));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:ArmorEquipEvent", false, 2, PlayerInventoryItemChangeEvent.class, (event -> new ArmorEquipListener().onArmorEquip(event))));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:BlockPlaceEvent", false, 2, PlayerBlockPlaceEvent.class, (event -> new BlockPlaceListener().onBlockPlace(event))));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:DamageEvent", false, 2, EntityDamageEvent.class, (event -> new DamageListener().onDamage(event))));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:DropItemEvent", false, 2, ItemDropEvent.class, (event -> new DropItemListener().onDrop(event))));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:GamemodeChangeEvent", false, 2, PlayerGameModeChangeEvent.class, (event -> new GamemodeChangeListener().onGamemodeChange(event))));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:PlayerBlockInteractEvent", false, 2, PlayerBlockInteractEvent.class, (event -> new InteractListener().onInteract(event))));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:PlayerEntityInteractEvent", false, 2, PlayerEntityInteractEvent.class, (event -> new InteractListener().onInteract(event))));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:InventoryPreClickEvent", false, 2, InventoryPreClickEvent.class, (event -> new InventoryClickListener().onInventoryClick(event))));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:AsyncPlayerConfigurationEvent", true, 2, AsyncPlayerConfigurationEvent.class, (event -> new JoinListener().onJoin(event))));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:PlayerSpawnEvent", false, 2, PlayerSpawnEvent.class, (event -> new JoinListener().onJoin(event))));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:PlayerDisconnectEvent", false, 2, PlayerDisconnectEvent.class, (event -> new LeaveListener().onLeave(event))));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:PlayerMoveEvent", false, 2, PlayerMoveEvent.class, (event -> new MoveListener().onMove(event))));
    }

    private void registerCommands() {
        Cytosis.getCommandManager().register(new DebugCommand());
        Cytosis.getCommandManager().register(new ItemCommand());
    }

    private void createConfig() {
        File file = new File("configs/bedwars/config.json");
        if (!file.exists()) {
            Logger.info("File does not exist");
            InputStream stream = Objects.requireNonNull(CytonicBedWars.class.getResourceAsStream("/config.json"));
            try {
                Files.createDirectories(Path.of("configs/bedwars"));
            } catch (IOException e) {
                Logger.error("error", e);
            }
            Cytosis.getFileManager().extractResource(stream, Path.of("configs/bedwars/config.json")).whenComplete(((_, _) -> loadConfig()));
        } else loadConfig();
    }

    private void loadConfig() {
        File file = new File("configs/bedwars/config.json");
        JsonObject obj;
        try {
            FileInputStream stream = new FileInputStream(file);
            String string = new String(stream.readAllBytes());
            stream.close();
            obj = Cytosis.GSON.fromJson(string, JsonObject.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CytonicBedwarsSettings.importConfig(Objects.requireNonNull(obj));
    }
}

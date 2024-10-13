package net.cytonic.cytonicbedwars;

import com.google.gson.JsonObject;
import lombok.Getter;
import net.cytonic.cytonicbedwars.commands.DebugCommand;
import net.cytonic.cytonicbedwars.commands.ItemCommand;
import net.cytonic.cytonicbedwars.listeners.*;
import net.cytonic.cytonicbedwars.managers.GameManager;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.config.CytosisSettings;
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
        CytosisSettings.TAB_LIST_ENABLED = false;
        CytosisSettings.SIDEBOARD_ENABLED = false;
        Logger.debug("1");
        createConfig();
        Logger.debug("2");
        gameManager = new GameManager();
        Logger.debug("3");
        registerCommands();
        Logger.debug("4");
        registerListeners();
        Logger.debug("5");
        gameManager.setup();
        Logger.debug("after setup");
        itemAbilityDispatcher = new ItemAbilityDispatcher();
    }

    @Override
    public void shutdown() {
        gameManager.cleanup();
    }

    private void registerListeners() {
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:PlayerEatEvent", true, 2,
                PlayerEatEvent.class, (event -> new PotionDrinkListener().onPotionDrink(event))));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:ArmorEquipEvent", true, 2,
                PlayerInventoryItemChangeEvent.class, (event -> new ArmorEquipListener().onArmorEquip(event))));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:BlockPlaceEvent", true, 2,
                PlayerBlockPlaceEvent.class, (event -> new BlockPlaceListener(this).onBlockPlace(event))));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:BlockBreakEvent", true, 2,
                PlayerBlockBreakEvent.class, (event -> new BlockBreakListener(this).onBlockBreak(event))));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:DamageEvent", true, 2,
                EntityDamageEvent.class, (event -> new DamageListener(this).onDamage(event))));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:DropItemEvent", true, 2,
                ItemDropEvent.class, (event -> new DropItemListener(this).onDrop(event))));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:GamemodeChangeEvent", true, 2,
                PlayerGameModeChangeEvent.class, (event -> new GamemodeChangeListener(this).onGamemodeChange(event))));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:PlayerBlockInteractEvent", true, 2,
                PlayerBlockInteractEvent.class, (event -> new InteractListener(this).onInteract(event))));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:PlayerEntityInteractEvent", true, 2,
                PlayerEntityInteractEvent.class, (event -> new InteractListener(this).onInteract(event))));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:InventoryPreClickEvent", true, 2,
                InventoryPreClickEvent.class, (event -> new InventoryClickListener(this).onInventoryClick(event))));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:AsyncPlayerConfigurationEvent", true, 2,
                AsyncPlayerConfigurationEvent.class, (event -> new JoinListener().onJoin(event))));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:PlayerSpawnEvent", true, 2,
                PlayerSpawnEvent.class, (event -> new JoinListener().onJoin(event))));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:PlayerDisconnectEvent", true, 2,
                PlayerDisconnectEvent.class, (event -> new LeaveListener(this).onLeave(event))));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:PlayerMoveEvent", true, 2,
                PlayerMoveEvent.class, (event -> new MoveListener(this).onMove(event))));
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

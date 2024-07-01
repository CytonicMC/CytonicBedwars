package net.cytonic.cytonicbedwars;

import lombok.Getter;
import net.cytonic.cytonicbedwars.commands.*;
import net.cytonic.cytonicbedwars.managers.GameManager;
import net.cytonic.cytonicbedwars.listeners.*;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.events.EventListener;
import net.cytonic.cytosis.plugins.CytosisPlugin;
import net.cytonic.cytosis.plugins.Plugin;
import net.minestom.server.event.entity.EntityDamageEvent;
import net.minestom.server.event.inventory.*;
import net.minestom.server.event.item.ItemDropEvent;
import net.minestom.server.event.player.*;

@Getter
@Plugin(name = "bedwars", version = "0.1")
public final class CytonicBedWars implements CytosisPlugin {

    private GameManager gameManager;
    public static CytonicBedWars INSTANCE;
    private ItemAbilityDispatcher itemAbilityDispatcher;
    public static final String version = "0.1";

    @Override
    public void initialize() {
        INSTANCE = this;
        //todo config
//        File file = new File("plugins/CytonicBedWars/config.yml");
//        if(!file.exists())
//            this.saveResource("config.yml", false);

        gameManager = new GameManager(this);
        registerCommands();
        registerListeners();
        gameManager.setup();
        itemAbilityDispatcher = new ItemAbilityDispatcher(this);
    }

    @Override
    public void shutdown() {
        gameManager.cleanup();
    }

    private void registerListeners(){
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:PlayerEatEvent", true, 2,
                PlayerEatEvent.class, (event -> new PotionDrinkListener().onPotionDrink(event))));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:ArmorEquipEvent", true, 2,
                PlayerInventoryItemChangeEvent.class, (event -> new ArmorEquipListener().onArmorEquip(event))));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:BlockPlaceEvent", true, 2,
                PlayerBlockPlaceEvent.class, (event -> new BlockPlaceListener(this).onBlockPlace(event))));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:BlockBreakEvent", true, 2,
                PlayerBlockBreakEvent.class, (event -> new BlockBreakListener(this).onBlockBreak(event))));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:DamageEvent", true, 2,
                EntityDamageEvent.class, (event -> {new DamageListener(this).onDamage(event);})));
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
                AsyncPlayerConfigurationEvent.class, (event -> new JoinListener(this).onJoin(event))));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:PlayerSpawnEvent", true, 2,
                PlayerSpawnEvent.class, (event -> new JoinListener(this).onJoin(event))));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:PlayerDisconnectEvent", true, 2,
                PlayerDisconnectEvent.class, (event -> new LeaveListener(this).onLeave(event))));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:PlayerMoveEvent", true, 2,
                PlayerMoveEvent.class, (event -> new MoveListener(this).onMove(event))));
    }

    private void registerCommands(){
        Cytosis.getCommandManager().register(new DebugCommand(this));
        Cytosis.getCommandManager().register(new ItemCommand(this));
    }
}

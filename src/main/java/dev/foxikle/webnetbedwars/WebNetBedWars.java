package dev.foxikle.webnetbedwars;

import dev.foxikle.webnetbedwars.listeners.*;
import dev.foxikle.webnetbedwars.managers.GameManager;
import net.minecraft.world.level.block.Blocks;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;

public final class WebNetBedWars extends JavaPlugin {

    private GameManager gameManager;

    @Override
    public void onEnable() {
        File file = new File("plugins/WebNetBedWars/config.yml");
        if(!file.exists())
            this.saveResource("config.yml", false);

        gameManager = new GameManager(this);
        registerCommands();
        registerListeners();
        gameManager.setup();
        //aF == resistence

        Arrays.stream(Blocks.GLASS.getClass().getSuperclass().getSuperclass().getSuperclass().getSuperclass().getDeclaredFields()).forEach(field -> {
            if(field.getType() == float.class){
                try {
                    field.setAccessible(true);
                    if(field.getName().equals("aF")){
                        field.setFloat(Blocks.GLASS, 1200.0F);

                        field.setFloat(Blocks.BLACK_STAINED_GLASS, 1200.0F);
                        field.setFloat(Blocks.WHITE_STAINED_GLASS, 1200.0F);
                        field.setFloat(Blocks.CYAN_STAINED_GLASS, 1200.0F);
                        field.setFloat(Blocks.LIGHT_BLUE_STAINED_GLASS, 1200.0F);
                        field.setFloat(Blocks.BLUE_STAINED_GLASS, 1200.0F);
                        field.setFloat(Blocks.PINK_STAINED_GLASS, 1200.0F);
                        field.setFloat(Blocks.PURPLE_STAINED_GLASS, 1200.0F);
                        field.setFloat(Blocks.LIME_STAINED_GLASS, 1200.0F);
                        field.setFloat(Blocks.GREEN_STAINED_GLASS, 1200.0F);
                        field.setFloat(Blocks.RED_STAINED_GLASS, 1200.0F);
                        field.setFloat(Blocks.ORANGE_STAINED_GLASS, 1200.0F);
                        field.setFloat(Blocks.YELLOW_STAINED_GLASS, 1200.0F);
                        field.setFloat(Blocks.LIGHT_GRAY_STAINED_GLASS, 1200.0F);
                        field.setFloat(Blocks.GRAY_STAINED_GLASS, 1200.0F);
                        field.setFloat(Blocks.BROWN_STAINED_GLASS, 1200.0F);
                        field.setFloat(Blocks.MAGENTA_STAINED_GLASS, 1200.0F);

                        field.setFloat(Blocks.BLACK_BED, 1200.0F);
                        field.setFloat(Blocks.WHITE_BED, 1200.0F);
                        field.setFloat(Blocks.CYAN_BED, 1200.0F);
                        field.setFloat(Blocks.LIGHT_BLUE_BED, 1200.0F);
                        field.setFloat(Blocks.BLUE_BED, 1200.0F);
                        field.setFloat(Blocks.PINK_BED, 1200.0F);
                        field.setFloat(Blocks.PURPLE_BED, 1200.0F);
                        field.setFloat(Blocks.LIME_BED, 1200.0F);
                        field.setFloat(Blocks.GREEN_BED, 1200.0F);
                        field.setFloat(Blocks.RED_BED, 1200.0F);
                        field.setFloat(Blocks.ORANGE_BED, 1200.0F);
                        field.setFloat(Blocks.YELLOW_BED, 1200.0F);
                        field.setFloat(Blocks.LIGHT_GRAY_BED, 1200.0F);
                        field.setFloat(Blocks.GRAY_BED, 1200.0F);
                        field.setFloat(Blocks.BROWN_BED, 1200.0F);
                        field.setFloat(Blocks.MAGENTA_BED, 1200.0F);
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void onDisable() {
        gameManager.cleanup();
    }

    private void registerListeners(){
        getServer().getPluginManager().registerEvents(new DamageListener(this), this);
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(this), this);
        getServer().getPluginManager().registerEvents(new ExplosionListener(this), this);
        getServer().getPluginManager().registerEvents(new InteractListener(this), this);
    }



    private void registerCommands(){
        getCommand("debug").setExecutor(new DebugCommand(this));
    }

    public GameManager getGameManager() {
        return gameManager;
    }
}

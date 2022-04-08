package net.quickwrite.cansteingottesdienst;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import net.quickwrite.cansteingottesdienst.blocks.Blocks;
import net.quickwrite.cansteingottesdienst.blocks.TestBlock;
import net.quickwrite.cansteingottesdienst.builder.recipes.FurnaceRecipeBuilder;
import net.quickwrite.cansteingottesdienst.builder.recipes.RecipeBuilder;
import net.quickwrite.cansteingottesdienst.commands.GetCustomBlockCommand;
import net.quickwrite.cansteingottesdienst.commands.rlgl.RedLightGreenLightCommand;
import net.quickwrite.cansteingottesdienst.listener.block.BlockInteractListener;
import net.quickwrite.cansteingottesdienst.rlgl.RedLightGreenLightGame;
import net.quickwrite.cansteingottesdienst.rlgl.RedLightGreenLightSettings;
import net.quickwrite.cansteingottesdienst.tabcomplete.GetCustomBlockTabCompleter;
import net.quickwrite.cansteingottesdienst.tabcomplete.RedLightGreenLightTabCompleter;
import net.quickwrite.cansteingottesdienst.util.storage.Flags;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import net.quickwrite.cansteingottesdienst.listener.BlockListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.java.JavaPlugin;

public final class CansteinGottesdienst extends JavaPlugin {

    private static CansteinGottesdienst instance;
    public static Blocks BLOCKS;

    public static WorldGuardPlugin WORLDGUARD_PLUGIN;
    public static String PREFIX = "[Gottesdienst] ";
    public static final String PATH = "canstein";

    private RedLightGreenLightGame raceGame;

    @Override
    public void onLoad() {
        initializeWorldGuard();
    }

    @Override
    public void onEnable() {
        instance = this;
        ConfigurationSerialization.registerClass(RedLightGreenLightSettings.class);

        // registration of custom Blocks
        BLOCKS = new Blocks();
        BLOCKS.register(new TestBlock());

        // register Commands
        PluginCommand rlglCommand = getCommand("rlgl");
        assert rlglCommand != null;
        rlglCommand.setExecutor(new RedLightGreenLightCommand());
        rlglCommand.setTabCompleter(new RedLightGreenLightTabCompleter());

        PluginCommand getCustomBlockCommand = getCommand("getCustomBlock");
        assert getCustomBlockCommand != null;
        getCustomBlockCommand.setExecutor(new GetCustomBlockCommand());
        getCustomBlockCommand.setTabCompleter(new GetCustomBlockTabCompleter());

        // register EventListener
        getServer().getPluginManager().registerEvents(new BlockListener(), this);
        getServer().getPluginManager().registerEvents(new BlockInteractListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void initializeWorldGuard() {
        // This can be cast as every WorldGuardPlugin is a JavaPlugin that is a Plugin
        WORLDGUARD_PLUGIN = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");

        // register the flags from here
        Flags.INFINITE_CROPS = Flags.addFlag("infinite-crops", false);
        Flags.CUSTOM_BLOCKS = Flags.addFlag("custom-blocks", false);
    }

    public boolean initGame(Player player){
        if(raceGame != null) {
            raceGame.stop();
        }
        raceGame = new RedLightGreenLightGame(player.getWorld());
        if(!raceGame.isValid()) {
            raceGame = null;
            return false;
        }
        raceGame.start();
        return true;
    }

    public RedLightGreenLightGame getRaceGame() {
        return raceGame;
    }

    public static CansteinGottesdienst getInstance() {
        return instance;
    }

    public void stopGame() {
        if(raceGame != null){
            raceGame.stop();
        }
        raceGame = null;
    }
}

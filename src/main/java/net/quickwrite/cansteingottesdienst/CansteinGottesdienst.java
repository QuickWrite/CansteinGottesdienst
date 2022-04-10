package net.quickwrite.cansteingottesdienst;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import net.quickwrite.cansteingottesdienst.blocks.Blocks;
import net.quickwrite.cansteingottesdienst.blocks.CeleryBlock;
import net.quickwrite.cansteingottesdienst.blocks.GrapesBlock;
import net.quickwrite.cansteingottesdienst.blocks.WorldBlockPair;
import net.quickwrite.cansteingottesdienst.builder.items.ItemBuilder;
import net.quickwrite.cansteingottesdienst.commands.CustomBlockCommand;
import net.quickwrite.cansteingottesdienst.commands.rlgl.RedLightGreenLightCommand;
import net.quickwrite.cansteingottesdienst.config.CustomBlockConfig;
import net.quickwrite.cansteingottesdienst.listener.FoodListener;
import net.quickwrite.cansteingottesdienst.listener.block.BlockInteractListener;
import net.quickwrite.cansteingottesdienst.rlgl.RedLightGreenLightGame;
import net.quickwrite.cansteingottesdienst.rlgl.RedLightGreenLightSettings;
import net.quickwrite.cansteingottesdienst.commands.tabcomplete.CustomBlockCommandTabCompleter;
import net.quickwrite.cansteingottesdienst.commands.tabcomplete.RedLightGreenLightTabCompleter;
import net.quickwrite.cansteingottesdienst.util.CropInfo;
import net.quickwrite.cansteingottesdienst.util.storage.Flags;
import org.bukkit.Material;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import net.quickwrite.cansteingottesdienst.listener.BlockListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class CansteinGottesdienst extends JavaPlugin {

    private static CansteinGottesdienst instance;
    public static Blocks BLOCKS;

    public static WorldGuardPlugin WORLDGUARD_PLUGIN;
    public static String PREFIX = "[Gottesdienst] ";
    public static final String PATH = "canstein";
    public static CustomBlockConfig CUSTOM_BLOCK_CONFIG;

    private RedLightGreenLightGame raceGame;

    @Override
    public void onLoad() {
        initializeWorldGuard();
    }

    @Override
    public void onEnable() {
        instance = this;
        ConfigurationSerialization.registerClass(RedLightGreenLightSettings.class);
        ConfigurationSerialization.registerClass(WorldBlockPair.class);
        CUSTOM_BLOCK_CONFIG = new CustomBlockConfig();

        // registration of custom Blocks
        BLOCKS = new Blocks();
        BLOCKS.register(new CeleryBlock());
        BLOCKS.register(new GrapesBlock());

        initializeCrops();

        // register Commands
        PluginCommand rlglCommand = getCommand("rlgl");
        assert rlglCommand != null;
        rlglCommand.setExecutor(new RedLightGreenLightCommand());
        rlglCommand.setTabCompleter(new RedLightGreenLightTabCompleter());

        PluginCommand customBlockCommand = getCommand("customblock");
        assert customBlockCommand != null;
        customBlockCommand.setExecutor(new CustomBlockCommand());
        customBlockCommand.setTabCompleter(new CustomBlockCommandTabCompleter());

        // register EventListener
        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new BlockListener(), this);
        pluginManager.registerEvents(new BlockInteractListener(), this);
        pluginManager.registerEvents(new FoodListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        CropInfo.flush();
        BLOCKS.save();
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

    private void initializeCrops() {
        ArrayList<ItemStack> wheatDrop = new ArrayList<>();
        wheatDrop.add(new ItemBuilder(Material.WHEAT).setAmount(3).build());

        ArrayList<ItemStack> carrotDrop = new ArrayList<>();
        carrotDrop.add(new ItemBuilder(Material.CARROT).setAmount(3).build());

        CropInfo.addCrop(Material.WHEAT, wheatDrop);
        CropInfo.addCrop(Material.CARROTS, carrotDrop);

        CropInfo.addCrop(new CeleryBlock());
        CropInfo.addCrop(new GrapesBlock());
    }
}

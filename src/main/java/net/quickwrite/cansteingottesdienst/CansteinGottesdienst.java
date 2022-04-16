package net.quickwrite.cansteingottesdienst;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import net.quickwrite.cansteingottesdienst.blocks.*;
import net.quickwrite.cansteingottesdienst.builder.items.ItemBuilder;
import net.quickwrite.cansteingottesdienst.builder.recipes.FurnaceRecipeBuilder;
import net.quickwrite.cansteingottesdienst.builder.recipes.ShapedRecipeBuilder;
import net.quickwrite.cansteingottesdienst.builder.recipes.ShapelessRecipeBuilder;
import net.quickwrite.cansteingottesdienst.commands.*;
import net.quickwrite.cansteingottesdienst.commands.rlgl.RedLightGreenLightCommand;
import net.quickwrite.cansteingottesdienst.commands.tabcomplete.CustomBlockCommandTabCompleter;
import net.quickwrite.cansteingottesdienst.commands.tabcomplete.CustomItemCommandTabCompleter;
import net.quickwrite.cansteingottesdienst.commands.tabcomplete.RedLightGreenLightTabCompleter;
import net.quickwrite.cansteingottesdienst.commands.tabcomplete.TrackerMapCommandTabCompleter;
import net.quickwrite.cansteingottesdienst.config.DefaultConfig;
import net.quickwrite.cansteingottesdienst.config.MapInformationConfig;
import net.quickwrite.cansteingottesdienst.items.Items;
import net.quickwrite.cansteingottesdienst.listener.*;
import net.quickwrite.cansteingottesdienst.listener.block.BlockInteractListener;
import net.quickwrite.cansteingottesdienst.rlgl.RedLightGreenLightGame;
import net.quickwrite.cansteingottesdienst.rlgl.RedLightGreenLightSettings;
import net.quickwrite.cansteingottesdienst.util.CropInfo;
import net.quickwrite.cansteingottesdienst.util.storage.Flags;
import net.quickwrite.cansteingottesdienst.util.storage.WinepressList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class CansteinGottesdienst extends JavaPlugin {

    private static CansteinGottesdienst instance;
    public static Blocks BLOCKS;

    public static WorldGuardPlugin WORLDGUARD_PLUGIN;
    public static String PREFIX;
    public static final String PATH = "canstein";

    private RedLightGreenLightGame raceGame;
    private MapInformationConfig mapInformationConfig;
    private DefaultConfig config;

    @Override
    public void onLoad() {
        initializeWorldGuard();
    }

    @Override
    public void onEnable() {
        instance = this;
        ConfigurationSerialization.registerClass(RedLightGreenLightSettings.class);

        config = new DefaultConfig();
        mapInformationConfig = new MapInformationConfig();

        PREFIX = config.getConfig().getString("prefix", "[Gottesdienst] ");

        // registration of custom Blocks
        BLOCKS = new Blocks();
        BLOCKS.register(new CeleryBlock());
        BLOCKS.register(new GrapesBlock());
        BLOCKS.register(new EmtpyGrapesBlock());
        BLOCKS.register(new OxTongueBlock());

        initializeCrops();

        // register Commands
        registerCommand("rlgl", new RedLightGreenLightCommand(), new RedLightGreenLightTabCompleter());
        registerCommand("customblock", new CustomBlockCommand(), new CustomBlockCommandTabCompleter());
        registerCommand("initMap", new InitMapCommand(), null);
        registerCommand("customItem", new CustomItemCommand(), new CustomItemCommandTabCompleter());
        registerCommand("pmsg", new PMsgCommand(), null);
        registerCommand("trackerMap", new TrackerMapCommand(), new TrackerMapCommandTabCompleter());
        registerCommand("setupmap", new MapInformationCommand(), null);

        // register EventListener
        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new BlockListener(), this);
        pluginManager.registerEvents(new BlockInteractListener(), this);
        pluginManager.registerEvents(new FoodListener(), this);
        pluginManager.registerEvents(new ConveyorListener(), this);
        pluginManager.registerEvents(new EntityChangeListener(), this);
        pluginManager.registerEvents(new MapListener(), this);
        pluginManager.registerEvents(new WinepressJumpListener(), this);

        initializeRecipes();
    }

    public void registerCommand(String name, CommandExecutor executor, TabCompleter tabCompleter){
        PluginCommand command = getCommand(name);
        assert command != null;
        command.setExecutor(executor);
        command.setTabCompleter(tabCompleter);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        CropInfo.flush();

        WinepressList.delete();
    }

    public void initializeWorldGuard() {
        // This can be cast as every WorldGuardPlugin is a JavaPlugin that is a Plugin
        WORLDGUARD_PLUGIN = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");

        // register the flags from here
        Flags.INFINITE_CROPS = Flags.addFlag("infinite-crops", false);
        Flags.CUSTOM_BLOCKS = Flags.addFlag("custom-blocks", false);
        Flags.SHEEP_TO_LAMB = Flags.addFlag("sheep-to-lamb", false);

        Flags.WINE_PRESS = Flags.addFlag("wine-press");
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
    }

    private void initializeRecipes() {
        //System.out.println(Items.FLOUR.getItemStack());

        /*Recipe breadRecipe = new ShapedRecipeBuilder(this, "bread", Items.BREAD.getItemStack())
                .setShape("aaa", " b ")
                .addShapedIngredient('a', new RecipeChoice.ExactChoice(Items.FLOUR.getItemStack()))
                .addShapedIngredient('b', Material.POTION)
                .build();

         */
        Recipe breadRecipe = new ShapelessRecipeBuilder(this, "bread", Items.BREAD.getItemStack())
                .addShapelessIngredient(new RecipeChoice.ExactChoice(Items.FLOUR.getItemStack()))
                .addShapelessIngredient(new RecipeChoice.ExactChoice(Items.FLOUR.getItemStack()))
                .addShapelessIngredient(new RecipeChoice.ExactChoice(Items.FLOUR.getItemStack()))
                .addShapelessIngredient(Material.POTION)
                .build();
        Recipe oxTonguePowder = new ShapelessRecipeBuilder(this, "ox_tongue_powder",
                Items.OX_TONGUE_POWDER.getItemStack())
                .addShapelessIngredient(new RecipeChoice.ExactChoice(Items.OX_TONGUE.getItemStack()))
                .build();
        Recipe cookLambGigot = new FurnaceRecipeBuilder(this, "cook_lamb_gigot",
                Items.COOKED_LAMB_GIGOT.getItemStack())
                .setInput(new RecipeChoice.ExactChoice(Items.LAMB_GIGOT.getItemStack()))
                .setExperience(0)
                .build();

        Bukkit.removeRecipe(NamespacedKey.fromString("bread"));

        addRecipe(breadRecipe);
        addRecipe(oxTonguePowder);
        addRecipe(cookLambGigot);
    }

    private boolean addRecipe(Recipe recipe) {
        return Bukkit.addRecipe(recipe);
    }

    public DefaultConfig getDefaultConfig() {
        return config;
    }

    public MapInformationConfig getMapInformationConfig() {
        return mapInformationConfig;
    }
}

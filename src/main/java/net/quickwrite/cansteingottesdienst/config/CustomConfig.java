package net.quickwrite.cansteingottesdienst.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class CustomConfig {

    private File configFile;
    private FileConfiguration config;

    public CustomConfig(JavaPlugin plugin, String name){
        createCustomConfig(plugin, name);
    }

    public void createCustomConfig(JavaPlugin plugin, String name){
        configFile = new File(plugin.getDataFolder(), name);
        if(!configFile.exists()){
            configFile.getParentFile().mkdirs();
            plugin.saveResource(name, false);
        }

        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void saveConfig(){
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }
}

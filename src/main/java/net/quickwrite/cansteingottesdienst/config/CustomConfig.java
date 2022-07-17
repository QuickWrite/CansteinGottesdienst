package net.quickwrite.cansteingottesdienst.config;

import com.google.common.base.Charsets;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CustomConfig {

    private File configFile;
    private FileConfiguration config;
    private JavaPlugin plugin;

    public CustomConfig(JavaPlugin plugin, String name){
        this.plugin = plugin;
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

    public void reloadConfig(){
        config = YamlConfiguration.loadConfiguration(configFile);

        final InputStream defConfigStream = plugin.getResource("config.yml");
        if (defConfigStream == null) {
            return;
        }

        config.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8)));
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

package net.quickwrite.cansteingottesdienst.map;

import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import net.quickwrite.cansteingottesdienst.items.Items;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MapInformation {

    public static final MapInformation INSTANCE;

    private final HashMap<Items, Integer> toSearchAmounts;
    private final HashMap<String, Integer> amounts;
    private int x, y, z;

    static {
        INSTANCE = new MapInformation();
    }

    private MapInformation() {
        amounts = new HashMap<>();
        toSearchAmounts = new HashMap<>();
        x = y = z = 0;

        loadFromConfig();
    }

    public void startTracker(Item item){
        new BukkitRunnable(){

            @Override
            public void run() {
                if(item.isOnGround()){
                    if(item.getLocation().distance(new Location(item.getWorld(), x + .5, y + .5, z + .5)) < 3) {
                        ItemStack itemStack = item.getItemStack();
                        if(addItem(itemStack, itemStack.getAmount()))
                            item.remove();
                    }
                    cancel();
                }
            }
        }.runTaskTimer(CansteinGottesdienst.getInstance(), 0, 1);
    }

    public void loadFromConfig(){
        FileConfiguration config = CansteinGottesdienst.getInstance().getMapInformationConfig().getConfig();
        toSearchAmounts.clear();
        if(!config.contains("map.information")){
            toSearchAmounts.put(Items.BREAD, 10);
            toSearchAmounts.put(Items.CELERY, 10);
            toSearchAmounts.put(Items.OX_TONGUE_POWDER, 10);
            toSearchAmounts.put(Items.WINE, 10);
            toSearchAmounts.put(Items.COOKED_LAMB_GIGOT, 10);
            save();
            convert();
            return;
        }
        x = config.getInt("map.information.x");
        y = config.getInt("map.information.y");
        z = config.getInt("map.information.z");

        ConfigurationSection section = config.getConfigurationSection("map.information.items");
        if (section == null) return;
        for(Map.Entry<String, Object> entry : section.getValues(false).entrySet()){
            Items item = Items.valueOf(entry.getKey().toUpperCase(Locale.ROOT));
            int amount = (int) entry.getValue();
            toSearchAmounts.put(item, amount);
        }
        convert();
    }

    private void convert(){
        amounts.clear();
        for(Items item : toSearchAmounts.keySet()){
            amounts.put(item.name(), 0);
        }
    }

    public void setup(Location loc){
        x = loc.getBlockX();
        y = loc.getBlockY();
        z = loc.getBlockZ();

        save();
    }

    public void save(){
        FileConfiguration config = CansteinGottesdienst.getInstance().getMapInformationConfig().getConfig();
        config.set("map.information.x", x);
        config.set("map.information.y", y);
        config.set("map.information.z", z);
        for(Items i : toSearchAmounts.keySet()){
            config.set("map.information.items." + i.name(), toSearchAmounts.get(i));
        }
        CansteinGottesdienst.getInstance().getMapInformationConfig().saveConfig();
    }

    public boolean addItem(ItemStack item, int amount){
        for(Items i : toSearchAmounts.keySet()){
            if(item.isSimilar(i.getItemStack())) {
                String key = i.name();
                amounts.replace(key, amounts.get(key) + amount);
                DisplayMapRenderer.INSTANCE.update();
                return true;
            }
        }
        return false;
    }

    public HashMap<Items, Integer> getToSearchAmounts() {
        return toSearchAmounts;
    }

    public HashMap<String, Integer> getAmounts() {
        return amounts;
    }

    public void reset() {
        convert();
        DisplayMapRenderer.INSTANCE.update();
    }
}

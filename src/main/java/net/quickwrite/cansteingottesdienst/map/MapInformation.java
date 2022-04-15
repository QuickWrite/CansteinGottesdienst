package net.quickwrite.cansteingottesdienst.map;

import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import net.quickwrite.cansteingottesdienst.items.Items;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

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

    private void loadFromConfig(){
        FileConfiguration config = CansteinGottesdienst.getInstance().getMapInformationConfig().getConfig();
        x = config.getInt("map.information.x",233);
        y = config.getInt("map.information.y",88);
        z = config.getInt("map.information.z",280);
        toSearchAmounts.put(Items.BREAD, 20);
        toSearchAmounts.put(Items.CELERY, 30);


        CansteinGottesdienst.getInstance().getMapInformationConfig().saveConfig();
        convert();
    }

    private void convert(){
        amounts.clear();
        for(Items item : toSearchAmounts.keySet()){
            amounts.put(item.name(), 0);
        }
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
}

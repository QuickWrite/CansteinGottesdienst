package net.quickwrite.cansteingottesdienst.map;

import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import net.quickwrite.cansteingottesdienst.items.Items;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class InformationGatherer {

    public static final InformationGatherer INSTANCE;

    private final HashMap<Items, Integer> toSearchAmounts;
    private final HashMap<String, Integer> amounts;
    private int x, y, z;

    static {
        INSTANCE = new InformationGatherer();
    }

    private InformationGatherer() {
        amounts = new HashMap<>();
        toSearchAmounts = new HashMap<>();

        loadFromConfig();
    }

    public void startTracker(Item item){
        new BukkitRunnable(){

            @Override
            public void run() {
                if(item.isOnGround()){
                    if(item.getLocation().distance(new Location(item.getWorld(), x + .5, y + .5, z + .5)) < 1) {
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
        toSearchAmounts.put(Items.BREAD, 30);
        toSearchAmounts.put(Items.CELERY, 30);
        x = -242;
        y = 85;
        z = -103;

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

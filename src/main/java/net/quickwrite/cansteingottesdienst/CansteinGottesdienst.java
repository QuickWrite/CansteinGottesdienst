package net.quickwrite.cansteingottesdienst;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import org.bukkit.plugin.java.JavaPlugin;

public final class CansteinGottesdienst extends JavaPlugin {

    public static StateFlag INFINITE_CROPS;

    @Override
    public void onLoad() {
        initializeWorldGuard();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void initializeWorldGuard() {
        FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();

        try {
            // create a flag with the name "my-custom-flag", defaulting to true
            StateFlag flag = new StateFlag("infinite-crops", false);
            registry.register(flag);
            INFINITE_CROPS = flag; // only set our field if there was no error
        } catch (FlagConflictException e) {
            // some other plugin registered a flag by the same name already.
            // you can use the existing flag, but this may cause conflicts - be sure to check type
            Flag<?> existing = registry.get("infinite-crops");
            if (existing instanceof StateFlag) {
                INFINITE_CROPS = (StateFlag) existing;
            } else {
                // types don't match - this is bad news! some other plugin conflicts with you
                // hopefully this never actually happens
                throw new RuntimeException("Somehow the 'infinite-crops' flag cannot be initialized!\n" +
                                            "Check if another plugin is conflicting with the flags.");
            }
        }
    }
}

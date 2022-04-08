package net.quickwrite.cansteingottesdienst.util.storage;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;

public class Flags {
    private static final FlagRegistry REGISTRY;

    public static StateFlag INFINITE_CROPS;
    public static StateFlag CUSTOM_BLOCKS;

    static {
        REGISTRY = WorldGuard.getInstance().getFlagRegistry();
    }

    private Flags() {

    }

    public static StateFlag addFlag(String name, boolean def){
        try {
            StateFlag flag = new StateFlag(name, def);
            REGISTRY.register(flag);
            return flag;
        } catch (FlagConflictException e) {
            Flag<?> existing = REGISTRY.get(name);
            if(existing instanceof StateFlag){
                return (StateFlag) existing;
            } else {
                throw new RuntimeException("Somehow the '" + name + "' flag cannot be initialized!\n" +
                        "Check if another plugin is conflicting with the flags.");
            }
        }
    }

}

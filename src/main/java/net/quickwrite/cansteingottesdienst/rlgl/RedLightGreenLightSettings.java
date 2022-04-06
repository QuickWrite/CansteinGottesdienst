package net.quickwrite.cansteingottesdienst.rlgl;

import com.google.common.collect.ImmutableMap;
import org.bukkit.Sound;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.Map;

public class RedLightGreenLightSettings implements ConfigurationSerializable {

    private Direction direction;
    private int x, z;
    private String titleRun, titleHalt, sound;
    private int minTime, maxTime, haltTime;

    public RedLightGreenLightSettings(Direction d, int x, int z){
        this.direction = d;
        this.x = x;
        this.z = z;
        titleRun = "§aLos geht's";
        titleHalt = "§cHALT";
        minTime = 20 * 2;
        maxTime = 20 * 7;
        haltTime = 20 * 2;
        sound = "music_disc.pigstep";
    }

    public void set(Direction d, int x, int z){
        this.direction = d;
        this.x = x;
        this.z = z;
    }

    public RedLightGreenLightSettings(Map<String, Object> inp){
        this.direction = Direction.valueOf((String) inp.get("direction"));
        this.x = (int) inp.get("x");
        this.z = (int) inp.get("z");
        this.titleRun = (String) inp.get("titleRun");
        this.titleHalt = (String) inp.get("titleHalt");
        this.minTime = (int) inp.get("minTime");
        this.maxTime = (int) inp.get("maxTime");
        this.haltTime = (int) inp.get("haltTime");
        this.sound = (String) inp.get("sound");
    }

    public Direction getDirection() {
        return direction;
    }

    public int getX() {
        return x * direction.getxMod();
    }

    public int getZ() {
        return z * direction.getzMod();
    }

    public String getTitleRun() {
        return titleRun;
    }

    public String getTitleHalt() {
        return titleHalt;
    }

    public int getMinTime() {
        return minTime;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public int getHaltTime() {
        return haltTime;
    }

    public String getSound() {
        return sound;
    }

    @Override
    public Map<String, Object> serialize() {
        return new ImmutableMap.Builder<String, Object>()
                .put("direction", direction.name())
                .put("x", x)
                .put("z", z)
                .put("titleRun", titleRun)
                .put("titleHalt", titleHalt)
                .put("minTime", minTime)
                .put("maxTime", maxTime)
                .put("haltTime", haltTime)
                .put("sound", sound)
                .build();
    }
}

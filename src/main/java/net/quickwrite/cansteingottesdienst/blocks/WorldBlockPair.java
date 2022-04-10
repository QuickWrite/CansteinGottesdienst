package net.quickwrite.cansteingottesdienst.blocks;

import com.google.common.collect.ImmutableMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;

import java.util.Map;
import java.util.UUID;

public class WorldBlockPair implements ConfigurationSerializable {

    private final Location loc;
    private final UUID uuid;

    public WorldBlockPair(Location loc, UUID uuid) {
        this.loc = loc;
        this.uuid = uuid;
    }

    public WorldBlockPair(Map<String, Object> map){
        this.loc = (Location) map.get("loc");
        this.uuid = UUID.fromString((String) map.get("uuid"));
    }

    public Location getLoc() {
        return loc;
    }

    public ArmorStand getArmorStand() {
        Entity e = Bukkit.getEntity(uuid);
        if(e instanceof ArmorStand) {
            return (ArmorStand) e;
        }
        return null;
    }

    @Override
    public Map<String, Object> serialize() {
        return new ImmutableMap.Builder<String, Object>()
                .put("loc", loc)
                .put("uuid", uuid.toString())
                .build();
    }
}

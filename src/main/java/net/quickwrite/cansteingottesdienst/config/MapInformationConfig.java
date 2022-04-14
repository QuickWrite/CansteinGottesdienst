package net.quickwrite.cansteingottesdienst.config;

import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;

public class MapInformationConfig extends CustomConfig{

    public MapInformationConfig() {
        super(CansteinGottesdienst.getInstance(), "mapinformation.yml");
    }
}

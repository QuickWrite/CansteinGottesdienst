package net.quickwrite.cansteingottesdienst.config;

import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;

public class CustomBlockConfig extends CustomConfig{

    public CustomBlockConfig() {
        super(CansteinGottesdienst.getInstance(), "customBlockConfig.yml");
    }
}

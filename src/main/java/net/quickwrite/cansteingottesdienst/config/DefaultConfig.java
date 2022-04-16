package net.quickwrite.cansteingottesdienst.config;

import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;

public class DefaultConfig extends CustomConfig {
    public DefaultConfig() {
        super(CansteinGottesdienst.getInstance(), "config.yml");
    }
}

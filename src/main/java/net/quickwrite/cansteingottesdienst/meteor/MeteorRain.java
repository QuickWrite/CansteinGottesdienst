package net.quickwrite.cansteingottesdienst.meteor;

import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class MeteorRain {

    private int amount;
    private int timeOut;
    private int radius;
    private boolean running;
    private Location start;

    public MeteorRain(Location start, int amount, int timeOut, int radius) {
        this.amount = amount;
        this.timeOut = timeOut;
        this.radius = radius;
        this.start = start;
    }

    public void start(){
        running = true;
        new BukkitRunnable(){

            @Override
            public void run() {
                if(!running || amount == 0) cancel();
                amount--;
                Random random = new Random();
                Location s = start.clone().add(random.nextInt(radius * 2) - radius, random.nextInt(30) + 180, random.nextInt(radius * 2) - radius);
                new Meteor(s, 1000, Math.random() + 0.5).start();
            }
        }.runTaskTimer(CansteinGottesdienst.getInstance(), 0, timeOut);
    }
}

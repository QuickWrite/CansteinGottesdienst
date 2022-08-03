package net.quickwrite.cansteingottesdienst.meteor;

import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

public class Meteor {

    private Location location;
    private int liveTime;
    private double speed;
    private Vector direction;
    private boolean flying;

    public Meteor(Location l, double speed){
        location = l;
        direction = new Vector(Math.random() - 0.5, Math.random() * -CansteinGottesdienst.angle, Math.random() - 0.5).normalize();
        this.speed = speed;
        location.add(0, 5, 0);
        location.setY(new Random().nextInt(CansteinGottesdienst.maxY - CansteinGottesdienst.minY) + CansteinGottesdienst.minY);
    }

    public Meteor(Location start, int time, double speed){
        liveTime = time;
        location = start;
        direction = new Vector(Math.random() - 0.5, Math.random() * -CansteinGottesdienst.angle, Math.random() - 0.5).normalize();
        this.speed = speed;
    }

    public void start(){
        liveTime = CansteinGottesdienst.liveTime;
        flying = true;
        Random random = new Random();
        new BukkitRunnable(){

            @Override
            public void run() {
                for(int a = 0; a < CansteinGottesdienst.stepAmount; a++) {
                    if (!flying || (liveTime == 0 && !CansteinGottesdienst.liveUntilSomeY)) cancel();
                    if (!CansteinGottesdienst.liveUntilSomeY) liveTime--;

                    location.add(direction.clone().multiply(speed / (CansteinGottesdienst.stepAmount + 0.0)));

                    if (CansteinGottesdienst.liveUntilSomeY && location.getY() < CansteinGottesdienst.minimumExistY) cancel();

                    for (int i = 0; i < CansteinGottesdienst.amount1; i++) {
                        location.getWorld().spawnParticle(CansteinGottesdienst.particle1,
                                location.clone().add(random.nextInt((int) (CansteinGottesdienst.size * 100)) / 100.0 - CansteinGottesdienst.size / 2,
                                        random.nextInt((int) (CansteinGottesdienst.size * 100)) / 100.0 - CansteinGottesdienst.size / 2,
                                        random.nextInt((int) (CansteinGottesdienst.size * 100)) / 100.0 - CansteinGottesdienst.size / 2),
                                0,
                                direction.getX() * -speed, // + (Math.random() - 0.5) * 2
                                direction.getY() * -speed,
                                direction.getZ() * -speed,
                                0.5,
                                null,
                                true);
                    }
                    location.getWorld().spawnParticle(CansteinGottesdienst.particle2, location, CansteinGottesdienst.amount2, CansteinGottesdienst.size / 8, CansteinGottesdienst.size / 8, CansteinGottesdienst.size / 8, 0, null, true);
                    location.getWorld().spawnParticle(CansteinGottesdienst.particle3, location, CansteinGottesdienst.amount3, CansteinGottesdienst.size / 8, CansteinGottesdienst.size / 8, CansteinGottesdienst.size / 8, 0, null, true);
                }
            }
        }.runTaskTimer(CansteinGottesdienst.getInstance(), 0, 1);
    }
}

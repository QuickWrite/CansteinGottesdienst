package net.quickwrite.cansteingottesdienst.util;

public class Random {
    private Random() {
        
    }

    public static int getRandomInt(int min, int max) {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }
}

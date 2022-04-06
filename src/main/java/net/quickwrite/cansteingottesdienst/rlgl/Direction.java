package net.quickwrite.cansteingottesdienst.rlgl;

public enum Direction {

    NORTH(0, -1),
    SOUTH(0, 1),
    EAST(1, 0),
    WEST(-1, 0);
    ;

    private final int xMod, zMod;
    Direction(int xMod, int zMod){
        this.xMod = xMod;
        this.zMod = zMod;
    }

    public int getxMod() {
        return xMod;
    }

    public int getzMod() {
        return zMod;
    }

    public static Direction fromPitch(int yaw){
        if(yaw >= -135 && yaw <= -45){
            return EAST;
        }
        if(yaw >= -45 && yaw <= 45){
            return SOUTH;
        }
        if(yaw >= 45 && yaw <= 135){
            return WEST;
        }
        return NORTH;
    }
}

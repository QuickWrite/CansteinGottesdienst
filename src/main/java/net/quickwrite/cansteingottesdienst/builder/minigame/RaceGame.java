package net.quickwrite.cansteingottesdienst.builder.minigame;

import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class RaceGame {

    private final ArrayList<Player> playingPlayers;
    private ArrayList<Player> alivePlayers;

    public RaceGame(World world){
        playingPlayers = new ArrayList<>(world.getPlayers());
        alivePlayers = (ArrayList<Player>) playingPlayers.clone();
    }

    public void start(){

    }



    public void stop(){

    }


}

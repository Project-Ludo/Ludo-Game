package io.github.ludogame.network.server;


import io.github.ludogame.player.LudoPlayer;

import java.util.ArrayList;

public class LudoGame {

    private LudoServer server;
    private ArrayList<LudoPlayer> players;

    public LudoGame() {
        this.players = new ArrayList<>();
    }

    public ArrayList<LudoPlayer> getPlayers() {
        return players;
    }

    public void addPlayer(LudoPlayer player) {
        this.players.add(player);
    }

    public void removePlayer(LudoPlayer player) {
        this.players.remove(player);
    }

    public LudoServer getServer() {
        return server;
    }

    public void setServer(LudoServer server) {
        this.server = server;
    }

    public void addPlayers(ArrayList<LudoPlayer> players){
        this.players.addAll(players);
    }

    public void setPlayers(ArrayList<LudoPlayer> players) {
        this.players = players;
    }
}

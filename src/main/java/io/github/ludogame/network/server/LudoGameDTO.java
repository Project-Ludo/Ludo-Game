package io.github.ludogame.network.server;

import io.github.ludogame.player.LudoPlayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class LudoGameDTO implements Serializable {

    private ArrayList<LudoPlayer> players = new ArrayList<>();
    private AtomicInteger startCountdown = new AtomicInteger(60);
    private boolean countdownStarted;

    public ArrayList<LudoPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<LudoPlayer> players) {
        this.players = players;
    }

    public AtomicInteger getStartCountdown() {
        return startCountdown;
    }

    public void setStartCountdown(AtomicInteger startCountdown) {
        this.startCountdown = startCountdown;
    }

    public boolean isCountdownStarted() {
        return countdownStarted;
    }

    public void setCountdownStarted(boolean countdownStarted) {
        this.countdownStarted = countdownStarted;
    }
}

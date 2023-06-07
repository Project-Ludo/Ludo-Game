package io.github.ludogame.game;

import io.github.ludogame.player.LudoPlayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class LudoGameDTO implements Serializable {

    private CopyOnWriteArrayList<LudoPlayer> players = new CopyOnWriteArrayList<>();
    private AtomicInteger startCountdown = new AtomicInteger(60);
    private boolean countdownStarted;

    public CopyOnWriteArrayList<LudoPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(CopyOnWriteArrayList<LudoPlayer> players) {
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

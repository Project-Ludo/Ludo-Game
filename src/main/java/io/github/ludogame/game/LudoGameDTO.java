package io.github.ludogame.game;

import io.github.ludogame.player.LudoPlayerDTO;
import io.github.ludogame.player.PlayerColor;

import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class LudoGameDTO implements Serializable {

    private CopyOnWriteArrayList<LudoPlayerDTO> players = new CopyOnWriteArrayList<>();
    private AtomicInteger startCountdown = new AtomicInteger(60);
    private boolean countdownStarted;
    private PlayerColor playerColorTurn;

    public PlayerColor getPlayerColorTurn() {
        return playerColorTurn;
    }

    public void setPlayerColorTurn(PlayerColor playerColorTurn) {
        this.playerColorTurn = playerColorTurn;
    }

    public CopyOnWriteArrayList<LudoPlayerDTO> getPlayers() {
        return players;
    }

    public void setPlayers(CopyOnWriteArrayList<LudoPlayerDTO> players) {
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

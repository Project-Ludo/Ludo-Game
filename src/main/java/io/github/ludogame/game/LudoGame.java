package io.github.ludogame.game;


import com.almasb.fxgl.dsl.FXGL;
import io.github.ludogame.player.LudoPlayer;
import javafx.util.Duration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class LudoGame implements Serializable {

    private CopyOnWriteArrayList<LudoPlayer> players = new CopyOnWriteArrayList<>();
    private AtomicInteger startCountdown = new AtomicInteger(60);
    private boolean countdownStarted;

    public LudoGame() {
    }

    public CopyOnWriteArrayList<LudoPlayer> getPlayers() {
        return players;
    }

    public void addPlayer(LudoPlayer player) {
        this.players.add(player);
    }

    public void removePlayer(LudoPlayer player) {
        this.players.remove(player);
    }

    public void addPlayers(ArrayList<LudoPlayer> players) {
        this.players.addAll(players);
    }

    public void setPlayers(CopyOnWriteArrayList<LudoPlayer> players) {
        this.players = players;
    }

    public void updatePlayer(LudoPlayer player) {
        players.stream()
                .filter(p -> p.getUuid().equals(player.getUuid()))
                .findFirst()
                .ifPresent(p -> {
                    p.setReady(player.isReady());
                    p.setConnected(player.isConnected());
                    p.setNickname(player.getNickname());
                });
    }

    public long getReadyPlayersAmount() {
        return players.stream()
                .filter(LudoPlayer::isReady)
                .count();
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

    public void startCountdown() {
        this.countdownStarted = true;
        FXGL.run(() -> {
            this.startCountdown.decrementAndGet();

            if (getReadyPlayersAmount() >= 4 && this.startCountdown.get() > 10) {
                this.startCountdown.set(10);
            }

            if (this.startCountdown.get() < 0) {
                this.startCountdown.set(0);
            }
        }, Duration.seconds(1), 60);
    }
}

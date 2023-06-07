package io.github.ludogame.player;

import java.io.Serializable;
import java.util.UUID;

public class LudoPlayerDTO implements Serializable {
    private UUID uuid;
    private PlayerColor color;
    private String nickname;
    private boolean isConnected;
    private boolean isReady;

    public UUID getUuid() {
        return uuid;
    }

    public PlayerColor getColor() {
        return color;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setColor(PlayerColor color) {
        this.color = color;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }
}

package io.github.ludogame.player;

import com.almasb.fxgl.core.serialization.Bundle;
import com.almasb.fxgl.net.Client;

import java.io.Serializable;
import java.util.UUID;

public class LudoPlayer implements Serializable {
    private UUID uuid;
    private PlayerColor color;
    private Client<Bundle> dataBundle;
    private String nickname;
    private boolean connected;
    private boolean ready;

    public LudoPlayer() {
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public LudoPlayer(UUID uuid) {
        this.uuid = uuid;
        this.connected = true;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public PlayerColor getColor() {
        return color;
    }

    public void setColor(PlayerColor color) {
        this.color = color;
    }

    public Client<Bundle> getDataBundle() {
        return dataBundle;
    }

    public void setDataBundle(Client<Bundle> dataBundle) {
        this.dataBundle = dataBundle;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public void disconnectFromServer() {
        this.dataBundle.disconnect();
        this.connected = false;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    @Override
    public String toString() {
        return "LudoPlayer{" +
                "uuid=" + uuid +
                ", color=" + color +
                ", dataBundle=" + dataBundle +
                ", nickname='" + nickname + '\'' +
                ", isConnected=" + connected +
                ", isReady=" + ready +
                '}';
    }
}

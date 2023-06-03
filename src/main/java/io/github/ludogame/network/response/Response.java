package io.github.ludogame.network.response;

import io.github.ludogame.player.LudoPlayer;

import java.io.Serializable;

public class Response implements Serializable {
    private final ResponseStatus status;
    private final String message;
    private final LudoPlayer player;

    public Response(ResponseStatus status, String message, LudoPlayer player) {
        this.status = status;
        this.message = message;
        this.player = player;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public LudoPlayer getPlayer() {
        return player;
    }
}

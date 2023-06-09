package io.github.ludogame.network.response;

import io.github.ludogame.player.LudoPlayer;

import java.io.Serializable;

public class Response implements Serializable {
    private final ResponseStatus status;
    private final String message;
    private final LudoPlayerDTO ludoPlayerDTO;

    public Response(ResponseStatus status, String message, LudoPlayerDTO ludoPlayerDTO) {
        this.status = status;
        this.message = message;
        this.ludoPlayerDTO = ludoPlayerDTO;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public LudoPlayerDTO getPlayer() {
        return ludoPlayerDTO;
    }
}

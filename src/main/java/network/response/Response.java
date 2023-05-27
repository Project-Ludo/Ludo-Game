package network.response;

import player.Player;

import java.io.Serializable;

public class Response implements Serializable {
    private final ResponseStatus status;
    private final String message;
    private final Player player;

    public Response(ResponseStatus status, String message, Player player) {
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

    public Player getPlayer() {
        return player;
    }
}

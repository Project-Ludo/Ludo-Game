package network.response;

import player.PlayerColor;

import java.io.Serializable;
import java.util.UUID;

public class Response implements Serializable {
    private final ResponseStatus status;
    private final String message;
    private final PlayerColor playerColor;
    private final UUID playerUUID;

    public Response(ResponseStatus status, String message, PlayerColor playerColor, UUID playerUUID) {
        this.status = status;
        this.message = message;
        this.playerColor = playerColor;
        this.playerUUID = playerUUID;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public PlayerColor getPlayerColor() {
        return playerColor;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }
}

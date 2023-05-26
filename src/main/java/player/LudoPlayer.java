package player;

import com.almasb.fxgl.core.serialization.Bundle;
import com.almasb.fxgl.net.Client;

import java.util.UUID;

public class LudoPlayer {
    private final UUID uuid;
    private PlayerColor color;
    private Client<Bundle> dataBundle;

    public LudoPlayer(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
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
}

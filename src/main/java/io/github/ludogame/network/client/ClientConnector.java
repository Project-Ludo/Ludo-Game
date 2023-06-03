package io.github.ludogame.network.client;

import com.almasb.fxgl.core.serialization.Bundle;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.net.Client;
import io.github.ludogame.network.response.Response;
import io.github.ludogame.network.response.ResponseStatus;
import io.github.ludogame.player.LudoPlayer;
import javafx.util.Duration;

public class ClientConnector implements IClient {

    @Override
    public Client<Bundle> connect(String ip, int port, LudoPlayer player) {
        Client<Bundle> client = FXGL.getNetService().newTCPClient(ip, port);
        client.setOnConnected(connection -> connection.addMessageHandlerFX((conn, message) -> {
            if (message.getName().equals("ConnectionResponse")) {
                Response response = message.get("response");
                LudoPlayer responsePlayer = (LudoPlayer) response.getPlayer();

                if (!responsePlayer.getUuid().equals(player.getUuid())) {
                    return;
                }

                if (response.getStatus() == ResponseStatus.SUCCESS) {
                    System.out.println("Success, your color: " + responsePlayer.getColor());
                } else {
                    System.out.println("Failed to connect to server (" + response.getMessage() + ")");
                    client.disconnect();
                }
            }

            if(message.getName().equals("LobbyInfo")){

            }
        }));
        client.connectAsync();

        FXGL.runOnce(() -> {
            Bundle connectionData = new Bundle("ConnectionRequest");
            connectionData.put("player", player);
            client.broadcast(connectionData);
        }, Duration.seconds(0.2));

        return client;
    }
}

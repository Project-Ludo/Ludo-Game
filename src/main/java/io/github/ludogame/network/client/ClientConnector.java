package io.github.ludogame.network.client;

import com.almasb.fxgl.core.serialization.Bundle;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.net.Client;
import io.github.ludogame.LudoPlayerApp;
import io.github.ludogame.network.response.Response;
import io.github.ludogame.network.response.ResponseStatus;
import io.github.ludogame.player.LudoPlayer;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class ClientConnector implements IClient {

    @Override
    public Client<Bundle> connect(String ip, int port, LudoPlayer player) {
        Client<Bundle> client = FXGL.getNetService().newTCPClient(ip, port);
        client.connectAsync();
        setConnectionHandler(client, player);

        FXGL.runOnce(() -> {
            Bundle connectionData = new Bundle("ConnectionRequest");
            connectionData.put("player", player);
            client.broadcast(connectionData);
        }, Duration.seconds(0.2));

        return client;
    }

    private void setConnectionHandler(Client<Bundle> client, LudoPlayer player) {
        client.setOnConnected(connection -> connection.addMessageHandlerFX((conn, message) -> {
            handleConnection(message, player, client);
            handleLobby(message);
        }));
    }

    private void handleConnection(Bundle message, LudoPlayer player, Client<Bundle> client) {
        if (!message.getName().equals("ConnectionResponse")) {
            return;
        }

        Response response = message.get("response");
        LudoPlayer responsePlayer = response.getPlayer();
        if (!responsePlayer.getUuid().equals(player.getUuid())) {
            return;
        }

        if (response.getStatus() == ResponseStatus.SUCCESS) {
            player.setColor(responsePlayer.getColor());
            System.out.println("Success, your color: " + player.getColor());
        }
    }

    private void handleLobby(Bundle message) {
        if (!message.getName().equals("LobbyInfo")) {
            return;
        }
        //FIXME player receiving another list than server is sending ??????? LudoServer class

        ArrayList<LudoPlayer> players = message.get("players");
        System.out.println(message.getData());
        System.out.println("LobbyP:" + players.size());
        LudoPlayerApp.ludoGame.setPlayers(players);
    }
}

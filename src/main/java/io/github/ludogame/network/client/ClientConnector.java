package io.github.ludogame.network.client;

import com.almasb.fxgl.core.serialization.Bundle;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.net.Client;
import com.almasb.fxgl.time.TimerAction;
import io.github.ludogame.LudoPlayerApp;
import io.github.ludogame.network.response.Response;
import io.github.ludogame.network.response.ResponseStatus;
import io.github.ludogame.game.LudoGameDTO;
import io.github.ludogame.player.LudoPlayer;
import io.github.ludogame.player.LudoPlayerDTO;
import io.github.ludogame.player.PlayerService;
import javafx.util.Duration;

public class ClientConnector implements IClient {

    private TimerAction connectionHandlerTask;

    @Override
    public Client<Bundle> connect(String ip, int port, LudoPlayer player) {
        Client<Bundle> client = FXGL.getNetService().newUDPClient(ip, port);
        client.connectAsync();
        setConnectionHandler(client, player);

        FXGL.runOnce(() -> {
            Bundle connectionData = new Bundle("ConnectionRequest");
            connectionData.put("player", player);
            client.broadcast(connectionData);
        }, Duration.seconds(0.2));

        connectionHandlerTask(client, player);
        return client;
    }

    private void setConnectionHandler(Client<Bundle> client, LudoPlayer player) {
        client.setOnConnected(connection -> connection.addMessageHandlerFX((conn, message) -> {
            handleConnection(message, player);
            handleLobby(message);
        }));
    }

    private void handleConnection(Bundle message, LudoPlayer player) {
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

        LudoGameDTO game = message.get("game");
        LudoPlayerApp.ludoGame.setStartCountdown(game.getStartCountdown());
        LudoPlayerApp.ludoGame.setCountdownStarted(game.isCountdownStarted());
        LudoPlayerApp.ludoGame.setPlayers(game.getPlayers());
    }

    private void connectionHandlerTask(Client<Bundle> client, LudoPlayer player) {
        this.connectionHandlerTask = FXGL.run(() -> {
            //FIXME asynchronous task expiration :((
            if (!player.isConnected()) {
                this.connectionHandlerTask.expire();
                return;
            }

            LudoPlayerDTO playerDTO = PlayerService.convertToDTO(player);
            Bundle bundle = new Bundle("ConnectionFlag");
            bundle.put("player", playerDTO);
            client.broadcast(bundle);
        }, Duration.millis(500));
    }
}

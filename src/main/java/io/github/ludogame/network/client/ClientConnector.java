package io.github.ludogame.network.client;

import com.almasb.fxgl.core.serialization.Bundle;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.net.Client;
import com.almasb.fxgl.time.TimerAction;
import io.github.ludogame.LudoPlayerApp;
import io.github.ludogame.network.response.Response;
import io.github.ludogame.network.response.ResponseStatus;
import io.github.ludogame.game.LudoGameDTO;
import io.github.ludogame.notification.ErrorNotification;
import io.github.ludogame.player.LudoPlayer;
import io.github.ludogame.player.LudoPlayerDTO;
import io.github.ludogame.player.PlayerService;
import javafx.util.Duration;

public class ClientConnector implements IClient {

    @Override
    public Client<Bundle> connect(String ip, int port, LudoPlayer player) {
        Client<Bundle> client = FXGL.getNetService().newUDPClient(ip, port);
        client.connectAsync();
        client.setOnConnected(connection -> connection.addMessageHandlerFX((conn, message) -> {
            handleConnection(message, player);
            handleLobby(message);
        }));

        FXGL.runOnce(() -> {
            Bundle connectionData = new Bundle("ConnectionRequest");
            LudoPlayerDTO playerDTO = PlayerService.convertToDTO(player);
            connectionData.put("player", playerDTO);
            client.broadcast(connectionData);
            player.setDataBundle(client);
        }, Duration.seconds(0.2));

        connectionHandlerTask(client, player);
        return client;
    }

    private void handleConnection(Bundle message, LudoPlayer player) {
        if (!message.getName().equals("ConnectionResponse")) {
            return;
        }

        Response response = message.get("response");
        LudoPlayerDTO responsePlayerDTO = response.getPlayer();
        LudoPlayer responsePlayer = PlayerService.convertToPlayer(responsePlayerDTO);
        if (!responsePlayer.getUuid().equals(player.getUuid())) {
            return;
        }

        if (response.getStatus() == ResponseStatus.SUCCESS) {
            player.setColor(responsePlayer.getColor());
            player.setReady(responsePlayer.isReady());
            player.setConnected(response.getPlayer().isConnected());
            System.out.println("Success, your color: " + player.getColor());
        }else{
            new ErrorNotification(response.getMessage());
        }
    }

    private void handleLobby(Bundle message) {
        if (!message.getName().equals("LobbyInfo")) {
            return;
        }

        LudoGameDTO game = message.get("game");
        LudoPlayerApp.ludoGame.updateGame(game);
    }

    private void connectionHandlerTask(Client<Bundle> client, LudoPlayer player) {
        TimerAction task = FXGL.run(() -> {
            LudoPlayerDTO playerDTO = PlayerService.convertToDTO(player);
            Bundle bundle = new Bundle("ConnectionFlag");
            bundle.put("player", playerDTO);
            client.broadcast(bundle);
        }, Duration.millis(500));

        player.addTask(task);
    }
}

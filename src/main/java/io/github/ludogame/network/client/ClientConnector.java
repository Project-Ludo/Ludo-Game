package io.github.ludogame.network.client;

import com.almasb.fxgl.core.serialization.Bundle;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.net.Client;
import com.almasb.fxgl.time.TimerAction;
import io.github.ludogame.LudoPlayerApp;
import io.github.ludogame.component.PawnComponent;
import io.github.ludogame.controller.SceneController;
import io.github.ludogame.network.response.Response;
import io.github.ludogame.network.response.ResponseStatus;
import io.github.ludogame.game.LudoGameDTO;
import io.github.ludogame.notification.ErrorNotification;
import io.github.ludogame.pawn.Pawn;
import io.github.ludogame.pawn.PawnColor;
import io.github.ludogame.pawn.PawnMoveData;
import io.github.ludogame.player.LudoPlayer;
import io.github.ludogame.player.LudoPlayerDTO;
import io.github.ludogame.player.PlayerColor;
import io.github.ludogame.player.PlayerService;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.Optional;

public class ClientConnector implements IClient {

    private final SceneController sceneController;

    public ClientConnector(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    @Override
    public Client<Bundle> connect(String ip, int port, LudoPlayer player) {
        Client<Bundle> client = FXGL.getNetService().newUDPClient(ip, port);
        client.connectAsync();
        client.setOnConnected(connection -> connection.addMessageHandlerFX((conn, message) -> {
            handleConnection(message, player);
            handleLobby(message);
            handleDice(message);
            handlePawnMove(message);
            handleTurn(message);
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
        } else {
            new ErrorNotification(response.getMessage());
        }
    }

    private PawnColor getPawnColorFromPlayerColor(PlayerColor playerColor) {
        return switch (playerColor) {
            case YELLOW -> PawnColor.YEllOW;
            case GREEN -> PawnColor.GREEN;
            case BLUE -> PawnColor.BLUE;
            default -> PawnColor.RED;
        };
    }

    private void handlePawnMove(Bundle message) {
        if (!message.getName().equals("PawnMove")) {
            return;
        }

        PawnMoveData data = message.get("data");
        Optional<LudoPlayer> first =
                LudoPlayerApp.ludoGame.getPlayers().stream().filter(p -> data.getPawnColor().equals(getPawnColorFromPlayerColor(p.getColor()))).findFirst();

        if (first.isEmpty()) {
            System.out.println("No player");
            return;
        }

        LudoPlayer ludoPlayer = first.get();
        Optional<Pawn> first1 = ludoPlayer.getPawns().stream().filter(pawn -> pawn.getId() == data.getId()).findFirst();
        if(first1.isEmpty()){
            System.out.println("No pawn");
            return;
        }

        first1.get().getEntity().getComponent(PawnComponent.class).move(data.getDiceResult(), first1.get());
        FXGL.<LudoPlayerApp>getAppCast().getSceneController().getGameSceneController()
                .diceView.setImage(new Image("assets/textures/dice/dice_throw_fast.gif"));
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

    private void handleDice(Bundle message) {
        if (!message.getName().equals("DiceRoll")) {
            return;
        }

        int result = message.get("result");
        sceneController.getGameSceneController().rollDice(result);
    }

    private void handleTurn(Bundle message) {
        if (!message.getName().equals("ChangeTurn")) {
            return;
        }

        FXGL.<LudoPlayerApp>getAppCast().getSceneController().getGameSceneController()
                .diceView.setImage(new Image("assets/textures/dice/dice_throw_fast.gif"));
    }
}

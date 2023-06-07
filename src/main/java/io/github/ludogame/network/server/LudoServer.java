package io.github.ludogame.network.server;

import com.almasb.fxgl.core.serialization.Bundle;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.net.Server;
import io.github.ludogame.game.GameService;
import io.github.ludogame.game.LudoGameDTO;
import io.github.ludogame.network.response.Response;
import io.github.ludogame.network.response.ResponseStatus;
import io.github.ludogame.player.LudoPlayer;
import io.github.ludogame.player.PlayerColor;
import io.github.ludogame.player.PlayerService;
import javafx.util.Duration;

import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LudoServer {

    private final static int MAX_PLAYERS_AMOUNT = 4;
    private final static String SERVER_CONNECTED = "Server is now working on port: %s";
    private final static String PLAYER_CONNECT_REQUEST = "Player with id %s is trying to connect to server";
    private final static String PLAYER_CONNECT_ACCEPT = "Player with id %s has joined to server";
    private final static String PLAYER_CONNECT_REJECT = "Player with id %s connection's attempt has been rejected (Message: %s)";
    private final static Map<Integer, PlayerColor> PLAYER_COLOR_MAP = Map.of(
            1, PlayerColor.BLUE,
            2, PlayerColor.RED,
            3, PlayerColor.GREEN,
            4, PlayerColor.YELLOW
    );

    private final ConnectionHandler connectionHandler;
    private final Logger logger;
    private Server<Bundle> serverBundle;

    public LudoServer() {
        this.logger = Logger.getLogger(LudoServer.class.getName());
        this.connectionHandler = new ConnectionHandler();
    }

    public void initializeServer(int port) {
        this.serverBundle = FXGL.getNetService().newUDPServer(port);
        serverBundle.setOnConnected(connection -> connection.addMessageHandlerFX((conn, message) -> {
            handleConnectionRequest(message);
            connectionHandler(message);
        }));
        serverBundle.startAsync();
        lobbyTask();
        logger.log(Level.INFO, String.format(SERVER_CONNECTED, port));
    }

    public void lobbyTask() {
        FXGL.run(() -> {
            Bundle bundle = new Bundle("LobbyInfo");
            LudoGameDTO ludoGameDTO = GameService.convertToDTO(LudoServerApp.ludoGame);
            bundle.put("game", ludoGameDTO);
            serverBundle.broadcast(bundle);

            if (LudoServerApp.ludoGame.getReadyPlayersAmount() >= 2 && !LudoServerApp.ludoGame.isCountdownStarted()) {
                LudoServerApp.ludoGame.startCountdown();
            }

            LudoServerApp.ludoGame.getPlayers().forEach(System.out::println);
        }, Duration.millis(500));
    }

    public void connectionHandler(Bundle message) {
        if (!message.getName().equals("ConnectionFlag")) {
            return;
        }

        LudoPlayer ludoPlayer = PlayerService.convertToPlayer(message.get("player"));
        connectionHandler.updateLastRequestTime(ludoPlayer.getUuid());
        LudoServerApp.ludoGame.updatePlayer(ludoPlayer);
    }

    public void handleConnectionRequest(Bundle message) {
        if (!message.getName().equals("ConnectionRequest")) {
            return;
        }

        LudoPlayer player = message.get("player");
        UUID playerUUID = player.getUuid();
        logger.log(Level.INFO, String.format(PLAYER_CONNECT_REQUEST, playerUUID));

        Response response;
        //TODO jesli uuid gracza jest w playerach to znaczy ze byl on w tej grze i powinien moc wrocic do poprzedniego stanu swojego konta.
        if (isFull()) {
            String responseMessage = "Server is full";
            response = new Response(ResponseStatus.ERROR, responseMessage, player);
            logger.log(Level.INFO, String.format(PLAYER_CONNECT_REJECT, playerUUID, responseMessage));
        } else {
            player.setColor(PLAYER_COLOR_MAP.get(serverBundle.getConnections().size()));
            response = new Response(ResponseStatus.SUCCESS, "Connected", player);
            LudoServerApp.ludoGame.addPlayer(player);
            logger.log(Level.INFO, String.format(PLAYER_CONNECT_ACCEPT, playerUUID));
        }

        Bundle bundle = new Bundle("ConnectionResponse");
        bundle.put("response", response);
        serverBundle.broadcast(bundle);
    }

    public boolean isFull() {
        return serverBundle.getConnections().size() > MAX_PLAYERS_AMOUNT;
    }

    public Server<Bundle> getBundle() {
        return serverBundle;
    }
}

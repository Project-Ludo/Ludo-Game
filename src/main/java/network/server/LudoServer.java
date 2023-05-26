package network.server;

import com.almasb.fxgl.core.serialization.Bundle;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.net.Server;
import network.response.Response;
import network.response.ResponseStatus;
import player.LudoPlayer;
import player.PlayerColor;

import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LudoServer implements IServer {

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

    private final Logger logger = Logger.getLogger(LudoServer.class.getName());
    private Server<Bundle> serverBundle;

    @Override
    public void initializeServer(int port, LudoGame ludoGame) {
        this.serverBundle = FXGL.getNetService().newTCPServer(port);
        serverBundle.setOnConnected(connection -> connection.addMessageHandlerFX((conn, message) -> {
            if (message.getName().equals("ConnectionRequest")) {
                LudoPlayer player = message.get("player");
                UUID playerUUID = player.getUuid();
                logger.log(Level.INFO, String.format(PLAYER_CONNECT_REQUEST, playerUUID));

                Response response;
                if (isFull()) {
                    String responseMessage = "Server is full";
                    response = new Response(ResponseStatus.ERROR, responseMessage, player);
                    logger.log(Level.INFO, String.format(PLAYER_CONNECT_REJECT, playerUUID, responseMessage));
                } else {
                    player.setColor(PLAYER_COLOR_MAP.get(serverBundle.getConnections().size()));
                    response = new Response(ResponseStatus.SUCCESS, "Connected", player);
                    ludoGame.addPlayer(player);
                    logger.log(Level.INFO, String.format(PLAYER_CONNECT_ACCEPT, playerUUID));
                }

                Bundle bundle = new Bundle("ConnectionResponse");
                bundle.put("response", response);
                serverBundle.broadcast(bundle);
            }
        }));
        serverBundle.startAsync();
        logger.log(Level.INFO, String.format(SERVER_CONNECTED, port));
    }

    @Override
    public boolean isFull() {
        return serverBundle.getConnections().size() > MAX_PLAYERS_AMOUNT;
    }

    public Server<Bundle> getBundle() {
        return serverBundle;
    }
}

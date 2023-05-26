package network.server;

import com.almasb.fxgl.core.serialization.Bundle;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.net.Server;
import network.response.Response;
import network.response.ResponseStatus;
import player.LudoPlayer;
import player.Player;
import player.PlayerColor;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LudoServer implements IServer {

    private final static int MAX_PLAYERS_AMOUNT = 4;
    private final static String SERVER_CONNECTED = "Server is now working on port: %s";
    private final static String PLAYER_CONNECT_REQUEST = "Player with id %s is trying to connect to server";
    private final static String PLAYER_CONNECT_ACCEPT = "Player with id %s has joined to server";
    private final static String PLAYER_CONNECT_REJECT = "Player with id %s connection's attempt has been rejected (Message: %s)";

    private final Logger logger = Logger.getLogger(LudoServer.class.getName());
    private final Server<Bundle> serverBundle;

    public LudoServer(int port) {
        this.serverBundle = initializeServer(port);
    }

    @Override
    public Server<Bundle> initializeServer(int port) {
        Server<Bundle> server = FXGL.getNetService().newTCPServer(port);
        server.setOnConnected(connection -> connection.addMessageHandlerFX((conn, message) -> {
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
                    //TODO add player color assignment
                    player.setColor(PlayerColor.BLUE);
                    response = new Response(ResponseStatus.SUCCESS, "Connected", player);
                    logger.log(Level.INFO, String.format(PLAYER_CONNECT_ACCEPT, playerUUID));
                }

                Bundle bundle = new Bundle("ConnectionResponse");
                bundle.put("response", response);
                server.broadcast(bundle);
            }
        }));
        server.startAsync();
        logger.log(Level.INFO, String.format(SERVER_CONNECTED, port));
        return server;
    }

    @Override
    public boolean isFull() {
        return serverBundle.getConnections().size() > MAX_PLAYERS_AMOUNT;
    }

    public Server<Bundle> getBundle() {
        return serverBundle;
    }
}

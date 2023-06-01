package network.server;

import game.LudoGame;

public interface IServer {
    void initializeServer(int port, LudoGame ludoGame);

    boolean isFull();
}

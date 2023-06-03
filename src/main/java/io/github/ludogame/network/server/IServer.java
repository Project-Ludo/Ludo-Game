package io.github.ludogame.network.server;

public interface IServer {
    void initializeServer(int port, LudoGame ludoGame);

    boolean isFull();
}

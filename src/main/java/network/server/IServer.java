package network.server;

public interface IServer {
    void initializeServer(int port, LudoGame ludoGame);

    boolean isFull();
}

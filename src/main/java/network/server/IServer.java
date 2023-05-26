package network.server;

import com.almasb.fxgl.core.serialization.Bundle;
import com.almasb.fxgl.net.Server;

public interface IServer {
    Server<Bundle> initializeServer(int port);

    boolean isFull();
}

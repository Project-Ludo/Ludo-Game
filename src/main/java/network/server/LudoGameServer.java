package network.server;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;

public class LudoGameServer extends GameApplication {

    private LudoServer ludoServer;

    @Override
    protected void initSettings(GameSettings gameSettings) {

    }

    @Override
    protected void initGame() {
        this.ludoServer = new LudoServer(55555);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

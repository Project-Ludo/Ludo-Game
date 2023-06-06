package io.github.ludogame.network.server;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;

public class LudoServerApp extends GameApplication {

    public static LudoGame ludoGame;

    @Override
    protected void initSettings(GameSettings gameSettings) {

    }

    @Override
    protected void initGame() {
        ludoGame = new LudoGame();
        LudoServer ludoServer = new LudoServer();
        ludoServer.initializeServer(55555);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

package io.github.ludogame.network.server;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;

public class LudoServerApp extends GameApplication {

    private LudoGame ludoGame;

    @Override
    protected void initSettings(GameSettings gameSettings) {

    }

    @Override
    protected void initGame() {
        this.ludoGame = new LudoGame();
        LudoServer ludoServer = new LudoServer();
        ludoServer.initializeServer(55555, ludoGame);
        ludoGame.setServer(ludoServer);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

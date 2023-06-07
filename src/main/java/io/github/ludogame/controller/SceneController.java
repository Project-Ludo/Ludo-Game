package io.github.ludogame.controller;

import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.Parent;
import javafx.util.Duration;

public class SceneController {

    private final Parent mainMenuScene;
    private final Parent rulesScene;
    private final Parent serverConnectScene;
    private final Parent serverLobbyScene;
    //private final Parent game;

    public SceneController(Parent mainMenuScene, Parent rulesScene, Parent serverConnectScene, Parent serverLobbyScene) {
        this.mainMenuScene = mainMenuScene;
        this.rulesScene = rulesScene;
        this.serverConnectScene = serverConnectScene;
        this.serverLobbyScene = serverLobbyScene;
    }

    public Parent getMainMenuScene() {
        return mainMenuScene;
    }

    public Parent getRulesScene() {
        return rulesScene;
    }

    public Parent getServerConnectScene() {
        return serverConnectScene;
    }

    public Parent getServerLobbyScene() {
        return serverLobbyScene;
    }

    public void changeSceneAfter(Parent nextScene, double millis) {
        FXGL.runOnce(() -> {
            FXGL.getGameScene().clearUINodes();
            FXGL.getGameScene().addUINode(nextScene);
        }, Duration.millis(millis));
    }

    public void changeScene(Parent nextScene) {
        FXGL.getGameScene().clearUINodes();
        FXGL.getGameScene().addUINode(nextScene);
    }
}

package io.github.ludogame.controller;

import com.almasb.fxgl.dsl.FXGL;
import io.github.ludogame.game.GameController;
import io.github.ludogame.menu.ConnectionMenuController;
import io.github.ludogame.menu.LobbyController;
import io.github.ludogame.menu.MainMenuController;
import io.github.ludogame.menu.RulesMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Duration;

import java.io.IOException;

public class SceneController {

    private final FXMLLoader mainMenuScene;
    private final FXMLLoader rulesScene;
    private final FXMLLoader serverConnectScene;
    private final FXMLLoader serverLobbyScene;
    private final FXMLLoader gameScene;

    public SceneController(FXMLLoader mainMenuScene, FXMLLoader rulesScene, FXMLLoader serverConnectScene, FXMLLoader serverLobbyScene, FXMLLoader gameScene) {
        this.mainMenuScene = mainMenuScene;
        this.rulesScene = rulesScene;
        this.serverConnectScene = serverConnectScene;
        this.serverLobbyScene = serverLobbyScene;
        this.gameScene = gameScene;
    }

    public Parent getMainMenuScene() throws IOException {
        Parent load = mainMenuScene.load();
        MainMenuController controller = mainMenuScene.getController();
        controller.initSceneController(this);
        return load;
    }

    public Parent getRulesScene() throws IOException {
        Parent load = rulesScene.load();
        RulesMenuController controller = rulesScene.getController();
        controller.initSceneController(this);
        return load;
    }

    public Parent getServerConnectScene() throws IOException {
        Parent load = serverConnectScene.load();
        ConnectionMenuController controller = serverConnectScene.getController();
        controller.initSceneController(this);
        return load;
    }

    public Parent getServerLobbyScene() throws IOException {
        Parent load = serverLobbyScene.load();
        LobbyController controller = serverLobbyScene.getController();
        controller.initSceneController(this);
        return load;
    }

    public Parent getGameScene() throws IOException {
        Parent load = gameScene.load();
        GameController controller = gameScene.getController();
        controller.initSceneController(this);
        return load;
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

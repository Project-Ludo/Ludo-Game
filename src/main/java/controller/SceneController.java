package controller;

import com.almasb.fxgl.dsl.FXGL;
import config.Config;
import javafx.scene.Parent;
import javafx.util.Duration;

public class SceneController {

    private Parent menuStart;
    private Parent menuRules;
    private Parent menuConnection;
    private Parent lobby;
    //private final Parent game;

    public SceneController(Parent menuStart, Parent menuRules, Parent menuConnection, Parent lobby) {
        this.menuStart = menuStart;
        this.menuRules = menuRules;
        this.menuConnection = menuConnection;
        this.lobby = lobby;

        menuStart.setLayoutX(Config.MENU_START_LAYOUT_X);
        menuStart.setLayoutY(Config.MENU_START_LAYOUT_Y);

        menuRules.setLayoutX(Config.MENU_START_LAYOUT_X);
        menuRules.setLayoutY(Config.MENU_START_LAYOUT_Y);

        menuConnection.setLayoutX(Config.MENU_START_LAYOUT_X);
        menuConnection.setLayoutY(Config.MENU_START_LAYOUT_Y);

        lobby.setLayoutX(Config.MENU_START_LAYOUT_X);
        lobby.setLayoutY(Config.MENU_START_LAYOUT_Y);
    }

    public Parent getMenuStart() {
        return menuStart;
    }

    public Parent getMenuRules() {
        return menuRules;
    }

    public Parent getMenuConnection() {
        return menuConnection;
    }

    public Parent getLobby() {
        return lobby;
    }

    public void changeSceneAfter(Parent nextScene, double millis){
        FXGL.runOnce(() -> {
            FXGL.getGameScene().clearUINodes();
            FXGL.getGameScene().addUINode(nextScene);
        }, Duration.millis(millis));
    }
}

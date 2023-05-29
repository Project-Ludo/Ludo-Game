package controller;

import config.Config;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class SceneController {

    private Parent menuStart;
    private Parent menuRules;
    private Parent menuConnection;
    //private final Parent game;

    public SceneController(){}

    public SceneController(Parent menuStart, Parent menuRules, Parent menuConnection, Parent game){
        //this.game = game;
        this.menuStart = menuStart;
        this.menuConnection = menuConnection;
        this.menuRules = menuRules;

    }

    public SceneController(Parent menuStart, Parent menuRules, Parent menuConnection) {
        this.menuStart = menuStart;
        this.menuRules = menuRules;
        this.menuConnection = menuConnection;

        menuStart.setLayoutX(Config.MENU_START_LAYOUT_X);
        menuStart.setLayoutY(Config.MENU_START_LAYOUT_Y);

        menuRules.setLayoutX(Config.MENU_START_LAYOUT_X);
        menuRules.setLayoutY(Config.MENU_START_LAYOUT_Y);

        menuConnection.setLayoutX(Config.MENU_START_LAYOUT_X);
        menuConnection.setLayoutY(Config.MENU_START_LAYOUT_Y);

    }

    public Parent getMenuStart() {
        return menuStart;
    }

    public void setMenuStart(Parent menuStart) {
        this.menuStart = menuStart;
    }

    public Parent getMenuRules() {
        return menuRules;
    }

    public void setMenuRules(Parent menuRules) {
        this.menuRules = menuRules;
    }

    public Parent getMenuConnection() {
        return menuConnection;
    }

    public void setMenuConnection(Parent menuConnection) {
        this.menuConnection = menuConnection;
    }
}

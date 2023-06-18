package io.github.ludogame.menu;

import com.almasb.fxgl.ui.UI;
import io.github.ludogame.notification.ErrorNotification;
import io.github.ludogame.config.UIConfig;
import com.almasb.fxgl.dsl.FXGL;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController extends DefaultMenuButtonAction implements Initializable {

    @FXML
    public Label titleText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeControlTexture(startButton, UIConfig.START_BUTTON_DEFAULT);
        changeControlTexture(rulesButton, UIConfig.RULES_BUTTON_DEFAULT);
        changeControlTexture(exitButton, UIConfig.EXIT_BUTTON_DEFAULT);
        changeControlTexture(musicButton, UIConfig.MUSIC_BUTTON_DEFAULT);
        changeControlTexture(titleText, UIConfig.TITLE_TEXT);
    }

    public void onStartButtonClick() throws IOException {
        changeControlTexture(startButton, UIConfig.START_BUTTON_CLICK);
        sceneController.changeSceneAfter(sceneController.getServerConnectScene(), 150);
    }

    public void onExitButtonClick() {
        changeControlTexture(exitButton, UIConfig.EXIT_BUTTON_CLICK);
        FXGL.getGameController().exit();
    }

    public void onRulesButtonClick() throws IOException {
        changeControlTexture(rulesButton, UIConfig.RULES_BUTTON_CLICK);
        sceneController.changeSceneAfter(sceneController.getRulesScene(), 150);
    }

    @Override
    public void onStartButtonHover() {
        changeControlTexture(startButton, UIConfig.START_BUTTON_HOVER);
    }

    @Override
    public void onStartButtonExit() {
        changeControlTexture(startButton, UIConfig.START_BUTTON_DEFAULT);
    }
}
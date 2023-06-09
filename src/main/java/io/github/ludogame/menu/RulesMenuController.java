package io.github.ludogame.menu;

import com.almasb.fxgl.dsl.FXGL;
import io.github.ludogame.config.UIConfig;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RulesMenuController extends DefaultMenuButtonAction implements Initializable {

    @FXML
    public Label rulesText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeControlTexture(exitButton, UIConfig.EXIT_BUTTON_DEFAULT);
        changeControlTexture(rulesText, "menu/texture/rules_text.png");
    }

    public void onExitButtonClick() throws IOException {
        changeControlTexture(exitButton, UIConfig.EXIT_BUTTON_CLICK);
        sceneController.changeSceneAfter(sceneController.getMainMenuScene(), 150);
    }

    @Override
    public void onStartButtonHover() {
        FXGL.play("click-select.wav");
        changeControlTexture(startButton, UIConfig.START_BUTTON_HOVER);
    }

    @Override
    public void onStartButtonExit() {
        FXGL.play("click-select.wav");
        changeControlTexture(startButton, UIConfig.START_BUTTON_DEFAULT);
    }
}
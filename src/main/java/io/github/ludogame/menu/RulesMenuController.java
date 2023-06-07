package io.github.ludogame.menu;

import io.github.ludogame.config.UIConfig;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class RulesMenuController extends DefaultMenuButtonAction implements Initializable {

    @FXML
    public Label rulesText;

    @FXML
    public Label rulesTileText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeControlTexture(exitButton, UIConfig.EXIT_BUTTON_DEFAULT);
        changeControlTexture(rulesText, "menu/texture/rules_text.png");
    }

    public void onExitButtonClick() {
        changeControlTexture(exitButton, UIConfig.EXIT_BUTTON_CLICK);
        sceneController.changeSceneAfter(sceneController.getMainMenuScene(), 150);
    }
}

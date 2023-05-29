package menu;

import config.UIConfig;
import controller.SceneController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuRulesController extends MenuDefaultButtonAction implements Initializable {

    @FXML
    public Label rulesText;

    @FXML
    public Label rulesTileText;

//    @FXML
//    public Button exitButton;

    private SceneController sceneController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeControlTexture(exitButton, UIConfig.EXIT_BUTTON_DEFAULT);
    }

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    public void onExitButtonClick() {
        sceneController.changeSceneAfter(sceneController.getMainMenuScene(), 150);
    }
}

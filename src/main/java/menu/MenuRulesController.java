package menu;

import config.UIConfig;
import controller.SceneController;
import com.almasb.fxgl.dsl.FXGL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;

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
        imageInit(exitButton, UIConfig.EXIT_BUTTON_DEFAULT);
    }
    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    public void onExitButtonClick(ActionEvent actionEvent) {
        FXGL.getGameScene().removeUINode(sceneController.getMenuRules());
        FXGL.getGameScene().addUINode(sceneController.getMenuStart());
    }
}
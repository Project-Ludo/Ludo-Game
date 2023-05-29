package menu;

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
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuConnectionController extends MenuDefaultButtonAction implements Initializable {
//    @FXML
//    public Button toGameSceneButton;
//    @FXML
//    public Button exitButton;
//    @FXML
//    public Button musicButton;
//    @FXML
//    public Label LudoConnectionText;

    private SceneController sceneController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageInit(toGameSceneButton, "menu/texture/start-button.png");
        imageInit(exitButton, "menu/texture/exit-button_default.png");
        imageInit(musicButton, "menu/texture/music-button_default.png");
    }

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    public void toGameSceneButtonAction(ActionEvent actionEvent) {
        System.out.println("start button Click");
    }

    public void exitButtonAction(ActionEvent actionEvent) {
        FXGL.getGameScene().removeUINode(sceneController.getMenuConnection());
        FXGL.getGameScene().addUINode(sceneController.getMenuStart());
    }
}

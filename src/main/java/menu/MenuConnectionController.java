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
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuConnectionController extends MenuDefaultButtonAction implements Initializable {

    private SceneController sceneController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageInit(startButton, UIConfig.START_BUTTON_DEFAULT);
        imageInit(exitButton, UIConfig.EXIT_BUTTON_DEFAULT);
        imageInit(musicButton, UIConfig.MUSIC_BUTTON_DEFAULT);
    }

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    public void onStartButtonClick(ActionEvent actionEvent) {
        System.out.println("start button Click");
    }

    public void onExitButtonClick(ActionEvent actionEvent) {
        FXGL.getGameScene().removeUINode(sceneController.getMenuConnection());
        FXGL.getGameScene().addUINode(sceneController.getMenuStart());
    }

    public void onMusicButtonClick(ActionEvent actionEvent) {
        System.out.println("Music");
    }
}

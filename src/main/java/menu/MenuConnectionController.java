package menu;

import config.UIConfig;
import controller.SceneController;
import com.almasb.fxgl.dsl.FXGL;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.util.Duration;

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

        imageInit(startButton, UIConfig.START_BUTTON_CLICK);

        FXGL.runOnce(() -> {
            FXGL.getGameScene().removeUINode(sceneController.getMenuConnection());
            FXGL.getGameScene().addUINode(sceneController.getLobby());
        }, Duration.seconds(0.15));
    }

    public void onExitButtonClick(ActionEvent actionEvent) {
        FXGL.getGameScene().removeUINode(sceneController.getMenuConnection());
        FXGL.getGameScene().addUINode(sceneController.getMenuStart());
    }

    public void onMusicButtonClick(ActionEvent actionEvent) {
        System.out.println("Music");
    }
}

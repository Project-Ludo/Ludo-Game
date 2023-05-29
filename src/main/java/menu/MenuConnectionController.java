package menu;

import config.UIConfig;
import controller.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuConnectionController extends MenuDefaultButtonAction implements Initializable {

    private SceneController sceneController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeControlTexture(startButton, UIConfig.START_BUTTON_DEFAULT);
        changeControlTexture(exitButton, UIConfig.EXIT_BUTTON_DEFAULT);
        changeControlTexture(musicButton, UIConfig.MUSIC_BUTTON_DEFAULT);
    }

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    public void onStartButtonClick(ActionEvent actionEvent) {
        changeControlTexture(startButton, UIConfig.START_BUTTON_CLICK);
        sceneController.changeSceneAfter(sceneController.getLobby(), 150);
    }

    public void onExitButtonClick(ActionEvent actionEvent) {
        sceneController.changeSceneAfter(sceneController.getMenuStart(), 150);
    }

    public void onMusicButtonClick(ActionEvent actionEvent) {
        System.out.println("Music");
    }
}

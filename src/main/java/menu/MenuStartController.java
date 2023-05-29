package menu;

import config.UIConfig;
import controller.SceneController;
import com.almasb.fxgl.dsl.FXGL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;


public class MenuStartController extends MenuDefaultButtonAction implements Initializable {

    @FXML
    public Label bajkoweLudoText;

    private SceneController sceneController;

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeControlTexture(startButton, UIConfig.START_BUTTON_DEFAULT);
        changeControlTexture(rulesButton, UIConfig.RULES_BUTTON_DEFAULT);
        changeControlTexture(exitButton, UIConfig.EXIT_BUTTON_DEFAULT);
        changeControlTexture(musicButton, UIConfig.MUSIC_BUTTON_DEFAULT);
        changeControlTexture(bajkoweLudoText, "menu/texture/tile.png");
    }

    public void onStartButtonClick() {
        changeControlTexture(startButton, UIConfig.START_BUTTON_CLICK);
        sceneController.changeSceneAfter(sceneController.getServerConnectScene(), 150);
    }

    public void onExitButtonClick(ActionEvent actionEvent) {
        FXGL.getGameController().exit();
    }

    public void onMusicButtonClick(ActionEvent actionEvent) {
        System.out.println("Click");
    }

    public void onRulesButtonClick(ActionEvent actionEvent) {
        sceneController.changeSceneAfter(sceneController.getRulesScene(), 150);
    }
}

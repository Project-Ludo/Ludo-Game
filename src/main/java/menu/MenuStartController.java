package menu;

import config.UIConfig;
import controller.SceneController;
import com.almasb.fxgl.dsl.FXGL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;

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
        imageInit(startButton, UIConfig.START_BUTTON_DEFAULT);
        imageInit(rulesButton, UIConfig.RULES_BUTTON_DEFAULT);
        imageInit(exitButton, UIConfig.EXIT_BUTTON_DEFAULT);
        imageInit(musicButton, UIConfig.MUSIC_BUTTON_DEFAULT);
        imageInit(bajkoweLudoText, "menu/texture/tile.png");
    }

    ///Start Button
    public void onStartButtonClick(ActionEvent actionEvent) {
        imageInit(startButton, UIConfig.START_BUTTON_CLICK);
        FXGL.runOnce(() -> {
            FXGL.getGameScene().removeUINode(sceneController.getMenuStart());
            FXGL.getGameScene().addUINode(sceneController.getMenuConnection());
        }, Duration.seconds(0.15));
    }

    public void onExitButtonClick(ActionEvent actionEvent) {
        FXGL.getGameController().exit();
    }

    public void onMusicButtonClick(ActionEvent actionEvent) {
        System.out.println("Click");
    }

    public void onRulesButtonClick(ActionEvent actionEvent) {
        FXGL.getGameScene().removeUINode(sceneController.getMenuStart());
        FXGL.getGameScene().addUINode(sceneController.getMenuRules());
    }
}

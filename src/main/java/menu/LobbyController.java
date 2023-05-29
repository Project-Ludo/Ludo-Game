package menu;

import com.almasb.fxgl.dsl.FXGL;
import config.UIConfig;
import controller.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class LobbyController extends MenuDefaultButtonAction implements Initializable {

    @FXML
    public Label playerInLobby;

    private SceneController sceneController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeControlTexture(startButton, UIConfig.START_BUTTON_DEFAULT);
        changeControlTexture(exitButton, UIConfig.EXIT_BUTTON_DEFAULT);
        changeControlTexture(musicButton, UIConfig.MUSIC_BUTTON_DEFAULT);

        Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/04B_30__.TTF"), 12);
        playerInLobby.setFont(font);
    }

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    public void onStartButtonClick(ActionEvent actionEvent) {
        changeControlTexture(startButton, UIConfig.START_BUTTON_CLICK);
        FXGL.runOnce(() -> {
            changeControlTexture(startButton, UIConfig.START_BUTTON_DEFAULT);
        }, Duration.seconds(0.15));
    }

    public void onExitButtonClick(ActionEvent actionEvent) {
        FXGL.getGameScene().removeUINode(sceneController.getLobby());
        FXGL.getGameScene().addUINode(sceneController.getMenuStart());
    }

    public void onMusicButtonClick(ActionEvent actionEvent) {
        System.out.println("MUSIC <3");
    }
}

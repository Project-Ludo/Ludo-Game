package menu;

import config.UIConfig;
import com.almasb.fxgl.dsl.FXGL;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;


public class MenuStartController extends MenuDefaultButtonAction implements Initializable {

    @FXML
    public Label bajkoweLudoText;

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

    public void onExitButtonClick() {
        changeControlTexture(exitButton, UIConfig.EXIT_BUTTON_CLICK);
        FXGL.getGameController().exit();
    }

    public void onMusicButtonClick() {
        changeControlTextureFor(musicButton, UIConfig.MUSIC_BUTTON_CLICK, 150, UIConfig.MUSIC_BUTTON_HOVER);
    }

    public void onRulesButtonClick() {
        sceneController.changeSceneAfter(sceneController.getRulesScene(), 150);
    }
}

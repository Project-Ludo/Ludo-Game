package menu;

import com.almasb.fxgl.dsl.FXGL;
import config.UIConfig;
import controller.SceneController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public abstract class MenuDefaultButtonAction {

    @FXML
    public Button rulesButton;

    @FXML
    public Button musicButton;

    @FXML
    public Button exitButton;

    @FXML
    public Button startButton;

    protected SceneController sceneController;

    public void changeControlTexture(Labeled control, String imagePath) {
        ImageView view = new ImageView(new Image(imagePath));
        view.setFitWidth(control.getPrefWidth());
        view.setPreserveRatio(true);
        control.setGraphic(view);
    }

    public void changeControlTextureFor(Labeled control, String imagePath, int duration, String finalImagePath) {
        changeControlTexture(control, imagePath);
        FXGL.runOnce(() -> changeControlTexture(control, finalImagePath), Duration.millis(duration));
    }

    public void onStartButtonHover() {
        changeControlTexture(startButton, UIConfig.START_BUTTON_HOVER);
    }

    public void onStartButtonExit() {
        changeControlTexture(startButton, UIConfig.START_BUTTON_DEFAULT);
    }

    public void onMusicButtonHover() {
        changeControlTexture(musicButton, UIConfig.MUSIC_BUTTON_HOVER);
    }

    public void onMusicButtonExit() {
        changeControlTexture(musicButton, UIConfig.MUSIC_BUTTON_DEFAULT);
    }

    public void onRulesButtonHover() {
        changeControlTexture(rulesButton, UIConfig.RULES_BUTTON_HOVER);
    }

    public void onRulesButtonExit() {
        changeControlTexture(rulesButton, UIConfig.RULES_BUTTON_DEFAULT);
    }

    public void onExitButtonHover() {
        changeControlTexture(exitButton, UIConfig.EXIT_BUTTON_HOVER);
    }

    public void onExitButtonExit() {
        changeControlTexture(exitButton, UIConfig.EXIT_BUTTON_DEFAULT);
    }

    public void initSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }
}

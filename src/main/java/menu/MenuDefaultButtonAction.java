package menu;

import config.UIConfig;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class MenuDefaultButtonAction {

    @FXML
    public Button rulesButton;

    @FXML
    public Button musicButton;

    @FXML
    public Button exitButton;

    @FXML
    public Button startButton;

    public void changeControlTexture(Labeled control, String imagePath) {
        ImageView view = new ImageView(new Image(imagePath));
        view.setFitWidth(control.getPrefWidth());
        view.setPreserveRatio(true);
        control.setGraphic(view);
    }

    public void onStartButtonHover() {
        changeControlTexture(startButton, UIConfig.START_BUTTON_HOVER);
    }

    public void onStartButtonExited() {
        changeControlTexture(startButton, UIConfig.START_BUTTON_DEFAULT);
    }

    public void onMusicButtonHover() {
        changeControlTexture(musicButton, UIConfig.MUSIC_BUTTON_HOVER);
    }

    public void onMusicButtonExited() {
        changeControlTexture(musicButton, UIConfig.MUSIC_BUTTON_DEFAULT);
    }

    public void onRulesButtonHover() {
        changeControlTexture(rulesButton, UIConfig.RULES_BUTTON_HOVER);
    }

    public void onRulesButtonExited() {
        changeControlTexture(rulesButton, UIConfig.RULES_BUTTON_DEFAULT);
    }

    public void onExitButtonHover() {
        changeControlTexture(exitButton, UIConfig.EXIT_BUTTON_HOVER);
    }

    public void onExitButtonExited() {
        changeControlTexture(exitButton, UIConfig.EXIT_BUTTON_DEFAULT);
    }
}

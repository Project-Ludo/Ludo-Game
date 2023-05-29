package menu;

import config.UIConfig;
import controller.SceneController;
import com.almasb.fxgl.dsl.FXGL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class MenuDefaultButtonAction{
    @FXML
    public Button rulesButton;
    @FXML
    public Button musicButton;
    @FXML
    public Button exitButton;
    @FXML
    public Button startButton;

    public void imageInit(Labeled control, String imagePath) {
        ImageView view = new ImageView(new Image(imagePath));
        view.setFitWidth(control.getPrefWidth());
        view.setPreserveRatio(true);
        control.setGraphic(view);
    }

    public void onStartButtonHover(MouseEvent mouseEvent) {

        imageInit(startButton, UIConfig.START_BUTTON_HOVER);
    }

    public void onStartButtonExited(MouseEvent mouseEvent) {

        imageInit(startButton, UIConfig.START_BUTTON_DEFAULT);
    }
    ///Music Button
    public void onMusicButtonHover(MouseEvent dragEvent) {
        imageInit(musicButton, UIConfig.MUSIC_BUTTON_HOVER);
    }
    public void onMusicButtonExited(MouseEvent mouseEvent) {
        imageInit(musicButton, UIConfig.MUSIC_BUTTON_DEFAULT);
    }

    //Rules button
    public void onRulesButtonHover(MouseEvent dragEvent) {
        imageInit(rulesButton, UIConfig.RULES_BUTTON_HOVER);
    }
    public void onRulesButtonExited(MouseEvent mouseEvent) {
        imageInit(rulesButton, UIConfig.RULES_BUTTON_DEFAULT);
    }

    //Exit Button
    public void onExitButtonHover(MouseEvent dragEvent) {
        imageInit(exitButton, UIConfig.EXIT_BUTTON_HOVER);
    }

    public void onExitButtonExited(MouseEvent dragEvent) {
        imageInit(exitButton, UIConfig.EXIT_BUTTON_DEFAULT);
    }
}
package controller;

import com.almasb.fxgl.app.scene.GameView;
import com.almasb.fxgl.dsl.FXGL;
import config.UIConfig;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import menu.DefaultMenuButtonAction;

import java.net.URL;
import java.util.ResourceBundle;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;

public class GameController extends DefaultMenuButtonAction implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeControlTexture(startButton, UIConfig.START_BUTTON_DEFAULT);
        changeControlTexture(exitButton, UIConfig.EXIT_BUTTON_DEFAULT);
        changeControlTexture(musicButton, UIConfig.MUSIC_BUTTON_DEFAULT);

        setBoard();
    }

    private void setBoard(){
        ImageView imageView = new ImageView(new Image("assets/textures/board/board.png"));

        imageView.setFitWidth(UIConfig.BOARD_WIDTH);
        imageView.setPreserveRatio(true);
        imageView.setVisible(true);

        imageView.setLayoutX(UIConfig.BOARD_START_LAYOUT_X);
        imageView.setLayoutY(UIConfig.BOARD_START_LAYOUT_Y);

        GameView gameView = new GameView(imageView, 100);
        getGameScene().addGameView(gameView);
    }

    public void onStartButtonClick(ActionEvent actionEvent) {
        System.out.println("RZUT");
    }

    public void onMusicButtonClick(ActionEvent actionEvent) {
        System.out.println("Music");
    }

    public void onExitButtonClick(ActionEvent actionEvent) {
        sceneController.changeSceneAfter(sceneController.getMainMenuScene(), 150);
        getGameScene().clearGameViews();
    }
}

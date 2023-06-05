package io.github.ludogame.menu;

import com.almasb.fxgl.dsl.FXGL;
import io.github.ludogame.LudoPlayerApp;
import io.github.ludogame.config.UIConfig;
import io.github.ludogame.player.LudoPlayer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class LobbyController extends DefaultMenuButtonAction implements Initializable {

    @FXML
    public Label playerInLobby;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeControlTexture(startButton, UIConfig.START_BUTTON_DEFAULT);
        changeControlTexture(exitButton, UIConfig.EXIT_BUTTON_DEFAULT);
        changeControlTexture(musicButton, UIConfig.MUSIC_BUTTON_DEFAULT);

        //FIXME font below doesnt support multiline

        //FIXME handle player disconnection (i guess by using isConnected flag in LudoPlayer - setting the flag is not done)
//        Font font = Font.loadFont(getClass().getResourceAsStream("/assets/ui/fonts/04B_30__.TTF"), 12);
//        playerInLobby.setFont(font);

        FXGL.run(() -> {
            String collect = LudoPlayerApp.ludoGame.getPlayers().stream()
                    .map(LudoPlayer::getNickname)
                    .collect(Collectors.joining("\n"));

            playerInLobby.setText(collect);
        }, Duration.millis(500));
    }

    public void onStartButtonClick() {
        changeControlTexture(startButton, UIConfig.START_BUTTON_CLICK);
    }

    public void onExitButtonClick() {
        changeControlTexture(exitButton, UIConfig.EXIT_BUTTON_CLICK);
        sceneController.changeSceneAfter(sceneController.getMainMenuScene(), 150);
    }

    public void onMusicButtonClick() {
        changeControlTextureFor(musicButton, UIConfig.MUSIC_BUTTON_CLICK, 150, UIConfig.MUSIC_BUTTON_HOVER);
    }
}

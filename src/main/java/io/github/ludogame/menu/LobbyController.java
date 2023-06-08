package io.github.ludogame.menu;

import com.almasb.fxgl.dsl.FXGL;
import io.github.ludogame.LudoPlayerApp;
import io.github.ludogame.config.UIConfig;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class LobbyController extends DefaultMenuButtonAction implements Initializable {

    @FXML
    public Label playerInLobby;

    @FXML
    public Label countdownText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeControlTexture(startButton, UIConfig.START_BUTTON_DEFAULT);
        changeControlTexture(exitButton, UIConfig.EXIT_BUTTON_DEFAULT);
        changeControlTexture(musicButton, UIConfig.MUSIC_BUTTON_DEFAULT);

        //FIXME font below doesnt support multiline
//        Font font = Font.loadFont(getClass().getResourceAsStream("/assets/ui/fonts/04B_30__.TTF"), 12);
//        playerInLobby.setFont(font);

        FXGL.run(() -> {
            StringBuffer stringBuffer = new StringBuffer();
            LudoPlayerApp.ludoGame.getPlayers().forEach(player -> {
                stringBuffer.append(player.getNickname());

                if (!player.isConnected()) {
                    stringBuffer.append(" (offline)");
                }

                stringBuffer.append(player.isReady() ? "[ready]" : "[not ready]");

                stringBuffer.append("\n");

            });

            playerInLobby.setText(stringBuffer.toString());
            if (LudoPlayerApp.ludoGame.isCountdownStarted()) {
                countdownText.setText("Do startu: " + LudoPlayerApp.ludoGame.getStartCountdown());
            }
        }, Duration.millis(500));
    }

    public void onStartButtonClick() {
        LudoPlayerApp.player.setReady(true);
        changeControlTexture(startButton, UIConfig.START_BUTTON_CLICK);
    }

    public void onExitButtonClick() {
        changeControlTexture(exitButton, UIConfig.EXIT_BUTTON_CLICK);
        LudoPlayerApp.player.disconnectFromServer();
        sceneController.changeSceneAfter(sceneController.getMainMenuScene(), 150);
    }
}

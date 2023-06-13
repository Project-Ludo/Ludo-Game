package io.github.ludogame.menu;

import animatefx.animation.ZoomIn;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.time.TimerAction;
import io.github.ludogame.LudoPlayerApp;
import io.github.ludogame.config.UIConfig;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LobbyController extends DefaultMenuButtonAction implements Initializable {
    @FXML
    public Label countdownText;
    @FXML
    public Label lobbyTile;
    @FXML
    public StackPane lobbyStackPane;

    public Label playerInLobby;

    TimerAction run;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeControlTexture(startButton, UIConfig.START_BUTTON_DEFAULT);
        changeControlTexture(exitButton, UIConfig.EXIT_BUTTON_DEFAULT);
        changeControlTexture(musicButton, UIConfig.MUSIC_BUTTON_DEFAULT);

        if (run != null) {
            run.resume();
            return;
        }

        addImageToLobbyPane();
        addLabelToLobbyPane();

        run = FXGL.run(() -> {
            StringBuffer stringBuffer = new StringBuffer();
            LudoPlayerApp.ludoGame.getPlayers().forEach(player -> {
                stringBuffer.append(player.getNickname());

                if (!player.isConnected()) {
                    stringBuffer.append(" (offline)");
                }

                stringBuffer.append(player.isReady() ? "[ready]" : "[not ready]");
                stringBuffer.append("[color: ").append(player.getColor().name()).append("]");
                stringBuffer.append("\n");

            });

            playerInLobby.setText(stringBuffer.toString());

            if (LudoPlayerApp.ludoGame.isCountdownStarted()) {
                countdownText.setText("Do startu: " + LudoPlayerApp.ludoGame.getStartCountdown());
                new ZoomIn(countdownText).setSpeed(2).play();
                if (LudoPlayerApp.ludoGame.getStartCountdown().get() == 0) {
                    try {
                        sceneController.changeSceneAfter(sceneController.getGameScene(), 500);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    run.pause();
                }
            }
        }, Duration.millis(500));
    }
    private void addLabelToLobbyPane(){
        playerInLobby = new Label();
        playerInLobby.setFont(new Font(18));
        playerInLobby.setTextAlignment(TextAlignment.CENTER);
        playerInLobby.setWrapText(true);
        playerInLobby.setPrefWidth(300);
        lobbyStackPane.getChildren().add(playerInLobby);
    }

    private void addImageToLobbyPane() {
        ImageView imageView = new ImageView(
                new Image(getClass().getClassLoader().getResource("menu/texture/player-lobby_v2.png").toExternalForm())
        );
        imageView.setFitWidth(400);
        imageView.setPreserveRatio(true);
        lobbyStackPane.getChildren().add(imageView);
    }

    public void onStartButtonClick() {
        LudoPlayerApp.player.setReady(true);
        changeControlTexture(startButton, UIConfig.START_BUTTON_CLICK);
    }

    public void onExitButtonClick() throws IOException {
        changeControlTexture(exitButton, UIConfig.EXIT_BUTTON_CLICK);
        LudoPlayerApp.player.disconnectFromServer();
        sceneController.changeSceneAfter(sceneController.getMainMenuScene(), 150);
    }

    public void onMusicButtonClick() {
        changeControlTextureFor(musicButton, UIConfig.MUSIC_BUTTON_CLICK, 150, UIConfig.MUSIC_BUTTON_HOVER);
    }
}

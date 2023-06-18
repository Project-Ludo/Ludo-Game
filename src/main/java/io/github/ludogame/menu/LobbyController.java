package io.github.ludogame.menu;

import animatefx.animation.Shake;
import animatefx.animation.ZoomIn;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.time.TimerAction;
import io.github.ludogame.LudoPlayerApp;
import io.github.ludogame.config.UIConfig;
import io.github.ludogame.player.LudoPlayer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class LobbyController extends DefaultMenuButtonAction implements Initializable {
    @FXML
    public Label countdownText;
    @FXML
    public Label lobbyTile;
    @FXML
    public StackPane lobbyStackPane;

    public TextFlow firstPlayer = new TextFlow();
    public TextFlow secondPlayer = new TextFlow();
    public TextFlow thirdPlayer = new TextFlow();
    public TextFlow fourthPlayer = new TextFlow();
    public TextFlow countDown = new TextFlow();

    private final List<TextFlow> playerLabels = List.of(firstPlayer, secondPlayer, thirdPlayer, fourthPlayer);

    TimerAction run;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeControlTexture(startButton, UIConfig.READY_BUTTON_DEFAULT);
        changeControlTexture(exitButton, UIConfig.EXIT_BUTTON_DEFAULT);
        changeControlTexture(musicButton, UIConfig.MUSIC_BUTTON_DEFAULT);

        if (run != null) {
            run.resume();
            return;
        }

        addImageToLobbyPane();
        addLabelToLobbyPane();

        AtomicInteger previousCounter = new AtomicInteger(0);
        run = FXGL.run(() -> {
            playerLabels.forEach(p -> p.getChildren().clear());
            for (int i = 0; i < LudoPlayerApp.ludoGame.getPlayers().size(); i++) {
                LudoPlayer player = LudoPlayerApp.ludoGame.getPlayers().get(i);
                TextFlow textFlow = playerLabels.get(i);
                ObservableList<Node> children = textFlow.getChildren();

                Text text = getText(player.getNickname(), player.getColor().paint, 25);
                children.add(text);
                if (player.isConnected()) {
                    text = getText("(gotowy)", player.isReady() ? Color.GREEN : Color.RED, 21);
                } else {
                    text = getText("(wyszedl)", Color.DARKGOLDENROD, 21);
                }

                text.setTranslateX(25);
                children.add(text);
            }

            if (LudoPlayerApp.ludoGame.isCountdownStarted()) {
                if (previousCounter.get() != LudoPlayerApp.ludoGame.getStartCountdown().get()) {
                    countDown.getChildren().clear();
                    Text text = getText("Do startu:", Color.WHITE, 25);
                    countDown.getChildren().add(text);
                    countDown.getChildren().add(getTimeCountDownText());
                    new ZoomIn(countDown).setSpeed(1.5).play();
                    previousCounter.set(LudoPlayerApp.ludoGame.getStartCountdown().get());
                }

                if (LudoPlayerApp.ludoGame.getStartCountdown().get() == 0) {
                    try {
                        sceneController.changeScene(sceneController.getGameScene());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    run.pause();
                }
            }
        }, Duration.millis(100));
    }

    private Text getTimeCountDownText() {
        int i = LudoPlayerApp.ludoGame.getStartCountdown().get();
        Paint paint = null;

        if (i <= 60) {
            paint = Color.GREEN;
        }
        if (i <= 10) {
            paint = Color.DARKGOLDENROD;
        }
        if (i <= 3) {
            paint = Color.RED;
        }

        return getText(" " + i, paint, 25);
    }

    private Text getText(String content, Paint color, double size) {
        Text text = new Text(content);
        text.setFont(FXGL.getAssetLoader().loadFont("04B_30__.TTF").newFont(size));
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFill(color);
        return text;
    }

    private void addLabelToLobbyPane() {
        int translateY = 140;
        for (TextFlow textFlow : playerLabels) {
            lobbyStackPane.getChildren().add(textFlow);
            textFlow.setTranslateX(170);
            textFlow.setTranslateY(translateY);
            textFlow.setMouseTransparent(true);
            textFlow.setOnMouseClicked(mouseEvent -> {
                System.out.println(mouseEvent.getTarget().toString());
            });
            translateY += 55;
        }

        countDown.setTranslateX(215);
        countDown.setTranslateY(translateY + 10);
        lobbyStackPane.getChildren().add(countDown);
        lobbyStackPane.setMouseTransparent(true);
    }

    private void addImageToLobbyPane() {
        ImageView imageView = new ImageView(
                new Image(getClass().getClassLoader().getResource("menu/texture/lobby.png").toExternalForm())
        );
        imageView.setFitWidth(700);
        imageView.setPreserveRatio(true);
        lobbyStackPane.getChildren().add(imageView);
    }

    public void onStartButtonClick() {
        LudoPlayerApp.player.setReady(!LudoPlayerApp.player.isReady());
        changeControlTexture(startButton, UIConfig.READY_BUTTON_CLICK);
    }

    public void onExitButtonClick() throws IOException {
        changeControlTexture(exitButton, UIConfig.EXIT_BUTTON_CLICK);
        LudoPlayerApp.player.disconnectFromServer();
        sceneController.changeSceneAfter(sceneController.getMainMenuScene(), 150);
    }
    @Override
    public void onStartButtonHover() {
        changeControlTexture(startButton, UIConfig.READY_BUTTON_HOVER);
    }

    @Override
    public void onStartButtonExit() {
        changeControlTexture(startButton, UIConfig.READY_BUTTON_DEFAULT);
    }
}
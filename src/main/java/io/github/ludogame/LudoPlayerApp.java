package io.github.ludogame;

import io.github.ludogame.config.Config;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import io.github.ludogame.controller.SceneController;
import io.github.ludogame.game.LudoGame;
import io.github.ludogame.music.GameMusic;
import io.github.ludogame.player.LudoPlayer;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.UUID;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;

public class LudoPlayerApp extends GameApplication {

    private SceneController sceneController;
    public static final LudoGame ludoGame = new LudoGame();
    public static final LudoPlayer player = new LudoPlayer(UUID.randomUUID());
    private GameMusic music;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(Config.MAP_WIDTH);
        settings.setHeight(Config.MAP_HEIGHT);
        settings.setTitle("Bajkowe Ludo");
    }

    @Override
    protected void initGame() {
        loadScene();
        try {
            getGameScene().addUINode(sceneController.getMainMenuScene());
        } catch (IOException e) {
            e.printStackTrace();
        }
      
        music = new GameMusic("start_menu.wav");
    }

    public GameMusic getMusic() {
        return music;
    }

    private void loadScene() {
        FXMLLoader fxmlLoaderStartMenu = new FXMLLoader(getClass().getClassLoader().getResource("Menu/ludo-start-menu.fxml"));
        FXMLLoader fxmlLoaderRulesMenu = new FXMLLoader(getClass().getClassLoader().getResource("Menu/ludo-rules-menu.fxml"));
        FXMLLoader fxmlLoaderConnectionMenu = new FXMLLoader(getClass().getClassLoader().getResource("Menu/ludo-connection-menu.fxml"));
        FXMLLoader fxmlLoaderLobby = new FXMLLoader(getClass().getClassLoader().getResource("Menu/ludo-lobby.fxml"));
        FXMLLoader fxmlLoaderGame = new FXMLLoader(getClass().getClassLoader().getResource("game/ludo-game.fxml"));

        sceneController = new SceneController(fxmlLoaderStartMenu, fxmlLoaderRulesMenu, fxmlLoaderConnectionMenu, fxmlLoaderLobby, fxmlLoaderGame);
    }

    @Override
    protected void initUI() {
        setBackground();
    }

    private void setBackground() {
        getGameScene().setBackgroundRepeat("background/background.png");
    }

    public SceneController getSceneController() {
        return sceneController;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

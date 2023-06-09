package io.github.ludogame;

import io.github.ludogame.config.Config;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import io.github.ludogame.controller.SceneController;
import io.github.ludogame.menu.ConnectionMenuController;
import io.github.ludogame.menu.LobbyController;
import io.github.ludogame.menu.MainMenuController;
import io.github.ludogame.menu.RulesMenuController;
import io.github.ludogame.game.LudoGame;
import io.github.ludogame.player.LudoPlayer;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.UUID;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;

public class LudoPlayerApp extends GameApplication {

    private AStarGrid grid;
    private SceneController sceneController;
    public static final LudoGame ludoGame = new LudoGame();
    public static final LudoPlayer player = new LudoPlayer(UUID.randomUUID());

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(Config.MAP_WIDTH);
        settings.setHeight(Config.MAP_HEIGHT);
        settings.setTitle("Bajkowe Ludo");
    }

    @Override
    protected void initGame() {

        loadScene();
        getGameScene().addUINode(sceneController.getMainMenuScene());

    }

    private void loadScene() {
        FXMLLoader fxmlLoaderStartMenu = new FXMLLoader(getClass().getClassLoader().getResource("Menu/ludo-start-menu.fxml"));
        FXMLLoader fxmlLoaderRulesMenu = new FXMLLoader(getClass().getClassLoader().getResource("Menu/ludo-rules-menu.fxml"));
        FXMLLoader fxmlLoaderConnectionMenu = new FXMLLoader(getClass().getClassLoader().getResource("Menu/ludo-connection-menu.fxml"));
        FXMLLoader fxmlLoaderLobby = new FXMLLoader(getClass().getClassLoader().getResource("Menu/ludo-lobby.fxml"));

        try {
            sceneController = new SceneController(fxmlLoaderStartMenu.load(), fxmlLoaderRulesMenu.load(), fxmlLoaderConnectionMenu.load(), fxmlLoaderLobby.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MainMenuController menuStartController = fxmlLoaderStartMenu.getController();
        RulesMenuController menuRulesController = fxmlLoaderRulesMenu.getController();
        ConnectionMenuController menuConnectionController = fxmlLoaderConnectionMenu.getController();
        LobbyController lobbyController = fxmlLoaderLobby.getController();
        menuStartController.initSceneController(sceneController);
        menuRulesController.initSceneController(sceneController);
        menuConnectionController.initSceneController(sceneController);
        lobbyController.initSceneController(sceneController);
    }

    @Override
    protected void initUI() {
        setBackground();
    }

    private void setBackground() {
        getGameScene().setBackgroundRepeat("background/background.png");
    }

    public static void main(String[] args) {
        launch(args);
    }
}

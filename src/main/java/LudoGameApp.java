import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import config.Config;
import config.UIConfig;
import game.BoardCell;
import game.GameController;
import controller.SceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import menu.ConnectionMenuController;
import menu.LobbyController;
import menu.MainMenuController;
import menu.RulesMenuController;

import java.io.IOException;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;

public class LudoGameApp extends GameApplication {

    private AStarGrid grid;
    private SceneController sceneController;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(Config.MAP_WIDTH);
        settings.setHeight(Config.MAP_HEIGHT);
        settings.setTitle("Bajkowe Ludo");
    }
    @Override
    protected void initGame() {

        loadScene();
        //getGameScene().addUINode(sceneController.getGameScene());
//        LudoPlayer ludoPlayer = new LudoPlayer(UUID.randomUUID());
//        ClientConnector clientConnector = new ClientConnector();
//        clientConnector.connect("localhost", 55555, ludoPlayer);
//        setBoard();
    }

    private void loadScene () {
        FXMLLoader fxmlLoaderStartMenu = new FXMLLoader(getClass().getResource("Menu/ludo-start-menu.fxml"));
        FXMLLoader fxmlLoaderRulesMenu = new FXMLLoader(getClass().getResource("Menu/ludo-rules-menu.fxml"));
        FXMLLoader fxmlLoaderConnectionMenu = new FXMLLoader(getClass().getResource("Menu/ludo-connection-menu.fxml"));
        FXMLLoader fxmlLoaderLobby = new FXMLLoader(getClass().getResource("Menu/ludo-lobby.fxml"));
        FXMLLoader fxmlLoaderGame = new FXMLLoader(getClass().getResource("game/ludo-game.fxml"));

        try {
            sceneController = new SceneController(fxmlLoaderStartMenu.load(), fxmlLoaderRulesMenu.load(), fxmlLoaderConnectionMenu.load(), fxmlLoaderLobby.load(), fxmlLoaderGame.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MainMenuController menuStartController = fxmlLoaderStartMenu.getController();
        RulesMenuController menuRulesController = fxmlLoaderRulesMenu.getController();
        ConnectionMenuController menuConnectionController = fxmlLoaderConnectionMenu.getController();
        LobbyController lobbyController = fxmlLoaderLobby.getController();
        GameController gameController = fxmlLoaderGame.getController();

        menuStartController.initSceneController(sceneController);
        menuRulesController.initSceneController(sceneController);
        menuConnectionController.initSceneController(sceneController);
        lobbyController.initSceneController(sceneController);
        gameController.initSceneController(sceneController);
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

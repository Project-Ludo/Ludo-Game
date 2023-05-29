import config.Config;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import controller.SceneController;
import javafx.fxml.FXMLLoader;
import menu.LobbyController;
import menu.MenuConnectionController;
import menu.MenuRulesController;
import menu.MenuStartController;

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
        getGameScene().addUINode(sceneController.getMenuStart());

//        getGameWorld().addEntityFactory(new LudoFactory());
//        Level level = getAssetLoader().loadLevel("Ludo.txt", new TextLevelLoader(Config.BLOCK_SIZE, Config.BLOCK_SIZE, '0'));
//        level.getEntities().forEach(entity -> {
//            Point2D fixedPoint = new Point2D(((double) Config.MAP_WIDTH / 2) - ((double) Config.BLOCK_SIZE * Config.MAP_SIZE / 2),
//                    ((double) Config.MAP_HEIGHT / 2) - ((double) Config.BLOCK_SIZE * Config.MAP_SIZE / 2));
//            entity.translate(fixedPoint);
//        });
//
//        getGameWorld().setLevel(level);
//
//        this.grid = AStarGrid.fromWorld(getGameWorld(), Config.MAP_SIZE, Config.MAP_SIZE, Config.BLOCK_SIZE, Config.BLOCK_SIZE, type -> CellState.NOT_WALKABLE);
//        getGameWorld().getEntitiesByType(EntityType.BACKGROUND)
//                .forEach(entity -> entity.setVisible(false));
//
//        LudoPlayer ludoPlayer = new LudoPlayer(UUID.randomUUID());
//        ClientConnector clientConnector = new ClientConnector();
//        clientConnector.connect("localhost", 55555, ludoPlayer);
    }

    private void loadScene() {
        FXMLLoader fxmlLoaderStartMenu = new FXMLLoader(getClass().getResource("Menu/ludo-start-menu.fxml"));
        FXMLLoader fxmlLoaderRulesMenu = new FXMLLoader(getClass().getResource("Menu/ludo-rules-menu.fxml"));
        FXMLLoader fxmlLoaderConnectionMenu = new FXMLLoader(getClass().getResource("Menu/ludo-connection-menu.fxml"));
        FXMLLoader fxmlLoaderLobby = new FXMLLoader(getClass().getResource("Menu/ludo-lobby.fxml"));

        try {
            sceneController = new SceneController(fxmlLoaderStartMenu.load(), fxmlLoaderRulesMenu.load(), fxmlLoaderConnectionMenu.load(), fxmlLoaderLobby.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MenuStartController menuStartController = fxmlLoaderStartMenu.getController();
        MenuRulesController menuRulesController = fxmlLoaderRulesMenu.getController();
        MenuConnectionController menuConnectionController = fxmlLoaderConnectionMenu.getController();
        LobbyController lobbyController = fxmlLoaderLobby.getController();
        menuStartController.setSceneController(sceneController);
        menuRulesController.setSceneController(sceneController);
        menuConnectionController.setSceneController(sceneController);
        lobbyController.setSceneController(sceneController);
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

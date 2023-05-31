import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.GameView;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.entity.level.text.TextLevelLoader;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import network.client.ClientConnector;
import player.LudoPlayer;

import java.util.UUID;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getAssetLoader;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class LudoGameApp extends GameApplication {

    private AStarGrid grid;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(Config.MAP_WIDTH);
        settings.setHeight(Config.MAP_HEIGHT);
        settings.setTitle("Bajkowe Ludo");
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new LudoFactory());
        Level level = getAssetLoader().loadLevel("Ludo.txt", new TextLevelLoader(Config.BLOCK_SIZE, Config.BLOCK_SIZE, '0'));
        level.getEntities().forEach(entity -> {
            Point2D fixedPoint = new Point2D(((double) Config.MAP_WIDTH / 2) - ((double) Config.BLOCK_SIZE * Config.MAP_SIZE / 2),
                    ((double) Config.MAP_HEIGHT / 2) - ((double) Config.BLOCK_SIZE * Config.MAP_SIZE / 2));
            entity.translate(fixedPoint);
        });

        getGameWorld().setLevel(level);

        this.grid = AStarGrid.fromWorld(getGameWorld(), Config.MAP_SIZE, Config.MAP_SIZE, Config.BLOCK_SIZE, Config.BLOCK_SIZE, type -> CellState.NOT_WALKABLE);
        getGameWorld().getEntitiesByType(EntityType.BACKGROUND)
                .forEach(entity -> entity.setVisible(false));

        LudoPlayer ludoPlayer = new LudoPlayer(UUID.randomUUID());
        ClientConnector clientConnector = new ClientConnector();
        clientConnector.connect("localhost", 55555, ludoPlayer);

        setBoard();
    }

    @Override
    protected void initUI() {
        setBackground();
    }

    private void setBackground() {
        getGameScene().setBackgroundRepeat("background/background.png");
    }
    private void setBoard(){
        ImageView imageView = new ImageView(new Image("assets/textures/board/ilemozna2.png"));
        //TODO extract to UIConfig
        int BOARD_WIDTH = 500;
        int BOARD_START_LAYOUT_X = ((Config.MAP_WIDTH / 2) - (BOARD_WIDTH / 2));
        int BOARD_START_LAYOUT_Y = ((Config.MAP_HEIGHT / 2) - (BOARD_WIDTH / 2));

        imageView.setFitWidth(BOARD_WIDTH);
        imageView.setPreserveRatio(true);
        imageView.setVisible(true);

        imageView.setLayoutX(BOARD_START_LAYOUT_X);
        imageView.setLayoutY(BOARD_START_LAYOUT_Y);

        GameView gameView = new GameView(imageView, 100);
        getGameScene().addGameView(gameView);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

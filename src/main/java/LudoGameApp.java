import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.entity.level.text.TextLevelLoader;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import javafx.geometry.Point2D;
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

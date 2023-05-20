import Components.AnimationComponent;
import EntityTypePackage.EntityType;
import EntityTypePackage.PlayerType;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.entity.level.text.TextLevelLoader;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

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
        LudoFactory ludoFactory = new LudoFactory();
        getGameWorld().addEntityFactory(ludoFactory);
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

        spawnPlayersPawn(ludoFactory);
    }

    @Override
    protected void initUI() {
        setBackground();
    }

    private void setBackground() {
        getGameScene().setBackgroundRepeat("background/background.png");
    }

    private void spawnPlayersPawn(LudoFactory ludoFactory) {
        List<Entity> bluePlayers = new ArrayList<>();
        bluePlayers.add(ludoFactory.spawnPlayer(new SpawnData(Config.SPAWN_POINT_RED_V1), PlayerType.RED_PLAYER));
        bluePlayers.add(ludoFactory.spawnPlayer(new SpawnData(Config.SPAWN_POINT_RED_V2), PlayerType.RED_PLAYER));
        bluePlayers.add(ludoFactory.spawnPlayer(new SpawnData(Config.SPAWN_POINT_RED_V3), PlayerType.RED_PLAYER));
        bluePlayers.add(ludoFactory.spawnPlayer(new SpawnData(Config.SPAWN_POINT_RED_V4), PlayerType.RED_PLAYER));

        bluePlayers.add(ludoFactory.spawnPlayer(new SpawnData(Config.SPAWN_POINT_BLUE_V1), PlayerType.BLUE_PLAYER));
        bluePlayers.add(ludoFactory.spawnPlayer(new SpawnData(Config.SPAWN_POINT_BLUE_V2), PlayerType.BLUE_PLAYER));
        bluePlayers.add(ludoFactory.spawnPlayer(new SpawnData(Config.SPAWN_POINT_BLUE_V3), PlayerType.BLUE_PLAYER));
        bluePlayers.add(ludoFactory.spawnPlayer(new SpawnData(Config.SPAWN_POINT_BLUE_V4), PlayerType.BLUE_PLAYER));

        bluePlayers.add(ludoFactory.spawnPlayer(new SpawnData(Config.SPAWN_POINT_YELLOW_V1), PlayerType.YEllOW_PLAYER));
        bluePlayers.add(ludoFactory.spawnPlayer(new SpawnData(Config.SPAWN_POINT_YELLOW_V2), PlayerType.YEllOW_PLAYER));
        bluePlayers.add(ludoFactory.spawnPlayer(new SpawnData(Config.SPAWN_POINT_YELLOW_V3), PlayerType.YEllOW_PLAYER));
        bluePlayers.add(ludoFactory.spawnPlayer(new SpawnData(Config.SPAWN_POINT_YELLOW_V4), PlayerType.YEllOW_PLAYER));

        bluePlayers.add(ludoFactory.spawnPlayer(new SpawnData(Config.SPAWN_POINT_GREEN_V1), PlayerType.BLUE_PLAYER));
        bluePlayers.add(ludoFactory.spawnPlayer(new SpawnData(Config.SPAWN_POINT_GREEN_V2), PlayerType.BLUE_PLAYER));
        bluePlayers.add(ludoFactory.spawnPlayer(new SpawnData(Config.SPAWN_POINT_GREEN_V3), PlayerType.BLUE_PLAYER));
        bluePlayers.add(ludoFactory.spawnPlayer(new SpawnData(Config.SPAWN_POINT_GREEN_V4), PlayerType.BLUE_PLAYER));


        int number = 1;
        for (Entity player :
                bluePlayers) {
            AnimationComponent animationComponent = player.getComponent(AnimationComponent.class);
            animationComponent.setAnimatedTextureRun();
            getGameWorld().addEntity(player);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

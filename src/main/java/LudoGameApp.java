import Components.AnimationComponent;
import EntityTypePackage.EntityType;
import EntityTypePackage.PlayerType;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.GameView;
import com.almasb.fxgl.core.collection.grid.Cell;
import com.almasb.fxgl.core.collection.grid.Grid;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.entity.level.text.TextLevelLoader;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarCell;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.*;

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
        getGameWorld().setLevel(level);

        //TODO To fixed looking like shit
        for (int i = 0; i < getGameWorld().getEntities().size(); i++) {
            Point2D point2D = getGameWorld().getEntities().get(i).getAnchoredPosition();
            getGameWorld().getEntities().get(i).setPosition(point2D.getX() + 160, point2D.getY() + 60);
        }

        grid = AStarGrid.fromWorld(getGameWorld(), Config.MAP_SIZE, Config.MAP_SIZE, Config.BLOCK_SIZE, Config.BLOCK_SIZE, type -> {
            return CellState.NOT_WALKABLE;
        });

        List<Entity> entityList = getGameWorld().getEntitiesByType(EntityType.BACKGROUND);
        entityList.forEach(e -> {
            e.setVisible(false);
        });

        spawnPlayersPawn(ludoFactory);

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
        for (Entity player:
             bluePlayers) {
            AnimationComponent animationComponent = player.getComponent(AnimationComponent.class);
            animationComponent.setAnimatedTexture();
            getGameWorld().addEntity(player);
        }
    }

    @Override
    protected void initUI() {
        setBackground();
    }

    private void setBackground(){
        var clouds = FXGL.getAssetLoader().loadTexture("/background/Clouds V2/Clouds V2.png");

        clouds.setFitHeight(600);
        clouds.setFitWidth(800);

        getGameScene().setBackgroundColor(Color.LIGHTBLUE);
        GameView gameView = new GameView(clouds, -1);
        getGameScene().addGameView(gameView);
    }


    public static void main(String[] args) {
        launch(args);
    }
}

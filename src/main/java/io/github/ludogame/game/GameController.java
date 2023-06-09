package io.github.ludogame.game;

import com.almasb.fxgl.app.scene.GameView;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.entity.level.text.TextLevelLoader;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import io.github.ludogame.LudoFactory;
import io.github.ludogame.LudoPlayerApp;
import io.github.ludogame.component.AnimationComponent;
import io.github.ludogame.config.Config;
import io.github.ludogame.config.UIConfig;
import io.github.ludogame.menu.DefaultMenuButtonAction;
import io.github.ludogame.pawn.PawnColor;
import io.github.ludogame.player.PlayerColor;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getAssetLoader;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class GameController extends DefaultMenuButtonAction implements Initializable {

    @FXML
    public ImageView boardView;
    private LudoFactory ludoFactory;
    private AStarGrid grid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeControlTexture(startButton, UIConfig.START_BUTTON_DEFAULT);
        changeControlTexture(exitButton, UIConfig.EXIT_BUTTON_DEFAULT);
        changeControlTexture(musicButton, UIConfig.MUSIC_BUTTON_DEFAULT);

        setGridFromText();
        setBoard();

        FXGL.runOnce(() -> {
            spawnPlayersPawn(ludoFactory);
        }, Duration.millis(300));
    }

    private void setGridFromText() {
        ludoFactory = new LudoFactory();
        getGameWorld().addEntityFactory(ludoFactory);
        Level level = getAssetLoader().loadLevel("Ludo.txt", new TextLevelLoader(Config.BLOCK_SIZE, Config.BLOCK_SIZE, '0'));
        level.getEntities().forEach(entity -> {
            Point2D fixedPoint = new Point2D(((double) Config.MAP_WIDTH / 2) - ((double) Config.BLOCK_SIZE * Config.MAP_SIZE / 2),
                    ((double) Config.MAP_HEIGHT / 2) - ((double) Config.BLOCK_SIZE * Config.MAP_SIZE / 2));
            entity.translate(fixedPoint);

            entity.setVisible(false);
        });

        getGameWorld().setLevel(level);

        this.grid = AStarGrid.fromWorld(
                getGameWorld(),
                Config.MAP_SIZE,
                Config.MAP_SIZE,
                Config.BLOCK_SIZE,
                Config.BLOCK_SIZE,
                type -> CellState.NOT_WALKABLE
        );
    }

    private void spawnPlayersPawn(LudoFactory ludoFactory) {
        List<Entity> players = new ArrayList<>();
        UIConfig.SPAWN_POINTS_RED.forEach(point -> players.add(ludoFactory.spawnPawn(new SpawnData(point), PawnColor.RED)));
        UIConfig.SPAWN_POINTS_BLUE.forEach(point -> players.add(ludoFactory.spawnPawn(new SpawnData(point), PawnColor.BLUE)));
        UIConfig.SPAWN_POINTS_YELLOW.forEach(point -> players.add(ludoFactory.spawnPawn(new SpawnData(point), PawnColor.GREEN)));
        UIConfig.SPAWN_POINTS_GREEN.forEach(point -> players.add(ludoFactory.spawnPawn(new SpawnData(point), PawnColor.YEllOW)));

        players.forEach(player -> {
            AnimationComponent animationComponent = player.getComponent(AnimationComponent.class);
            animationComponent.setAnimatedTextureIdle();
            getGameWorld().addEntity(player);
        });
    }

    private void setBoard() {
        boardView.setImage(new Image("assets/textures/board/board.png"));

        boardView.setFitWidth(UIConfig.BOARD_WIDTH);
        boardView.setPreserveRatio(true);
        boardView.setVisible(true);

        boardView.setLayoutX(UIConfig.BOARD_START_LAYOUT_X);
        boardView.setLayoutY(UIConfig.BOARD_START_LAYOUT_Y);

        GameView gameView = new GameView(boardView, -10);
        getGameScene().addGameView(gameView);
    }

    public void onStartButtonClick() {
        System.out.println("RZUT");
    }

    public void onMusicButtonClick() {
        System.out.println("Music");
    }

    public void onExitButtonClick() {
        System.out.println("Exit");
    }
}

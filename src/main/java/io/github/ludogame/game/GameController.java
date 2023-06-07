package io.github.ludogame.game;

import com.almasb.fxgl.app.scene.GameView;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.entity.level.text.TextLevelLoader;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import io.github.ludogame.LudoFactory;
import io.github.ludogame.config.Config;
import io.github.ludogame.config.UIConfig;
import io.github.ludogame.menu.DefaultMenuButtonAction;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getAssetLoader;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class GameController extends DefaultMenuButtonAction implements Initializable {

    private LudoFactory ludoFactory;
    private AStarGrid grid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeControlTexture(startButton, UIConfig.START_BUTTON_DEFAULT);
        changeControlTexture(exitButton, UIConfig.EXIT_BUTTON_DEFAULT);
        changeControlTexture(musicButton, UIConfig.MUSIC_BUTTON_DEFAULT);

        setGridFromText();
        setBoard();

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

    private void setBoard() {
        ImageView imageView = new ImageView(new Image("assets/textures/board/board.png"));

        imageView.setFitWidth(UIConfig.BOARD_WIDTH);
        imageView.setPreserveRatio(true);
        imageView.setVisible(true);

        imageView.setLayoutX(UIConfig.BOARD_START_LAYOUT_X);
        imageView.setLayoutY(UIConfig.BOARD_START_LAYOUT_Y);

        GameView gameView = new GameView(imageView, -10);
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

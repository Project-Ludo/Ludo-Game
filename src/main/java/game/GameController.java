package game;

import com.almasb.fxgl.app.scene.GameView;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.entity.level.text.TextLevelLoader;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import config.Config;
import config.UIConfig;
import factory.LudoFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import menu.DefaultMenuButtonAction;
import type.PlayerType;

import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;
import static com.almasb.fxgl.dsl.FXGL.*;

public class GameController extends DefaultMenuButtonAction implements Initializable {

    @FXML
    public AnchorPane anchorPane;

    private AStarGrid grid;
    private LudoGame game;

    //Testts

    Circle circle;
    BoardCell boardCell;
    LudoFactory ludoFactory;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeControlTexture(startButton, UIConfig.START_BUTTON_DEFAULT);
        changeControlTexture(exitButton, UIConfig.EXIT_BUTTON_DEFAULT);
        changeControlTexture(musicButton, UIConfig.MUSIC_BUTTON_DEFAULT);

        setGridFromText();

        setBoard();


        runOnce(() -> {
            mapGridInit();

            //Testing purpose
            //-->
            boardCell = game.getCells().get(0);

            circle = new Circle(Config.BLOCK_SIZE / 2, Color.BLACK);
            AtomicInteger i = new AtomicInteger(1);
            circle.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                boardCell = game.getNextCell(boardCell);

                circle.setLayoutX(UIConfig.BOARD_START_LAYOUT_X + (Config.BLOCK_SIZE * (boardCell.getCell().getX())) + 18);
                circle.setLayoutY(UIConfig.BOARD_START_LAYOUT_Y + (Config.BLOCK_SIZE * (boardCell.getCell().getY())) + 18);
                i.getAndIncrement();
            });

            circle.setLayoutX(UIConfig.BOARD_START_LAYOUT_X + (Config.BLOCK_SIZE * (boardCell.getCell().getX())) + 18);
            circle.setLayoutY(UIConfig.BOARD_START_LAYOUT_Y + (Config.BLOCK_SIZE * (boardCell.getCell().getY())) + 18);
            anchorPane.getChildren().add(circle);
            //getGameScene().addUINode(circle);
            //<--

            spawnPlayersPawn(ludoFactory);
        }, Duration.millis(100));

        getGameScene().addGameView(new GameView(anchorPane, -1));

    }

    private void spawnPlayersPawn(LudoFactory ludoFactory) {
        List<Entity> players = new ArrayList<>();
        UIConfig.SPAWN_POINTS_RED.forEach(point -> players.add(ludoFactory.spawnPlayer(new SpawnData(point), PlayerType.RED)));
        UIConfig.SPAWN_POINTS_BLUE.forEach(point -> players.add(ludoFactory.spawnPlayer(new SpawnData(point), PlayerType.BLUE)));
        UIConfig.SPAWN_POINTS_YELLOW.forEach(point -> players.add(ludoFactory.spawnPlayer(new SpawnData(point), PlayerType.YEllOW)));
        UIConfig.SPAWN_POINTS_GREEN.forEach(point -> players.add(ludoFactory.spawnPlayer(new SpawnData(point), PlayerType.GREEN)));

        players.forEach(player -> {
//            AnimationComponent animationComponent = player.getComponent(AnimationComponent.class);
//            animationComponent.setAnimatedTextureIdle();
            getGameWorld().addEntity(player);
        });
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

    public void onStartButtonClick(ActionEvent actionEvent) {
        System.out.println("RZUT");
    }

    public void onMusicButtonClick(ActionEvent actionEvent) {
        System.out.println("Music");
    }

    public void onExitButtonClick(ActionEvent actionEvent) {
        sceneController.changeSceneAfter(sceneController.getMainMenuScene(), 150);
        getGameScene().clearGameViews();
    }

    private void mapGridInit() {
        int x = 0, y = 6;
        List<BoardCell> boardCells = new ArrayList<>();

        while (x < 6) {
            BoardCell boardCell = new BoardCell(grid.get(++x, y), CellColor.DEFAULT);
            boardCells.add(boardCell);
        }

        while (y > 1) {
            BoardCell boardCell = new BoardCell(grid.get(x, --y), CellColor.DEFAULT);
            boardCells.add(boardCell);
        }

        while (x < 8) {
            BoardCell boardCell = new BoardCell(grid.get(++x, y), CellColor.DEFAULT);
            boardCells.add(boardCell);
        }

        while (y < 6) {
            BoardCell boardCell = new BoardCell(grid.get(x, ++y), CellColor.DEFAULT);
            boardCells.add(boardCell);
        }

        while (x < 13) {
            BoardCell boardCell = new BoardCell(grid.get(++x, y), CellColor.DEFAULT);
            boardCells.add(boardCell);
        }

        while (y < 8) {
            BoardCell boardCell = new BoardCell(grid.get(x, ++y), CellColor.DEFAULT);
            boardCells.add(boardCell);
        }

        while (x > 8) {
            BoardCell boardCell = new BoardCell(grid.get(--x, y), CellColor.DEFAULT);
            boardCells.add(boardCell);
        }

        while (y < 13) {
            BoardCell boardCell = new BoardCell(grid.get(x, ++y), CellColor.DEFAULT);
            boardCells.add(boardCell);
        }

        while (x > 6) {
            BoardCell boardCell = new BoardCell(grid.get(--x, y), CellColor.DEFAULT);
            boardCells.add(boardCell);
        }

        while (y > 8) {
            BoardCell boardCell = new BoardCell(grid.get(x, --y), CellColor.DEFAULT);
            boardCells.add(boardCell);
        }

        while (x > 1) {
            BoardCell boardCell = new BoardCell(grid.get(--x, y), CellColor.DEFAULT);
            boardCells.add(boardCell);
        }

        while (y > 6) {
            BoardCell boardCell = new BoardCell(grid.get(x, --y), CellColor.DEFAULT);
            boardCells.add(boardCell);
        }

        game.addCells(boardCells);
    }

    public void setGame(LudoGame game) {
        this.game = game;
    }
}

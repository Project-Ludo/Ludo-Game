package game;

import com.almasb.fxgl.app.scene.GameView;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.entity.level.text.TextLevelLoader;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarCell;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import config.Config;
import config.UIConfig;
import factory.LudoFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import menu.DefaultMenuButtonAction;
import type.EntityType;
import type.PlayerType;

import java.net.URL;
import java.util.*;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;
import static com.almasb.fxgl.dsl.FXGL.*;

public class GameController extends DefaultMenuButtonAction implements Initializable {

    @FXML
    public AnchorPane anchorPane;
    private AStarGrid grid;
    //LinkedHashMap<BoardCell, CellColor> map;
    LinkedList<BoardCell> map;

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

        //map = new LinkedHashMap<>();
        map = new LinkedList<>();

        mapGridInit(map);

        //Testing purpose
        //-->
        boardCell = map.get(0);

        circle = new Circle(Config.BLOCK_SIZE / 2, Color.BLACK);
        circle.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            boardCell = boardCell.getNextBoardCell();

            circle.setLayoutX(UIConfig.BOARD_START_LAYOUT_X + (Config.BLOCK_SIZE * (boardCell.getCell().getX()))+18);
            circle.setLayoutY(UIConfig.BOARD_START_LAYOUT_Y + (Config.BLOCK_SIZE * (boardCell.getCell().getY()))+18);
        });

        circle.setLayoutX(UIConfig.BOARD_START_LAYOUT_X + (Config.BLOCK_SIZE * (boardCell.getCell().getX()))+18);
        circle.setLayoutY(UIConfig.BOARD_START_LAYOUT_Y + (Config.BLOCK_SIZE * (boardCell.getCell().getY()))+18);
        anchorPane.getChildren().add(circle);
        //getGameScene().addUINode(circle);
        //<--

        spawnPlayersPawn(ludoFactory);

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

    private void mapGridInit(LinkedList<BoardCell> map) {
        int x = 2, y = 6;

        BoardCell actualBoardCell = null;
        BoardCell nextBoardCell = null;
        BoardCell firstBoardCell = null;

        actualBoardCell = new BoardCell(grid.get(x, y), null);
        firstBoardCell = actualBoardCell;
        //x=2, y=6 idziemy w prawo o 5
        for (; x < 6; x++) {
            nextBoardCell = new BoardCell(grid.get(x + 1, y), null);
            actualBoardCell.setNextBoardCell(nextBoardCell);
            map.add(actualBoardCell);
            actualBoardCell = nextBoardCell;
        }
        //x=6, y=6 idziemy w góre o 5
        for (; y > 1; y--) {
            nextBoardCell = new BoardCell(grid.get(x, y - 1), null);
            actualBoardCell.setNextBoardCell(nextBoardCell);
            map.add(actualBoardCell);
            actualBoardCell = nextBoardCell;
        }
        //x=6, y=1 idziemy w prawo o 2
        for (; x < 8; x++) {
            nextBoardCell = new BoardCell(grid.get(x + 1, y), null);
            actualBoardCell.setNextBoardCell(nextBoardCell);
            map.add(actualBoardCell);
            actualBoardCell = nextBoardCell;
        }
        //x=8, y=1 idziemy w dól o 5
        for (; y < 6; y++) {
            nextBoardCell = new BoardCell(grid.get(x, y + 1), null);
            actualBoardCell.setNextBoardCell(nextBoardCell);
            map.add(actualBoardCell);
            actualBoardCell = nextBoardCell;
        }
        // idziemy w prawo o 5
        for (; x < 13; x++) {
            nextBoardCell = new BoardCell(grid.get(x + 1, y), null);
            actualBoardCell.setNextBoardCell(nextBoardCell);
            map.add(actualBoardCell);
            actualBoardCell = nextBoardCell;
        }
        // idziemy w dól o 2
        for (; y < 8; y++) {
            nextBoardCell = new BoardCell(grid.get(x, y + 1), null);
            actualBoardCell.setNextBoardCell(nextBoardCell);
            map.add(actualBoardCell);
            actualBoardCell = nextBoardCell;
        }
        // idziemy w lewo o 5
        for (; x > 8; x--) {
            nextBoardCell = new BoardCell(grid.get(x - 1, y), null);
            actualBoardCell.setNextBoardCell(nextBoardCell);
            map.add(actualBoardCell);
            actualBoardCell = nextBoardCell;
        }
        // idziemy w dól o 5
        for (; y < 13; y++) {
            nextBoardCell = new BoardCell(grid.get(x, y + 1), null);
            actualBoardCell.setNextBoardCell(nextBoardCell);
            map.add(actualBoardCell);
            actualBoardCell = nextBoardCell;
        }
        // idziemy w lewo o 2
        for (; x > 6; x--) {
            nextBoardCell = new BoardCell(grid.get(x - 1, y), null);
            actualBoardCell.setNextBoardCell(nextBoardCell);
            map.add(actualBoardCell);
            actualBoardCell = nextBoardCell;
        }
        //idziemy w gore o 5
        for (; y > 8; y--) {
            nextBoardCell = new BoardCell(grid.get(x, y - 1), null);
            actualBoardCell.setNextBoardCell(nextBoardCell);
            map.add(actualBoardCell);
            actualBoardCell = nextBoardCell;
        }
        // idziemy w lewo o 5
        for (; x > 1; x--) {
            nextBoardCell = new BoardCell(grid.get(x - 1, y), null);
            actualBoardCell.setNextBoardCell(nextBoardCell);
            map.add(actualBoardCell);
            actualBoardCell = nextBoardCell;
        }
        //idziemy w gore o 2
        for (; y > 6; y--) {
            nextBoardCell = new BoardCell(grid.get(x, y - 1), null);
            actualBoardCell.setNextBoardCell(nextBoardCell);
            map.add(actualBoardCell);
            actualBoardCell = nextBoardCell;
        }
        actualBoardCell.setNextBoardCell(firstBoardCell);
        map.add(actualBoardCell);
    }

//    private void mapGridInit(LinkedHashMap<BoardCell, CellColor> map) {
//        int x = 2, y = 6;
//
//        //x=2, y=6 idziemy w prawo o 5
//        for (; x < 6; x++) {
//            map.put(new BoardCell(grid.get(x, y), grid.get(x + 1, y)), CellColor.DEFAULT);
//        }
//        //x=6, y=6 idziemy w góre o 5
//        for (; y > 1; y--) {
//            map.put(new BoardCell(grid.get(x, y), grid.get(x, y - 1)), CellColor.DEFAULT);
//        }
//        //x=6, y=1 idziemy w prawo o 2
//        for (; x < 8; x++) {
//            map.put(new BoardCell(grid.get(x, y), grid.get(x + 1, y)), CellColor.DEFAULT);
//        }
//        //x=8, y=1 idziemy w dól o 5
//        for (; y < 6; y++) {
//            map.put(new BoardCell(grid.get(x, y), grid.get(x, y + 1)), CellColor.DEFAULT);
//        }
//        // idziemy w prawo o 5
//        for (; x < 13; x++) {
//            map.put(new BoardCell(grid.get(x, y), grid.get(x + 1, y)), CellColor.DEFAULT);
//        }
//        // idziemy w dól o 2
//        for (; y < 8; y++) {
//            map.put(new BoardCell(grid.get(x, y), grid.get(x, y + 1)), CellColor.DEFAULT);
//        }
//        // idziemy w lewo o 5
//        for (; x > 8; x--) {
//            map.put(new BoardCell(grid.get(x, y), grid.get(x - 1, y)), CellColor.DEFAULT);
//        }
//        // idziemy w dól o 5
//        for (; y < 13; y++) {
//            map.put(new BoardCell(grid.get(x, y), grid.get(x, y + 1)), CellColor.DEFAULT);
//        }
//        // idziemy w lewo o 2
//        for (; x > 6; x--) {
//            map.put(new BoardCell(grid.get(x, y), grid.get(x - 1, y)), CellColor.DEFAULT);
//        }
//        //idziemy w gore o 5
//        for (; y > 8; y--) {
//            map.put(new BoardCell(grid.get(x, y), grid.get(x, y - 1)), CellColor.DEFAULT);
//        }
//        // idziemy w lewo o 5
//        for (; x > 1; x--) {
//            map.put(new BoardCell(grid.get(x, y), grid.get(x - 1, y)), CellColor.DEFAULT);
//        }
//        //idziemy w gore o 2
//        for (; y > 6; y--) {
//            map.put(new BoardCell(grid.get(x, y), grid.get(x, y - 1)), CellColor.DEFAULT);
//        }
//        map.put(new BoardCell(grid.get(x, y), grid.get(x + 1, y)), CellColor.DEFAULT);
//    }
}

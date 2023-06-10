package io.github.ludogame.game;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.entity.level.text.TextLevelLoader;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import io.github.ludogame.LudoFactory;
import io.github.ludogame.LudoPlayerApp;
import io.github.ludogame.config.Config;
import io.github.ludogame.config.UIConfig;
import io.github.ludogame.menu.DefaultMenuButtonAction;
import io.github.ludogame.pawn.Pawn;
import io.github.ludogame.pawn.PawnColor;
import io.github.ludogame.player.LudoPlayer;
import io.github.ludogame.player.PlayerColor;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class GameController extends DefaultMenuButtonAction implements Initializable {

    @FXML
    public Label testText;
    @FXML
    public ImageView boardView;
    @FXML
    public AnchorPane anchorPane;
    @FXML
    public ImageView diceView;
    private LudoFactory ludoFactory;
    private AStarGrid grid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeControlTexture(startButton, UIConfig.START_BUTTON_DEFAULT);
        changeControlTexture(exitButton, UIConfig.EXIT_BUTTON_DEFAULT);
        changeControlTexture(musicButton, UIConfig.MUSIC_BUTTON_DEFAULT);

        setGridFromText();
        setBoard();

        FXGL.runOnce(this::spawnPlayerPawns, Duration.millis(300));


        //ONLY FOR TESTING PURPOSES
        FXGL.run(() -> {
            StringBuffer stringBuffer = new StringBuffer();
            LudoPlayerApp.ludoGame.getPlayers().forEach(player -> {
                stringBuffer.append(player.getNickname()).append(" - ").append(player.getColor()).append("\n");
            });
            stringBuffer.append("Turn: ").append(LudoPlayerApp.ludoGame.getPlayerColorTurn());
            testText.setText(stringBuffer.toString());
        }, Duration.millis(500));
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

    private void spawnPlayerPawns() {
        LudoPlayerApp.ludoGame.getPlayers().forEach(player -> {
            if (player.getUuid().equals(LudoPlayerApp.player.getUuid())) {
                List<Entity> entities = spawnPawns(player.getColor());
                List<Pawn> pawns = entities.stream()
                        .map(Pawn::new)
                        .collect(Collectors.toList());

                LudoPlayerApp.player.setPawns(pawns);
                return;
            }

            spawnPawns(player.getColor());
        });
    }

    private List<Entity> spawnPawns(PlayerColor playerColor) {
        List<Entity> pawns = new ArrayList<>();

        CopyOnWriteArrayList<LudoPlayer> playerslist = LudoPlayerApp.ludoGame.getPlayers();
        LudoPlayer player = playerslist.stream().filter(p -> p.getColor().equals(playerColor)).findFirst().get();

        switch (playerColor) {
            case RED -> UIConfig.SPAWN_POINTS_RED.forEach(point -> pawns.add(ludoFactory.spawnPawn(new SpawnData(point).put("player", player), PawnColor.RED)));
            case BLUE -> UIConfig.SPAWN_POINTS_BLUE.forEach(point -> pawns.add(ludoFactory.spawnPawn(new SpawnData(point).put("player", player), PawnColor.BLUE)));
            case GREEN -> UIConfig.SPAWN_POINTS_YELLOW.forEach(point -> pawns.add(ludoFactory.spawnPawn(new SpawnData(point).put("player", player), PawnColor.GREEN)));
            case YELLOW -> UIConfig.SPAWN_POINTS_GREEN.forEach(point -> pawns.add(ludoFactory.spawnPawn(new SpawnData(point).put("player", player), PawnColor.YEllOW)));
        }

        pawns.forEach(pawn -> getGameWorld().addEntity(pawn));
        return pawns;
    }

    private void setBoard() {
        boardView.setLayoutX(UIConfig.BOARD_START_LAYOUT_X);
        boardView.setLayoutY(UIConfig.BOARD_START_LAYOUT_Y);
    }

    public void onStartButtonClick() {
        System.out.println("RZUT");
        Image diceFastThrow = new Image("assets/textures/dice/dice_throw_fast.gif");
        diceView.setImage(diceFastThrow);
        runOnce(() -> {
            setSpecificDiceImage(random(1, 6));
            return null;
        }, Duration.seconds(1));
    }

    private void setSpecificDiceImage(int number) {
        if (number < 1 || number > 6) {
            return;
        }
        Image diceImage = new Image("assets/textures/dice/dice_" + String.valueOf(number) + ".png");
        diceView.setImage(diceImage);
    }

    public void onMusicButtonClick() {
        System.out.println("Music");
    }

    public void onExitButtonClick() {
        System.out.println("Exit");
    }
}

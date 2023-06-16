package io.github.ludogame.game;

import com.almasb.fxgl.core.collection.grid.Cell;
import com.almasb.fxgl.core.serialization.Bundle;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.entity.level.text.TextLevelLoader;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarCell;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import io.github.ludogame.EntityType;
import io.github.ludogame.LudoFactory;
import io.github.ludogame.LudoPlayerApp;
import io.github.ludogame.component.PawnComponent;
import io.github.ludogame.config.Config;
import io.github.ludogame.config.UIConfig;
import io.github.ludogame.menu.DefaultMenuButtonAction;
import io.github.ludogame.notification.ErrorNotification;
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
import java.util.concurrent.atomic.AtomicInteger;
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
    private PawnComponent pawnComponent;

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

        getGameWorld().setLevel(level);

        this.grid = AStarGrid.fromWorld(
                getGameWorld(),
                24,
                18,
                Config.BLOCK_SIZE,
                Config.BLOCK_SIZE,
                type -> {
                    return CellState.WALKABLE;
                }
        );

        //list of grid
        //Set list of ceel into LudoGame list
        LudoPlayerApp.ludoGame.setaStarGrid(grid);
    }

    private PawnColor getPawnColorFromPlayerColor(PlayerColor playerColor){
        return switch (playerColor) {
            case YELLOW -> PawnColor.YEllOW;
            case GREEN -> PawnColor.GREEN;
            case BLUE -> PawnColor.BLUE;
            default -> PawnColor.RED;
        };
    }

    private void spawnPlayerPawns() {
        LudoPlayerApp.ludoGame.getPlayers().forEach(player -> {
            List<Entity> entities = spawnPawns(player.getColor());
            AtomicInteger i = new AtomicInteger(0);
            List<Pawn> pawns = entities.stream()
                    .map(entity -> new Pawn(entity, getPawnColorFromPlayerColor(player.getColor()), i.getAndIncrement()))
                    .collect(Collectors.toList());

            player.setPawns(pawns);

            if (player.getUuid().equals(LudoPlayerApp.player.getUuid())) {
                LudoPlayerApp.player.setPawns(pawns);
            }
        });
    }

    private List<Entity> spawnPawns(PlayerColor playerColor) {
        List<Entity> pawns = new ArrayList<>();

        CopyOnWriteArrayList<LudoPlayer> playerslist = LudoPlayerApp.ludoGame.getPlayers();
        LudoPlayer player = playerslist.stream().filter(p -> p.getColor().equals(playerColor)).findFirst().get();

        switch (playerColor) {
            case RED -> UIConfig.SPAWN_POINTS_RED.forEach(point -> pawns.add(spawn("Pawn",new SpawnData(point).put("player", player).put("pawnColor", PawnColor.RED))));
            case BLUE -> UIConfig.SPAWN_POINTS_BLUE.forEach(point -> pawns.add(spawn("Pawn",new SpawnData(point).put("player", player).put("pawnColor", PawnColor.BLUE))));
            case GREEN -> UIConfig.SPAWN_POINTS_YELLOW.forEach(point -> pawns.add(spawn("Pawn",new SpawnData(point).put("player", player).put("pawnColor", PawnColor.GREEN))));
            case YELLOW -> UIConfig.SPAWN_POINTS_GREEN.forEach(point -> pawns.add(spawn("Pawn",new SpawnData(point).put("player", player).put("pawnColor", PawnColor.YEllOW))));
        }

        return pawns;
    }

    private void setBoard() {
        boardView.setLayoutX(UIConfig.BOARD_START_LAYOUT_X + Config.BLOCK_SIZE/2 - 4);
        boardView.setLayoutY(UIConfig.BOARD_START_LAYOUT_Y + Config.BLOCK_SIZE/2);
    }

    public void onStartButtonClick() {
        if(!LudoPlayerApp.ludoGame.getPlayerColorTurn().equals(LudoPlayerApp.player.getColor())){
            new ErrorNotification("Not your turn!");
            return;
        }

        if(LudoPlayerApp.player.isDiceRolled()){
            new ErrorNotification("Dice is rolled");
            return;
        }

        LudoPlayerApp.player.setDiceRolled(true);
        System.out.println("start");
        Bundle bundle = new Bundle("DiceRoll");
        bundle.put("result", 0);
        LudoPlayerApp.player.getDataBundle().broadcast(bundle);
    }

    public void rollDice(int result){
        Image diceFastThrow = new Image("assets/textures/dice/dice_throw_fast.gif");
        diceView.setImage(diceFastThrow);

        runOnce(() -> {
            setSpecificDiceImage(result);
            return null;
        }, Duration.seconds(1));
    }

    private void setSpecificDiceImage(int number) {
        if (number < 1 || number > 6) {
            return;
        }

        Image diceImage = new Image("assets/textures/dice/dice_" + number + ".png");
        diceView.setImage(diceImage);
    }

    public void onMusicButtonClick() {
        System.out.println("Music");
    }

    public void onExitButtonClick() {
        System.out.println("Exit");
    }
}

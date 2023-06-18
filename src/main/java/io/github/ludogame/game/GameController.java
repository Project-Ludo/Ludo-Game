package io.github.ludogame.game;

import com.almasb.fxgl.core.serialization.Bundle;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.entity.level.text.TextLevelLoader;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import com.almasb.fxgl.ui.UI;
import io.github.ludogame.EntityType;
import io.github.ludogame.LudoFactory;
import io.github.ludogame.LudoPlayerApp;
import io.github.ludogame.component.AnimationComponent;
import io.github.ludogame.component.PawnComponent;
import io.github.ludogame.config.Config;
import io.github.ludogame.config.UIConfig;
import io.github.ludogame.menu.DefaultMenuButtonAction;
import io.github.ludogame.notification.ErrorNotification;
import io.github.ludogame.pawn.Pawn;
import io.github.ludogame.pawn.PawnColor;
import io.github.ludogame.player.LudoPlayer;
import io.github.ludogame.player.PlayerColor;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    @FXML
    public ImageView bluePlayerImageView;
    @FXML
    public ImageView greenPlayerImageView;
    @FXML
    public ImageView yellowPlayerImageView;
    @FXML
    public ImageView redPlayerImageView;
    @FXML
    public Label bluePlayerLabel;
    @FXML
    public Label greenPlayerLabel;
    @FXML
    public Label yellowPlayerLabel;
    @FXML
    public Label redPlayerLabel;
    @FXML
    public ImageView bluePlayerImageViewFrame;
    @FXML
    public ImageView greenPlayerImageViewFrame;
    @FXML
    public ImageView yellowPlayerImageViewFrame;
    @FXML
    public ImageView redPlayerImageViewFrame;
    public ImageView winImage;
    public Label winLabel;

    private LudoFactory ludoFactory;
    private AStarGrid grid;

    private Map<PlayerColor, ImageView> playerColorFrameMap;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeControlTexture(startButton, UIConfig.THROW_BUTTON_DEFAULT);
        changeControlTexture(exitButton, UIConfig.EXIT_BUTTON_DEFAULT);
        changeControlTexture(musicButton, UIConfig.MUSIC_BUTTON_DEFAULT);
        this.winImage.setImage(new Image(UIConfig.WIN_IMAGE));
        this.winImage.setVisible(false);
        this.winLabel = new Label();
        this.winLabel.setFont(FXGL.getAssetLoader().loadFont("04B_30__.TTF").newFont(25));
        this.winLabel.setTextFill(Color.WHITE);
        this.winLabel.setTranslateX(400);
        this.winLabel.setTranslateY(300);
        this.winLabel.setVisible(false);

        setGridFromText();
        setBoard();
        playerColorFrameMap = Map.of(
                PlayerColor.RED, redPlayerImageViewFrame,
                PlayerColor.BLUE, bluePlayerImageViewFrame,
                PlayerColor.GREEN, greenPlayerImageViewFrame,
                PlayerColor.YELLOW, yellowPlayerImageViewFrame
        );

        FXGL.runOnce(this::spawnPlayerPawns, Duration.millis(300));


        //ONLY FOR TESTING PURPOSES
        FXGL.run(() -> {
            StringBuffer stringBuffer = new StringBuffer();
            LudoPlayerApp.ludoGame.getPlayers().forEach(player -> {
                stringBuffer.append(player.getNickname()).append(" - ").append(player.getColor()).append("\n");
            });
            stringBuffer.append("Turn: ").append(LudoPlayerApp.ludoGame.getPlayerColorTurn());
            testText.setText(stringBuffer.toString());

            setFrameToPlayerOnTurn();
            addPlayerNicknameToPlayerLabel();
        }, Duration.millis(500));
    }

    private void setFrameToPlayerOnTurn(){
        playerColorFrameMap.entrySet()
                .stream()
                .peek(entry -> entry.getValue().setVisible(false))
                .filter(entry -> entry.getKey().equals(LudoPlayerApp.ludoGame.getPlayerColorTurn()))
                .forEach(entry -> entry.getValue().setVisible(true));
    }
    private void setVisibleFrame(boolean value){
        playerColorFrameMap.entrySet()
                .forEach(entry -> entry.getValue().setVisible(value));
    }

    private void addPlayerNicknameToPlayerLabel(){
        LudoPlayerApp.ludoGame.getPlayers().forEach(this::addNicknameToLabel);
    }

    private void addNicknameToLabel(LudoPlayer player){
        switch (player.getColor()){
            case RED -> redPlayerLabel.setText(player.getNickname());
            case BLUE -> bluePlayerLabel.setText(player.getNickname());
            case GREEN -> greenPlayerLabel.setText(player.getNickname());
            case YELLOW -> yellowPlayerLabel.setText(player.getNickname());
        }
    }

    private void setGridFromText() {
        Level level = getAssetLoader().loadLevel("Ludo.txt", new TextLevelLoader(Config.BLOCK_SIZE, Config.BLOCK_SIZE, '0'));

        getGameWorld().setLevel(level);

        this.grid = AStarGrid.fromWorld(
                getGameWorld(),
                25,
                18,
                Config.BLOCK_SIZE,
                Config.BLOCK_SIZE,
                type -> {
                    if(!type.equals(EntityType.BACKGROUND)){
                        return CellState.WALKABLE;
                    }
                    return CellState.NOT_WALKABLE;
                }
        );

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

            player.getPawns().forEach(pawn -> {
                pawn.getEntity().getViewComponent().addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        AnimationComponent pawnAnimComponent = pawn.getEntity().getComponent(AnimationComponent.class);
                        if(pawnAnimComponent == null){
                            return;
                        }
                        pawnAnimComponent.setOnceTextureJump();
                    }
                });

                //TODO TO CHECK IF CORRECT !!! | SEEMS WORK
                pawn.getCell().setUserData(pawn);
            });

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
            case GREEN -> UIConfig.SPAWN_POINTS_GREEN.forEach(point -> pawns.add(spawn("Pawn",new SpawnData(point).put("player", player).put("pawnColor", PawnColor.GREEN))));
            case YELLOW -> UIConfig.SPAWN_POINTS_YELLOW.forEach(point -> pawns.add(spawn("Pawn",new SpawnData(point).put("player", player).put("pawnColor", PawnColor.YEllOW))));
        }

        return pawns;
    }

    private void setBoard() {
        boardView.setLayoutX(UIConfig.BOARD_START_LAYOUT_X + Config.BLOCK_SIZE/2 - 4);
        boardView.setLayoutY(UIConfig.BOARD_START_LAYOUT_Y + Config.BLOCK_SIZE/2);
    }

    public void onStartButtonClick() {
        changeControlTexture(startButton, UIConfig.THROW_BUTTON_CLICK);

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

    //TODO błąd przy początku
    public void rollDice(int result){
        Image diceFastThrow = new Image("assets/textures/dice/dice_throw_fast.gif");
        diceView.setImage(diceFastThrow);

        FXGL.runOnce(() -> {
            setSpecificDiceImage(result);

            FXGL.runOnce(() -> {
                if (!LudoPlayerApp.player.hasPossibleMove(LudoPlayerApp.ludoGame.getDiceResult()) &&
                        LudoPlayerApp.ludoGame.getPlayerColorTurn().equals(LudoPlayerApp.player.getColor()) &&
                        LudoPlayerApp.player.isDiceRolled()) {
                                        new ErrorNotification("Brak możliwości ruchu");
                                        //Serwer że następna tura
                                        Bundle bundle = new Bundle("ChangeTurn");
                                        LudoPlayerApp.player.getDataBundle().broadcast(bundle);
                                        LudoPlayerApp.player.setDiceRolled(false);
                                    }
            }, Duration.seconds(1));
        }, Duration.seconds(1));
    }

    private void setSpecificDiceImage(int number) {
        if (number < 1 || number > 6) {
            return;
        }

        Image diceImage = new Image("assets/textures/dice/dice_" + number + ".png");
        diceView.setImage(diceImage);
    }

    public void onExitButtonClick() {
        System.out.println("Exit");
    }

    @Override
    public void onStartButtonHover() {
        changeControlTexture(startButton, UIConfig.THROW_BUTTON_HOVER);
    }

    @Override
    public void onStartButtonExit() {
        changeControlTexture(startButton, UIConfig.THROW_BUTTON_DEFAULT);
    }
}

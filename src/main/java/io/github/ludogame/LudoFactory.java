package io.github.ludogame;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.core.serialization.Bundle;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.pathfinding.CellMoveComponent;
import com.almasb.fxgl.pathfinding.astar.AStarCell;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import io.github.ludogame.component.AnimationComponent;
import io.github.ludogame.component.BalloonComponent;
import io.github.ludogame.component.PawnComponent;
import io.github.ludogame.config.Config;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import io.github.ludogame.game.LudoGame;
import io.github.ludogame.notification.ErrorNotification;
import io.github.ludogame.pawn.Pawn;
import io.github.ludogame.pawn.PawnMoveData;
import io.github.ludogame.player.LudoPlayer;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Optional;
import java.util.Random;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getSettings;
import static com.almasb.fxgl.dsl.FXGLForKtKt.image;
import static com.almasb.fxgl.dsl.FXGLForKtKt.runOnce;

public class LudoFactory implements EntityFactory {

    private static final Random random = FXGLMath.getRandom();

    @Spawns("P")
    public Entity spawnPlatform(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.PLATFORM)
                .viewWithBBox(new Rectangle(Config.BLOCK_SIZE, Config.BLOCK_SIZE, Color.YELLOW))
                .build();
    }

    @Spawns("_")
    public Entity spawnBG(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.BACKGROUND)
                .viewWithBBox(new Rectangle(Config.BLOCK_SIZE, Config.BLOCK_SIZE, Color.LIGHTBLUE.saturate()))
                .build();
    }

    @Spawns("|")
    public Entity spawnFinishPlatform(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.FINISH_CELL)
                .viewWithBBox(new Rectangle(Config.BLOCK_SIZE, Config.BLOCK_SIZE, Color.BLACK))
                .build();
    }

    @Spawns("S")
    public Entity spawnStartSpawnPoint(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.START_SPAWN_POINT)
                .viewWithBBox(new Rectangle(Config.BLOCK_SIZE, Config.BLOCK_SIZE, Color.RED))
                .build();
    }

    @Spawns("s")
    public Entity spawnSpawnPoint(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.SPAWN_POINT)
                .viewWithBBox(new Rectangle(Config.BLOCK_SIZE, Config.BLOCK_SIZE, Color.BLUE))
                .build();
    }

    @Spawns("Balloon")
    public Entity newBalloon(SpawnData data) {
        double w = getSettings().getWidth();
        double h = getSettings().getHeight();
        double x;
        double y;

        if (random.nextBoolean()) {
            if (random.nextBoolean()) {
                x = -50;
            } else {
                x = w + 50;
            }

            y = h + 50;
        } else {
            y = h + 50;
            x = random.nextInt((int) w);
        }

        Image image = new Image("assets/textures/balloon.gif", 125, 125, false, false);
        Entity meteor = entityBuilder()
                .at(x, y)
                .view(new ImageView(image))
                .zIndex(-400)
                .with(new BalloonComponent())
                .collidable()
                .build();

        FXGL.runOnce(() -> meteor.addComponent(new OffscreenCleanComponent()), Duration.seconds(20));
        return meteor;
    }


    @Spawns("Pawn")
    public Entity spawnPawn(SpawnData data) {
        PawnComponent pawnComponent = new PawnComponent(data);
        AnimationComponent animationComponent = new AnimationComponent(data.get("pawnColor"));
        AStarMoveComponent aStarMoveComponent = new AStarMoveComponent(LudoPlayerApp.ludoGame.getaStarGrid());

        return entityBuilder(data)
                .type(EntityType.PAWN)
                .with(animationComponent)
//                .view(new Rectangle(Config.BLOCK_SIZE, Config.BLOCK_SIZE))
                .bbox(new HitBox(BoundingShape.box(Config.BLOCK_SIZE, Config.BLOCK_SIZE)))
                .onClick(entity -> {
                    LudoPlayer player = LudoPlayerApp.player;
                    LudoGame game = LudoPlayerApp.ludoGame;

                    LudoPlayer pawnOwner = data.get("player");
                    addTextOverThePoint(pawnComponent.getEntity().getPosition().getX(), pawnComponent.getEntity().getPosition().getY(), pawnOwner);

                    //TODO możlwiy ruch do wykonania
                    if (!player.getColor().equals(pawnOwner.getColor())) {
                        new ErrorNotification("Mozesz ruszac tylko swoimi pionkami");
                        return;
                    }

                    if (!game.getPlayerColorTurn().equals(player.getColor())) {
                        LudoPlayer ludoPlayer = game.getPlayers()
                                .stream()
                                .filter(p -> p.getColor().equals(game.getPlayerColorTurn()))
                                .findFirst()
                                .get();

                        new ErrorNotification("Nie twoja tura! Aktualnie rusza " + ludoPlayer.getNickname());
                        return;
                    }


                    if (!player.isDiceRolled()) {
                        new ErrorNotification("Rzuc kostka");
                        return;
                    }

                    Optional<AStarCell> pawnComponentCell = pawnComponent.getaStar().getCurrentCell();
                    if (pawnComponentCell.isEmpty()) {
                        System.out.println("Blad pawnComponentCell");
                        return;
                    }

                    //System.out.println("pawnComponentCell: " + pawnComponentCell.get().getX() + " " + pawnComponentCell.get().getY());
                    Optional<Pawn> currentPawn = LudoPlayerApp.player.getPawns().stream().filter(
                            p -> p.getCell().equals(pawnComponentCell.get())
                    ).findFirst();

                    if (currentPawn.isEmpty()) {
                        //TODO JAK PIONEK ZOSTANIE ZBITY I CHCEMY GO ZNOWU WYJAC Z BAZY TO TUTAJ SIE WYPIERDALA CZYLI JAKBY TEN OPTIONAL WYZEJ GO NIE ZNAJDUJE
                        System.out.println("pawn not exist Pawn in player.getPawns() ->");
                        LudoPlayerApp.player.getPawns().forEach(
                                p -> System.out.println(p.getCell().getX() + " " + p.getCell().getY())
                        );
                        return;
                    }
                    if (currentPawn.get().isFinished()) {
                        new ErrorNotification("Pionek skonczyl gre");
                        return;
                    }
                    if (!currentPawn.get().isStarted()) {
                        if (LudoPlayerApp.ludoGame.getDiceResult() == 6) {
                            player.setDiceRolled(false);

                            PawnMoveData pawnMoveData = new PawnMoveData(currentPawn.get().getId(), currentPawn.get().getPawnColor(), LudoPlayerApp.ludoGame.getDiceResult());
                            Bundle bundle = new Bundle("PawnMove");
                            bundle.put("data", pawnMoveData);
                            LudoPlayerApp.player.getDataBundle().broadcast(bundle);
                            return;
                        }

                        long count = LudoPlayerApp.player.getPawns().stream().filter(Pawn::isStarted).count();
                        System.out.println("Players count: " + count);
                        if (count > 0) {
                            new ErrorNotification("Musisz ruszyc sie innym pionkiem");
                            return;
                        } else {
                            player.setDiceRolled(false);
                            FXGL.<LudoPlayerApp>getAppCast().getSceneController().getGameSceneController()
                                    .diceView.setImage(new Image("assets/textures/dice/dice_throw_fast.gif"));
                            Bundle bundle = new Bundle("ChangeTurn");
                            LudoPlayerApp.player.getDataBundle().broadcast(bundle);
                        }

                        new ErrorNotification("No action");
                    } else {

                        //TODO Sprawdzenie pinka czy może sie poruszyć
                        int index = LudoPlayerApp.ludoGame.findIndexOfCellInListByPawn(currentPawn.get());
                        int nextIndex = (index + game.getDiceResult()) % currentPawn.get().getPawnColor().path.size();
                        Pawn pawnInNextIndexCell = pawnComponent.checkIfCellHasPawn(currentPawn.get(), nextIndex);

                        if (pawnInNextIndexCell != null && pawnInNextIndexCell.getPawnColor() == currentPawn.get().getPawnColor()) {
                            new ErrorNotification("Nie zbijaj swojego pionak");
                            return;
                        }
                        player.setDiceRolled(false);

                        PawnMoveData pawnMoveData = new PawnMoveData(currentPawn.get().getId(), currentPawn.get().getPawnColor(), LudoPlayerApp.ludoGame.getDiceResult());
                        Bundle bundle = new Bundle("PawnMove");
                        bundle.put("data", pawnMoveData);
                        LudoPlayerApp.player.getDataBundle().broadcast(bundle);
                    }
                })
                .with(new CellMoveComponent(Config.BLOCK_SIZE, Config.BLOCK_SIZE, 75))
                .with(aStarMoveComponent)
                .with(pawnComponent)
                .build();
    }

    private void addTextOverThePoint(double x, double y, LudoPlayer player) {
        Label label = new Label(player.getNickname());
//        label.setLocation(x, y);
        label.setLayoutX(x);
        label.setLayoutY(y - 5);
        FXGL.getGameScene().addUINode(label);
        runOnce(() -> {
            FXGL.getGameScene().removeUINode(label);
            return null;
        }, Duration.seconds(2));
    }

}

package io.github.ludogame;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.pathfinding.CellMoveComponent;
import com.almasb.fxgl.pathfinding.astar.AStarCell;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.CircleShapeData;
import com.almasb.fxgl.physics.HitBox;
import io.github.ludogame.component.AnimationComponent;
import io.github.ludogame.component.PawnComponent;
import io.github.ludogame.config.Config;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import io.github.ludogame.game.LudoGame;
import io.github.ludogame.notification.ErrorNotification;
import io.github.ludogame.pawn.Pawn;
import io.github.ludogame.pawn.PawnColor;
import io.github.ludogame.player.LudoPlayer;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Optional;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;
import static com.almasb.fxgl.dsl.FXGLForKtKt.runOnce;

public class LudoFactory implements EntityFactory {

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

    @Spawns("Pawn")
    public Entity spawnPawn(SpawnData data) {
        PawnComponent pawnComponent = new PawnComponent(data);
        AnimationComponent animationComponent = new AnimationComponent(data.get("pawnColor"));
        AStarMoveComponent aStarMoveComponent = new AStarMoveComponent(LudoPlayerApp.ludoGame.getaStarGrid());
        CellMoveComponent cellMoveComponent = new CellMoveComponent(Config.BLOCK_SIZE, Config.BLOCK_SIZE, 150);
        HitBox hitBox = new HitBox(BoundingShape.circle(Config.BLOCK_SIZE/2));

        return entityBuilder(data)
                .type(EntityType.PAWN)
                .with(animationComponent)
                .bbox(new HitBox(BoundingShape.box(Config.BLOCK_SIZE, Config.BLOCK_SIZE)))
                .onClick(entity -> {
                    LudoPlayer player = LudoPlayerApp.player;
                    LudoGame game = LudoPlayerApp.ludoGame;

                    LudoPlayer pawnOwner = data.get("player");
                    addTextOverThePoint(pawnComponent.getEntity().getPosition().getX(), pawnComponent.getEntity().getPosition().getY(), pawnOwner);

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

                    System.out.println("Move");
                    //pawnComponent.moveToStartPoint();
                    //pawnComponent.getEntity().setPosition((int) Config.BLUE_PAWN_START_SPAWN_POINT.getX() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2, (int)Config.BLUE_PAWN_START_SPAWN_POINT.getY() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2);

                    Optional<AStarCell> pawnComponentCell = pawnComponent.getAstar().getCurrentCell();
                    if(pawnComponentCell.isEmpty()){
                        System.out.println("Blad pawnComponentCell");
                        return;
                    }
                    System.out.println("pawnComponentCell: " + pawnComponentCell.get().getX() + " " + pawnComponentCell.get().getY());
                    Optional<Pawn> currentPawn = LudoPlayerApp.player.getPawns().stream().filter(
                            p -> p.getCell().equals(pawnComponentCell.get())
                    ).findFirst();

                    if(currentPawn.isEmpty()){
                        System.out.println("pawn not exist Pawn in player.getPawns() ->");
                        LudoPlayerApp.player.getPawns().forEach(
                                p -> System.out.println(p.getCell().getX() + " " + p.getCell().getY())
                        );
                        return;
                    }

                    if(LudoPlayerApp.ludoGame.getDiceResult() == 6){
                        pawnComponent.getEntity().setPosition((int) Config.BLUE_PAWN_START_SPAWN_POINT.getX() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2, (int)Config.BLUE_PAWN_START_SPAWN_POINT.getY() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2);
                    }else {
                        pawnComponent.move(LudoPlayerApp.ludoGame.getDiceResult(), currentPawn.get());
                    }


                        //TODO Do odkomentowania
//                    AnimationComponent animationComponent = entity.getComponent(AnimationComponent.class);
//                    animationComponent.switchAnimation();

                })
                .with(new CellMoveComponent(Config.BLOCK_SIZE, Config.BLOCK_SIZE, 150))
                .with(aStarMoveComponent)
                .with(pawnComponent)
                .build();
    }

    private void addTextOverThePoint(double x, double y, LudoPlayer player) {
        Label label = new Label(player.getNickname());
//        label.setLocation(x, y);
        label.setLayoutX(x);
        label.setLayoutY(y-5);
        FXGL.getGameScene().addUINode(label);
        runOnce(() -> {
            FXGL.getGameScene().removeUINode(label);
            return null;
        }, Duration.seconds(2));
    }

}

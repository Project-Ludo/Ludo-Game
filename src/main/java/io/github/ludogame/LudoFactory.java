package io.github.ludogame;

import com.almasb.fxgl.app.scene.GameView;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import io.github.ludogame.component.AnimationComponent;
import io.github.ludogame.config.Config;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import io.github.ludogame.game.LudoGame;
import io.github.ludogame.notification.ErrorNotification;
import io.github.ludogame.pawn.PawnColor;
import io.github.ludogame.player.LudoPlayer;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

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
    public Entity spawnSpawnPoint(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.SPAWN_POINT)
                .viewWithBBox(new Rectangle(Config.BLOCK_SIZE, Config.BLOCK_SIZE, Color.RED))
                .build();
    }

    @Spawns("Pawn")
    public Entity spawnPawn(SpawnData data, PawnColor pawnColor) {
        return entityBuilder(data)
                .type(EntityType.PAWN)
                .with(new AnimationComponent(pawnColor))
                .onClick(entity -> {
                    LudoPlayer player = LudoPlayerApp.player;
                    LudoGame game = LudoPlayerApp.ludoGame;

                    LudoPlayer pawnOwner = data.get("player");
                    addTextOverThePoint(data.getX(), data.getY(), pawnOwner);

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

                    AnimationComponent animationComponent = entity.getComponent(AnimationComponent.class);
                    animationComponent.switchAnimation();
                })
                .bbox(new HitBox(BoundingShape.box(32, 32)))
                .build();
    }

    private void addTextOverThePoint(double x, double y, LudoPlayer player) {
        Label label = new Label(player.getNickname());
//        label.setLocation(x, y);
        label.setLayoutX(x);
        label.setLayoutY(y);
        FXGL.getGameScene().addUINode(label);
        runOnce(() -> {
            FXGL.getGameScene().removeUINode(label);
            return null;
        }, Duration.seconds(2));
    }

}

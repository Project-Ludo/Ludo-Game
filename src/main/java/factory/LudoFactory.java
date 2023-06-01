package factory;

import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import component.AnimationComponent;
import config.Config;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import type.EntityType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import type.PlayerType;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

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

    @Spawns("Player")
    public Entity spawnPlayer(SpawnData data, PlayerType playerType) {
        return entityBuilder(data)
                .type(EntityType.PLAYER)
                .with(new AnimationComponent(playerType))
                .onClick(entity -> {
                    AnimationComponent animationComponent = entity.getComponent(AnimationComponent.class);
                    animationComponent.switchAnimation();
                })
                .bbox(new HitBox(BoundingShape.box(32, 32)))
                .build();
    }
}
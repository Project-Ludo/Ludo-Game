import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.sun.marlin.MarlinConst.BLOCK_SIZE;

public class LudoFactory implements EntityFactory {

    @Spawns("P")
    public Entity spawnPlatform(SpawnData data){
        return entityBuilder()
                .type(EntityType.PLATFORM)
                .view(new Rectangle(0,0,BLOCK_SIZE, BLOCK_SIZE))
                .build();
    }

}

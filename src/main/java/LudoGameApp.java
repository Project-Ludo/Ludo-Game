import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.collection.grid.Cell;
import com.almasb.fxgl.core.collection.grid.Grid;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarCell;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import javax.swing.text.html.parser.Entity;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.sun.marlin.MarlinConst.BLOCK_SIZE;

public class LudoGameApp extends GameApplication {
    private AStarGrid grid;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setTitle("Bajkowe Ludo");
    }

    @Override
    protected void initGame() {

    }

    @Override
    protected void initUI() {

        setBackground();
    }

    private void setBackground(){
        var clouds = FXGL.getAssetLoader().loadTexture("Clouds V2.png");

        clouds.setFitHeight(600);
        clouds.setFitWidth(800);

        getGameScene().setBackgroundColor(Color.LIGHTBLUE);
        getGameScene().addUINode(
                clouds
        );
    }

    public static void main(String[] args) {
        launch(args);
    }
}

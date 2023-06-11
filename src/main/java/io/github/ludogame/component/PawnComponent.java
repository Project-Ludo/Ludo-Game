package io.github.ludogame.component;

import com.almasb.fxgl.core.collection.grid.Cell;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.pathfinding.astar.AStarCell;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import io.github.ludogame.LudoPlayerApp;
import io.github.ludogame.config.Config;
import io.github.ludogame.config.UIConfig;
import io.github.ludogame.game.LudoGame;
import io.github.ludogame.notification.ErrorNotification;
import io.github.ludogame.pawn.Pawn;
import io.github.ludogame.player.LudoPlayer;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class PawnComponent extends Component {
    private AStarMoveComponent astar;

    private Point2D actualPosition;

    private SpawnData data;

    public PawnComponent(SpawnData data) {

        this.data = data;
    }

    public void move(int number, Pawn pawn) {
        Cell currentCell = astar.getCurrentCell().get();

        //Aktualna pozycja
        int x = UIConfig.BOARD_START_LAYOUT_X + currentCell.getX() * Config.BLOCK_SIZE;
        int y = UIConfig.BOARD_START_LAYOUT_Y + currentCell.getY() * Config.BLOCK_SIZE;

        int index = LudoPlayerApp.ludoGame.findIndexOfCellPositionInList(pawn.getCell().getX(), pawn.getCell().getY(), pawn);
        int nextIndex = (index + number) % Config.DEFAULT_PATH.size();

        pawn.setCell(astar.getGrid().get(
                (int) Config.DEFAULT_PATH.get(nextIndex).getX(), (int) Config.DEFAULT_PATH.get(nextIndex).getY())
        );

        astar.moveToCell(
                (int) Config.DEFAULT_PATH.get(nextIndex).getX(), (int) Config.DEFAULT_PATH.get(nextIndex).getY()
        );
    }

    public void moveToStartPoint() {
        if (astar == null) {
            System.out.println("ASTRAT NULL");
            return;
        }

        if (astar.getCurrentCell().isEmpty()) {
            System.out.println("Kurwa astar is empty");
            return;
        }
        astar.moveToCell((int) Config.BLUE_PAWN_START_SPAWN_POINT.getX(), (int) Config.BLUE_PAWN_START_SPAWN_POINT.getY());
    }

    private boolean isMoving() {
        return !actualPosition.equals(this.entity.getPosition());
    }


    public AStarMoveComponent getAstar() {
        return astar;
    }
}

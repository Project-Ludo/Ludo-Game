package io.github.ludogame.component;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import io.github.ludogame.LudoPlayerApp;
import io.github.ludogame.config.Config;
import io.github.ludogame.notification.ErrorNotification;
import io.github.ludogame.pawn.Pawn;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.util.List;

public class PawnComponent extends Component {
    private AStarMoveComponent aStar;
    private SpawnData data;

    public PawnComponent(SpawnData data) {

        this.data = data;
    }

    /**
     * Move Pawn to next position (depends on diceResult) in board
     *
     * @param diceResult How many hops to next position
     * @param pawn       Pawn
     */
    public void move(int diceResult, Pawn pawn) {
        if (pawn.isFinished()) {
            new ErrorNotification("Ten pionek juz skonczyl gre -.-");
            return;
        }

        if (!pawn.isStarted()) {
            if (diceResult == 6) {
                moveToStartPoint(pawn);
                return;
            }

            new ErrorNotification("Musi byc 6");
            return;
        }

        List<Point2D> path = pawn.getPawnColor().path;
        int currentIndex = LudoPlayerApp.ludoGame.findIndexOfCellInListByPawn(pawn);
        int nextIndex = (currentIndex + diceResult) % path.size();
        int toEnd = (path.size() - 1) - currentIndex;

        if (toEnd <= 6 && toEnd < diceResult) {
            new ErrorNotification("Tym nie mozesz");
            return;
        }

        if (nextIndex == path.size() - 1) {
            pawn.setFinished(true);
            FXGL.runOnce(() -> pawn.getEntity().setVisible(false), Duration.seconds(1));
        }

        moveToCellByIndex(pawn, nextIndex);
    }

    /**
     * Teleport Pawn to start point depends on his pawnColor
     *
     * @param pawn Pawn
     */
    private void moveToStartPoint(Pawn pawn) {
        Point2D startPoint = pawn.getPawnColor().startPoint;
        setPawnPosition(pawn, (int) startPoint.getX(), (int) startPoint.getY());
        pawn.setStarted(true);
    }

    /**
     * Teleport Pawn to position in grid
     *
     * @param pawn Pawn
     * @param x    Position X in grid
     * @param y    Position Y in grid
     */
    private void setPawnPosition(Pawn pawn, int x, int y) {
        pawn.getEntity().setPosition(
                x * Config.BLOCK_SIZE + Config.BLOCK_SIZE / 2,
                y * Config.BLOCK_SIZE + Config.BLOCK_SIZE / 2
        );
        pawn.setCell(aStar.getGrid().get(x, y));
    }

    /**
     * Move Pawn to position from CONFIG."..."_PATH
     *
     * @param pawn  Pawn
     * @param index Index of CONFIG."..."_PATH
     */
    private void moveToCellByIndex(Pawn pawn, int index) {
        pawn.setCell(aStar.getGrid().get(
                (int) pawn.getPawnColor().path.get(index).getX(), (int) pawn.getPawnColor().path.get(index).getY())
        );
        aStar.moveToCell(
                (int) pawn.getPawnColor().path.get(index).getX(), (int) pawn.getPawnColor().path.get(index).getY()
        );
    }

    /**
     * Move Pawn to cell from CONFIG."..."_PATH
     *
     * @param pawn Pawn
     * @param cell Cell from CONFIG."..."_PATH
     */
//    private void moveToCellByCell(Pawn pawn, AStarCell cell) {
//        int index = LudoPlayerApp.ludoGame.findIndexOfCellInListByCell(cell);
//
//        moveToCellByIndex(pawn, index);
//    }
    public AStarMoveComponent getaStar() {
        return aStar;
    }
}

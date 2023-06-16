package io.github.ludogame.component;

import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.pathfinding.astar.AStarCell;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import io.github.ludogame.LudoPlayerApp;
import io.github.ludogame.config.Config;
import io.github.ludogame.notification.ErrorNotification;
import io.github.ludogame.pawn.Pawn;
import io.github.ludogame.pawn.PawnColor;

public class PawnComponent extends Component {
    private AStarMoveComponent aStar;
    private SpawnData data;

    public PawnComponent(SpawnData data) {

        this.data = data;
    }

    /**
     * Move Pawn to next position (depends on number) in board
     *
     * @param number How many hops to next position
     * @param pawn   Pawn
     */
    public void move(int number, Pawn pawn) {
        if (!pawn.isStarted()) {
            if (number == 6) {
                moveToStartPoint(pawn);
            }else {
                new ErrorNotification("Musi byc 6");
            }
            return;
        }

        int index = LudoPlayerApp.ludoGame.findIndexOfCellInListByPawn(pawn);
        int nextIndex = (index + number) % Config.DEFAULT_PATH.size();

        moveToCellByIndex(pawn, nextIndex);
    }

    /**
     * Teleport Pawn to start point depends on his pawnColor
     *
     * @param pawn Pawn
     */
    private void moveToStartPoint(Pawn pawn) {
        switch (pawn.getPawnColor()) {
            case BLUE ->
                    setPawnPosition(pawn, (int) Config.BLUE_PAWN_START_SPAWN_POINT.getX(), (int) Config.BLUE_PAWN_START_SPAWN_POINT.getY());
            case RED ->
                    setPawnPosition(pawn, (int) Config.RED_PAWN_START_SPAWN_POINT.getX(), (int) Config.RED_PAWN_START_SPAWN_POINT.getY());
            case GREEN ->
                    setPawnPosition(pawn, (int) Config.GREEN_PAWN_START_SPAWN_POINT.getX(), (int) Config.GREEN_PAWN_START_SPAWN_POINT.getY());
            case YEllOW ->
                    setPawnPosition(pawn, (int) Config.YELLOW_PAWN_START_SPAWN_POINT.getX(), (int) Config.YELLOW_PAWN_START_SPAWN_POINT.getY());
        }
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
                (double) (x * Config.BLOCK_SIZE + Config.BLOCK_SIZE / 2),
                (double) (y * Config.BLOCK_SIZE + Config.BLOCK_SIZE / 2)
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
                (int) Config.DEFAULT_PATH.get(index).getX(), (int) Config.DEFAULT_PATH.get(index).getY())
        );
        aStar.moveToCell(
                (int) Config.DEFAULT_PATH.get(index).getX(), (int) Config.DEFAULT_PATH.get(index).getY()
        );
    }

    /**
     * Move Pawn to cell from CONFIG."..."_PATH
     *
     * @param pawn Pawn
     * @param cell Cell from CONFIG."..."_PATH
     */
    private void moveToCellByCell(Pawn pawn, AStarCell cell) {
        int index = LudoPlayerApp.ludoGame.findIndexOfCellInListByCell(cell);

        moveToCellByIndex(pawn, index);
    }

    public AStarMoveComponent getaStar() {
        return aStar;
    }
}

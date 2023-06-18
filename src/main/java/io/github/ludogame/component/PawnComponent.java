package io.github.ludogame.component;

import animatefx.animation.FadeOut;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.pathfinding.CellMoveComponent;
import com.almasb.fxgl.pathfinding.astar.AStarCell;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import io.github.ludogame.LudoPlayerApp;
import io.github.ludogame.config.Config;
import io.github.ludogame.config.UIConfig;
import io.github.ludogame.notification.ErrorNotification;
import io.github.ludogame.pawn.Pawn;
import io.github.ludogame.pawn.PawnColor;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.util.List;
import java.util.Optional;

public class PawnComponent extends Component {
    private AStarMoveComponent aStar;
    private CellMoveComponent cellMoveComponent;
    private SpawnData data;

    public PawnComponent(SpawnData data) {

        this.data = data;
    }

    //Set Animation while moving
    @Override
    public void onUpdate(double tpf) {
        AnimationComponent pawnAnimComponent = this.getEntity().getComponent(AnimationComponent.class);
        if (pawnAnimComponent == null) {
            return;
        }
        //        //Jeśli pionek sie porusza
        if (aStar.isMoving()) {
            //Jeśli sie porusza i nie ma animacji
            if (pawnAnimComponent.isAnimIdle()) {
                if (cellMoveComponent != null && cellMoveComponent.isMovingRight()) {
                    pawnAnimComponent.setMovingRight(true);
                } else if (cellMoveComponent != null && cellMoveComponent.isMovingLeft()) {
                    pawnAnimComponent.setMovingRight(false);
                }

                pawnAnimComponent.switchAnimation();
            }
        } else {
            if (!pawnAnimComponent.isAnimIdle()) {
                pawnAnimComponent.switchAnimation();
            }
        }
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
            //moveToCellByIndex(pawn, nextIndex);
            pawn.getEntity().getComponent(AnimationComponent.class).setOnceTextureDeath();
            FXGL.runOnce(() -> {
                //Mozna go przeteleportować do finish cell i niech tam jest
                //pawn.getEntity().getViewComponent().setVisible(false);
                moveToCellByIndex(pawn, pawn.getPawnColor().path.size()-1);
            }, Duration.seconds(AnimationComponent.ANIMATION_DURATION));
            return;
        }

        Pawn pawnInNextIndexCell = checkIfCellHasPawn(pawn, nextIndex);

        if (pawnInNextIndexCell != null) {

            pawnInNextIndexCell.getEntity().getComponent(AnimationComponent.class).setOnceTextureDeath();
            pawnInNextIndexCell.setStarted(false);

            FXGL.runOnce(() -> {
                killPawn(pawnInNextIndexCell, pawn);
            }, Duration.seconds(AnimationComponent.ANIMATION_DURATION));
        }

        moveToCellByIndex(pawn, nextIndex);
    }

    /**
     * Method return Pawn that is in nextIndex (Depends on DEFAULT_PATH)
     * @param nextIndex index in DEFAULT_PATH
     * @return Pawn if cell has pawn else null
     */
    public Pawn checkIfCellHasPawn(Pawn pawn, int nextIndex) {
        return (Pawn) LudoPlayerApp.ludoGame.getaStarGrid()
                .get((int) pawn.getPawnColor().path.get(nextIndex).getX(), (int) pawn.getPawnColor().path.get(nextIndex).getY())
                .getUserData();
    }

    /**
     * Method return Pawn that is in x, y coordination (Uses checkIfCellHasPawn(int nextIndex))
     * @param x X
     * @param y Y
     * @return Pawn if cell has pawn else null
     */
    private Pawn checkIfCellHasPawn(Pawn pawn,int x, int y) {
        int index = LudoPlayerApp.ludoGame.findIndexOfCellInListByCoordination(pawn,x, y);
        if (index == -1) {
            return null;
        }
        return checkIfCellHasPawn(pawn, index);
    }

    /**
     * Teleport Pawn to start point depends on his pawnColor
     *
     * @param pawn Pawn
     */
    private void moveToStartPoint(Pawn pawn) {
        Point2D startPoint = pawn.getPawnColor().startPoint;
        Pawn pawnInNextIndexCell = checkIfCellHasPawn(pawn, (int) startPoint.getX(), (int) startPoint.getY());
        if(pawnInNextIndexCell != null){
            if (pawn.getPawnColor().equals(pawnInNextIndexCell.getPawnColor())){
                new ErrorNotification("Masz już pionka na starcie");
                return;
            }
            killPawn(pawnInNextIndexCell);
        }
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
        setPawnPosition(pawn, x, y, null);
    }

    /**
     * Teleport Pawn to position in grid
     *
     * @param pawn Pawn
     * @param x    Position X in grid
     * @param y    Position Y in grid
     */
    private void setPawnPosition(Pawn pawn, int x, int y, Pawn pawnToSetOnCell) {

        pawn.getEntity().getComponent(PawnComponent.class).aStar.getCurrentCell().get().setUserData(pawnToSetOnCell);

        pawn.getEntity().setPosition(
                x * Config.BLOCK_SIZE + Config.BLOCK_SIZE / 2,
                y * Config.BLOCK_SIZE + Config.BLOCK_SIZE / 2
        );
        pawn.setCell(aStar.getGrid().get(x, y));

        pawn.getEntity().getComponent(PawnComponent.class).aStar.getCurrentCell().get().setUserData(pawn);
    }


    /**
     * Method to kill pawn and teleport it on spawn point depends on his pawnColor. Method find first
     * empty spot on Spawn Point
     *
     * @param pawn Pawn to kill
     */
    private void killPawn(Pawn pawn) {
        pawn.getEntity().getComponent(AnimationComponent.class).setOnceTextureDeath();
        List<Point2D> firstFindEmptySpawnPoint = findListOfSpawnPawnByColor(pawn.getPawnColor());

        Optional<Point2D> point2D = Optional.of(firstFindEmptySpawnPoint.stream()
                .filter(p -> LudoPlayerApp.ludoGame.getaStarGrid().get((int) p.getX(), (int) p.getY()).getUserData() == null)
                .findFirst()
                .get());

        setPawnPosition(pawn, (int) point2D.get().getX(), (int) point2D.get().getY());
        pawn.setStarted(false);
        //TODO trzeba si eruszyć pironkien
    }

    /**
     * Method to kill pawn and teleport it on spawn point depends on his pawnColor. Method find first
     * empty spot on Spawn Point. PawnToSetOnCell is necessary to set pawn on actual cell
     *
     * @param pawnToKill      Pawn to Kill
     * @param pawnToSetOnCell Pawn to set on grid
     */
    private void killPawn(Pawn pawnToKill, Pawn pawnToSetOnCell) {
        List<Point2D> firstFindEmptySpawnPoint = findListOfSpawnPawnByColor(pawnToKill.getPawnColor());

        Optional<Point2D> firstEmptySpawnPoint = Optional.of(firstFindEmptySpawnPoint.stream()
                .filter(point2D -> LudoPlayerApp.ludoGame.getaStarGrid().get((int) point2D.getX(), (int) point2D.getY()).getUserData() == null)
                .findFirst()
                .get());
        pawnToKill.setStarted(false);
        firstEmptySpawnPoint.ifPresent(point2D -> setPawnPosition(pawnToKill, (int) point2D.getX(), (int) point2D.getY(), pawnToSetOnCell));
    }

    /**
     * Find list on spawn point depends on PawnColor
     *
     * @param color Pawn Color
     * @return List of Spawn Point
     */
    private List<Point2D> findListOfSpawnPawnByColor(PawnColor color) {
        switch (color) {
            case RED -> {
                return UIConfig.SPAWN_POINTS_CELL_RED;
            }
            case BLUE -> {
                return UIConfig.SPAWN_POINTS_CELL_BLUE;
            }
            case GREEN -> {
                return UIConfig.SPAWN_POINTS_CELL_GREEN;
            }
            case YEllOW -> {
                return UIConfig.SPAWN_POINTS_CELL_YELLOW;
            }
        }
        return null;
    }


    /**
     * Move Pawn to position from CONFIG."..."_PATH
     *
     * @param pawn  Pawn
     * @param index Index of CONFIG."..."_PATH
     */
    private void moveToCellByIndex(Pawn pawn, int index) {

        pawn.getCell().setUserData(null);

        pawn.setCell(aStar.getGrid().get(
                (int) pawn.getPawnColor().path.get(index).getX(), (int) pawn.getPawnColor().path.get(index).getY())
        );
        aStar.moveToCell(
                (int) pawn.getPawnColor().path.get(index).getX(), (int) pawn.getPawnColor().path.get(index).getY()
        );

        pawn.getCell().setUserData(pawn);
    }
    public AStarMoveComponent getaStar() {
        return aStar;
    }
}

package io.github.ludogame.pawn;

import com.almasb.fxgl.pathfinding.astar.AStarCell;

import java.io.Serializable;

public class Pawn implements Serializable {

    private AStarCell cell;
    private boolean finished;
    private boolean started;

    public AStarCell getCell() {
        return cell;
    }

    public void setCell(AStarCell cell) {
        this.cell = cell;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}

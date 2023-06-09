package io.github.ludogame.pawn;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.pathfinding.astar.AStarCell;

import java.io.Serializable;

public class Pawn implements Serializable {

    private Entity entity;
    private AStarCell cell;
    private boolean finished;
    private boolean started;

    public Pawn(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

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

    @Override
    public String toString() {
        return "Pawn{" +
                "entity=" + entity +
                ", cell=" + cell +
                ", finished=" + finished +
                ", started=" + started +
                '}';
    }
}

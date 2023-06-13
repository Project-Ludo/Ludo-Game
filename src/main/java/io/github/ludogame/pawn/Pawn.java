package io.github.ludogame.pawn;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.pathfinding.astar.AStarCell;
import io.github.ludogame.component.PawnComponent;

import java.io.Serializable;

public class Pawn implements Serializable {

    private Entity entity;
    private AStarCell cell;
    private boolean finished;
    private boolean started;
    private PawnColor pawnColor;
    private int id;

    public Pawn(Entity entity, PawnColor pawnColor, int id) {
        this.entity = entity;
        this.cell = entity.getComponent(PawnComponent.class).getaStar().getCurrentCell().get();
        this.pawnColor = pawnColor;
        this.id = id;
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

    public PawnColor getPawnColor() {
        return pawnColor;
    }

    public void setPawnColor(PawnColor pawnColor) {
        this.pawnColor = pawnColor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Pawn{" +
                "entity=" + entity +
                ", cell=" + cell +
                ", finished=" + finished +
                ", started=" + started +
                ", pawnColor=" + pawnColor +
                ", id=" + id +
                '}';
    }
}

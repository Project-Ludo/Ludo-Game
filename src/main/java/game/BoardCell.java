package game;

import com.almasb.fxgl.pathfinding.astar.AStarCell;

public class BoardCell {

    private final AStarCell cell;
    private final CellColor color;

    //TODO to work with
    //  - Actual Player on cell
    private String objOnCell;

    public BoardCell(AStarCell cell, CellColor color) {
        this.cell = cell;
        this.color = color;
    }

    public AStarCell getCell() {
        return cell;
    }

    public CellColor getColor() {
        return color;
    }

    public String getObjOnCell() {
        return objOnCell;
    }

    public void setObjOnCell(String objOnCell) {
        this.objOnCell = objOnCell;
    }
}

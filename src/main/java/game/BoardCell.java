package game;

import com.almasb.fxgl.pathfinding.astar.AStarCell;

public class BoardCell {

    private final AStarCell cell;

    private final AStarCell nextCell;

    //TODO to work with
    //  - Actual Player on cell
    private String objOnCell;

    public BoardCell(AStarCell cell, AStarCell nextCell){
        this.cell = cell;
        this.nextCell = nextCell;
    }

    public AStarCell getCell() {
        return cell;
    }

    public AStarCell getNextCell() {
        return nextCell;
    }

    public String getObjOnCell() {
        return objOnCell;
    }

    public void setObjOnCell(String objOnCell) {
        this.objOnCell = objOnCell;
    }
}

package game;

import com.almasb.fxgl.pathfinding.astar.AStarCell;

public class BoardCell {
    private final AStarCell cell;
    private BoardCell nextBoardCell;

    //TODO to work with
    //  - Actual Player on cell
    private String objOnCell;

    public BoardCell(AStarCell cell, BoardCell nextBoardCell){
        this.cell = cell;
        this.nextBoardCell = nextBoardCell;
    }

    public AStarCell getCell() {
        return cell;
    }

    public BoardCell getNextBoardCell() {
        return nextBoardCell;
    }

    public String getObjOnCell() {
        return objOnCell;
    }

    public void setNextBoardCell(BoardCell nextBoardCell) {
        this.nextBoardCell = nextBoardCell;
    }

    public void setObjOnCell(String objOnCell) {
        this.objOnCell = objOnCell;
    }

    public boolean hasNext(){
        return nextBoardCell != null;
    }
}

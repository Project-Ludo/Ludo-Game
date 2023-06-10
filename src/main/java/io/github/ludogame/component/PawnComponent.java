package io.github.ludogame.component;

import com.almasb.fxgl.core.collection.grid.Cell;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.pathfinding.astar.AStarCell;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import io.github.ludogame.LudoPlayerApp;
import io.github.ludogame.config.Config;
import io.github.ludogame.config.UIConfig;

public class PawnComponent extends Component {
    private AStarMoveComponent astar;

    public void move(int number){
        Cell currentCell = astar.getCurrentCell().get();

        //Aktualna pozycja
        int x = UIConfig.BOARD_START_LAYOUT_X + currentCell.getX() * Config.BLOCK_SIZE;
        int y = UIConfig.BOARD_START_LAYOUT_Y + currentCell.getY() * Config.BLOCK_SIZE;

        int index = LudoPlayerApp.ludoGame.findIndexOfCellPositionInList(x,y);
        int nextIndex = (index+number)%LudoPlayerApp.ludoGame.getListOfGrid().size();

        astar.moveToCell(
                LudoPlayerApp.ludoGame.getListOfGrid().get(nextIndex)
        );
    }

    public void moveToStartPoint(){
        if(astar == null){
            System.out.println("ASTRAT NULL");
            return;
        }
        System.out.println("trynna move");
        //Możliwe żeby to zadziałalo musi używac tej funckji spawn("Pawn", X, Y);

        System.out.println("actual X: " + astar.getCurrentCell().get().getX());
        System.out.println("actual Y: " + astar.getCurrentCell().get().getY());

        System.out.println("to X: " + Config.BLUE_PAWN_START_SPAWN_POINT.getX());
        System.out.println("to Y: " + (int)Config.BLUE_PAWN_START_SPAWN_POINT.getY());
        astar.moveToCell((int) Config.BLUE_PAWN_START_SPAWN_POINT.getX(), (int)Config.BLUE_PAWN_START_SPAWN_POINT.getY());
    }

}

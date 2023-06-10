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
}

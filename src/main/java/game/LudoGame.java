package game;

import network.server.LudoServer;
import player.Player;

import java.util.ArrayList;
import java.util.List;

public class LudoGame {

    private LudoServer server;
    private final List<Player> players;
    private final List<BoardCell> cells;

    public LudoGame() {
        this.players = new ArrayList<>();
        this.cells = new ArrayList<>();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
    }

    public LudoServer getServer() {
        return server;
    }

    public void setServer(LudoServer server) {
        this.server = server;
    }

    public List<BoardCell> getCells() {
        return cells;
    }

    public void addCell(BoardCell boardCell) {
        this.cells.add(boardCell);
    }

    public void addCells(List<BoardCell> boardCells) {
        this.cells.addAll(boardCells);
    }

    public BoardCell getNextCell(BoardCell currentCell) {
        int currentCellIndex = getCellIndex(currentCell);
        if (currentCellIndex == cells.size() - 1) {
            return cells.get(0);
        }

        return cells.get(currentCellIndex + 1);
    }

    private int getCellIndex(BoardCell boardCell) {
        for (int i = 0; i < cells.size(); i++) {
            if (cells.get(i).equals(boardCell)) {
                return i;
            }
        }

        return 0;
    }
}

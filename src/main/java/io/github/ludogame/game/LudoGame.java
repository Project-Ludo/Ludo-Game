package io.github.ludogame.game;

import com.almasb.fxgl.core.serialization.Bundle;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.net.Server;
import com.almasb.fxgl.pathfinding.astar.AStarCell;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import com.almasb.fxgl.time.TimerAction;
import io.github.ludogame.config.Config;
import io.github.ludogame.pawn.Pawn;
import io.github.ludogame.player.LudoPlayer;
import io.github.ludogame.player.PlayerColor;
import io.github.ludogame.player.PlayerService;
import javafx.util.Duration;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class LudoGame implements Serializable {

    private CopyOnWriteArrayList<LudoPlayer> players = new CopyOnWriteArrayList<>();
    private AtomicInteger startCountdown = new AtomicInteger(60);
    private Server<Bundle> server;
    private boolean countdownStarted;
    private PlayerColor playerColorTurn;
    private TimerAction countdownTask;
    private int diceResult;
    private AStarGrid aStarGrid;

    public LudoGame() {
    }

    public int findIndexOfCellInListByPawn(Pawn pawn) {
        if (!pawn.isStarted()) {
            pawn.setStarted(true);
            return 0;
        }
        int i;
        for (i = 0; i < Config.DEFAULT_PATH.size(); i++) {
            if (Config.DEFAULT_PATH.get(i).getY() == pawn.getCell().getY() && Config.DEFAULT_PATH.get(i).getX() == pawn.getCell().getX()) {
                System.out.println(Config.DEFAULT_PATH.get(i).getX() + " " + Config.DEFAULT_PATH.get(i).getY());
                return i;
            }
        }
        return -1;
    }

    public int findIndexOfCellInListByCell(AStarCell cell) {
        int i;
        for (i = 0; i < Config.DEFAULT_PATH.size(); i++) {
            if (Config.DEFAULT_PATH.get(i).getY() == cell.getY() && Config.DEFAULT_PATH.get(i).getX() == cell.getX()) {
                return i;
            }
        }
        return -1;
    }

    public AStarGrid getListOfGrid() {
        return aStarGrid;
    }

    public void setaStarGrid(AStarGrid aStarGrid) {
        this.aStarGrid = aStarGrid;
    }

    public AStarGrid getaStarGrid() {
        return aStarGrid;
    }

    public PlayerColor getPlayerColorTurn() {
        return playerColorTurn;
    }

    public void setPlayerColorTurn(PlayerColor playerColorTurn) {
        this.playerColorTurn = playerColorTurn;
    }

    public CopyOnWriteArrayList<LudoPlayer> getPlayers() {
        return players;
    }

    public void addPlayer(LudoPlayer player) {
        this.players.add(player);
    }

    public void removePlayer(LudoPlayer player) {
        this.players.remove(player);
    }

    public void addPlayers(ArrayList<LudoPlayer> players) {
        this.players.addAll(players);
    }

    public void setPlayers(CopyOnWriteArrayList<LudoPlayer> players) {
        this.players = players;
    }

    public void updatePlayer(LudoPlayer player) {
        players.stream()
                .filter(p -> p.getUuid().equals(player.getUuid()))
                .findFirst()
                .ifPresent(p -> {
                    p.setReady(player.isReady());
                    p.setConnected(player.isConnected());
                    p.setNickname(player.getNickname());
                });
    }

    public long getReadyPlayersAmount() {
        return players.stream()
                .filter(LudoPlayer::isReady)
                .count();
    }

    public AtomicInteger getStartCountdown() {
        return startCountdown;
    }

    public void setStartCountdown(AtomicInteger startCountdown) {
        this.startCountdown = startCountdown;
    }

    public boolean isCountdownStarted() {
        return countdownStarted;
    }

    public void setCountdownStarted(boolean countdownStarted) {
        this.countdownStarted = countdownStarted;
    }

    public void startCountdown() {
        this.countdownStarted = true;
        this.countdownTask = FXGL.run(() -> {
            this.startCountdown.decrementAndGet();

            if (getReadyPlayersAmount() >= 2 && this.startCountdown.get() > 5) {
                this.startCountdown.set(5);
            }

            if (this.startCountdown.get() < 0) {
                setPlayerColorTurn(getTakenColor());
                countdownTask.expire();
            }
        }, Duration.seconds(1), 60);
    }

    public PlayerColor getAvailableColor() {
        List<PlayerColor> allColors = new ArrayList<>(Arrays.asList(PlayerColor.BLUE, PlayerColor.RED, PlayerColor.GREEN, PlayerColor.YELLOW));
        players.forEach(player -> allColors.removeIf(color -> color.equals(player.getColor())));
        Collections.shuffle(allColors);
        return allColors.get(0);
    }

    public PlayerColor getTakenColor() {
        List<PlayerColor> allColors = new ArrayList<>(Arrays.asList(PlayerColor.BLUE, PlayerColor.RED, PlayerColor.GREEN, PlayerColor.YELLOW));
        List<PlayerColor> playerColors = players.stream().map(LudoPlayer::getColor).collect(Collectors.toList());
        allColors.retainAll(playerColors);
        Collections.shuffle(allColors);
        return allColors.get(0);
    }


    public Server<Bundle> getServer() {
        return server;
    }

    public void setServer(Server<Bundle> server) {
        this.server = server;
    }

    public void updateGame(LudoGameDTO ludoGameDTO) {
        Map<PlayerColor, List<Pawn>> toRollback = new HashMap<>();
        this.players.forEach(p -> toRollback.put(p.getColor(), p.getPawns()));
        this.players = PlayerService.convertToPlayerList(ludoGameDTO.getPlayers());
        this.players.forEach(p -> p.setPawns(toRollback.get(p.getColor())));
        this.startCountdown = ludoGameDTO.getStartCountdown();
        this.countdownStarted = ludoGameDTO.isCountdownStarted();
        this.playerColorTurn = ludoGameDTO.getPlayerColorTurn();
        this.diceResult = ludoGameDTO.getDiceResult();
    }

    public int getDiceResult() {
        return diceResult;
    }

    public void setDiceResult(int diceResult) {
        this.diceResult = diceResult;
    }
}

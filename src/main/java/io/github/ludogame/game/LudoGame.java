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
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class LudoGame implements Serializable {

    private final int COUNTDOWN_VALUE = 10;

    private CopyOnWriteArrayList<LudoPlayer> players = new CopyOnWriteArrayList<>();
    private AtomicInteger startCountdown = new AtomicInteger(COUNTDOWN_VALUE);
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

        List<Point2D> path = pawn.getPawnColor().path;
        for (int i = 0; i < path.size(); i++) {
            if (path.get(i).getY() == pawn.getCell().getY() && path.get(i).getX() == pawn.getCell().getX()) {
                return i;
            }
        }

        return -1;
    }
    public int findIndexOfCellInListByCoordination(Pawn pawn, int x, int y) {
        int i;
        for (i = 0; i < pawn.getPawnColor().path.size(); i++) {
            if (pawn.getPawnColor().path.get(i).getY() == y && pawn.getPawnColor().path.get(i).getX() == x) {
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

            if (getReadyPlayersAmount() >= 3 && this.startCountdown.get() > 5) {
                this.startCountdown.set(5);
            }

            if (this.startCountdown.get() <= 0) {
                setPlayerColorTurn(getTakenColor());
                countdownTask.expire();
            }
        }, Duration.seconds(1), COUNTDOWN_VALUE);
    }

    public PlayerColor getAvailableColor() {
        List<PlayerColor> allColors = new ArrayList<>(Arrays.asList(PlayerColor.BLUE, PlayerColor.YELLOW, PlayerColor.RED, PlayerColor.GREEN));
        players.forEach(player -> allColors.removeIf(color -> color.equals(player.getColor())));
        return allColors.get(0);
    }

    public PlayerColor getTakenColor() {
        List<PlayerColor> allColors = new ArrayList<>(Arrays.asList(PlayerColor.BLUE, PlayerColor.YELLOW, PlayerColor.RED, PlayerColor.GREEN));
        List<PlayerColor> playerColors = players.stream().map(LudoPlayer::getColor).collect(Collectors.toList());
        allColors.retainAll(playerColors);
        return allColors.get(0);
    }

    public void nextPlayerColorTurn() {
        List<PlayerColor> allColors = new ArrayList<>(Arrays.asList(PlayerColor.BLUE, PlayerColor.YELLOW, PlayerColor.RED, PlayerColor.GREEN));
        List<PlayerColor> fixedList = new ArrayList<>();
        fixedList.add(playerColorTurn);

        int index = allColors.indexOf(playerColorTurn);

        for (int i = index + 1; i < allColors.size(); i++) {
            fixedList.add(allColors.get(i));
        }

        for (int i = 0; i < index; i++) {
            fixedList.add(allColors.get(i));
        }

        int resultIndex = 0;
        for (PlayerColor playerColor : fixedList) {
            if (playerColor == playerColorTurn) {
                continue;
            }

            for (LudoPlayer player : this.players) {
                if (player.getColor() == playerColor) {
                    setPlayerColorTurn(playerColor);
                    System.out.println("Player Color: " + playerColor);
                    return;
                }
            }
        }
    }

    //TODO pomija ture jak nie może wejśc na koniec a ma pionki jeszcze w grze
    //  Pomija ture gdy mam 6 i nasz pionek jest na starcie

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

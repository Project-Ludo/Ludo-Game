package network.server;

import player.Player;

import java.util.List;

public class LudoGame {

    private final LudoGameServer ludoGameServer;
    private final List<Player> players;

    public LudoGame(LudoGameServer ludoGameServer, List<Player> players) {
        this.ludoGameServer = ludoGameServer;
        this.players = players;
    }

    public LudoGameServer getLudoGameServer() {
        return ludoGameServer;
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
}

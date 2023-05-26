package network.server;

import player.Player;

import java.util.ArrayList;
import java.util.List;

public class LudoGame {

    private LudoServer server;
    private final List<Player> players;

    public LudoGame() {
        this.players = new ArrayList<>();
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
}

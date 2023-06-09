package io.github.ludogame.network.server;

import com.almasb.fxgl.dsl.FXGL;
import javafx.util.Duration;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ConnectionHandler {
    private final static Integer SECONDS_TO_OFFLINE_STATUS = 5;
    private final static Integer SECONDS_TO_REMOVE = 10;
    private final Map<UUID, Long> lastRequestTime;

    public ConnectionHandler() {
        this.lastRequestTime = new HashMap<>();
        initializeValidationTask();
    }

    public void updateLastRequestTime(UUID uuid) {
        this.lastRequestTime.put(uuid, System.currentTimeMillis());
    }

    public Long getLastRequestTime(UUID uuid) {
        return lastRequestTime.getOrDefault(uuid, System.currentTimeMillis());
    }

    public boolean hasPassed(UUID uuid, int seconds) {
        Long lastRequestTime = getLastRequestTime(uuid);
        Timestamp timestamp = new Timestamp(lastRequestTime);
        Instant instant = timestamp.toInstant().plusSeconds(seconds);
        return instant.isAfter(Instant.now());
    }

    private boolean validateTime(UUID uuid) {
        return hasPassed(uuid, SECONDS_TO_OFFLINE_STATUS);
    }

    private boolean validateRemoval(UUID uuid) {
        return hasPassed(uuid, SECONDS_TO_REMOVE);
    }

    private void validatePlayer(LudoPlayer player) {
        if (!validateRemoval(player.getUuid())) {
            LudoServerApp.ludoGame.getPlayers().removeIf(p -> p.getUuid().equals(player.getUuid()));
            lastRequestTime.remove(player.getUuid());
            return;
        }

        player.setConnected(validateTime(player.getUuid()));
    }

    private void validatePlayers() {
        LudoServerApp.ludoGame.getPlayers().forEach(this::validatePlayer);
    }

    private void initializeValidationTask() {
        FXGL.run(this::validatePlayers, Duration.millis(1000));
    }
}

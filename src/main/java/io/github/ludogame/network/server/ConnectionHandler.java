package io.github.ludogame.network.server;

import com.almasb.fxgl.dsl.FXGL;
import javafx.util.Duration;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ConnectionHandler {
    private final Map<UUID, Long> lastRequestTime;
    private final static Integer SECONDS_LIMIT = 5;

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

    private boolean validateTime(UUID uuid) {
        Long lastRequestTime = getLastRequestTime(uuid);
        Timestamp timestamp = new Timestamp(lastRequestTime);
        Instant instant = timestamp.toInstant().plusSeconds(SECONDS_LIMIT);
        return instant.isAfter(Instant.now());
    }

    private void validatePlayers() {
        LudoServerApp.ludoGame.getPlayers().forEach(player -> player.setConnected(validateTime(player.getUuid())));
    }

    private void initializeValidationTask() {
        FXGL.run(this::validatePlayers, Duration.millis(1000));
    }
}

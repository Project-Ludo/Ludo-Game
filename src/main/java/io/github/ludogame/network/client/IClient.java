package io.github.ludogame.network.client;

import com.almasb.fxgl.core.serialization.Bundle;
import com.almasb.fxgl.net.Client;
import io.github.ludogame.player.LudoPlayer;

public interface IClient {

    Client<Bundle> connect(String ip, int port, LudoPlayer ludoPlayer);
}

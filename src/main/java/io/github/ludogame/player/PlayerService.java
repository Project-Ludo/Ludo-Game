package io.github.ludogame.player;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlayerService {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static LudoPlayerDTO convertToDTO(LudoPlayer player) {
        return modelMapper.map(player, LudoPlayerDTO.class);
    }

    public static LudoPlayer convertToPlayer(LudoPlayerDTO playerDTO) {
        return modelMapper.map(playerDTO, LudoPlayer.class);
    }

    public static CopyOnWriteArrayList<LudoPlayer> convertToPlayerList(List<LudoPlayerDTO> ludoPlayerDTOList) {
        CopyOnWriteArrayList<LudoPlayer> ludoPlayers = new CopyOnWriteArrayList<>();

        for (LudoPlayerDTO playerDTO : ludoPlayerDTOList) {
            ludoPlayers.add(convertToPlayer(playerDTO));
        }

        return ludoPlayers;
    }
}

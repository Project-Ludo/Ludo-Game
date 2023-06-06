package io.github.ludogame.player;

import org.modelmapper.ModelMapper;

public class PlayerService {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static LudoPlayerDTO convertToDTO(LudoPlayer player) {
        return modelMapper.map(player, LudoPlayerDTO.class);
    }

    public static LudoPlayer convertToPlayer(LudoPlayerDTO playerDTO) {
        return modelMapper.map(playerDTO, LudoPlayer.class);
    }
}

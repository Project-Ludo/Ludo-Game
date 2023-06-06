package io.github.ludogame.network.server;

import org.modelmapper.ModelMapper;

public class GameService {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static LudoGameDTO convertToDTO(LudoGame game) {
        return modelMapper.map(game, LudoGameDTO.class);
    }

    public static LudoGame convertToGame(LudoGameDTO gameDTO) {
        return modelMapper.map(gameDTO, LudoGame.class);
    }
}

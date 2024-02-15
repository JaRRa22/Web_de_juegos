package com.springboot.webdejuegos.mapper;

import com.springboot.webdejuegos.dto.GameDto;
import com.springboot.webdejuegos.entity.Game;

public class GameMapper {
    public static GameDto mapToGameDto(Game game){
        return new GameDto(
                game.getId(),
                game.getName(),
                game.getImage(),
                game.getDescription(),
                game.getRoute()
        );
    }

    public static Game mapToGame(GameDto gameDto){
        return new Game(
                gameDto.getId(),
                gameDto.getName(),
                gameDto.getImage(),
                gameDto.getDescription(),
                gameDto.getRoute()
        );
    }
}

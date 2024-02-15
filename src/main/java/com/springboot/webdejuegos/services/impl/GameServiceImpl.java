package com.springboot.webdejuegos.services.impl;

import com.springboot.webdejuegos.dto.GameDto;
import com.springboot.webdejuegos.entity.Game;
import com.springboot.webdejuegos.exception.ResourceNotFoundException;
import com.springboot.webdejuegos.mapper.GameMapper;
import com.springboot.webdejuegos.repository.GameRepository;
import com.springboot.webdejuegos.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    private GameRepository gameRepository;
    @Override
    public void save(GameDto gameDto) {
        Game game = new Game();
        game.setName(gameDto.getName());
        game.setImage(gameDto.getImage());
        game.setDescription(gameDto.getDescription());
        game.setRoute(gameDto.getRoute());
        gameRepository.save(game);
    }

    @Override
    public GameDto findById(Long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un juego con el id: " + id));
        return GameMapper.mapToGameDto(game);
    }

    @Override
    public List<GameDto> findAll() {
        List<Game> games = gameRepository.findAll();
        return games.stream().map((game) -> GameMapper.mapToGameDto(game))
                .collect(Collectors.toList());
    }

    @Override
    public List<GameDto> findAllByName(String name) {
        List<Game> games = gameRepository.findAllByName(name);
        return games.stream().map((game -> GameMapper.mapToGameDto(game)))
                .collect(Collectors.toList());
    }

    @Override
    public GameDto update(Long id, GameDto gameDto) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un juego con el id: " + id));
        game.setName(gameDto.getName());
        if (gameDto.getImage() != null){
            game.setImage(gameDto.getImage());
        }
        game.setDescription(gameDto.getDescription());
        game.setRoute(gameDto.getRoute());
        gameRepository.save(game);
        return GameMapper.mapToGameDto(game);
    }

    @Override
    public void delete(Long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un juego con el id: " + id));
        gameRepository.delete(game);
    }
}

package com.springboot.webdejuegos.services;

import com.springboot.webdejuegos.dto.GameDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameService {
    void save(GameDto gameDto);

    GameDto findById(Long id);

    List<GameDto> findAll();
    List<GameDto> findAllByName(String name);

    GameDto update(Long id, GameDto gameDto);

    void delete(Long id);
}

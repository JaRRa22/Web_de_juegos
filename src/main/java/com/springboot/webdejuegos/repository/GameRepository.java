package com.springboot.webdejuegos.repository;

import com.springboot.webdejuegos.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    ArrayList<Game> findAll();
    Game save(Game game);
    Game findById(long id);
    Game findByName(String name);
    @Query("SELECT g FROM Game g WHERE g.name LIKE CONCAT('%', :name, '%')")
    List<Game> findAllByName(@Param("name") String name);
}

package com.springboot.webdejuegos.repository;

import com.springboot.webdejuegos.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.springboot.webdejuegos.entity.User;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    ArrayList<User> findAll();

    User save(User user);

    User findById(long id);
    User findByEmail(String email);
    User findByName(String name);
    User findByNickname(String nickname);
    @Query("SELECT a FROM User a WHERE a.name LIKE CONCAT('%', :name, '%')")
    List<User> findAllByName(@Param("name") String name);
}
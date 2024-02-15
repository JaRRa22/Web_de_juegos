package com.springboot.webdejuegos.repository;

import com.springboot.webdejuegos.entity.Result;
import com.springboot.webdejuegos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findAllByUserId(long id);
}

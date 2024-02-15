package com.springboot.webdejuegos.controller;

import com.springboot.webdejuegos.dto.GameDto;
import com.springboot.webdejuegos.entity.Game;
import com.springboot.webdejuegos.entity.Result;
import com.springboot.webdejuegos.entity.User;
import com.springboot.webdejuegos.mapper.GameMapper;
import com.springboot.webdejuegos.mapper.UserMapper;
import com.springboot.webdejuegos.repository.ResultRepository;
import com.springboot.webdejuegos.services.impl.GameServiceImpl;
import com.springboot.webdejuegos.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Random;

@Controller
public class ResultController {
    @Autowired
    GameServiceImpl gameService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    ResultRepository resultRepository;

    @GetMapping("/guardarresultado/{id}/{points}")
    public String guardarResultados(@PathVariable("points") long points, @PathVariable("id") Long id, Principal principal) {
        User user = userService.findByEmail(principal.getName());

        Random rd = new Random();
        Result resultado = new Result();
        resultado.setUser(user);
        resultado.setGame(GameMapper.mapToGame(gameService.findById(id)));
        resultado.setPoints(rd.nextLong(1,11));
        //resultado.setPoints(points);
        resultRepository.save(resultado);

        return "redirect:/games";
    }
}
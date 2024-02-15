package com.springboot.webdejuegos.controller;

import com.springboot.webdejuegos.dto.GameDto;
import com.springboot.webdejuegos.dto.UserDto;
import com.springboot.webdejuegos.entity.Game;
import com.springboot.webdejuegos.entity.Image;
import com.springboot.webdejuegos.entity.Result;
import com.springboot.webdejuegos.entity.User;
import com.springboot.webdejuegos.mapper.GameMapper;
import com.springboot.webdejuegos.repository.ResultRepository;
import com.springboot.webdejuegos.services.ImageService;
import com.springboot.webdejuegos.services.impl.GameServiceImpl;
import com.springboot.webdejuegos.services.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

@Controller
public class GameController {
    @Autowired
    GameServiceImpl gameService;
    @Autowired
    ImageService imageService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    ResultRepository resultRepository;

    @GetMapping("/games")
    public String games(Model model, Principal principal){
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("games", gameService.findAll());
        model.addAttribute("user", user);
        model.addAttribute("nombre", "");

        return "/games";
    }

    @GetMapping("/games/encontrados")
    public String gamesEncontrados(@RequestParam("nombre") String nombre, Model model, Principal principal){
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("games", gameService.findAllByName(nombre));
        model.addAttribute("user", user);

        return "/games";
    }

    @GetMapping("/game/{id}")
    public String game(@PathVariable Long id, Model model, Principal principal){
        User user = userService.findByEmail(principal.getName());
        GameDto game = gameService.findById(id);

        model.addAttribute("game", game);
        model.addAttribute("user", user);
        return "/game";
    }

    @GetMapping("/games/crud")
    public String crud(Model model, Principal principal){
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("games", gameService.findAll());
        model.addAttribute("user", user);

        return "/crud_juegos";
    }

    @GetMapping("/games/crud/add")
    public String addGame(Model model){
        model.addAttribute("game", new Game());

        return "form_juegos";
    }

    @PostMapping("/games/crud/save")
    public String save(@Valid @ModelAttribute("game") GameDto game,
                       BindingResult result,
                       Model model,  @RequestParam("image") MultipartFile file) throws IOException, SQLException {

        if (!file.isEmpty()){
            byte[] bytes = file.getBytes();
            Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

            Image image = new Image();
            image.setImage(blob);
            imageService.save(image);


            game.setImage(image);
        }

        gameService.save(game);
        return "redirect:/games/crud";
    }

    @GetMapping("/games/crud/update/{id}")
    public String updateGame(@PathVariable long id, Model model){
        GameDto game = gameService.findById(id);

        model.addAttribute("game", game);
        return "form_juegos";
    }

    @PostMapping("/games/crud/modificar")
    public String modificarGame(@Valid @ModelAttribute("game") GameDto game,
                       BindingResult result,
                       Model model,  @RequestParam("image") MultipartFile file) throws IOException, SQLException {

        if (!file.isEmpty()){
            byte[] bytes = file.getBytes();
            Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

            Image image = new Image();
            image.setImage(blob);
            imageService.save(image);


            game.setImage(image);
        }

        gameService.update(game.getId(), game);
        return "redirect:/games/crud";
    }


    @GetMapping("/games/crud/delete/{id}")
    public String deleteGame(@PathVariable Long id){
        gameService.delete(id);

        return "redirect:/games/crud";
    }

}

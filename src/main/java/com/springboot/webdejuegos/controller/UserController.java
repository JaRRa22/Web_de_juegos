package com.springboot.webdejuegos.controller;

import com.springboot.webdejuegos.dto.UserDto;
import com.springboot.webdejuegos.entity.Image;
import com.springboot.webdejuegos.entity.User;
import com.springboot.webdejuegos.mapper.UserMapper;
import com.springboot.webdejuegos.repository.ImageRepository;
import com.springboot.webdejuegos.repository.ResultRepository;
import com.springboot.webdejuegos.repository.UserRepository;
import com.springboot.webdejuegos.security.CustomUserDetailsService;
import com.springboot.webdejuegos.services.ImageService;
import jakarta.validation.Valid;
import com.springboot.webdejuegos.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

@Controller
public class UserController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    ImageService imageService;
    @Autowired
    CustomUserDetailsService customUserDetailsService;
    @Autowired
    ResultRepository resultRepository;


    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model) {

        User existing = userService.findByEmail(user.getEmail());

        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }

        userService.save(user);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String listRegisteredUsers(Model model, Principal principal){
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("user", user);
        model.addAttribute("results", resultRepository.findAllByUserId(user.getId()));
        return "users";
    }

    @GetMapping("/users/encontrados")
    public String users(@RequestParam("nombre")String nombre, Model model, Principal principal){
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("users", userService.findAllByName(nombre));
        model.addAttribute("user", user);
        return "users";
    }

    @GetMapping("/user")
    public String userRegistered(Model model, Principal principal){
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/crud")
    public String crud(Model model, Principal principal){
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("user", user);

        return "/crud";
    }

    @GetMapping("/crud/update/{id}")
    public String updateUser(@PathVariable long id, Model model){
        UserDto user = userService.findById(id);

        model.addAttribute("user", user);
        return "form_update_user";
    }


    @PostMapping("/crud/modificar")
    public String modificarUser(@Valid @ModelAttribute("user") UserDto userUpdated,
                                BindingResult result,
                                Model model, @RequestParam("avatar") MultipartFile file) throws IOException, SQLException {

        if (!file.isEmpty()){
            byte[] bytes = file.getBytes();
            Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

            Image image = new Image();
            image.setImage(blob);
            imageService.save(image);


            userUpdated.setAvatar(image);
        }

        userService.update(userUpdated.getId(), userUpdated);
        return "redirect:/users";
    }


    @GetMapping("/crud/delete/{id}")
    public String deleteUser(@PathVariable Long id){
        userService.delete(id);

        return "redirect:/crud";
    }
}

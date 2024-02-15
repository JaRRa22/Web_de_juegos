package com.springboot.webdejuegos.services;

import com.springboot.webdejuegos.dto.GameDto;
import com.springboot.webdejuegos.dto.UserDto;
import com.springboot.webdejuegos.entity.User;

import java.util.List;

public interface UserService {
    void save(UserDto userDto);

    UserDto findById(Long id);

    User findByEmail(String email);
    List<UserDto> findAllByName(String name);
    List<UserDto> findAll();

    UserDto update(Long id, UserDto userDto);

    void delete(Long id);
}

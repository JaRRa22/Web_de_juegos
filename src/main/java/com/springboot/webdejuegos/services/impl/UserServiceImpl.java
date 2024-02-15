package com.springboot.webdejuegos.services.impl;

import com.springboot.webdejuegos.dto.GameDto;
import com.springboot.webdejuegos.dto.UserDto;
import com.springboot.webdejuegos.entity.Game;
import com.springboot.webdejuegos.entity.Role;
import com.springboot.webdejuegos.entity.User;
import com.springboot.webdejuegos.exception.ResourceNotFoundException;
import com.springboot.webdejuegos.mapper.UserMapper;
import com.springboot.webdejuegos.repository.RoleRepository;
import com.springboot.webdejuegos.repository.UserRepository;
import com.springboot.webdejuegos.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void save(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setNickname(userDto.getNickname());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setAvatar(userDto.getAvatar());
        Role role = roleRepository.findByName("ROLE_USER");
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public UserDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un usuario con el id: " + id));
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllByName(String name) {
        List<User> users = userRepository.findAllByName(name);
        return users.stream().map((user) -> UserMapper.mapToUserDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> UserMapper.mapToUserDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto update(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un usuario con el id: " + id));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setNickname(userDto.getNickname());

        if (!userDto.getPassword().isEmpty()){
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        if (userDto.getAvatar() != null){
            user.setAvatar(userDto.getAvatar());
        }

        userRepository.save(user);
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un usuario con el id: " + id));
        userRepository.delete(user);
    }
}

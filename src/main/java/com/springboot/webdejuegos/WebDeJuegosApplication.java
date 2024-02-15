package com.springboot.webdejuegos;

import com.springboot.webdejuegos.entity.Image;
import com.springboot.webdejuegos.entity.Role;
import com.springboot.webdejuegos.entity.User;
import com.springboot.webdejuegos.repository.ImageRepository;
import com.springboot.webdejuegos.repository.RoleRepository;
import com.springboot.webdejuegos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.util.Arrays;

@SpringBootApplication(scanBasePackages = "com.springboot.webdejuegos")
public class WebDeJuegosApplication {
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		//ProcessBuilder pb = new ProcessBuilder("\\xampp\\xampp_start.exe");
		ProcessBuilder pb = new ProcessBuilder("\\xampp\\mysql_start.bat");
		ProcessBuilder pb1 = new ProcessBuilder("\\xampp\\apache_start.bat");
		try {
			pb.start();
			pb1.start();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		SpringApplication.run(WebDeJuegosApplication.class, args);
	}

	@Bean
	CommandLineRunner crearRoles() {
		return args -> {

			if (roleRepository.findAll().size() < 2) {
				Role admin = new Role();
				admin.setName("ROLE_ADMIN");
				roleRepository.save(admin);

				Role user = new Role();
				user.setName("ROLE_USER");
				roleRepository.save(user);
			}

			if (userRepository.findAll().isEmpty()) {
				User admin = new User();
				admin.setName("admin");
				admin.setEmail("admin@gmail.com");
				admin.setNickname("admin");
				admin.setPassword(passwordEncoder.encode("1234"));
				admin.setRoles(Arrays.asList(roleRepository.findByName("ROLE_ADMIN")));
				userRepository.save(admin);

				User user = new User();
				user.setName("Alberto");
				user.setEmail("alberto@gmail.com");
				user.setNickname("Jarra");
				user.setPassword(passwordEncoder.encode("1234"));
				user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
				userRepository.save(user);

				User user1 = new User();
				user1.setName("Fran");
				user1.setEmail("fran@gmail.com");
				user1.setNickname("Dragonero");
				user1.setPassword(passwordEncoder.encode("1234"));
				user1.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
				userRepository.save(user1);
			}
		};
	}
}

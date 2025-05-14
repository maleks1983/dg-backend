package com.app.dgBackend.config;

import com.app.dgBackend.entity.AppUser;
import com.app.dgBackend.entity.Role;
import com.app.dgBackend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByTel("963749996").isEmpty()) {
                AppUser user = new AppUser();
                user.setTel("963749996");
                user.setName("Олександр");
                user.setPassword(passwordEncoder.encode("1234"));
                user.setEnabled(true);
                user.getRoles().add(Role.ROLE_ADMIN);
                userRepository.save(user);
            }
            if (userRepository.findByTel("983782815").isEmpty()) {
                AppUser user = new AppUser();
                user.setTel("983782815");
                user.setName("Валік");
                user.setPassword(passwordEncoder.encode("1234"));
                user.setEnabled(true);
                user.getRoles().add(Role.ROLE_ADMIN);
                userRepository.save(user);
            }
        };
    }
}
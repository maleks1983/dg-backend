package com.app.dgBackend.config;

import com.app.dgBackend.entity.AppUser;
import com.app.dgBackend.entity.Operation;
import com.app.dgBackend.entity.Role;
import com.app.dgBackend.repository.OperationRepository;
import com.app.dgBackend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class DataInitializer {
    List<String> operations = List.of(
            "Пломбування пристрою",
            "Тест плати, занесення плати в базу",
            "Прошивка робочою прошивкою",
            "Встановлення батарейки та фізичне тестування плати",
            "Складання паспортів",
            "Вкладання паспорта в пакет",
            "Збірка плати в корпус, наклейка наліпки",
            "Пакування плати в пакет 1 шт.",
            "Пакування плати в пакет по 5 шт.",
            "Пакування плати на відправку"
    );

    @Bean
    CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder, OperationRepository operationRepository) {
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
            if (operationRepository.findAll().isEmpty()){
                operations.forEach(s -> operationRepository.save(new Operation(s, 0)));
            }
        };
    }
}
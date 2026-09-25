package com.edumerge.support.config;

import com.edumerge.support.entity.User;
import com.edumerge.support.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializePasswords(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            for (User user : userRepository.findAll()) {

                String password = user.getPassword();

                if (password != null
                        && !password.startsWith("$2a$")
                        && !password.startsWith("$2b$")
                        && !password.startsWith("$2y$")) {

                    user.setPassword(
                            passwordEncoder.encode(password)
                    );

                    userRepository.save(user);
                }
            }
        };
    }
}

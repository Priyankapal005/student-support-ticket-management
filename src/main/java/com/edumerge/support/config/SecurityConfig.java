package com.edumerge.support.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/**")
            )

            .authorizeHttpRequests(auth -> auth

                // Public pages
                .requestMatchers("/login", "/css/**", "/js/**").permitAll()

                // Student access
                .requestMatchers("/student/**").hasRole("STUDENT")

                // Staff access
                .requestMatchers("/staff/**").hasAnyRole("STAFF", "ADMIN")

                // Admin access
                .requestMatchers("/admin/**").hasRole("ADMIN")

                // Existing APIs - keep open for Postman testing for now
                .requestMatchers("/api/**").permitAll()

                // Dashboard and ticket pages
                .requestMatchers("/dashboard", "/tickets/**")
                    .hasAnyRole("STUDENT", "STAFF", "ADMIN")

                // Everything else requires login
                .anyRequest().authenticated()
            )

            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )

            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            );

        return http.build();
    }
}
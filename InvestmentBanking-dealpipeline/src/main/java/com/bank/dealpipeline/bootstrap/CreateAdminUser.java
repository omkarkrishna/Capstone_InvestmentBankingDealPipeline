package com.bank.dealpipeline.bootstrap;

import com.bank.dealpipeline.model.Role;
import com.bank.dealpipeline.model.User;
import com.bank.dealpipeline.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CreateAdminUser {

    @Bean
    CommandLineRunner insertAdminUser(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {

            User user = new User();
            user.setUsername("admin2");
            user.setEmail("admin2@bank.com");
            user.setPassword(passwordEncoder.encode("admin2"));
            user.setRole(Role.ADMIN);   // ✅ FIX HERE
            user.setActive(true);

            userRepository.save(user);

            System.out.println("ADMIN2 USER INSERTED");
        };
    }
}

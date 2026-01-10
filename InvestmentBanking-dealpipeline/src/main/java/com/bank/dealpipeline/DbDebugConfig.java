/*package com.bank.dealpipeline;

import com.bank.dealpipeline.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class DbDebugConfig {

    @Bean
    CommandLineRunner debugDb(UserRepository userRepository) {
        return args -> {
            System.out.println("---- USERS FROM SPRING BOOT ----");
            userRepository.findAll().forEach(System.out::println);
            System.out.println("--------------------------------");
        };
    }

    @Bean
    CommandLineRunner mongoTest(MongoTemplate mongoTemplate) {
        return args -> {
            System.out.println("Mongo DB name = " + mongoTemplate.getDb().getName());
        };
    }

}
*/
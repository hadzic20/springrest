package com.example.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {
        return args -> log.info("Preloading " + repository.save(new myUser("Amilla", "Hadziç", "amillahadzic@gmail.com", 1, 0, "5393497123", "itü", "öğrenci", "I want to",  "Arilar172'", "Arilar172'")) + repository.save(new myUser("Ayşe", "Kurt", "ayse.kurt@boun.edu.tr", 1, 0, "5393497123", "boun", "eğitmen", "responsibilities", "Arilar172'",  "Arilar172'")));
    }
}

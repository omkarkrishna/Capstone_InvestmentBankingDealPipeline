package com.bank.dealpipeline;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class InvestmentBankingDealpipelineApplication {
	public static void main(String[] args) {
		SpringApplication.run(InvestmentBankingDealpipelineApplication.class, args);
        System.out.println("Spring Boot is at port 8080");
	}

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void printHash() {
        System.out.println(
                passwordEncoder.encode("admin123")
        );
    }


}




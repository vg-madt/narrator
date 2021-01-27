package com.narrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories
@EnableTransactionManagement
public class NarratorApplication {

    public static void main(final String[] args) {
        SpringApplication.run(NarratorApplication.class, args);
    }

}

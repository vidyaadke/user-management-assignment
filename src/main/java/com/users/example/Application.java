package com.users.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@SpringBootApplication public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    //Added logging before application releases all resources
    @PreDestroy public void preDestroy() {
        log.info("Application User Management stopped.");
    }

    //Added logging after application start
    @PostConstruct public void postConstruct() {
        log.info("Application User Management started.");
    }
}

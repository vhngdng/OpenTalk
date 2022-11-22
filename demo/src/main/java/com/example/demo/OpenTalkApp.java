package com.example.demo;

import com.github.javafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(
        scanBasePackages = "com.example.demo")
//        exclude = {
////                SecurityAutoConfiguration.class,
//                UserDetailsServiceAutoConfiguration.class})

//@EnableConfigurationProperties
public class OpenTalkApp {

    public static void main(String[] args) {
        SpringApplication.run(OpenTalkApp.class, args);
    }

    @Bean
    public Faker faker () {
        return new Faker();
    }

//    @Bean
//    public AuditorAware<Employee> auditorProvider() {
//        return new AuditorAwareImpl();
//    }

}



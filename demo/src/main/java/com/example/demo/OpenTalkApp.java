package com.example.demo;

import com.github.javafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties
// @EnableTransactionManagement   -> spring boot automatically add
public class OpenTalkApp {

  public static void main(String[] args) {
    SpringApplication.run(OpenTalkApp.class, args);
  }

  @Bean
  public Faker faker() {
    return new Faker();
  }


}



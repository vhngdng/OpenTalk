package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:mail/emailconfig.properties")
//@EnableAutoConfiguration
public class EmailConfig {

  private ApplicationContext applicationContext;
  private Environment environment;
  @Value("${mail.port}")
  private int GMAIL_SMTP_PORT;

  @Value("${mail.host}")
  private String host;

  @Value("${mail.username}")
  private String user;

  @Value("${mail.password}")
  private String password;

  @Value("${mail.debug}")
  private Boolean debug;

  @Bean
  public JavaMailSender getJavaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(host);
    mailSender.setPort(GMAIL_SMTP_PORT);
    mailSender.setUsername(user);
    mailSender.setPassword(password);

    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.debug", "true");
    return mailSender;
  }
}

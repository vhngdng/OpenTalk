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

//    private static final String JAVA_MAIL_FILE = "classpath:mail/javamail.properties";

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


//    @Bean
//    public JavaMailSender mailSender() throws IOException {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        //Basic mail sender configuration, based on emailconfig.properties
//        mailSender.setHost(host);
//        mailSender.setPort(GMAIL_SMTP_PORT);
//
//        // Set up email config
//        mailSender.setUsername(user);
//        mailSender.setPassword(password);
////
//        mailSender.setJavaMailProperties(javaMailProperties());
////        #spring.mail.properties.mail.smtp.ssl.enable=true
//
//        return mailSender;
//    }
//
//    @Bean
//    public Properties javaMailProperties() throws IOException
//    {
//        Properties props = new Properties();
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        props.put("mail.properties.mail.smtp.ssl.enable", "true");
//        props.put("mail.debug", debug);
//
//        return props;
//
//    }
//
////    @Primary
////    @Bean
////    public FreeMarkerConfigurationFactoryBean factoryBean() {
////        FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
////        bean.setTemplateLoaderPath("classpath:/templates");
////        return bean;
////    }
//
//    @Bean
//    public Properties factoryBean() throws IOException {
//        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
//        propertiesFactoryBean.setLocation(new ClassPathResource("mail/emailconfig.properties"));
//        propertiesFactoryBean.afterPropertiesSet();
//        return propertiesFactoryBean.getObject();
//    }

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

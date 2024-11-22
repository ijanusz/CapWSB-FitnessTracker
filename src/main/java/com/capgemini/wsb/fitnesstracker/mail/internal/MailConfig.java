package com.capgemini.wsb.fitnesstracker.mail.internal;

import com.capgemini.wsb.fitnesstracker.mail.api.EmailSender;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
@EnableConfigurationProperties(MailProperties.class)
class MailConfig {

    @Bean
    public EmailSender emailSender(JavaMailSender javaMailSender, MailProperties mailProperties) {
        return new EmailSenderImpl(javaMailSender, mailProperties);
    }
}

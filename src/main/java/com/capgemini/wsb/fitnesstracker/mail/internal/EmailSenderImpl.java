package com.capgemini.wsb.fitnesstracker.mail.internal;

import com.capgemini.wsb.fitnesstracker.mail.api.EmailSender;
import com.capgemini.wsb.fitnesstracker.mail.api.dto.EmailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@RequiredArgsConstructor
class EmailSenderImpl implements EmailSender {

    private final JavaMailSender mailSender;
    private final MailProperties mailProperties;

    @Override
    public void send(EmailDto email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailProperties.getFrom());
        message.setTo(email.toAddress());
        message.setSubject(email.subject());
        message.setText(email.content());
        mailSender.send(message);
    }
}

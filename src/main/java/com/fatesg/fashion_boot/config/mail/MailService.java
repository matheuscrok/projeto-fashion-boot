package com.fatesg.fashion_boot.config.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
@RequiredArgsConstructor
public class MailService {

    @Autowired
    private  JavaMailSender mailSender;

    public void sendEmail(String email, String titulo, String text) throws MessagingException {
        try {
            MimeMessage mail = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mail);
            helper.setTo(email);
            helper.setSubject(titulo);
            helper.setText(text, true);
            mailSender.send(mail);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

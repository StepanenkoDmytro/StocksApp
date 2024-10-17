package com.stock.mail;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;

@Service
public class MailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public MailService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public void sendRecoveryCode(String email, String recoveryCode) {
        MimeMessage mimeMessage  = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper  =  new  MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("grouppegazzo@gmail.com");
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("Money History Team");

            Context context = new Context();

            context.setVariable( "content" , recoveryCode);
            String  processingString = templateEngine.process( "email-reset-password" , context);

            mimeMessageHelper.setText(processingString, true );

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}

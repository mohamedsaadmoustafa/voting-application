package com.example.votingapplication.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


@Component
public class MailHelper {
    @Value("${spring.mail.userEmail}")
    private String senderEmail;

    @Value("${spring.mail.userName}")
    private String senderName;

    @Autowired private JavaMailSender javaMailSender;


    public String sendEmail(String to, String subject, String text)
    {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(senderEmail);
            mailMessage.setTo(to);
            mailMessage.setText(text);
            mailMessage.setSubject(subject);
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }
}
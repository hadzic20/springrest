package com.example.login;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") private String sender;

    public void sendSimpleMail(String email)
    {
        try {
            MimeMessage mailMessage = javaMailSender.createMimeMessage();
            String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz!'+%&/()=?*_-";
            StringBuilder sb = new StringBuilder(10);
            for (int i = 0; i < 10; i++) {
                int index = (int)(AlphaNumericString.length() * Math.random());
                sb.append(AlphaNumericString.charAt(index));
            }
            List<myUser> liste = repository.findAll();
            for (com.example.login.myUser myuser : liste) {
                if (myuser.getEmail().equals(email)) {
                    myUser chosenone = repository.getReferenceById(myuser.getId());
                    chosenone.setPassword(new BCryptPasswordEncoder().encode(sb));
                    repository.save(chosenone);
                }
            }
            mailMessage.setFrom(sender);
            mailMessage.setRecipients(MimeMessage.RecipientType.TO, email);
            mailMessage.setSubject("Şifreni mi unuttun?");
            String htmlContent = "<h4>Yeni şifren</h4>" + sb;
            mailMessage.setContent(htmlContent, "text/html; charset=utf-8");
            javaMailSender.send(mailMessage);
        }

        catch (Exception e) {
            throw new RuntimeException();
        }
    }
}

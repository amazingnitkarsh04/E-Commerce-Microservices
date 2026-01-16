package com.example.EmailService.Consumers;


import com.example.EmailService.Dtos.SendEmailMessageDto;
import com.example.EmailService.Utils.EmailUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import javax.mail.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.util.Properties;

@Service
public class SendEmailConsumer {

    @Autowired
    private ObjectMapper objectMapper;


    @KafkaListener(topics="sendEmail", groupId = "emailService")
    public void handleSendEmail(String message) {
        try {
            SendEmailMessageDto sendEmailMessageDto = objectMapper.readValue(message, SendEmailMessageDto.class);
//
//            Properties props = new Properties();
//            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
//            props.put("mail.smtp.port", "587"); //TLS Port
//            props.put("mail.smtp.auth", "true"); //enable authentication
//            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

            Properties props = new Properties();
            props.put("mail.smtp.host", "sandbox.smtp.mailtrap.io"); // Mailtrap SMTP Host
            props.put("mail.smtp.port", "587"); // TLS port (can also use 25, 465, or 2525)
            props.put("mail.smtp.auth", "true"); // enable authentication
            props.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS


            //create Authenticator object to pass in Session.getInstance argument
            Authenticator auth = new Authenticator() {
                //override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("dcf498bf456644", "b944f59327adb6");
                }
            };
            Session session = Session.getInstance(props, auth);

            EmailUtils.sendEmail(session, sendEmailMessageDto.getTo(), sendEmailMessageDto.getSubject(), sendEmailMessageDto.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

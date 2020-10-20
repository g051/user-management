package com.user.mngmnt.service;

import com.user.mngmnt.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements
        ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private UserService service;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(user, token);

        String recipient = user.getEmail();
        String subject = "Registration/Reset password Confirmation";
        String confirmationUrl
                = event.getAppUrl() + "/registrationConfirm?token=" + token;
        String body = "Click below link to activate your account and set password:\r\n" + "http://localhost:8080" + confirmationUrl;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipient);
        email.setSubject(subject);
        email.setText(body);
        System.out.println("RegistrationListener.confirmRegistration() to send email:\n\tTo: "
                +recipient+"\n\tsubject: "+subject+"\n\tBody: "+body);
        try {
            mailSender.send(email);
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

package prueba.emailservice.service;

import jakarta.mail.MessagingException;
import prueba.emailservice.dto.EmailDTO;

public interface EmailService {
    void sendEmail(EmailDTO email) throws MessagingException;
    void sendEmailNewUser(EmailDTO email) throws MessagingException;
    void sendVerificationEmail(EmailDTO email) throws MessagingException;
}
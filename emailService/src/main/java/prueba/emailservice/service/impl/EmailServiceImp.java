package prueba.emailservice.service.impl;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import prueba.emailservice.dto.EmailDTO;
import prueba.emailservice.service.EmailService;

@Service
@Slf4j
public class EmailServiceImp implements EmailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final EurekaClient eurekaClient;

    @Value("${email.service.url}")
    private String emailServiceUrlValue;

    public EmailServiceImp(JavaMailSender javaMailSender, TemplateEngine templateEngine, EurekaClient eurekaClient) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.eurekaClient = eurekaClient;
    }

    @Override
    public void sendEmail(EmailDTO email) throws MessagingException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(email.getDestinatario());
            helper.setSubject(email.getAsunto());

            Context context = new Context();
            context.setVariable("mensaje", email.getMensaje());
            String contentHTML = templateEngine.process("email", context);

            helper.setText(contentHTML, true);
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar correo: " + e.getMessage(), e);
        }
    }

    @Override
    public void sendEmailNewUser(EmailDTO email) throws MessagingException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(email.getDestinatario());
            helper.setSubject(email.getAsunto());

            Context context = new Context();
            context.setVariable("mensaje", email.getMensaje());
            context.setVariable("code", email.getMensaje());
            String contentHTML = templateEngine.process("usercreado", context);

            helper.setText(contentHTML, true);
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar correo: " + e.getMessage(), e);
        }
    }

    @Override
    public void sendVerificationEmail(EmailDTO email) throws MessagingException {
        try {
            log.info("Mensaje recibido de Kafka para verificación de cuenta:");
            log.info("Destinatario: {}", email.getDestinatario());
            log.info("Mensaje/Token: {}", email.getMensaje());
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email.getDestinatario());
            helper.setSubject("Verificacion de dos pasos");

            Context context = new Context();
            context.setVariable("token", email.getMensaje());

            String contentHTML = templateEngine.process("verification-email", context);

            helper.setText(contentHTML, true);

            javaMailSender.send(message);

            log.info("Correo de verificación en dos pasos enviado exitosamente a: {}", email.getDestinatario());

        } catch (Exception e) {
            log.error("Error al enviar correo de verificación", e);
            throw new MessagingException("Error al enviar correo de verificación: " + e.getMessage(), e);
        }
    }
}

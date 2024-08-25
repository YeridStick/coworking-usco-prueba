package prueba.emailservice.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import prueba.emailservice.dto.EmailDTO;
import prueba.emailservice.service.EmailService;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaEmailListener {

    private final EmailService emailService;

    @KafkaListener(topics = "new-user", groupId = "email-group")
    public void listen(EmailDTO emailDTO, Acknowledgment acknowledgment) {
        log.info("Recibido mensaje de Kafka para: {}", emailDTO.getDestinatario());
        try {
            emailService.sendVerificationEmail(emailDTO);
            log.info("Email de verificación enviado exitosamente a: {}", emailDTO.getDestinatario());
            log.info("Email de verificación", emailDTO.getMensaje());
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("Error al procesar mensaje de Kafka para: {}", emailDTO.getDestinatario(), e);
            // Aquí puedes implementar una lógica de reintento o manejo de errores
            // Por ejemplo, podrías volver a poner el mensaje en la cola o enviarlo a una cola de "deadletter"
        }
    }
}
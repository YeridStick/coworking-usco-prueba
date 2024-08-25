package prueba.emailservice.controller;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prueba.emailservice.client.VerificarUser;
import prueba.emailservice.dto.EmailDTO;
import prueba.emailservice.service.EmailService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/email")
@AllArgsConstructor
public class EmailServiceController {
    private final EmailService Email;
    private final VerificarUser verificarUser;

    @PostMapping("/newUser")
    public void sendEmailNewUser(@RequestBody EmailDTO emailDTO) throws MessagingException {
        Email.sendEmailNewUser(emailDTO);
    }

    @PostMapping("/vienvenida")
    public void enviarCorreo(@RequestBody EmailDTO emailDTO) throws MessagingException {
        Email.sendEmail(emailDTO);
    }

    @GetMapping("/verify/{token}")
    public ResponseEntity<Map<String, Boolean>> verifyUser(@PathVariable String token) {
        Map<String, Boolean> response = new HashMap<>();
        try {
            boolean isVerified = verificarUser.verifyUser(token).getBody();
            response.put("isVerified", isVerified);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("isVerified", false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
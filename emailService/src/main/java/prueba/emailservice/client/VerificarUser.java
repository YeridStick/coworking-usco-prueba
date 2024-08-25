package prueba.emailservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-microService")
public interface VerificarUser {
    @GetMapping("/api/user/verify/{token}")
    ResponseEntity<Boolean> verifyUser(@PathVariable("token") String token);
}

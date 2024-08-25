package prueba.authservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import prueba.authservice.dto.userDto.UserAuthDto;

@FeignClient(name = "userService")
public interface UserClient {
    @GetMapping("/api/user/email/{email}")
    UserAuthDto getUserByEmail(@PathVariable String email);

    @PutMapping("/api/user/update-two-factor-code")
    void updateUserTwoFactorCode(@RequestParam String email, @RequestParam String twoFactorCode);

    @PutMapping("/api/user/update-token")
    void updateUserToken(@RequestParam String email, @RequestParam String token);
}

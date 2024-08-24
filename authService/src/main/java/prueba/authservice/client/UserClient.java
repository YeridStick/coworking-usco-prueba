package prueba.authservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import prueba.authservice.dto.userDto.UserAuthDto;

@FeignClient(name = "userService")
public interface UserClient {
    @GetMapping("/api/user/email/{email}")
    UserAuthDto getUserByEmail(@PathVariable String email);
}

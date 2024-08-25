package prueba.reservaservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import prueba.reservaservice.dto.UserAuthDto;
import prueba.reservaservice.dto.user.UserDto;

@FeignClient(name = "userService")
public interface UserServiceClient {

    @GetMapping("api/user/{id}")
    String getUserById(@PathVariable String id);

    @GetMapping("api/user/email/{email}")
    ResponseEntity<UserAuthDto> getByEmail(@PathVariable String email);

    @GetMapping("/api/role/id/{idRol}")
    ResponseEntity<String> createUser(@PathVariable String idRol);
}

package prueba.apigateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthCheckController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/health")
    public Mono<ResponseEntity<Map<String, String>>> healthCheck() {
        return Mono.fromCallable(() -> {
            Map<String, String> status = new HashMap<>();
            status.put("status", "UP");

            discoveryClient.getServices().forEach(service ->
                    status.put(service, "UP")
            );

            return ResponseEntity.ok(status);
        });
    }
}
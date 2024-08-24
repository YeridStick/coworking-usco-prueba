package prueba.reservaservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Configuración del broker de mensajes en memoria
        // "/topic" es el prefijo que se usa para los mensajes que son enviados desde el servidor a los clientes
        config.enableSimpleBroker("/topic");

        // Prefijo que indica que el mensaje debe ser procesado por algún método @MessageMapping
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Definir el endpoint donde los clientes se conectarán
        // "/ws" es la URL que los clientes utilizarán para conectarse al WebSocket
        registry.addEndpoint("/ws")
                .setAllowedOrigins("*") // Permitir CORS desde cualquier origen
                .withSockJS(); // Habilitar SockJS como fallback para navegadores que no soporten WebSockets
    }
}
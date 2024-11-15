package prueba.balanceadorcarga.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import prueba.balanceadorcarga.client.UserServiceClient;
import prueba.balanceadorcarga.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserServiceClient userServiceClient;

    public SecurityConfig(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                // Rutas protegidas para ADMIN
                                .requestMatchers(
                                        "/api/balanceador/user/update-two-factor-code",
                                        "/api/balanceador/user/update-token",
                                        "/api/reservas/recursos/{id}/estado/{nuevoEstado}",
                                        "/api/reservas/tipos-recurso/{id}/categoria"
                                ).hasRole("ADMIN")

                                // Cualquier otra ruta bajo /api/** es pública
                                .requestMatchers("/api/**").permitAll()
                                .anyRequest().permitAll()
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(userServiceClient);
    }
}

package nutrivida.servicio_reserva.config;

import lombok.RequiredArgsConstructor;
import nutrivida.servicio_reserva.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/servicios").hasAuthority("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.PUT,"/api/servicios").hasAuthority("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.DELETE,"/api/servicios").hasAuthority("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.GET,"/api/servicios").permitAll()

                        .requestMatchers(HttpMethod.PUT,"/api/reservas/secretarios/").hasAuthority("SECRETARIO")
                        .requestMatchers(HttpMethod.PATCH, "/api/reservas/secretarios/cancelar/").hasAuthority("SECRETARIO")
                        .requestMatchers(HttpMethod.POST, "/api/reservas").hasAnyAuthority("CLIENTE", "SECRETARIO")
                        .requestMatchers(HttpMethod.PUT, "/api/reservas/clientes/").hasAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.PATCH, "/api/reservas/clientes/canelar/").hasAuthority("CLIENTE")
                        .requestMatchers(HttpMethod.GET, "/api/reservas/clientes").hasAuthority("CLIENTE")

                        //.requestMatchers("/api/reservas/**").hasAnyAuthority("CLIENTE", "SECRETARIO")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
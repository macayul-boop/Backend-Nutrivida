package nutrivida.servicio_usuario.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Desactiva la protección CSRF (necesario para probar POST, PUT, DELETE desde Postman)
                .csrf(csrf -> csrf.disable())
                // Permite todas las peticiones sin autenticación
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )
                // Desactiva el formulario de login predeterminado
                .formLogin(form -> form.disable())
                // Desactiva la autenticación HTTP Basic
                .httpBasic(basic -> basic.disable());

        return http.build();
    }
}

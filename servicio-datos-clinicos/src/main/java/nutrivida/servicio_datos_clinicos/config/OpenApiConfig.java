package nutrivida.servicio_datos_clinicos.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        servers = {
                @Server(url = "/", description = "Servidor por defecto (Gateway)")
        }
)
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info()
                .title("Microservicio datos clinicos")
                .version("1.0.0")
                .description("Microservicio para gestión los datos clinicos de los clientes del sistema nutrivida"));
    }

}

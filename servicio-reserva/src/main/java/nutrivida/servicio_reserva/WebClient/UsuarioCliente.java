package nutrivida.servicio_reserva.WebClient;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import nutrivida.servicio_reserva.dto.ClienteSimplificadoDto;
import nutrivida.servicio_reserva.dto.NutricionistaSimplificadoDto;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class UsuarioCliente {

    private final WebClient webClient;

    private String obtenerToken() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String bearerToken = request.getHeader("Authorization");

            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                return bearerToken.substring(7);
            }
        }

        throw new IllegalStateException("No se pudo obtener el token de la petición actual");
    }

    // VERIFICAR SI UN USUARIO EXISTE (CLIENTE Y NUTRICIONISTA)
    public Boolean verificarUsuario(Integer idUsuario){

        String token = obtenerToken();

        return webClient.get()
                .uri("http://localhost:8081/api/usuarios/verificar/{idUsuario}", idUsuario)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    }

    // TRAER INFORMACION SIMPLIFICADA DE UN CLIENTE
    public ClienteSimplificadoDto leerUnClienteSimplificado(Integer idCliente){

        String token = obtenerToken();

        return webClient.get()
                .uri("http://localhost:8081/api/usuarios/clienteSimplificado/{idCliente}", idCliente)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(ClienteSimplificadoDto.class)
                .block();
    }

    // TRAER INFORMACION SIMPLIFICADA DE UN EMPLEADO
    public NutricionistaSimplificadoDto leerUnNutricionistaSimplificado(Integer idEmpleado){

        String token = obtenerToken();

        return webClient.get()
                .uri("http://localhost:8081/api/usuarios/nutricionistaSimplificado/{idEmpleado}", idEmpleado)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(NutricionistaSimplificadoDto.class)
                .block();
    }

}

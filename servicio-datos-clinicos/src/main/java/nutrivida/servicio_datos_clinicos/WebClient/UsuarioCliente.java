package nutrivida.servicio_datos_clinicos.WebClient;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import nutrivida.servicio_datos_clinicos.dto.PacienteDto;
import nutrivida.servicio_datos_clinicos.exception.ForbiddenException;
import nutrivida.servicio_datos_clinicos.exception.NotFoundException;
import nutrivida.servicio_datos_clinicos.exception.ServicioNoDisponibleException;
import nutrivida.servicio_datos_clinicos.exception.UnauthorizedException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class UsuarioCliente {

    private final WebClient.Builder webClientBuilder;

    private String obtenerToken(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if(attributes != null){
            HttpServletRequest request = attributes.getRequest();
            String bearerToken = request.getHeader("Authorization");

            if(bearerToken != null && bearerToken.startsWith("Bearer ")){
                return bearerToken.substring(7);
            }
        }

        throw new UnauthorizedException("No se pudo obtener el token de la petición actual");
    }

    // BUSCAR PACIENTE POR RUT
    public PacienteDto buscarPorRut(String rut){
        String token = obtenerToken();

        WebClient.RequestHeadersSpec<?> peticion = webClientBuilder.build().get()
                .uri("http://servicio-usuario/api/usuarios/pacientes?rut={rut}", rut)
                .header("Authorization", "Bearer " + token);

        return consultarPaciente(peticion);
    }

    // BUSCAR PACIENTE POR ID
    public PacienteDto buscarPorId(Integer idCliente){
        String token = obtenerToken();

        WebClient.RequestHeadersSpec<?> peticion = webClientBuilder.build().get()
                .uri("http://servicio-usuario/api/usuarios/pacientes/{idCliente}", idCliente)
                .header("Authorization", "Bearer " + token);

        return consultarPaciente(peticion);
    }

    private PacienteDto consultarPaciente(WebClient.RequestHeadersSpec<?> peticion){
        try {
            PacienteDto paciente = peticion.retrieve()
                    .bodyToMono(PacienteDto.class)
                    .block(Duration.ofSeconds(5));

            if(paciente == null || paciente.getId() == null){
                throw new ServicioNoDisponibleException("El servicio de usuarios no devolvió un paciente");
            }

            return paciente;
        } catch (WebClientResponseException ex) {
            if(ex.getStatusCode().value() == 404){
                throw new NotFoundException("Paciente no encontrado");
            }
            if(ex.getStatusCode().value() == 401){
                throw new UnauthorizedException("El servicio de usuarios rechazó la autenticación");
            }
            if(ex.getStatusCode().value() == 403){
                throw new ForbiddenException("No tienes permiso para consultar este paciente");
            }
            throw new ServicioNoDisponibleException("No se pudo consultar el servicio de usuarios");
        } catch (WebClientException | IllegalStateException ex) {
            throw new ServicioNoDisponibleException("No se pudo consultar el servicio de usuarios");
        }
    }
}
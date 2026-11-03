package nutrivida.servicio_usuario.WebClient;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import nutrivida.servicio_usuario.dto.ReservaResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaClient {

    private final WebClient.Builder webClientBuilder;

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

    public Void eliminarReserva(Integer idUsuario){
        String token = obtenerToken();

        return webClientBuilder.build().delete()
                .uri("http://servicio-reserva/api/reservas/{idUsuario}", idUsuario)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public List<ReservaResponse> obtenerTodasReservasDeUnPaciente(Integer idCliente){
        String token = obtenerToken();

        return webClientBuilder.build().get()
                .uri("http://servicio-reserva/api/reservas/secretarios/{idCliente}", idCliente)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(List.class)
                .block();
    }

}

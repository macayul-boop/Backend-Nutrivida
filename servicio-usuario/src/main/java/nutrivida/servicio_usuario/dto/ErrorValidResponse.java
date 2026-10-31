package nutrivida.servicio_usuario.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorValidResponse {
    private LocalDateTime fecha;
    private int estado;
    private String error;
    private String ruta;
    private Map<String, String> errores;
}
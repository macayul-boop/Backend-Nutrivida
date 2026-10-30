package nutrivida.servicio_usuario.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Builder
public class TokenResponse {
    private String token;
}

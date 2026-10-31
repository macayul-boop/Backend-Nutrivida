package nutrivida.servicio_usuario.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NutricionistaSimplificadoResponse {
    private Integer id;
    private String nombre;
    private List<HorarioNutricionistaResponse> horarioNutricionista;
}

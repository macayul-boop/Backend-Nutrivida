package nutrivida.servicio_datos_clinicos.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlanAlimentacionResponse {

    private String planAlimentacion;
}
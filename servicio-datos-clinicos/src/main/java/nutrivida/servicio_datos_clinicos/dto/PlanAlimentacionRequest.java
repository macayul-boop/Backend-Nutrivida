package nutrivida.servicio_datos_clinicos.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PlanAlimentacionRequest {

    @NotNull(message = "El plan de alimentación es obligatorio")
    @Size(max = 4000, message = "El plan de alimentación no debe superar los 4000 caracteres")
    private String planAlimentacion;
}
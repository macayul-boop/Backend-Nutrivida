package nutrivida.servicio_usuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HorarioNutricionistaRequest {

    @NotBlank(message = "El atributo dia semana es obligatorio")
    private String diaSemana;

    @NotNull(message = "El atributo hora entrada es obligatorio")
    private LocalTime horaEntrada;

    @NotNull(message = "El atributo hora salida es obligatorio")
    private LocalTime horaSalida;

}

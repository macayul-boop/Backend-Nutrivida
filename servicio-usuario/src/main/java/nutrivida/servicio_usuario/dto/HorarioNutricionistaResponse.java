package nutrivida.servicio_usuario.dto;

import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HorarioNutricionistaResponse {

    private Integer id;
    private String diaSemana;
    private LocalTime horaEntrada;
    private LocalTime horaSalida;
}

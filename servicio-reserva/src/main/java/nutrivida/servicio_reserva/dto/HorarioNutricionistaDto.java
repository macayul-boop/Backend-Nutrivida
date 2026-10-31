package nutrivida.servicio_reserva.dto;

import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HorarioNutricionistaDto {

    private Integer id;
    private String diaSemana;
    private LocalTime horaEntrada;
    private LocalTime horaSalida;
}

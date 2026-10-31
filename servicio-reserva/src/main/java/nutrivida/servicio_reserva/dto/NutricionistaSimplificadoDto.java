package nutrivida.servicio_reserva.dto;

import lombok.Data;

import java.util.List;

@Data
public class NutricionistaSimplificadoDto {
    private Integer id;
    private String nombre;
    private List<HorarioNutricionistaDto> horarioNutricionista;
}

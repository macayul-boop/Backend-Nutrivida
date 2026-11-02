package nutrivida.servicio_reserva.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
public class HoraRequestDto {
    @NotNull(message = "El atirbuto fecha es obligatorio")
    private LocalDate fecha;

    @NotNull(message = "El atirbuto id nutricionista es obligatorio")
    private Integer idNutricionista;

    @NotNull(message = "El atributo id servicio es obligatorio")
    private Integer idServicio;

    public HoraRequestDto(Integer idServicio, Integer idNutricionista, LocalDate fecha) {
        this.idServicio = idServicio;
        this.idNutricionista = idNutricionista;
        this.fecha = fecha;
    }
}

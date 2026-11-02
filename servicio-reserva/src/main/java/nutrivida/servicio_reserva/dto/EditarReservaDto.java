package nutrivida.servicio_reserva.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class EditarReservaDto {

    @NotNull(message = "El atributo fecha es obligatorio")
    private LocalDate fecha;

    @NotNull(message = "El atributo hora inicio es obligatorio")
    private LocalTime horaInicio;
}

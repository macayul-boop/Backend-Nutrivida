package nutrivida.servicio_reserva.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import nutrivida.servicio_reserva.model.EstadoReserva;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ReservaRequest {

    @NotNull(message = "El atributo id nutricionista es obligatorio")
    private Integer idNutricionista;

    @NotNull(message = "El atributo id clientes es obligatorio")
    private Integer idCliente;

    @NotNull(message = "El atributo id servicio es obligatorio")
    private Integer idServicio;

    @NotNull(message = "El atributo fecha es obligatorio")
    private LocalDate fecha;

    @NotNull(message = "El atributo hora inicio es obligatorio")
    private LocalTime horaInicio;
}

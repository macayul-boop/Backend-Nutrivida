package nutrivida.servicio_reserva.dto;

import lombok.Builder;
import lombok.Data;
import nutrivida.servicio_reserva.model.EstadoReserva;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class ReservaResponse {
    private Integer id;
    private Integer idNutricionista;
    private Integer idCliente;
    private Integer idServicio;
    private EstadoReserva estado;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaTermino;
    private ClienteSimplificadoDto datosClientes;
    private NutricionistaSimplificadoDto datosNutricionista;
    private String nombreServicio;
}

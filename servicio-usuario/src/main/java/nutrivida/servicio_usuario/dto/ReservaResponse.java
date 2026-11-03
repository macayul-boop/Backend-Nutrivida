package nutrivida.servicio_usuario.dto;

import lombok.Builder;
import lombok.Data;
import nutrivida.servicio_usuario.model.EstadoReserva;

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
    private ClienteSimplificadoResponse datosClientes;
    private NutricionistaSimplificadoResponse datosNutricionista;
    private String nombreServicio;
}

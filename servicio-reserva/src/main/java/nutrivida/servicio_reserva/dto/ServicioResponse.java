package nutrivida.servicio_reserva.dto;

import lombok.Builder;
import lombok.Data;
import nutrivida.servicio_reserva.model.Modalidad;

@Data
@Builder
public class ServicioResponse {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer precio;
    private Integer duracionMinutos;
    private Modalidad modalidad;
}

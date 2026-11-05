package nutrivida.servicio_usuario.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PacienteResponse {

    private Integer id;
    private String rut;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;
}
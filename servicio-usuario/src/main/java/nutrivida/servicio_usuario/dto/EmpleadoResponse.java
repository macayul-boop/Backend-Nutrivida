package nutrivida.servicio_usuario.dto;

import lombok.*;
import nutrivida.servicio_usuario.model.Estado;
import nutrivida.servicio_usuario.model.Rol;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpleadoResponse {

    private Integer id;
    private String rut;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;
    private String contrasena;
    private Rol rol;
    private Estado estado;
    private List<HorarioNutricionistaResponse> horarioNutricionista;

}

package nutrivida.servicio_usuario.dto;

import lombok.Builder;
import nutrivida.servicio_usuario.model.Rol;

@Builder
public class UsuarioPrueba {
    private Integer id;
    private String nombre;
    private Rol rol;
}

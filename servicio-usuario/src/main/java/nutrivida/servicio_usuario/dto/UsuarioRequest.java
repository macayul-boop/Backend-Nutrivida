package nutrivida.servicio_usuario.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nutrivida.servicio_usuario.model.Estado;
import nutrivida.servicio_usuario.model.Rol;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequest {

    @NotBlank(message = "El atributo rut es obligatorio")
    private String rut;

    @NotBlank(message = "El atributo nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El atributo apellidos es obligatorio")
    private String apellidos;

    private String telefono;

    @NotBlank(message = "El atributo email es obligatorio")
    private String email;

    @NotBlank(message = "El atributo contrasena es obligatorio")
    private String contrasena;

    @NotNull(message = "El atributo rol es obligatorio")
    private Rol rol;

    @NotNull(message = "El atributo estado es obligatorio")
    private Estado estado;

    private List<HorarioNutricionistaRequest> horarioNutricionista;

    @AssertTrue(message = "Para los usuario de tipo nutricionista es obligatorio una lista de horario")
    public boolean isValidNutricionista(){
        if(rol == Rol.NUTRICIONISTA){
            return horarioNutricionista != null;
        }
        return true;
    }


}

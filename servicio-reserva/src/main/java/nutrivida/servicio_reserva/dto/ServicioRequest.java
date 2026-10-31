package nutrivida.servicio_reserva.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nutrivida.servicio_reserva.model.Modalidad;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicioRequest {

    @NotBlank(message = "El atributo nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El atributo descripcion es obligatorio")
    private String descripcion;

    @NotNull(message = "El atributo precio es obligatorio")
    @Min(value = 1, message = "El precio debe ser mayor a 0")
    private Integer precio;

    @NotNull(message = "El atributo duracion minutos es obligatorio")
    @Min(value = 1, message = "La duracion debe ser mayor a 0")
    private Integer duracionMinutos;

    @NotNull(message = "El atributo modalidad es obligatorio")
    private Modalidad modalidad;
}

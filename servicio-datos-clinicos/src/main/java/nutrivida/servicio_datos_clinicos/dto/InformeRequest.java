package nutrivida.servicio_datos_clinicos.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InformeRequest {

    @NotNull(message = "El peso es obligatorio")
    @Positive(message = "El peso debe ser mayor que cero")
    @Digits(integer = 4, fraction = 2)
    private BigDecimal peso;

    @NotNull(message = "La talla es obligatoria")
    @Positive(message = "La talla debe ser mayor que cero")
    @Digits(integer = 4, fraction = 2)
    private BigDecimal talla;

    @NotNull(message = "La circunferencia de cintura es obligatoria")
    @Positive(message = "La circunferencia de cintura debe ser mayor que cero")
    @Digits(integer = 4, fraction = 2)
    private BigDecimal circunferenciaCintura;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 10, max = 4000, message = "La descripción debe tener entre 10 y 4000 caracteres")
    private String descripcion;

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion == null ? null : descripcion.trim();
    }
}
package nutrivida.servicio_datos_clinicos.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class InformeResponse {

    private Integer id;
    private Integer idCliente;
    private Integer idNutricionista;
    private LocalDate fecha;
    private BigDecimal peso;
    private BigDecimal talla;
    private BigDecimal circunferenciaCintura;
    private String descripcion;
}
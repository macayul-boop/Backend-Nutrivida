package nutrivida.servicio_datos_clinicos.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FichaClinicaResponse {

    private PacienteDto paciente;
    private String planAlimentacion;
    private List<InformeResponse> informes;
}
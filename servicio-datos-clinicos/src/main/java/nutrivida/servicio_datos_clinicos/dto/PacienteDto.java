package nutrivida.servicio_datos_clinicos.dto;

import lombok.Data;

@Data
public class PacienteDto {

    private Integer id;
    private String rut;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;
}
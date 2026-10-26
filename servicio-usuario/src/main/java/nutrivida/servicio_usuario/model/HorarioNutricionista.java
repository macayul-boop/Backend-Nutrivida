package nutrivida.servicio_usuario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Table(name = "horario_nutricionista")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HorarioNutricionista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dia_semana", nullable = false)
    private String diaSemana;

    @Column(name = "hora_entrada", nullable = false)
    private LocalTime horaEntrada;

    @Column(name = "hora_salida", nullable = false)
    private LocalTime horaSalida;

    @ManyToOne()
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;


}

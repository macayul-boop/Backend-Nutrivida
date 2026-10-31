package nutrivida.servicio_reserva.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reservas")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_nutricionista")
    private Integer idNutricionista;

    @Column(name = "id_cliente")
    private Integer idCliente;

    @ManyToOne()
    @JoinColumn(name = "id_servicio")
    private Servicio servicio;

    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    private EstadoReserva estado;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "hora_inicio")
    private LocalTime horaInicio;

}

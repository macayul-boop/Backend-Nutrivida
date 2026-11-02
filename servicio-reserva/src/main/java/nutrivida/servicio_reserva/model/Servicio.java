package nutrivida.servicio_reserva.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "servicios")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "precio", nullable = false)
    private Integer precio;

    @Column(name = "duracion_minutos", nullable = false)
    private Integer duracionMinutos;

    @Column(name = "modalidad", nullable = false)
    @Enumerated(EnumType.STRING)
    private Modalidad modalidad;

    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas = new ArrayList<>();

}

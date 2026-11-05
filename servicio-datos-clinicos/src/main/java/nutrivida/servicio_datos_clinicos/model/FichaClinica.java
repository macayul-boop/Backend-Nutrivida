package nutrivida.servicio_datos_clinicos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "fichas_clinicas")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FichaClinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_cliente", nullable = false, unique = true)
    private Integer idCliente;

    @Column(name = "plan_alimentacion", length = 4000)
    private String planAlimentacion;
}
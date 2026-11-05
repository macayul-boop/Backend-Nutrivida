package nutrivida.servicio_datos_clinicos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "informes_clinicos")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Informe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_ficha_clinica", nullable = false)
    private FichaClinica fichaClinica;

    @Column(name = "id_nutricionista", nullable = false)
    private Integer idNutricionista;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal peso;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal talla;

    @Column(name = "circunferencia_cintura", nullable = false, precision = 6, scale = 2)
    private BigDecimal circunferenciaCintura;

    @Column(nullable = false, length = 4000)
    private String descripcion;
}
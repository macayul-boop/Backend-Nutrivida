package nutrivida.servicio_usuario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "rut", nullable = false, unique = true)
    private String rut;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellidos", nullable = false)
    private String apellidos;

    @Column(name = "telefono", nullable = true)
    private String telefono;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "contrasena", nullable = false)
    private String contrasena;

    @Column(name = "rol", nullable = false)
    @Enumerated(EnumType.STRING)
    private Rol rol;

    @Column(name = "estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private Estado estado;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HorarioNutricionista> horarioNutricionista = new ArrayList<>();

    public void setHorarios(List<HorarioNutricionista> nuevosHorarios) {
        this.horarioNutricionista.clear();
        if (nuevosHorarios != null) {
            nuevosHorarios.forEach(this::addHorario);
        }
    }

    public void addHorario(HorarioNutricionista horario) {
        this.horarioNutricionista.add(horario);
        horario.setUsuario(this);
    }
}

package nutrivida.servicio_reserva.repository;

import nutrivida.servicio_reserva.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservaRespository extends JpaRepository<Reserva, Integer> {
    @Query("""
    SELECT COUNT(r) > 0
    FROM Reserva r
    WHERE r.idNutricionista = :nutricionistaId
    AND r.estado = 'RESERVADA'
    AND r.fecha = :fecha
    AND r.horaInicio < :horaFin
    AND r.horaTermino > :horaInicio
    """)
    boolean existeSolapamiento(
            Integer nutricionistaId,
            LocalDate fecha,
            LocalTime horaInicio,
            LocalTime horaFin
    );

    List<Reserva> findAllByIdCliente(Integer idCliente);

    List<Reserva> findAllByIdNutricionistaAndFecha(Integer idNutricionista, LocalDate fecha);
}

package nutrivida.servicio_reserva.repository;

import nutrivida.servicio_reserva.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRespository extends JpaRepository<Reserva, Integer> {
}

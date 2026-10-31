package nutrivida.servicio_reserva.repository;

import nutrivida.servicio_reserva.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicioRepository extends JpaRepository<Servicio, Integer> {
}

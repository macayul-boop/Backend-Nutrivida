package nutrivida.servicio_datos_clinicos.repository;

import nutrivida.servicio_datos_clinicos.model.FichaClinica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FichaClinicaRepository extends JpaRepository<FichaClinica, Integer> {

    Optional<FichaClinica> findByIdCliente(Integer idCliente);
}
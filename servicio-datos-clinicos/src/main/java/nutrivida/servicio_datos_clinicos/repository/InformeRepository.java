package nutrivida.servicio_datos_clinicos.repository;

import nutrivida.servicio_datos_clinicos.model.Informe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InformeRepository extends JpaRepository<Informe, Integer> {

    List<Informe> findAllByFichaClinica_IdClienteOrderByFechaDescIdDesc(Integer idCliente);

    Optional<Informe> findByIdAndFichaClinica_IdCliente(Integer id, Integer idCliente);
}
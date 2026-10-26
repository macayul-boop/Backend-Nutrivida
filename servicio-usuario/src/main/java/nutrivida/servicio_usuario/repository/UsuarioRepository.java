package nutrivida.servicio_usuario.repository;

import nutrivida.servicio_usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

}

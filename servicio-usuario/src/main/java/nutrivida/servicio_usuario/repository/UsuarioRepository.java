package nutrivida.servicio_usuario.repository;

import nutrivida.servicio_usuario.model.Estado;
import nutrivida.servicio_usuario.model.Rol;
import nutrivida.servicio_usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    List<Usuario> findByRolIn(Collection<Rol> roles);
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByRolAndEstado(Rol rol, Estado estado);

}

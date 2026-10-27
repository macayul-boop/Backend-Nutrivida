package nutrivida.servicio_usuario.repository;

import nutrivida.servicio_usuario.model.Rol;
import nutrivida.servicio_usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    List<Usuario> findByRolIn(Collection<Rol> roles);
}

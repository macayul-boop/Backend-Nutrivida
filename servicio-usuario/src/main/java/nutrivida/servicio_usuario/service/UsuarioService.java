package nutrivida.servicio_usuario.service;

import lombok.RequiredArgsConstructor;
import nutrivida.servicio_usuario.dto.*;
import nutrivida.servicio_usuario.model.HorarioNutricionista;
import nutrivida.servicio_usuario.model.Rol;
import nutrivida.servicio_usuario.model.Usuario;
import nutrivida.servicio_usuario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;


    public UsuarioPrueba crearCliente(UsuarioRequest request){

        Usuario usuario = Usuario.builder()
                .rut(request.getRut())
                .nombre(request.getNombre())
                .apellidos(request.getApellidos())
                .telefono(request.getTelefono())
                .email(request.getEmail())
                .contrasena(request.getContrasena())
                .rol(Rol.CLIENTE)
                .estado(request.getEstado())
                .horarioNutricionista(new ArrayList<>())
                .build();


        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        return UsuarioPrueba.builder()
                .id(usuarioGuardado.getId())
                .nombre(usuarioGuardado.getNombre())
                .rol(usuarioGuardado.getRol())
                .build();
    }

    public UsuarioPrueba crearEmpleado(UsuarioRequest request){

        Usuario usuario = Usuario.builder()
                .rut(request.getRut())
                .nombre(request.getNombre())
                .apellidos(request.getApellidos())
                .telefono(request.getTelefono())
                .email(request.getEmail())
                .contrasena(request.getContrasena())
                .rol(request.getRol())
                .estado(request.getEstado())
                .build();

        if (request.getRol() == Rol.NUTRICIONISTA && request.getHorarioNutricionista() != null) {
            List<HorarioNutricionista> horarios = request.getHorarioNutricionista().stream()
                    .map(hDto -> HorarioNutricionista.builder()
                            .diaSemana(hDto.getDiaSemana())
                            .horaEntrada(hDto.getHoraEntrada())
                            .horaSalida(hDto.getHoraSalida())
                            .usuario(usuario)
                            .build())
                    .toList();

            usuario.setHorarioNutricionista(horarios);
        } else {
            usuario.setHorarioNutricionista(new ArrayList<>());
        }

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        return UsuarioPrueba.builder()
                .id(usuarioGuardado.getId())
                .nombre(usuarioGuardado.getNombre())
                .rol(usuarioGuardado.getRol())
                .build();

    }

    public List<UsuarioPrueba> leerUsuarios(){
        List<Usuario> list = usuarioRepository.findAll();

        return list.stream()
                .map(hDto -> UsuarioPrueba.builder()
                        .id(hDto.getId())
                        .nombre(hDto.getNombre())
                        .rol(hDto.getRol())
                        .build())
                .toList();

    }

    public List<EmpleadoResponse> listarEmpleados(){
        List<Rol> rolesBuscados = List.of(Rol.NUTRICIONISTA, Rol.SECRETARIO);
        List<Usuario> usuarios = usuarioRepository.findByRolIn(rolesBuscados);

        return usuarios.stream()
                .map(u -> EmpleadoResponse.builder()
                        .id(u.getId())
                        .rut(u.getRut())
                        .nombre(u.getNombre())
                        .apellidos(u.getApellidos())
                        .telefono(u.getTelefono())
                        .email(u.getEmail())
                        .contrasena(u.getContrasena())
                        .rol(u.getRol())
                        .estado(u.getEstado())
                        .horarioNutricionista(

                                u.getHorarioNutricionista().stream()
                                        .map(h -> HorarioNutricionistaResponse.builder()
                                                .id(h.getId())
                                                .diaSemana(h.getDiaSemana())
                                                .horaEntrada(h.getHoraEntrada())
                                                .horaSalida(h.getHoraSalida())
                                                .build())
                                        .toList()

                        )
                        .build())
                .toList();
    }


}

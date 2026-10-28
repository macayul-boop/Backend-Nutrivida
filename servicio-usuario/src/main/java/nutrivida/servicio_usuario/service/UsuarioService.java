package nutrivida.servicio_usuario.service;

import lombok.RequiredArgsConstructor;
import nutrivida.servicio_usuario.dto.*;
import nutrivida.servicio_usuario.exception.NotFoundException;
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


    // CREAR UN CLIENTE
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

    // CREAR UN EMPLEADO
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

    // EDITAR UN EMPLEADO
    public EmpleadoResponse editarEmpleado(Integer idEmpleado, UsuarioRequest request){

        Usuario usuario = usuarioRepository.findById(idEmpleado)
                .orElseThrow(() -> new NotFoundException("El usuario no existe"));

        usuario.setRut(request.getRut());
        usuario.setNombre(request.getNombre());
        usuario.setApellidos(request.getApellidos());
        usuario.setTelefono(request.getTelefono());
        usuario.setEmail(request.getEmail());
        usuario.setContrasena(request.getContrasena());
        usuario.setRol(request.getRol());
        usuario.setEstado(request.getEstado());

        if (request.getRol() == Rol.NUTRICIONISTA) {

            List<HorarioNutricionista> nuevosHorarios = request.getHorarioNutricionista().stream()
                    .map(hDto -> HorarioNutricionista.builder()
                            .diaSemana(hDto.getDiaSemana())
                            .horaEntrada(hDto.getHoraEntrada())
                            .horaSalida(hDto.getHoraSalida())
                            .build())
                    .toList();

            usuario.setHorarios(nuevosHorarios);

        } else {
            usuario.getHorarioNutricionista().clear();
        }

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        List<HorarioNutricionistaResponse> horario = usuarioGuardado.getHorarioNutricionista().stream()
                .map(h -> HorarioNutricionistaResponse.builder()
                        .id(h.getId())
                        .diaSemana(h.getDiaSemana())
                        .horaEntrada(h.getHoraEntrada())
                        .horaSalida(h.getHoraSalida())
                        .build())
                .toList();

        List<HorarioNutricionistaResponse> horarioVerdadero = horario.isEmpty() ? null : horario;

        return EmpleadoResponse.builder()
                .id(usuarioGuardado.getId())
                .rut(usuarioGuardado.getRut())
                .nombre(usuarioGuardado.getNombre())
                .apellidos(usuarioGuardado.getApellidos())
                .telefono(usuarioGuardado.getTelefono())
                .email(usuarioGuardado.getEmail())
                .contrasena(usuarioGuardado.getContrasena())
                .rol(usuarioGuardado.getRol())
                .estado(usuarioGuardado.getEstado())
                .horarioNutricionista(horarioVerdadero)
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

    // Leer todos los empleados nutricionista y secretario
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

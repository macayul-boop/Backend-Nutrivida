package nutrivida.servicio_usuario.service;

import lombok.RequiredArgsConstructor;
import nutrivida.servicio_usuario.dto.HorarioNutricionistaRequest;
import nutrivida.servicio_usuario.dto.UsuarioPrueba;
import nutrivida.servicio_usuario.dto.UsuarioRequest;
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
                .rol(request.getRol())
                .estado(request.getEstado())
                .horarioNutricionista(new ArrayList<>())
                .build();


        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        return UsuarioPrueba.builder()
                .id(usuarioGuardado.getId())
                .nombre(usuario.getNombre())
                .rol(usuarioGuardado.getRol())
                .build();
    }

    public UsuarioPrueba crearEmpleado(UsuarioRequest request){

        List<HorarioNutricionista> horario = new ArrayList<>();

        if(request.getRol() == Rol.NUTRICIONISTA){
            horario = request.getHorarioNutricionista().stream()
                    .map(hDto -> HorarioNutricionista.builder()
                            .diaSemana(hDto.getDiaSemana())
                            .horaEntrada(hDto.getHoraEntrada())
                            .horaSalida(hDto.getHoraSalida())
                            .build())
                    .toList();
        }

        Usuario usuario = Usuario.builder()
                .rut(request.getRut())
                .nombre(request.getNombre())
                .apellidos(request.getApellidos())
                .telefono(request.getTelefono())
                .email(request.getEmail())
                .contrasena(request.getContrasena())
                .rol(request.getRol())
                .estado(request.getEstado())
                .horarioNutricionista(horario)
                .build();

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        return UsuarioPrueba.builder()
                .id(usuarioGuardado.getId())
                .nombre(usuario.getNombre())
                .rol(usuarioGuardado.getRol())
                .build();

    }



}

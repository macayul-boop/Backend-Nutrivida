package nutrivida.servicio_reserva.service;

import lombok.RequiredArgsConstructor;
import nutrivida.servicio_reserva.WebClient.UsuarioCliente;
import nutrivida.servicio_reserva.dto.*;
import nutrivida.servicio_reserva.exception.ConflictoException;
import nutrivida.servicio_reserva.exception.NotFoundException;
import nutrivida.servicio_reserva.model.EstadoReserva;
import nutrivida.servicio_reserva.model.Reserva;
import nutrivida.servicio_reserva.model.Servicio;
import nutrivida.servicio_reserva.repository.ReservaRespository;
import nutrivida.servicio_reserva.repository.ServicioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRespository reservaRespository;
    private final ServicioRepository servicioRepository;
    private final UsuarioCliente usuarioCliente;

    // CREAR UNA RESERVA
    public ReservaResponse crearReserva(ReservaRequest request){

        Boolean existeNutricionista = usuarioCliente.verificarUsuario(request.getIdNutricionista());
        Boolean existeCliente = usuarioCliente.verificarUsuario(request.getIdCliente());

        if(!existeNutricionista || !existeCliente){
            throw new NotFoundException("Nutricionista o Cliente no encontrado");
        }

        Servicio servicio = servicioRepository.findById(request.getIdServicio())
                .orElseThrow(()-> new NotFoundException("Servicio no encontrado"));

        LocalTime horaTermino = request.getHoraInicio().plusMinutes(servicio.getDuracionMinutos());

        Boolean reservaInvalida = reservaRespository.existeSolapamiento(
                request.getIdNutricionista(),
                request.getFecha(),
                request.getHoraInicio(),
                horaTermino
        );

        if(reservaInvalida){
            throw new ConflictoException("La cita solapa a una reserva ya existente");
        }

        Reserva reserva = Reserva.builder()
                .idNutricionista(request.getIdNutricionista())
                .idCliente(request.getIdCliente())
                .servicio(servicio)
                .estado(EstadoReserva.RESERVADA)
                .fecha(request.getFecha())
                .horaInicio(request.getHoraInicio())
                .horaTermino(horaTermino)
                .build();

        Reserva reservaGuardada = reservaRespository.save(reserva);

        ClienteSimplificadoDto cliente = usuarioCliente.leerUnClienteSimplificado(reservaGuardada.getIdCliente());
        NutricionistaSimplificadoDto nutricionista = usuarioCliente.leerUnNutricionistaSimplificado(reservaGuardada.getIdNutricionista());

        return ReservaResponse.builder()
                .id(reservaGuardada.getId())
                .idNutricionista(reservaGuardada.getIdNutricionista())
                .idCliente(reservaGuardada.getIdCliente())
                .idServicio(reservaGuardada.getServicio().getId())
                .estado(reservaGuardada.getEstado())
                .fecha(reservaGuardada.getFecha())
                .horaInicio(reservaGuardada.getHoraInicio())
                .horaTermino(reservaGuardada.getHoraTermino())
                .datosClientes(cliente)
                .datosNutricionista(nutricionista)
                .nombreServicio(reservaGuardada.getServicio().getNombre())
                .build();
    }

    // EDITAR UNA RESERVA COMO SECRETARIO
    public ReservaResponse editarReservaSecretario(Integer idReserva, EditarReservaDto dtoRequest){
        Reserva reserva = reservaRespository.findById(idReserva)
                .orElseThrow(()-> new NotFoundException("Reserva no encontrada"));

        LocalTime horaTermino = dtoRequest.getHoraInicio().plusMinutes(reserva.getServicio().getDuracionMinutos());

        Boolean reservaInvalida = reservaRespository.existeSolapamiento(
                reserva.getIdNutricionista(),
                dtoRequest.getFecha(),
                dtoRequest.getHoraInicio(),
                horaTermino
        );

        if(reservaInvalida){
            throw new ConflictoException("La cita solapa a una reserva ya existente");
        }

        reserva.setFecha(dtoRequest.getFecha());
        reserva.setHoraInicio(dtoRequest.getHoraInicio());
        reserva.setHoraTermino(horaTermino);

        Reserva reservaGuardada = reservaRespository.save(reserva);

        ClienteSimplificadoDto cliente = usuarioCliente.leerUnClienteSimplificado(reservaGuardada.getIdCliente());
        NutricionistaSimplificadoDto nutricionista = usuarioCliente.leerUnNutricionistaSimplificado(reservaGuardada.getIdNutricionista());

        return ReservaResponse.builder()
                .id(reservaGuardada.getId())
                .idNutricionista(reservaGuardada.getIdNutricionista())
                .idCliente(reservaGuardada.getIdCliente())
                .idServicio(reservaGuardada.getServicio().getId())
                .estado(reservaGuardada.getEstado())
                .fecha(reservaGuardada.getFecha())
                .horaInicio(reservaGuardada.getHoraInicio())
                .horaTermino(reservaGuardada.getHoraTermino())
                .datosClientes(cliente)
                .datosNutricionista(nutricionista)
                .nombreServicio(reservaGuardada.getServicio().getNombre())
                .build();
    }

    // EDITAR UNA RESERVA COMO CLIENTE
    public ReservaResponse editarReservaCliente(Integer idReserva, Integer idCliente,EditarReservaDto dtoRequest){
        Reserva reserva = reservaRespository.findById(idReserva)
                .orElseThrow(()-> new NotFoundException("Reserva no encontrada"));

        if(!reserva.getIdCliente().equals(idCliente)){
            throw new ConflictoException("El recurso no te pertenece");
        }

        LocalTime horaTermino = dtoRequest.getHoraInicio().plusMinutes(reserva.getServicio().getDuracionMinutos());

        Boolean reservaInvalida = reservaRespository.existeSolapamiento(
                reserva.getIdNutricionista(),
                dtoRequest.getFecha(),
                dtoRequest.getHoraInicio(),
                horaTermino
        );

        if(reservaInvalida){
            throw new ConflictoException("La cita solapa a una reserva ya existente");
        }

        reserva.setFecha(dtoRequest.getFecha());
        reserva.setHoraInicio(dtoRequest.getHoraInicio());
        reserva.setHoraTermino(horaTermino);

        Reserva reservaGuardada = reservaRespository.save(reserva);

        ClienteSimplificadoDto cliente = usuarioCliente.leerUnClienteSimplificado(reservaGuardada.getIdCliente());
        NutricionistaSimplificadoDto nutricionista = usuarioCliente.leerUnNutricionistaSimplificado(reservaGuardada.getIdNutricionista());

        return ReservaResponse.builder()
                .id(reservaGuardada.getId())
                .idNutricionista(reservaGuardada.getIdNutricionista())
                .idCliente(reservaGuardada.getIdCliente())
                .idServicio(reservaGuardada.getServicio().getId())
                .estado(reservaGuardada.getEstado())
                .fecha(reservaGuardada.getFecha())
                .horaInicio(reservaGuardada.getHoraInicio())
                .horaTermino(reservaGuardada.getHoraTermino())
                .datosClientes(cliente)
                .datosNutricionista(nutricionista)
                .nombreServicio(reservaGuardada.getServicio().getNombre())
                .build();
    }

    // CANCELAR CITA COMO SECRETARIO

    // CANCELAR CITA COMO CLIENTE

    // LEER LAS RESERVAS DE UN NUTRICIONISTA

    // LEER LAS RESERVAS DE UN PACIENTE


}

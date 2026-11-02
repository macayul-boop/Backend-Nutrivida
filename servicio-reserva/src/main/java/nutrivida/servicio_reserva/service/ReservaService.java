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
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    public ReservaResponse cancelarReservaSecretario(Integer idReserva){
        Reserva reserva = reservaRespository.findById(idReserva)
                .orElseThrow(()-> new NotFoundException("Reserva no encontrada"));

        reserva.setEstado(EstadoReserva.CANCELADA);

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

    // CANCELAR CITA COMO CLIENTE
    public ReservaResponse cancelarReservaCliente(Integer idReserva, Integer idCliente){
        Reserva reserva = reservaRespository.findById(idReserva)
                .orElseThrow(()-> new NotFoundException("Reserva no encontrada"));

        if(!reserva.getIdCliente().equals(idCliente)){
            throw new ConflictoException("El recurso no te pertenece");
        }

        reserva.setEstado(EstadoReserva.CANCELADA);

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


    // LEER LAS RESERVAS DE UN NUTRICIONISTA
    public List<ReservaResponse> leerReservasPorFechaDeUnNutricionista(Integer idNutricionista, LocalDate fecha){
        List<Reserva> lista = reservaRespository.findAllByIdNutricionistaAndFecha(idNutricionista, fecha);

        List<ReservaResponse> response = new ArrayList<>();

        for(Reserva r : lista){
            ClienteSimplificadoDto cliente = usuarioCliente.leerUnClienteSimplificado(r.getIdCliente());

            ReservaResponse reservaTransformada = ReservaResponse.builder()
                    .id(r.getId())
                    .idNutricionista(r.getIdNutricionista())
                    .idCliente(r.getIdCliente())
                    .idServicio(r.getServicio().getId())
                    .estado(r.getEstado())
                    .fecha(r.getFecha())
                    .horaInicio(r.getHoraInicio())
                    .horaTermino(r.getHoraTermino())
                    .datosClientes(cliente)
                    .nombreServicio(r.getServicio().getNombre())
                    .build();

            response.add(reservaTransformada);
        }

        return response;
    }

    // LEER LAS RESERVAS DE UN PACIENTE
    public List<ReservaResponse> leerTodasReservasDeUnPaciente(Integer idCliente){
        List<Reserva> lista = reservaRespository.findAllByIdCliente(idCliente);

        List<ReservaResponse> response = new ArrayList<>();

        for(Reserva r : lista){
            NutricionistaSimplificadoDto nutricionista = usuarioCliente.leerUnNutricionistaSimplificado(r.getIdNutricionista());

            ReservaResponse reservaTransformada = ReservaResponse.builder()
                    .id(r.getId())
                    .idNutricionista(r.getIdNutricionista())
                    .idCliente(r.getIdCliente())
                    .idServicio(r.getServicio().getId())
                    .estado(r.getEstado())
                    .fecha(r.getFecha())
                    .horaInicio(r.getHoraInicio())
                    .horaTermino(r.getHoraTermino())
                    .datosNutricionista(nutricionista)
                    .nombreServicio(r.getServicio().getNombre())
                    .build();

            response.add(reservaTransformada);
        }

        return response;
    }

    // GENERAR HORAS DISPONIBLES
    public List<LocalTime> generarHoras(HoraRequestDto request){

        // 1. Obtener la información del nutricionista
        NutricionistaSimplificadoDto nutricionista = usuarioCliente.leerUnNutricionistaSimplificado(request.getIdNutricionista());

        // 2. Obtener el día de la semana en español
        String nombreDia = request.getFecha().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("es-ES"));

        // 3. Filtrar el horario para el día seleccionado
        HorarioNutricionistaDto horarioDia = nutricionista.getHorarioNutricionista().stream()
                .filter(h -> h.getDiaSemana().equalsIgnoreCase(nombreDia))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("El nutricionista no atiende el día " + nombreDia));

        // 4. Obtener el servicio y su duración
        Servicio servicio = servicioRepository.findById(request.getIdServicio())
                .orElseThrow(() -> new NotFoundException("Servicio no encontrado"));

        int duracionMinutos = servicio.getDuracionMinutos();

        // 5. Consultar solo las reservas en estado "RESERVADO"
        List<Reserva> reservasOcupadas = reservaRespository.findAllByIdNutricionistaAndFechaAndEstado(
                request.getIdNutricionista(),
                request.getFecha(),
                EstadoReserva.RESERVADA
        );

        // 6. Generar los bloques de horarios disponibles
        List<LocalTime> horasDisponibles = new ArrayList<>();

        LocalTime horaActual = horarioDia.getHoraEntrada();
        LocalTime horaFinJornada = horarioDia.getHoraSalida();

        while (!horaActual.plusMinutes(duracionMinutos).isAfter(horaFinJornada)) {
            LocalTime horaFinBloque = horaActual.plusMinutes(duracionMinutos);

            final LocalTime inicio = horaActual;
            final LocalTime fin = horaFinBloque;

            // Comprobar si traslapa con alguna reserva ya existente
            boolean estaOcupado = reservasOcupadas.stream().anyMatch(reserva ->
                    inicio.isBefore(reserva.getHoraTermino()) && fin.isAfter(reserva.getHoraInicio())
            );

            if (!estaOcupado) {
                horasDisponibles.add(horaActual);
            }

            System.out.println(horaActual);

            // Avanzar el reloj
            horaActual = horaActual.plusMinutes(duracionMinutos);
        }

        return horasDisponibles;

    }

    // ELIMINAR RESERVA POR EL NUTRICIONISTA
    public void eliminarReservasPorUnNutricionista(Integer idNutricionista){
        if(reservaRespository.existsReservasByIdNutricionista(idNutricionista)) {
            reservaRespository.deleteByIdNutricionista(idNutricionista);
        }
    }

}

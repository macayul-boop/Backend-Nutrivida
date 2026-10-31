package nutrivida.servicio_reserva.service;

import lombok.RequiredArgsConstructor;
import nutrivida.servicio_reserva.dto.ServicioRequest;
import nutrivida.servicio_reserva.dto.ServicioResponse;
import nutrivida.servicio_reserva.model.Servicio;
import nutrivida.servicio_reserva.repository.ServicioRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServicioService {

    private final ServicioRepository servicioRepository;

    // CREAR SERVICIO
    public ServicioResponse crearServicio(ServicioRequest request){

        Servicio servicio = Servicio.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .precio(request.getPrecio())
                .duracionMinutos(request.getDuracionMinutos())
                .modalidad(request.getModalidad())
                .build();

        Servicio servicioGuardado = servicioRepository.save(servicio);

        return ServicioResponse.builder()
                .id(servicioGuardado.getId())
                .nombre(servicioGuardado.getNombre())
                .descripcion(servicioGuardado.getDescripcion())
                .precio(servicioGuardado.getPrecio())
                .duracionMinutos(servicioGuardado.getDuracionMinutos())
                .modalidad(servicioGuardado.getModalidad())
                .build();
    }

    // EDITAR SERVICIO

    // ELIMINAR SERVICIO

    // LEER UN SERVICIO

    // LEER TODOS LOS SERVICIOS

}

package nutrivida.servicio_reserva.service;

import lombok.RequiredArgsConstructor;
import nutrivida.servicio_reserva.dto.ServicioRequest;
import nutrivida.servicio_reserva.dto.ServicioResponse;
import nutrivida.servicio_reserva.exception.NotFoundException;
import nutrivida.servicio_reserva.model.Servicio;
import nutrivida.servicio_reserva.repository.ServicioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public ServicioResponse editarServicio(Integer idServicio ,ServicioRequest request){

        Servicio servicio = servicioRepository.findById(idServicio)
                .orElseThrow(()-> new NotFoundException("Servicio no encontrado"));

        servicio.setNombre(request.getNombre());
        servicio.setDescripcion(request.getDescripcion());
        servicio.setPrecio(request.getPrecio());
        servicio.setDuracionMinutos(request.getDuracionMinutos());
        servicio.setModalidad(request.getModalidad());

        Servicio servicioGuardado = servicioRepository.save(servicio);

        return ServicioResponse.builder()
                .id(servicioGuardado.getId())
                .nombre(servicioGuardado.getDescripcion())
                .descripcion(servicioGuardado.getDescripcion())
                .precio(servicioGuardado.getPrecio())
                .duracionMinutos(servicioGuardado.getDuracionMinutos())
                .modalidad(servicioGuardado.getModalidad())
                .build();

    }

    // ELIMINAR SERVICIO
    public void eliminarServicio(Integer idServicio){
        if(!servicioRepository.existsById(idServicio)){
            throw new NotFoundException("Servicio no encontrado");
        }
        servicioRepository.deleteById(idServicio);
    }

    // LEER UN SERVICIO
    public ServicioResponse leerUnServicio(Integer idServicio){
        Servicio servicio = servicioRepository.findById(idServicio)
                .orElseThrow(() -> new NotFoundException("Servicio no encontrado"));

        return ServicioResponse.builder()
                .id(servicio.getId())
                .nombre(servicio.getNombre())
                .descripcion(servicio.getDescripcion())
                .precio(servicio.getPrecio())
                .duracionMinutos(servicio.getDuracionMinutos())
                .modalidad(servicio.getModalidad())
                .build();
    }

    // LEER TODOS LOS SERVICIOS
    public List<ServicioResponse> leerTodosServicios(){
        List<Servicio> listaServicios = servicioRepository.findAll();

        return listaServicios.stream()
                .map(s -> ServicioResponse.builder()
                        .id(s.getId())
                        .nombre(s.getNombre())
                        .descripcion(s.getDescripcion())
                        .precio(s.getPrecio())
                        .duracionMinutos(s.getDuracionMinutos())
                        .modalidad(s.getModalidad())
                        .build()

                ).toList();
    }

}

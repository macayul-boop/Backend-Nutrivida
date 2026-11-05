package nutrivida.servicio_datos_clinicos.service;

import lombok.RequiredArgsConstructor;
import nutrivida.servicio_datos_clinicos.WebClient.UsuarioCliente;
import nutrivida.servicio_datos_clinicos.dto.*;
import nutrivida.servicio_datos_clinicos.exception.ForbiddenException;
import nutrivida.servicio_datos_clinicos.exception.NotFoundException;
import nutrivida.servicio_datos_clinicos.model.FichaClinica;
import nutrivida.servicio_datos_clinicos.model.Informe;
import nutrivida.servicio_datos_clinicos.repository.FichaClinicaRepository;
import nutrivida.servicio_datos_clinicos.repository.InformeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FichaClinicaService {

    private final UsuarioCliente usuarioCliente;
    private final FichaClinicaRepository fichaClinicaRepository;
    private final InformeRepository informeRepository;

    // BUSCAR FICHA POR EL RUT DEL PACIENTE
    @Transactional(readOnly = true)
    public FichaClinicaResponse buscarPorRut(String rut){
        PacienteDto paciente = usuarioCliente.buscarPorRut(rut.trim());
        String plan = fichaClinicaRepository.findByIdCliente(paciente.getId())
                .map(FichaClinica::getPlanAlimentacion)
                .orElse("");
        List<Informe> informes = informeRepository.findAllByFichaClinica_IdClienteOrderByFechaDescIdDesc(paciente.getId());
        List<InformeResponse> listaInformes = informes.stream()
                .map(this::convertirInforme)
                .toList();

        return FichaClinicaResponse.builder()
                .paciente(paciente)
                .planAlimentacion(plan)
                .informes(listaInformes)
                .build();
    }

    // ACTUALIZAR EL PLAN DE ALIMENTACION
    @Transactional
    public void actualizarPlan(Integer idCliente, PlanAlimentacionRequest request){
        usuarioCliente.buscarPorId(idCliente);
        FichaClinica ficha = obtenerOCrearFicha(idCliente);
        ficha.setPlanAlimentacion(request.getPlanAlimentacion().trim());
        fichaClinicaRepository.save(ficha);
    }

    // CREAR INFORME
    @Transactional
    public InformeResponse crearInforme(Integer idCliente, Integer idNutricionista, InformeRequest request){
        usuarioCliente.buscarPorId(idCliente);
        FichaClinica ficha = obtenerOCrearFicha(idCliente);

        Informe informe = Informe.builder()
                .fichaClinica(ficha)
                .idNutricionista(idNutricionista)
                .fecha(LocalDate.now())
                .build();
        completarInforme(informe, request);

        Informe informeGuardado = informeRepository.save(informe);
        return convertirInforme(informeGuardado);
    }

    // LEER UN INFORME DEL PACIENTE
    @Transactional(readOnly = true)
    public InformeResponse leerInforme(Integer idCliente, Integer idInforme){
        Informe informe = buscarInforme(idCliente, idInforme);
        return convertirInforme(informe);
    }

    // EDITAR INFORME
    @Transactional
    public InformeResponse editarInforme(
            Integer idCliente,
            Integer idInforme,
            Integer idNutricionista,
            InformeRequest request
    ){
        Informe informe = buscarInforme(idCliente, idInforme);
        if(!informe.getIdNutricionista().equals(idNutricionista)){
            throw new ForbiddenException("Solo el autor puede editar este informe");
        }

        completarInforme(informe, request);
        Informe informeGuardado = informeRepository.save(informe);
        return convertirInforme(informeGuardado);
    }

    private FichaClinica obtenerOCrearFicha(Integer idCliente){
        return fichaClinicaRepository.findByIdCliente(idCliente)
                .orElseGet(() -> {
                    FichaClinica ficha = FichaClinica.builder()
                            .idCliente(idCliente)
                            .build();
                    return fichaClinicaRepository.save(ficha);
                });
    }

    private Informe buscarInforme(Integer idCliente, Integer idInforme){
        return informeRepository.findByIdAndFichaClinica_IdCliente(idInforme, idCliente)
                .orElseThrow(() -> new NotFoundException("Informe no encontrado"));
    }

    private void completarInforme(Informe informe, InformeRequest request){
        informe.setPeso(request.getPeso());
        informe.setTalla(request.getTalla());
        informe.setCircunferenciaCintura(request.getCircunferenciaCintura());
        informe.setDescripcion(request.getDescripcion());
    }

    private InformeResponse convertirInforme(Informe informe){
        return InformeResponse.builder()
                .id(informe.getId())
                .idCliente(informe.getFichaClinica().getIdCliente())
                .idNutricionista(informe.getIdNutricionista())
                .fecha(informe.getFecha())
                .peso(informe.getPeso())
                .talla(informe.getTalla())
                .circunferenciaCintura(informe.getCircunferenciaCintura())
                .descripcion(informe.getDescripcion())
                .build();
    }
}
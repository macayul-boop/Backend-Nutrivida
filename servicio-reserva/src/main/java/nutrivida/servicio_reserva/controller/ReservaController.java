package nutrivida.servicio_reserva.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nutrivida.servicio_reserva.dto.EditarReservaDto;
import nutrivida.servicio_reserva.dto.HoraRequestDto;
import nutrivida.servicio_reserva.dto.ReservaRequest;
import nutrivida.servicio_reserva.dto.ReservaResponse;
import nutrivida.servicio_reserva.service.ReservaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @PostMapping("/reservas")
    public ResponseEntity<ReservaResponse> crearReserva(@Valid @RequestBody ReservaRequest request){
        ReservaResponse response = reservaService.crearReserva(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/reservas/secretarios/{idReserva}")
    public ResponseEntity<ReservaResponse> editarReservaSecretario(
            @PathVariable Integer idReserva,
            @Valid @RequestBody EditarReservaDto dtoRequest
    ){
        ReservaResponse response = reservaService.editarReservaSecretario(idReserva, dtoRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/reservas/clientes/{idReserva}")
    public ResponseEntity<ReservaResponse> editarReservaCliente(
            @PathVariable Integer idReserva,
            @Valid @RequestBody EditarReservaDto dtoRequest,
            @AuthenticationPrincipal Integer idCliente
    ){
        ReservaResponse response = reservaService.editarReservaCliente(idReserva,idCliente, dtoRequest);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/reservas/secretarios/cancelar/{idReserva}")
    public ResponseEntity<ReservaResponse> cancelarReservaSecretario(@PathVariable Integer idReserva){
        ReservaResponse response = reservaService.cancelarReservaSecretario(idReserva);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/reservas/clientes/cancelar/{idReserva}")
    public ResponseEntity<ReservaResponse> cancelarReservaCliente(
            @PathVariable Integer idReserva,
            @AuthenticationPrincipal Integer idCliente
    ){
        ReservaResponse response = reservaService.cancelarReservaCliente(idReserva, idCliente);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/reservas/clientes")
    public ResponseEntity<List<ReservaResponse>> listarTodasReservasDeUnClinte(
            @AuthenticationPrincipal Integer idCliente
    ){
        List<ReservaResponse> response = reservaService.leerTodasReservasDeUnPaciente(idCliente);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/reservas/nutricionista/{idNutricionista}")
    public ResponseEntity<List<ReservaResponse>> listarTodasReservasDeUnNutricionista(
            @PathVariable Integer idNutricionista,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha
    ){
        List<ReservaResponse> response = reservaService.leerReservasPorFechaDeUnNutricionista(idNutricionista, fecha);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/horas-disponibles")
    public ResponseEntity<List<LocalTime>> obtenerHorasDisponibles(
            @RequestParam Integer idNutricionista,
            @RequestParam Integer idServicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha
    ){
        HoraRequestDto request = new HoraRequestDto(idServicio, idNutricionista, fecha);
        List<LocalTime> horas = reservaService.generarHoras(request);
        return ResponseEntity.ok(horas);
    }

    @DeleteMapping("/reservas/{idUsuario}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Integer idUsuario){
        reservaService.eliminarReservasPorUnNutricionista(idUsuario);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reservas/secretarios")
    public ResponseEntity<List<ReservaResponse>> listarTodasReservas(){
        List<ReservaResponse> response = reservaService.listarTodasReservasSecretario();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/reservas/secretarios/{idCliente}")
    public ResponseEntity<List<ReservaResponse>> listarTodoasReservasDeUnPaciente(@PathVariable Integer idCliente){
        List<ReservaResponse> response = reservaService.listarTodasReservasDeUnPacienteSecretario(idCliente);
        return ResponseEntity.ok(response);
    }
}

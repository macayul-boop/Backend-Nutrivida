package nutrivida.servicio_reserva.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nutrivida.servicio_reserva.dto.EditarReservaDto;
import nutrivida.servicio_reserva.dto.ReservaRequest;
import nutrivida.servicio_reserva.dto.ReservaResponse;
import nutrivida.servicio_reserva.service.ReservaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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


}

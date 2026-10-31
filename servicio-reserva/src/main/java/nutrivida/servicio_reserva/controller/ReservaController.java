package nutrivida.servicio_reserva.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nutrivida.servicio_reserva.dto.ReservaRequest;
import nutrivida.servicio_reserva.dto.ReservaResponse;
import nutrivida.servicio_reserva.service.ReservaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}

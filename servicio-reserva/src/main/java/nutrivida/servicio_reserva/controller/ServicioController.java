package nutrivida.servicio_reserva.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nutrivida.servicio_reserva.dto.ServicioRequest;
import nutrivida.servicio_reserva.dto.ServicioResponse;
import nutrivida.servicio_reserva.service.ServicioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ServicioController {

    private final ServicioService servicioService;

    @GetMapping("/servicios")
    public ResponseEntity<List<ServicioResponse>> listarTodos(){
        List<ServicioResponse> response = servicioService.leerTodosServicios();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/servicios/{idServicio}")
    public ResponseEntity<ServicioResponse> listarServicioPorId(@PathVariable Integer idServicio){
        ServicioResponse response = servicioService.leerUnServicio(idServicio);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/servicios")
    public ResponseEntity<ServicioResponse> crearServicios(@Valid @RequestBody ServicioRequest request){
        ServicioResponse response = servicioService.crearServicio(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/servicios/{idServicio}")
    public ResponseEntity<ServicioResponse> editarServicio(@PathVariable Integer idServicio, @Valid  @RequestBody ServicioRequest request){
        ServicioResponse response = servicioService.editarServicio(idServicio, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/servicios/{idServicio}")
    public ResponseEntity<Void> eliminarServicio(@PathVariable Integer idServicio){
        servicioService.eliminarServicio(idServicio);
        return ResponseEntity.noContent().build();
    }

}

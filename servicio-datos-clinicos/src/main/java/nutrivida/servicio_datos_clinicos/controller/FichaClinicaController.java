package nutrivida.servicio_datos_clinicos.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import nutrivida.servicio_datos_clinicos.dto.FichaClinicaResponse;
import nutrivida.servicio_datos_clinicos.dto.InformeRequest;
import nutrivida.servicio_datos_clinicos.dto.InformeResponse;
import nutrivida.servicio_datos_clinicos.dto.PlanAlimentacionRequest;
import nutrivida.servicio_datos_clinicos.service.FichaClinicaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FichaClinicaController {

    private final FichaClinicaService fichaClinicaService;

    @GetMapping("/fichas-clinicas")
    public ResponseEntity<FichaClinicaResponse> buscarPorRut(@RequestParam @NotBlank @Size(max = 40) String rut){
        FichaClinicaResponse response = fichaClinicaService.buscarPorRut(rut);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/fichas-clinicas/{idCliente}/plan")
    public ResponseEntity<Void> actualizarPlan(
            @PathVariable Integer idCliente,
            @Valid @RequestBody PlanAlimentacionRequest request
    ){
        fichaClinicaService.actualizarPlan(idCliente, request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/fichas-clinicas/{idCliente}/informes")
    public ResponseEntity<InformeResponse> crearInforme(
            @PathVariable Integer idCliente,
            @AuthenticationPrincipal Integer idNutricionista,
            @Valid @RequestBody InformeRequest request
    ){
        InformeResponse response = fichaClinicaService.crearInforme(idCliente, idNutricionista, request);
        URI ubicacion = URI.create("/api/fichas-clinicas/" + idCliente + "/informes/" + response.getId());

        return ResponseEntity.status(HttpStatus.CREATED).location(ubicacion).body(response);
    }

    @GetMapping("/fichas-clinicas/{idCliente}/informes/{idInforme}")
    public ResponseEntity<InformeResponse> leerInforme(
            @PathVariable Integer idCliente,
            @PathVariable Integer idInforme
    ){
        InformeResponse response = fichaClinicaService.leerInforme(idCliente, idInforme);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/fichas-clinicas/{idCliente}/informes/{idInforme}")
    public ResponseEntity<InformeResponse> editarInforme(
            @PathVariable Integer idCliente,
            @PathVariable Integer idInforme,
            @AuthenticationPrincipal Integer idNutricionista,
            @Valid @RequestBody InformeRequest request
    ){
        InformeResponse response = fichaClinicaService.editarInforme(idCliente, idInforme, idNutricionista, request);
        return ResponseEntity.ok(response);
    }
}
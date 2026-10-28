package nutrivida.servicio_usuario.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nutrivida.servicio_usuario.dto.EmpleadoResponse;
import nutrivida.servicio_usuario.dto.UsuarioPrueba;
import nutrivida.servicio_usuario.dto.UsuarioRequest;
import nutrivida.servicio_usuario.model.Rol;
import nutrivida.servicio_usuario.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioPrueba>> leerTodos(){
        List<UsuarioPrueba> lista = usuarioService.leerUsuarios();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/usuarios/empleados")
    public ResponseEntity<List<EmpleadoResponse>> listarEmpleados(){
        List<EmpleadoResponse> lista = usuarioService.listarEmpleados();
        return ResponseEntity.ok(lista);
    }


    @PostMapping("/usuarios/clientes")
    public ResponseEntity<UsuarioPrueba> craerCliente(@Valid @RequestBody UsuarioRequest request){
        UsuarioPrueba response = usuarioService.crearCliente(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/usuarios/empleados")
    public ResponseEntity<UsuarioPrueba> crearEmpleado(@Valid @RequestBody UsuarioRequest request){
        UsuarioPrueba response = usuarioService.crearEmpleado(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/usuarios/empleados/{idEmpleado}")
    public ResponseEntity<EmpleadoResponse> editarEmpleado(@Valid @RequestBody UsuarioRequest request, @PathVariable Integer idEmpleado){
        EmpleadoResponse response = usuarioService.editarEmpleado(idEmpleado, request);
        return ResponseEntity.ok(response);
    }


}

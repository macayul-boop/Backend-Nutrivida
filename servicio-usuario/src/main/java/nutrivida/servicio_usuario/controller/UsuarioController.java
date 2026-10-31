package nutrivida.servicio_usuario.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nutrivida.servicio_usuario.dto.*;
import nutrivida.servicio_usuario.service.UsuarioService;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/usuarios/clienteSimplificado")
    public ResponseEntity<List<ClienteSimplificadoResponse>> leerTodosClientesSimplificado(){
        List<ClienteSimplificadoResponse> lista = usuarioService.leerTodosClienteSimplificado();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/usuarios/nutricionistaSimplificado")
    public ResponseEntity<List<NutricionistaSimplificadoResponse>> leerTodosNutricionistaSimplificado(){
        List<NutricionistaSimplificadoResponse> lista = usuarioService.listarTodosNutricionistaSimplificado();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/usuarios/empleados")
    public ResponseEntity<List<EmpleadoResponse>> listarEmpleados(){
        List<EmpleadoResponse> lista = usuarioService.listarEmpleados();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/usuarios/verificar/{idUsuario}")
    public ResponseEntity<Boolean> verificarUsuario(@PathVariable Integer idUsuario){
        Boolean response = usuarioService.verificarUsuario(idUsuario);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<TokenResponse> craerCliente(@Valid @RequestBody UsuarioRequest request){
        TokenResponse response = usuarioService.crearCliente(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request){
        TokenResponse response = usuarioService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/usuarios/empleados")
    public ResponseEntity<EmpleadoResponse> crearEmpleado(@Valid @RequestBody UsuarioRequest request){
        EmpleadoResponse response = usuarioService.crearEmpleado(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/usuarios/empleados/{idEmpleado}")
    public ResponseEntity<EmpleadoResponse> editarEmpleado(@Valid @RequestBody UsuarioRequest request, @PathVariable Integer idEmpleado){
        EmpleadoResponse response = usuarioService.editarEmpleado(idEmpleado, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/usuarios/{idUsuario}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer idUsuario){
        usuarioService.eliminarUsuario(idUsuario);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}

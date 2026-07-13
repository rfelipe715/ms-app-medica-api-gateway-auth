package cl.duoc.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.app.service.JwtService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Autenticación y generación de tokens JWT para usar en las demás APIs")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Operation(summary = "Iniciar sesión", description = "Autentica al usuario y retorna un token JWT para usar como Bearer token en las peticiones a Pacientes, Citas e Historial.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login exitoso, retorna el token JWT"),
            @ApiResponse(responseCode = "500", description = "Credenciales inválidas")
    })
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {

        String username = request.get("username");
        String password = request.get("password");

        if (!"admin".equals(username) || !"1234".equals(password)) {
            throw new RuntimeException("Credenciales inválidas");
        }
        String token = jwtService.generateToken(username);
        return Map.of("token", token);
    }

}

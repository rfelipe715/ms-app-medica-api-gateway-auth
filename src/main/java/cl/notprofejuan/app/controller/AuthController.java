package cl.notprofejuan.app.controller;

import cl.notprofejuan.app.service.JwtService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService){
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request){

        String username = request.get("username");
        String password = request.get("password");

        if(!"admin".equals(username) || !"1234".equals(password)){
            throw new RuntimeException("Credenciales inválidas");
        }
        String token = jwtService.generateToken(username);
        return Map.of("token", token);
    }

}

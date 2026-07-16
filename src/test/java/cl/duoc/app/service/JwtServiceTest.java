package cl.duoc.app.service;

import cl.duoc.app.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        JwtProperties jwtProperties = new JwtProperties(
                "una-clave-secreta-de-prueba-con-al-menos-32-caracteres",
                60L
        );
        jwtService = new JwtService(jwtProperties);
    }

    @Test
    void generateToken_creaUnTokenConElUsuarioComoSubject() {
        String token = jwtService.generateToken("admin");

        assertThat(token).isNotBlank();
    }

    @Test
    void validateToken_retornaLosClaimsDelTokenGenerado_cuandoEsValido() {
        String token = jwtService.generateToken("admin");

        Claims claims = jwtService.validateToken(token);

        assertThat(claims.getSubject()).isEqualTo("admin");
        assertThat(claims.get("roles", java.util.List.class)).contains("USER");
    }

    @Test
    void validateToken_lanzaExcepcion_cuandoElTokenEsInvalido() {
        assertThatThrownBy(() -> jwtService.validateToken("token-invalido"))
                .isInstanceOf(io.jsonwebtoken.JwtException.class);
    }

    @Test
    void validateToken_lanzaExcepcion_cuandoElTokenFueFirmadoConOtraClave() {
        JwtProperties otrasCredenciales = new JwtProperties(
                "otra-clave-secreta-completamente-distinta-de-32-car",
                60L
        );
        JwtService otroServicio = new JwtService(otrasCredenciales);
        String token = otroServicio.generateToken("admin");

        assertThatThrownBy(() -> jwtService.validateToken(token))
                .isInstanceOf(SignatureException.class);
    }
}

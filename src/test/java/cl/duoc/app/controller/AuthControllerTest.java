package cl.duoc.app.controller;

import cl.duoc.app.model.dto.LoginRequestDTO;
import cl.duoc.app.service.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthController authController;

    @Test
    void login_retornaElToken_cuandoLasCredencialesSonValidas() {
        when(jwtService.generateToken("admin")).thenReturn("token-generado");

        Map<String, String> resultado = authController.login(new LoginRequestDTO("admin", "1234"));

        assertThat(resultado).containsEntry("token", "token-generado");
    }

    @Test
    void login_lanzaExcepcion_cuandoElUsuarioEsIncorrecto() {
        assertThatThrownBy(() -> authController.login(new LoginRequestDTO("otro", "1234")))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Credenciales inválidas");
    }

    @Test
    void login_lanzaExcepcion_cuandoLaContrasenaEsIncorrecta() {
        assertThatThrownBy(() -> authController.login(new LoginRequestDTO("admin", "incorrecta")))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Credenciales inválidas");
    }
}

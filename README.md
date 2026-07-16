# ms-api-gateway

**API Gateway** del sistema (Spring Cloud Gateway, WebFlux). Único punto de entrada expuesto al cliente: centraliza el enrutamiento hacia los tres BFF, protege las rutas con **JWT** y agrega la documentación **Swagger** de los módulos en una sola UI.

| | |
|---|---|
| **Puerto** | `8080` |
| **Config** | `src/main/resources/application.yaml` (rutas, secreto JWT, agregación Swagger) |
| **Auth** | `POST /auth/login` (`admin` / `1234`) → devuelve token JWT |
| **Pruebas** | `JwtServiceTest`, `JwtAuthenticationFilterTest`, `AuthControllerTest` (JUnit 5 + Mockito) |
| **Swagger UI** | `http://localhost:8080/swagger-ui/index.html` (pestañas Auth, Pacientes, Citas, Historial) |

## Rutas principales

| Ruta | Destino |
|---|---|
| `/api/v1/pacientes/**` | `pacientes-bff` (8181) |
| `/api/v1/citas/**` | `citas-bff` (8090) |
| `/api/v1/historiales/**` | `historial-bff` (9090) |
| `/pacientes-docs/**`, `/citas-docs/**`, `/historial-docs/**` | proxy a la doc OpenAPI de cada BFF |
| `/auth/**`, `/swagger-ui/**`, `/v3/api-docs/**`, `/*-docs/**` | públicas (sin JWT) |

Toda otra ruta exige el header `Authorization: Bearer <token>`; el filtro `JwtAuthenticationFilter` valida el token y propaga el usuario como header `X-User` aguas abajo.

## Ejecución

```bash
# Con todo el ecosistema (recomendado), desde app-medica-et-fullstack-1/
docker compose up --build

# Individual
./mvnw spring-boot:run     # mvnw.cmd en Windows
./mvnw test
```

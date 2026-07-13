package cl.notprofejuan.app.service;

import cl.notprofejuan.app.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {

    private final JwtProperties jwtProperties;

    public JwtService(JwtProperties jwtProperties){
        this.jwtProperties = jwtProperties;
    }

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(jwtProperties.secret().getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username){
        Instant now = Instant.now();

        return Jwts.builder().subject(username).claim("roles", List.of("USER"))
                .issuedAt(Date.from(now)).expiration(Date.from(now.plusSeconds(jwtProperties.expirationMinutes())))
                .signWith(getSigningKey())
                .compact();
    }

    public Claims validateToken(String token){
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token)
                .getPayload();
    }
}

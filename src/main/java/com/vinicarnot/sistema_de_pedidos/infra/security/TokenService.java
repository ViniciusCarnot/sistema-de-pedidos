package com.vinicarnot.sistema_de_pedidos.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.vinicarnot.sistema_de_pedidos.entities.Cliente;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("{api.security.token.secret}")
    private String secret;

    public String generateToken(Cliente user) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("sistema-de-pedidos")
                    .withSubject(user.getEmail())
                    .withExpiresAt(generationExpirationDate())
                    .sign(algorithm);
            return token;
        }
        catch (JWTCreationException e) {
            throw new RuntimeException("Erro durante geração do token.", e);
        }

    }

    public String validateToken(String token) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("sistema-de-pedidos")
                    .build()
                    .verify(token)
                    .getSubject();
        }
        catch (JWTCreationException e) {
            return "";
        }
        
    }

    private Instant generationExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}

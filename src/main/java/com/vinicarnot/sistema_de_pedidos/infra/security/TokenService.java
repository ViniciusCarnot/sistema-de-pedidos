package com.vinicarnot.sistema_de_pedidos.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.vinicarnot.sistema_de_pedidos.dto.requests.JWTClienteDataDTO;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Cliente;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(Cliente user) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("sistema-de-pedidos")
                    .withClaim("clienteId", user.getId())
                    .withSubject(user.getEmail())
                    .withExpiresAt(generationExpirationDate())
                    .withIssuedAt(Instant.now())
                    .sign(algorithm);
        }
        catch (JWTCreationException e) {
            throw new RuntimeException("Erro durante geração do token.", e);
        }

    }

    public Optional<JWTClienteDataDTO> validateToken(String token) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .withIssuer("sistema-de-pedidos")
                    .build()
                    .verify(token);

            return Optional.of(JWTClienteDataDTO.builder()
                    .clienteId(decodedJWT.getClaim("clienteId").asLong())
                    .email(decodedJWT.getSubject())
                    .build());
        }
        catch (JWTVerificationException e) {
            return Optional.empty();
        }
        
    }

    private Instant generationExpirationDate() {
        return Instant.now().plus(2, ChronoUnit.HOURS);
    }

}

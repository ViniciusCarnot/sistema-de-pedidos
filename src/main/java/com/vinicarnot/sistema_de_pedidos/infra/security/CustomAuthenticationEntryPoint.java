package com.vinicarnot.sistema_de_pedidos.infra.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("sistema-de-pedidos/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        String json = String.format(
                "{\"timestamp\": \"%s\", \"status\": 401, \"error\": \"Unauthorized\", \"message\": \"Token inválido ou ausente. Autenticação é necessária.\"}",
                java.time.Instant.now()
        );
        response.getWriter().write(json);
    }
}

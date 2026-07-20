package com.vinicarnot.sistema_de_pedidos.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserUtil {

    public Optional<String> getClienteLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
        System.out.println("Claims disponíveis no token: " + jwtPrincipal.getClaims());
        return Optional.ofNullable(jwtPrincipal.getClaimAsString("username"));
    }

}

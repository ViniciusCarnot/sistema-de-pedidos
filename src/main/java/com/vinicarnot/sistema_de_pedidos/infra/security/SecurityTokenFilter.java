package com.vinicarnot.sistema_de_pedidos.infra.security;

import com.vinicarnot.sistema_de_pedidos.dto.requests.JWTClienteDataDTO;
import com.vinicarnot.sistema_de_pedidos.repositories.ClienteRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityTokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final ClienteRepository clienteRepository;

    public SecurityTokenFilter(TokenService tokenService, ClienteRepository clienteRepository) {
        this.tokenService = tokenService;
        this.clienteRepository = clienteRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring("Bearer ".length());
            Optional<JWTClienteDataDTO> jwtClienteDataDTO = tokenService.validateToken(token);
            if(jwtClienteDataDTO.isPresent()) {
                UserDetails cliente = clienteRepository.findByEmail(jwtClienteDataDTO.get().getEmail());
                if(cliente != null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(cliente, null, cliente.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            filterChain.doFilter(request, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }

}

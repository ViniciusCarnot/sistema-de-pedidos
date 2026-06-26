package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Cliente;
import com.vinicarnot.sistema_de_pedidos.repositories.ClienteRepository;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.ForbiddenException;
import com.vinicarnot.sistema_de_pedidos.util.CustomUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthService {

    private final ClienteService clienteService;

    public AuthService(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public void validateSelfOrAdmin(Long clienteId) {
        Cliente me = clienteService.autenticado();
        if (me.temRole("ROLE_ADMIN")) {
            return;
        }
        if(!me.getId().equals(clienteId)) {
            throw new ForbiddenException("Acesso negado. É necessário ser você mesmo ou admin.");
        }
    }

}

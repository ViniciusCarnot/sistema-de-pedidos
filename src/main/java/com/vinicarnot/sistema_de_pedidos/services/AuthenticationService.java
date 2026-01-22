package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.dto.ClienteLoginDTO;
import com.vinicarnot.sistema_de_pedidos.dto.ClienteRegistroDTO;
import com.vinicarnot.sistema_de_pedidos.dto.LoginResponseDTO;
import com.vinicarnot.sistema_de_pedidos.entities.Cliente;
import com.vinicarnot.sistema_de_pedidos.infra.security.TokenService;
import com.vinicarnot.sistema_de_pedidos.repositories.ClienteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final ClienteRepository clienteRepository;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    public AuthenticationService(ClienteRepository clienteRepository, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.clienteRepository = clienteRepository;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public ResponseEntity registroCliente(ClienteRegistroDTO dto) {
        if(clienteRepository.findByEmail(dto.getEmail()) != null) {
            return ResponseEntity.badRequest().build();
        }

        String senhaCriptografada = new BCryptPasswordEncoder().encode(dto.getSenha());
        Cliente novoCliente = new Cliente();
        novoCliente.setNome(dto.getNome());
        novoCliente.setEmail(dto.getEmail());
        novoCliente.setSenha(senhaCriptografada);
        novoCliente.setRole(dto.getRole());

        clienteRepository.save(novoCliente);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity loginCliente(ClienteLoginDTO dto) {
        UsernamePasswordAuthenticationToken dadosDoCliente = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha());

        var auth = authenticationManager.authenticate(dadosDoCliente);

        String token = tokenService.generateToken((Cliente) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

}

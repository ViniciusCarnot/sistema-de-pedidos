package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.dto.responses.LoginResponseDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.ClienteLoginRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.CreateClienteRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.CreateClienteResponseDTO;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Cliente;
import com.vinicarnot.sistema_de_pedidos.domain.enums.UserRole;
import com.vinicarnot.sistema_de_pedidos.infra.security.TokenService;
import com.vinicarnot.sistema_de_pedidos.repositories.ClienteRepository;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoJaExistenteException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(rollbackFor = Exception.class)
    public CreateClienteResponseDTO registroCliente(CreateClienteRequestDTO dtoRequest) {
        if(clienteRepository.findByEmail(dtoRequest.getEmail()) != null) {
            throw new RecursoJaExistenteException("Já existe uma conta cadastrada com esse email.");
        }

        String senhaCriptografada = new BCryptPasswordEncoder().encode(dtoRequest.getSenha());
        Cliente novoCliente = new Cliente();
        novoCliente.setNome(dtoRequest.getNome());
        novoCliente.setEmail(dtoRequest.getEmail());
        novoCliente.setSenha(senhaCriptografada);
        novoCliente.setRole(UserRole.NORMAL);

        return new CreateClienteResponseDTO(clienteRepository.save(novoCliente));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity loginCliente(ClienteLoginRequestDTO dtoRequest) {
        UsernamePasswordAuthenticationToken dadosDoCliente = new UsernamePasswordAuthenticationToken(dtoRequest.getEmail(), dtoRequest.getSenha());

        var auth = authenticationManager.authenticate(dadosDoCliente);

        String token = tokenService.generateToken((Cliente) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

}

package com.vinicarnot.sistema_de_pedidos.controllers;

import com.vinicarnot.sistema_de_pedidos.dto.requests.ClienteLoginRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.CreateClienteRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.CreateClienteResponseDTO;
import com.vinicarnot.sistema_de_pedidos.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/registro")
    public ResponseEntity<CreateClienteResponseDTO> registro(@RequestBody @Valid CreateClienteRequestDTO dtoRequest) {
        CreateClienteResponseDTO dtoResponse = authenticationService.registroCliente(dtoRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}")
                .buildAndExpand(dtoResponse.getId()).toUri();
        return ResponseEntity.created(uri).body(dtoResponse);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid ClienteLoginRequestDTO dtoRequest) {
        return authenticationService.loginCliente(dtoRequest);
    }

}

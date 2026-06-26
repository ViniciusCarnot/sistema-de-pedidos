package com.vinicarnot.sistema_de_pedidos.controllers;

import com.vinicarnot.sistema_de_pedidos.dto.requests.ClienteLoginRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.CreateClienteRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.CreateClienteResponseDTO;
import com.vinicarnot.sistema_de_pedidos.services.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    private final ClienteService clienteService;

    public AuthenticationController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("/registro")
    public ResponseEntity<CreateClienteResponseDTO> registrar(@RequestBody @Valid CreateClienteRequestDTO dtoRequest) {
        CreateClienteResponseDTO dtoResponse = clienteService.registrar(dtoRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}")
                .buildAndExpand(dtoResponse.getId()).toUri();
        return ResponseEntity.created(uri).body(dtoResponse);
    }

}

package com.vinicarnot.sistema_de_pedidos.controllers;

import com.vinicarnot.sistema_de_pedidos.dto.ClienteLoginDTO;
import com.vinicarnot.sistema_de_pedidos.dto.ClienteRegistroDTO;
import com.vinicarnot.sistema_de_pedidos.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity registro(@RequestBody @Valid ClienteRegistroDTO clienteRegistroDTO) {
        authenticationService.registroCliente(clienteRegistroDTO);
        return ResponseEntity.ok("Seu cadastro foi realizado com sucesso.");
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid ClienteLoginDTO clienteLoginDTO) {
        return authenticationService.loginCliente(clienteLoginDTO);
    }

}

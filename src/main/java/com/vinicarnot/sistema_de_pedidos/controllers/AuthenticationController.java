package com.vinicarnot.sistema_de_pedidos.controllers;

import com.vinicarnot.sistema_de_pedidos.dto.ClienteLoginDTO;
import com.vinicarnot.sistema_de_pedidos.dto.ClienteRegistroDTO;
import com.vinicarnot.sistema_de_pedidos.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/registro")
    public ResponseEntity<String> registro(@RequestBody @Valid ClienteRegistroDTO clienteRegistroDTO) {
        return authenticationService.registroCliente(clienteRegistroDTO);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid ClienteLoginDTO clienteLoginDTO) {
        return authenticationService.loginCliente(clienteLoginDTO);
    }

}

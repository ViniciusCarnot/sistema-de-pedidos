package com.vinicarnot.sistema_de_pedidos.controllers;

import com.vinicarnot.sistema_de_pedidos.dto.requests.UpdateClienteRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.UpdateClienteResponseDTO;
import com.vinicarnot.sistema_de_pedidos.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PutMapping("/me")
    public ResponseEntity<UpdateClienteResponseDTO> atualizarConta(@RequestBody @Valid UpdateClienteRequestDTO dto) {
        return ResponseEntity.ok().body(clienteService.atualizarDadosDoCliente(dto));
    }

}

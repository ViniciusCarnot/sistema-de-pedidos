package com.vinicarnot.sistema_de_pedidos.controllers;

import com.vinicarnot.sistema_de_pedidos.services.ClienteService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    /*
    @PreAuthorize("hasAnyRole('NORMAL', 'ADMIN')")
    @GetMapping("/me")
    public ResponseEntity<LerMinhaContaRespostaDTO> minhaConta() {
        return ResponseEntity.ok().body(clienteService.atualizarDadosDoCliente(dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<LerClienteRespostaDTO>

    @PreAuthorize("hasAnyRole('NORMAL', 'ADMIN')")
    @PutMapping("/me")
    public ResponseEntity<UpdateClienteResponseDTO> atualizarMinhaConta(@RequestBody @Valid UpdateClienteRequestDTO dto) {
        return ResponseEntity.ok().body(clienteService.atualizarDadosDoCliente(dto));
    }
    */
}

package com.vinicarnot.sistema_de_pedidos.controllers;

import com.vinicarnot.sistema_de_pedidos.dto.ClienteAtualizarDadosDTO;
import com.vinicarnot.sistema_de_pedidos.services.ContaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/conta")
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PutMapping("/me")
    public ResponseEntity<ClienteAtualizarDadosDTO> atualizarMinhaConta(@RequestBody @Valid ClienteAtualizarDadosDTO dto) {
        return ResponseEntity.ok().body(contaService.atualizarDadosDoCliente(dto));
    }

}

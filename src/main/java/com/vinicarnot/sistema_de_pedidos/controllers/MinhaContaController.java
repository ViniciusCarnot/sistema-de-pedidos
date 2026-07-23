package com.vinicarnot.sistema_de_pedidos.controllers;

import com.vinicarnot.sistema_de_pedidos.dto.requests.AtualizarMeuEnderecoRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.AtualizarMinhaContaRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.AtualizarTelefoneRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.*;
import com.vinicarnot.sistema_de_pedidos.services.ClienteService;
import com.vinicarnot.sistema_de_pedidos.services.EnderecoService;
import com.vinicarnot.sistema_de_pedidos.services.TelefoneService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/minha-conta")
public class MinhaContaController {

    private final ClienteService clienteService;
    private final EnderecoService enderecoService;

    public MinhaContaController(ClienteService clienteService, EnderecoService enderecoService) {
        this.clienteService = clienteService;
        this.enderecoService = enderecoService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_NORMAL', 'ROLE_ADMIN')")
    public ResponseEntity<LerMinhaContaRespostaDTO> verMinhaConta() {
        return ResponseEntity.ok(clienteService.verMinhaConta());
    }

    @GetMapping("/enderecos")
    @PreAuthorize("hasAnyRole('ROLE_NORMAL', 'ROLE_ADMIN')")
    public ResponseEntity<List<LerEnderecoRespostaDTO>> verMeusEnderecos() {
        return ResponseEntity.ok(enderecoService.verMeusEnderecos());
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_NORMAL', 'ROLE_ADMIN')")
    public ResponseEntity<AtualizarMinhaContaRespostaDTO> atualizarMinhaConta(@Valid @RequestBody AtualizarMinhaContaRequisicaoDTO dtoRequisicao) {
        return ResponseEntity.ok(clienteService.atualizarMinhaConta(dtoRequisicao));
    }

    @PutMapping("/enderecos")
    @PreAuthorize("hasAnyRole('ROLE_NORMAL', 'ROLE_ADMIN')")
    public ResponseEntity<Set<AtualizarMeuEnderecoRespostaDTO>> atualizarMeusEnderecos(@Valid @RequestBody List<AtualizarMeuEnderecoRequisicaoDTO> dtoRequisicao) {
        return ResponseEntity.ok(enderecoService.atualizarMeusEnderecos(dtoRequisicao));
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('ROLE_NORMAL', 'ROLE_ADMIN')")
    public ResponseEntity<Void> desativarMinhaConta() {
        clienteService.desativarMinhaConta();
        return ResponseEntity.noContent().build();
    }

}

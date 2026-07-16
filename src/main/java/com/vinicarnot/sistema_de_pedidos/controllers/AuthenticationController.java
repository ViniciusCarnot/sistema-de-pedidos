package com.vinicarnot.sistema_de_pedidos.controllers;

import com.vinicarnot.sistema_de_pedidos.dto.requests.CriarClienteRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.CriarClienteRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.services.ClienteService;
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

    @PostMapping("/cadastro")
    public ResponseEntity<CriarClienteRespostaDTO> cadastrarCliente(@RequestBody @Valid CriarClienteRequisicaoDTO dtoRequisicao) {
        CriarClienteRespostaDTO dtoResposta = clienteService.cadastrarCliente(dtoRequisicao);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}")
                .buildAndExpand(dtoResposta.getId()).toUri();
        return ResponseEntity.created(uri).body(dtoResposta);
    }

}

package com.vinicarnot.sistema_de_pedidos.controllers;

import com.vinicarnot.sistema_de_pedidos.dto.requests.CriarPedidoRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.PedidoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.services.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PreAuthorize("hasAnyRole('NORMAL', 'ADMIN')")
    @PostMapping
    public ResponseEntity<PedidoRespostaDTO> realizarPedido(@RequestBody CriarPedidoRequisicaoDTO dtoRequisicao) {
        PedidoRespostaDTO dtoResposta = pedidoService.realizarPedido(dtoRequisicao);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idPedido}").buildAndExpand(dtoResposta.getPedidoId()).toUri();
        return ResponseEntity.created(uri).body(dtoResposta);
    }


}

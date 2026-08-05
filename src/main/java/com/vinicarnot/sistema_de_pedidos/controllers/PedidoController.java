package com.vinicarnot.sistema_de_pedidos.controllers;

import com.vinicarnot.sistema_de_pedidos.dto.requests.CriarPagamentoRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.CriarPedidoRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.CriarPagamentoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.PedidoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.services.PagamentoService;
import com.vinicarnot.sistema_de_pedidos.services.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final PagamentoService pagamentoService;

    public PedidoController(PedidoService pedidoService, PagamentoService pagamentoService) {
        this.pedidoService = pedidoService;
        this.pagamentoService = pagamentoService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('NORMAL', 'ADMIN')")
    public ResponseEntity<PedidoRespostaDTO> realizarPedido(@Valid @RequestBody CriarPedidoRequisicaoDTO dtoRequisicao) {
        PedidoRespostaDTO dtoResposta = pedidoService.realizarPedido(dtoRequisicao);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idPedido}").buildAndExpand(dtoResposta.getPedidoId()).toUri();
        return ResponseEntity.created(uri).body(dtoResposta);
    }

    @PostMapping("/{pedidoId}/pagamento")
    @PreAuthorize("hasAnyRole('NORMAL', 'ADMIN')")
    public ResponseEntity<CriarPagamentoRespostaDTO> realizarPagamentoDoPedido(@PathVariable Long pedidoId, @Valid @RequestBody CriarPagamentoRequisicaoDTO dtoRequiscao) {
        CriarPagamentoRespostaDTO dtoResposta = pagamentoService.realizarPagamentoDoPedido(pedidoId, dtoRequiscao);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{pedidoId}").buildAndExpand(dtoResposta.getId()).toUri();
        return ResponseEntity.created(uri).body(dtoResposta);
    }


}

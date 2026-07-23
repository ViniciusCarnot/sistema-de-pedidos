package com.vinicarnot.sistema_de_pedidos.controllers;

import com.vinicarnot.sistema_de_pedidos.dto.requests.AtualizarPedidoRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.LerPedidoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.PedidoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.services.PedidoService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/meus-pedidos")
public class MeusPedidosController {

    private final PedidoService pedidoService;

    public MeusPedidosController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("/{pedidoId}")
    @PreAuthorize("hasAnyRole('ROLE_NORMAL', 'ROLE_ADMIN')")
    public ResponseEntity<PedidoRespostaDTO> verMeuPedido(@PathVariable Long pedidoId) {
        return ResponseEntity.ok(pedidoService.verMeuPedido(pedidoId));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_NORMAL', 'ROLE_ADMIN')")
    public ResponseEntity<Page<PedidoRespostaDTO>> verMeusPedidos(@PageableDefault(page = 0, size = 12, sort = "instanteDaCompra") Pageable pageable) {
        return ResponseEntity.ok(pedidoService.verMeusPedidos(pageable));
    }

    @PutMapping("/{pedidoId}")
    @PreAuthorize("hasAnyRole('ROLE_NORMAL', 'ROLE_ADMIN')")
    public ResponseEntity<PedidoRespostaDTO> atualizarMeuPedido(@PathVariable Long pedidoId, @RequestBody @Valid AtualizarPedidoRequisicaoDTO dtoRequisicao) {
        return ResponseEntity.ok(pedidoService.atualizarMeuPedido(pedidoId, dtoRequisicao));
    }

    @PutMapping("/{pedidoId}/cancelamento")
    @PreAuthorize("hasAnyRole('ROLE_NORMAL', 'ROLE_ADMIN')")
    public ResponseEntity<PedidoRespostaDTO> cancelarMeuPedido(@PathVariable Long pedidoId) {
        return ResponseEntity.ok(pedidoService.cancelarMeuPedido(pedidoId));
    }

}

package com.vinicarnot.sistema_de_pedidos.controllers;

import com.vinicarnot.sistema_de_pedidos.dto.requests.CreatePedidoRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.UpdatePedidoRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.CreatePedidoResponseDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.ReadPedidoResponseDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.UpdatePedidoResponseDTO;
import com.vinicarnot.sistema_de_pedidos.services.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<CreatePedidoResponseDTO> realizarPedido(@RequestBody @Valid CreatePedidoRequestDTO dtoRequest) {
        CreatePedidoResponseDTO createPedidoResponseDTO = pedidoService.realizarPedido(dtoRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createPedidoResponseDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(createPedidoResponseDTO);
    }

    @PreAuthorize("hasAnyRole('NORMAL', 'ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelarProprioPedido(@PathVariable Long id) {
        pedidoService.cancelarProprioPedido(id);
        return ResponseEntity.ok("Pedido cancelado com sucesso.");
    }

    @PreAuthorize("hasAnyRole('NORMAL', 'ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UpdatePedidoResponseDTO> atualizarProprioPedido(@PathVariable Long id, @RequestBody @Valid UpdatePedidoRequestDTO dtoRequest) {
        return ResponseEntity.ok(pedidoService.atualizarProprioPedido(id, dtoRequest));
    }

    @PreAuthorize("hasAnyRole('NORMAL', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ReadPedidoResponseDTO> lerProprioPedido(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.lerProprioPedido(id));
    }

    @PreAuthorize("hasAnyRole('NORMAL', 'ADMIN')")
    @GetMapping
    public ResponseEntity<Page<ReadPedidoResponseDTO>> lerPropriosPedidos(Pageable pageable) {
        return ResponseEntity.ok(pedidoService.lerPropriosPedidos(pageable));
    }

}

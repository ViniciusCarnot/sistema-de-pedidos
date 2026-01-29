package com.vinicarnot.sistema_de_pedidos.controllers;

import com.vinicarnot.sistema_de_pedidos.dto.requests.CreatePedidoRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.UpdatePedidoRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.CreatePedidoResponseDTO;
import com.vinicarnot.sistema_de_pedidos.dto.PedidoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.UpdatePedidoResponseDTO;
import com.vinicarnot.sistema_de_pedidos.services.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<CreatePedidoResponseDTO> realizarPedido(@RequestBody @Valid CreatePedidoRequestDTO dtoRequest) {
        CreatePedidoResponseDTO createPedidoResponseDTO = pedidoService.realizarPedido(dtoRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createPedidoResponseDTO.getPedidoId()).toUri();
        return ResponseEntity.created(uri).body(createPedidoResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdatePedidoResponseDTO> atualizarPedido(@PathVariable Long id, @RequestBody @Valid UpdatePedidoRequestDTO dtoRequest) {
        return ResponseEntity.ok(pedidoService.atualizarPedido(id, dtoRequest));
    }

}

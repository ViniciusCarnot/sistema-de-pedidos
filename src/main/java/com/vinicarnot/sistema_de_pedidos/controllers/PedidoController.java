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

    @Operation(summary = "Usuário realiza pedido.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Erro: login inválido ou login expirado."),
            @ApiResponse(responseCode = "403", description = "Erro: falha na autorização. Usuário sem as permissões necessárias."),
            @ApiResponse(responseCode = "404", description = "Erro: não foi possível encontrar um usuário com as credenciais informadas."),
            @ApiResponse(responseCode = "422", description = "Erro: dados do usuário incompletos."),
            @ApiResponse(responseCode = "404", description = "Erro: não foi possível encontrar uma cidade com o id informado."),
            @ApiResponse(responseCode = "404", description = "Erro: não foi possível encontrar um estado com o id informado."),
            @ApiResponse(responseCode = "404", description = "Erro: não foi possível encontrar um produto da lista de items, com o id informado."),
            @ApiResponse(responseCode = "400", description = "Erro: falha ao criar forma de pagamento. Forma de pagamento inválida."),
            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso.")
    })
    @PreAuthorize("hasAnyRole('NORMAL', 'ADMIN')")
    @PostMapping
    public ResponseEntity<CreatePedidoResponseDTO> realizarPedido(@RequestBody @Valid CreatePedidoRequestDTO dtoRequest) {
        CreatePedidoResponseDTO createPedidoResponseDTO = pedidoService.realizarPedido(dtoRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createPedidoResponseDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(createPedidoResponseDTO);
    }

    @Operation(summary = "Usuário cancela próprio pedido.", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Erro: login inválido ou login expirado."),
            @ApiResponse(responseCode = "403", description = "Erro: falha na autorização. Usuário sem as permissões necessárias."),
            @ApiResponse(responseCode = "404", description = "Erro: não foi possível encontrar um pedido com o id informado."),
            @ApiResponse(responseCode = "404", description = "Erro: não foi possível encontrar um usuário com as credenciais informadas."),
            @ApiResponse(responseCode = "401", description = "Erro: usuário não é dono do pedido."),
            @ApiResponse(responseCode = "400", description = "Erro: falha ao cancelar pedido, devido ao mesmo já ter sido entregue."),
            @ApiResponse(responseCode = "200", description = "Pedido cancelado com sucesso.")
    })
    @PreAuthorize("hasAnyRole('NORMAL', 'ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelarProprioPedido(@PathVariable Long id) {
        pedidoService.cancelarProprioPedido(id);
        return ResponseEntity.ok("Pedido cancelado com sucesso.");
    }

    @Operation(summary = "Usuário atualiza próprio pedido.", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Erro: login inválido ou login expirado."),
            @ApiResponse(responseCode = "403", description = "Erro: falha na autorização. Usuário sem as permissões necessárias."),
            @ApiResponse(responseCode = "404", description = "Erro: não foi possível encontrar um pedido com o id informado."),
            @ApiResponse(responseCode = "404", description = "Erro: não foi possível encontrar um usuário com as credenciais informadas."),
            @ApiResponse(responseCode = "401", description = "Erro: usuário não é dono do pedido."),
            @ApiResponse(responseCode = "400", description = "Erro: falha ao criar forma de pagamento. Forma de pagamento inválida."),
            @ApiResponse(responseCode = "404", description = "Erro: não foi possível encontrar uma cidade com o id informado."),
            @ApiResponse(responseCode = "404", description = "Erro: não foi possível encontrar um estado com o id informado."),
            @ApiResponse(responseCode = "200", description = "Pedido atualizado com sucesso.")
    })
    @PreAuthorize("hasAnyRole('NORMAL', 'ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UpdatePedidoResponseDTO> atualizarProprioPedido(@PathVariable Long id, @RequestBody @Valid UpdatePedidoRequestDTO dtoRequest) {
        return ResponseEntity.ok(pedidoService.atualizarProprioPedido(id, dtoRequest));
    }

    @Operation(summary = "Retorna dados, para um usuário, do próprio pedido.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Erro: login inválido ou login expirado."),
            @ApiResponse(responseCode = "403", description = "Erro: falha na autorização. Usuário sem as permissões necessárias."),
            @ApiResponse(responseCode = "404", description = "Erro: não foi possível encontrar um pedido com o id informado."),
            @ApiResponse(responseCode = "404", description = "Erro: não foi possível encontrar um usuário com as credenciais informadas."),
            @ApiResponse(responseCode = "401", description = "Erro: usuário não é dono do pedido."),
            @ApiResponse(responseCode = "200", description = "Dados do pedido recuperados com sucesso.")
    })
    @PreAuthorize("hasAnyRole('NORMAL', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ReadPedidoResponseDTO> lerProprioPedido(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.lerProprioPedido(id));
    }

    @Operation(summary = "Retorna dados, para um usuário, dos próprios pedidos.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Erro: login inválido ou login expirado."),
            @ApiResponse(responseCode = "403", description = "Erro: falha na autorização. Usuário sem as permissões necessárias."),
            @ApiResponse(responseCode = "404", description = "Erro: não foi possível encontrar um usuário com as credenciais informadas."),
            @ApiResponse(responseCode = "200", description = "Dados dos pedidos recuperados com sucesso.")
    })
    @PreAuthorize("hasAnyRole('NORMAL', 'ADMIN')")
    @GetMapping
    public ResponseEntity<Page<ReadPedidoResponseDTO>> lerPropriosPedidos(Pageable pageable) {
        return ResponseEntity.ok(pedidoService.lerPropriosPedidos(pageable));
    }

}

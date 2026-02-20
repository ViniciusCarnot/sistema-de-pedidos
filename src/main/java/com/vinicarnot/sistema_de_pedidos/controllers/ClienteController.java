package com.vinicarnot.sistema_de_pedidos.controllers;

import com.vinicarnot.sistema_de_pedidos.dto.requests.UpdateClienteRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.UpdateClienteResponseDTO;
import com.vinicarnot.sistema_de_pedidos.services.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(summary = "Usuário atualiza próprio conta.", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Erro: login inválido ou login expirado."),
            @ApiResponse(responseCode = "403", description = "Erro: falha na autorização. Usuário sem as permissões necessárias."),
            @ApiResponse(responseCode = "404", description = "Erro: não foi possível encontrar um usuário com as credenciais informadas."),
            @ApiResponse(responseCode = "404", description = "Erro: não foi possível encontrar uma cidade com o id informado."),
            @ApiResponse(responseCode = "404", description = "Erro: não foi possível encontrar um estado com o id informado."),
            @ApiResponse(responseCode = "201", description = "Conta atualizada com sucesso.")
    })
    @PreAuthorize("hasAnyRole('NORMAL', 'ADMIN')")
    @PutMapping("/me")
    public ResponseEntity<UpdateClienteResponseDTO> atualizarConta(@RequestBody @Valid UpdateClienteRequestDTO dto) {
        return ResponseEntity.ok().body(clienteService.atualizarDadosDoCliente(dto));
    }

}

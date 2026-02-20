package com.vinicarnot.sistema_de_pedidos.controllers;

import com.vinicarnot.sistema_de_pedidos.dto.responses.ReadProdutoResponseDTO;
import com.vinicarnot.sistema_de_pedidos.services.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Operation(summary = "Retorna dados de um produto para um usuário não autenticado.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Erro: produto não encontrado com o id informado."),
            @ApiResponse(responseCode = "200", description = "Dados do produto recuperados com sucesso.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReadProdutoResponseDTO> lerProduto(@PathVariable Long id) {
        return ResponseEntity.ok().body(produtoService.lerProduto(id));
    }

    @Operation(summary = "Retorna dados dos produtos disponíveis para um usuário não autenticado.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados do produto recuperados com sucesso.")
    })
    @GetMapping
    public ResponseEntity<Page<ReadProdutoResponseDTO>> lerProdutos(Pageable pageable) {
        return ResponseEntity.ok().body(produtoService.lerProdutos(pageable));
    }

}

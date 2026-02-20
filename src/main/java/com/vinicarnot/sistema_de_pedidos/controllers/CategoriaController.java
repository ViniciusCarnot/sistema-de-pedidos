package com.vinicarnot.sistema_de_pedidos.controllers;

import com.vinicarnot.sistema_de_pedidos.dto.responses.ReadCategoriaResponseDTO;
import com.vinicarnot.sistema_de_pedidos.services.CategoriaService;
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
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @Operation(summary = "Retorna dados de uma categoria e de seus produtos " +
            "- que estejam disponíveis - para um usuário não autenticado.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada com esse id."),
            @ApiResponse(responseCode = "200", description = "Dados da categoria e de seus produtos, recuperados com sucesso.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReadCategoriaResponseDTO> lerCategoria(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.lerCategoria(id));
    }

    @Operation(summary = "Retorna dados das categorias e de seus produtos " +
            "- que estejam disponíveis - para um usuário não autenticado.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados da categoria e de seus produtos, recuperados com sucesso.")
    })
    @GetMapping
    public ResponseEntity<Page<ReadCategoriaResponseDTO>> lerCategorias(Pageable pageable) {
        return ResponseEntity.ok().body(categoriaService.lerCategorias(pageable));
    }
}

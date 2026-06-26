package com.vinicarnot.sistema_de_pedidos.controllers;

import com.vinicarnot.sistema_de_pedidos.dto.requests.CreateProdutoRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.UpdateProdutoRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.CreateProdutoResponseDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.ReadProdutoResponseAdminDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.ReadProdutoResponseDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.UpdateProdutoResponseDTO;
import com.vinicarnot.sistema_de_pedidos.services.ProdutoService;
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
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/produtos")
    public ResponseEntity<CreateProdutoResponseDTO> adicionarProduto(@Valid @RequestBody CreateProdutoRequestDTO dtoRequest) {
        CreateProdutoResponseDTO dtoResponse = produtoService.adicionarProduto(dtoRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dtoResponse.getId()).toUri();
        return ResponseEntity.created(uri).body(dtoResponse);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/produtos/{id}")
    public ResponseEntity<String> removerProduto(@PathVariable Long id) {
        produtoService.removerProduto(id);
        return ResponseEntity.ok("Produto deletado com sucesso.");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/produtos/{id}")
    public ResponseEntity<UpdateProdutoResponseDTO> atualizarProduto(@PathVariable Long id, @Valid @RequestBody UpdateProdutoRequestDTO dtoRequest) {
        return ResponseEntity.ok(produtoService.atualizarProduto(id, dtoRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadProdutoResponseDTO> lerProduto(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.lerProduto(id));
    }

    @GetMapping
    public ResponseEntity<Page<ReadProdutoResponseDTO>> lerProdutos(
            @RequestParam(name = "nome", defaultValue = "") String nome,
            @RequestParam(name = "precoMaximo", defaultValue = "12000.00") String precoMaximo,
            Pageable pageable
    ) {
        return ResponseEntity.ok(produtoService.lerProdutos(nome, precoMaximo, pageable));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/produtos/{id}")
    public ResponseEntity<ReadProdutoResponseAdminDTO> adminLerProduto(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.adminLerProduto(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/produtos")
    public ResponseEntity<Page<ReadProdutoResponseAdminDTO>> adminLerProdutos(Pageable pageable) {
        return ResponseEntity.ok(produtoService.adminLerProdutos(pageable));
    }

}

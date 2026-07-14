package com.vinicarnot.sistema_de_pedidos.controllers;

import com.vinicarnot.sistema_de_pedidos.dto.requests.CreateProdutoRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.UpdateProdutoRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.CreateProdutoResponseDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.ReadProdutoResponseAdminDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.LerProdutoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.UpdateProdutoResponseDTO;
import com.vinicarnot.sistema_de_pedidos.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LerProdutoRespostaDTO> lerProduto(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.lerProduto(id));
    }

    @GetMapping
    public ResponseEntity<Page<LerProdutoRespostaDTO>> lerProdutos(
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "precoMaximo", required = false) BigDecimal precoMaximo,
            @RequestParam(value = "categoriasIds", required = false) List<Long> categoriasIds,
            @PageableDefault(page = 0, size = 12, sort = "preco") Pageable pageable
            ) {
        return ResponseEntity.ok(produtoService.lerProdutos(nome, precoMaximo, categoriasIds, pageable));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<CreateProdutoResponseDTO> adicionarProduto(@Valid @RequestBody CreateProdutoRequestDTO dtoRequest) {
        CreateProdutoResponseDTO dtoResponse = produtoService.adicionarProduto(dtoRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dtoResponse.getId()).toUri();
        return ResponseEntity.created(uri).body(dtoResponse);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removerProduto(@PathVariable Long id) {
        produtoService.removerProduto(id);
        return ResponseEntity.ok("Produto deletado com sucesso.");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UpdateProdutoResponseDTO> atualizarProduto(@PathVariable Long id, @Valid @RequestBody UpdateProdutoRequestDTO dtoRequest) {
        return ResponseEntity.ok(produtoService.atualizarProduto(id, dtoRequest));
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

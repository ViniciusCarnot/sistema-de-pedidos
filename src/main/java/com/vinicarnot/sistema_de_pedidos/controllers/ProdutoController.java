package com.vinicarnot.sistema_de_pedidos.controllers;

import com.vinicarnot.sistema_de_pedidos.dto.ProdutoDTO;
import com.vinicarnot.sistema_de_pedidos.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<ProdutoDTO> adicionarProduto(@Valid @RequestBody ProdutoDTO dto) {
        ProdutoDTO dtoResponse = produtoService.adicionarProduto(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{nome}")
                .buildAndExpand(dtoResponse.getNome()).toUri();
        return ResponseEntity.created(uri).body(dtoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removerProduto(@PathVariable Long id) {
        produtoService.removerProduto(id);
        return ResponseEntity.ok().body("Produto deletado com sucesso.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> lerProduto(@PathVariable Long id) {
        return ResponseEntity.ok().body(produtoService.lerProduto(id));
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> lerProdutos(Pageable pageable) {
        return ResponseEntity.ok().body(produtoService.lerProdutos(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> atualizarProduto(@PathVariable Long id, @Valid @RequestBody ProdutoDTO dto) {
        return ResponseEntity.ok().body(produtoService.atualizarProduto(id, dto));
    }

}

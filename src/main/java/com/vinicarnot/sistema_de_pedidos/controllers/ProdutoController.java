package com.vinicarnot.sistema_de_pedidos.controllers;

import com.vinicarnot.sistema_de_pedidos.dto.ProdutoDTO;
import com.vinicarnot.sistema_de_pedidos.services.ProdutoService;
import jakarta.validation.Valid;
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
                .buildAndExpand(dto.getNome()).toUri();
        return ResponseEntity.created(uri).body(dtoResponse);
    }

    @DeleteMapping("/{nome}")
    public ResponseEntity<String> removerProduto(@PathVariable String nome) {
        produtoService.removerProduto(nome);
        return ResponseEntity.ok().body("Produto deletado com sucesso.");
    }

    @GetMapping("/{nome}")
    public ResponseEntity<ProdutoDTO> lerProduto(@PathVariable String nome) {
        return ResponseEntity.ok().body(produtoService.lerProduto(nome));
    }

}

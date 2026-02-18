package com.vinicarnot.sistema_de_pedidos.controllers;

import com.vinicarnot.sistema_de_pedidos.dto.responses.ReadProdutoResponseDTO;
import com.vinicarnot.sistema_de_pedidos.services.ProdutoService;
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

    @GetMapping("/{id}")
    public ResponseEntity<ReadProdutoResponseDTO> lerProduto(@PathVariable Long id) {
        return ResponseEntity.ok().body(produtoService.lerProduto(id));
    }

    @GetMapping
    public ResponseEntity<Page<ReadProdutoResponseDTO>> lerProdutos(Pageable pageable) {
        return ResponseEntity.ok().body(produtoService.lerProdutos(pageable));
    }

}

package com.vinicarnot.sistema_de_pedidos.controllers;

import com.vinicarnot.sistema_de_pedidos.dto.ProdutoDTO;
import com.vinicarnot.sistema_de_pedidos.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping("/adicionar")
    public ResponseEntity<ProdutoDTO> adicionarProduto(@Valid @RequestBody ProdutoDTO dto) {
        ProdutoDTO dtoResponse = produtoService.adicionarProduto(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{nome}")
                .buildAndExpand(dto.getNome()).toUri();
        return ResponseEntity.created(uri).body(dtoResponse);
    }

}

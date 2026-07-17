package com.vinicarnot.sistema_de_pedidos.controllers;

import com.vinicarnot.sistema_de_pedidos.dto.responses.LerProdutoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.services.ProdutoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/{idProduto}")
    public ResponseEntity<LerProdutoRespostaDTO> lerProduto(@PathVariable Long idProduto) {
        return ResponseEntity.ok(produtoService.lerProduto(idProduto));
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

}

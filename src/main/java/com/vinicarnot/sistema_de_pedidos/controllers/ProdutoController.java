package com.vinicarnot.sistema_de_pedidos.controllers;

import com.vinicarnot.sistema_de_pedidos.dto.requests.CriarProdutoRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.AtualizarProdutoRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.CriarProdutoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.LerProdutoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.AtualizarProdutoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<CriarProdutoRespostaDTO> adicionarProduto(@Valid @RequestBody CriarProdutoRequisicaoDTO dtoRequest) {
        CriarProdutoRespostaDTO dtoResponse = produtoService.adicionarProduto(dtoRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dtoResponse.getId()).toUri();
        return ResponseEntity.created(uri).body(dtoResponse);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{idProduto}")
    public ResponseEntity<AtualizarProdutoRespostaDTO> atualizarProduto(@PathVariable Long idProduto, @Valid @RequestBody AtualizarProdutoRequisicaoDTO dtoRequest) {
        return ResponseEntity.ok(produtoService.atualizarProduto(idProduto, dtoRequest));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removerProduto(@PathVariable Long id) {
        produtoService.removerProduto(id);
        return ResponseEntity.ok("Produto deletado com sucesso.");
    }

}

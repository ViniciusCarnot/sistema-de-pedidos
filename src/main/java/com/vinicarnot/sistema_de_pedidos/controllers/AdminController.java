package com.vinicarnot.sistema_de_pedidos.controllers;

import com.vinicarnot.sistema_de_pedidos.dto.requests.*;
import com.vinicarnot.sistema_de_pedidos.dto.responses.*;
import com.vinicarnot.sistema_de_pedidos.services.CategoriaService;
import com.vinicarnot.sistema_de_pedidos.services.PedidoService;
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
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    private final ProdutoService produtoService;
    private final CategoriaService categoriaService;
    private final PedidoService pedidoService;

    public AdminController(ProdutoService produtoService, CategoriaService categoriaService, PedidoService pedidoService) {
        this.produtoService = produtoService;
        this.categoriaService = categoriaService;
        this.pedidoService = pedidoService;
    }

    @GetMapping("/produtos/{idProduto}")
    public ResponseEntity<AdminLerProdutoRespostaDTO> adminLerProduto(@PathVariable Long idProduto) {
        return ResponseEntity.ok(produtoService.adminLerProduto(idProduto));
    }

    @GetMapping("/produtos")
    public ResponseEntity<Page<AdminLerProdutoRespostaDTO>> adminLerProdutos(
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "precoMaximo", required = false) BigDecimal precoMaximo,
            @RequestParam(value = "categoriasIds", required = false) List<Long> categoriasIds,
            @PageableDefault(page = 0, size = 12, sort = "preco") Pageable pageable
    ) {
        return ResponseEntity.ok(produtoService.adminLerProdutos(nome, precoMaximo, categoriasIds, pageable));
    }

    @PostMapping("/produtos")
    public ResponseEntity<AdminCriarProdutoRespostaDTO> adminAdicionarProduto(@Valid @RequestBody AdminCriarProdutoRequisicaoDTO dtoRequisicao) {
        AdminCriarProdutoRespostaDTO dtoResposta = produtoService.adminAdicionarProduto(dtoRequisicao);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dtoResposta.getId()).toUri();
        return ResponseEntity.created(uri).body(dtoResposta);
    }

    @PutMapping("/produtos/{idProduto}")
    public ResponseEntity<AdminAtualizarProdutoRespostaDTO> adminAtualizarProduto(@PathVariable Long idProduto, @Valid @RequestBody AdminAtualizarProdutoRequisicaoDTO dtoRequisicao) {
        return ResponseEntity.ok(produtoService.adminAtualizarProduto(idProduto, dtoRequisicao));
    }

    @DeleteMapping("/produtos/{idProduto}")
    public ResponseEntity<Void> adminRemoverProduto(@PathVariable Long idProduto) {
        produtoService.adminRemoverProduto(idProduto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categorias/{idCategoria}/produtos")
    public ResponseEntity<Page<AdminLerProdutoRespostaDTO>> adminLerProdutosDeUmaCategoria(@PathVariable Long idCategoria, @PageableDefault(page = 0, size = 12, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(categoriaService.adminLerProdutosDeUmaCategoria(idCategoria, pageable));
    }


    @PostMapping("/categorias")
    public ResponseEntity<AdminCriarCategoriaRespostaDTO> adminAdicionarCategoria(@Valid @RequestBody AdminCriarCategoriaRequisicaoDTO dtoRequisicao) {
        AdminCriarCategoriaRespostaDTO dtoResposta = categoriaService.adminAdicionarCategoria(dtoRequisicao);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dtoResposta.getId()).toUri();
        return ResponseEntity.created(uri).body(dtoResposta);
    }

    @PutMapping("/categorias/{idCategoria}")
    public ResponseEntity<AdminAtualizarCategoriaRespostaDTO> adminAtualizarCategoria(@PathVariable Long idCategoria, @Valid @RequestBody AdminAtualizarCategoriaRequisicaoDTO dtoRequisicao) {
        return ResponseEntity.ok(categoriaService.adminAtualizarCategoria(idCategoria, dtoRequisicao));
    }

    @DeleteMapping("/categorias/{idCategoria}")
    public ResponseEntity<Void> adminRemoverCategoria(@PathVariable Long idCategoria) {
        categoriaService.adminRemoverCategoria(idCategoria);
        return ResponseEntity.noContent().build();
    }

    /*

    @DeleteMapping("/pedidos/{id}")
    public ResponseEntity<String> adminCancelarPedido(@PathVariable Long id) {
        pedidoService.adminCancelarPedido(id);
        return ResponseEntity.ok("Pedido cancelado com sucesso.");
    }

    @PutMapping("/pedidos/{id}")
    public ResponseEntity<UpdatePedidoResponseAdminDTO> adminAtualizarPedido(@PathVariable Long id, @Valid @RequestBody UpdatePedidoRequestDTO dtoRequest) {
        return ResponseEntity.ok(pedidoService.adminAtualizarPedido(id, dtoRequest));
    }

    @GetMapping("/pedidos/{id}")
    public ResponseEntity<ReadPedidoResponseAdminDTO> adminLerPedido(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.adminLerPedido(id));
    }

    @GetMapping("/pedidos")
    public ResponseEntity<Page<ReadPedidoResponseAdminDTO>> adminLerPedidos(Pageable pageable) {
        return ResponseEntity.ok(pedidoService.adminLerPedidos(pageable));
    }
    */

}

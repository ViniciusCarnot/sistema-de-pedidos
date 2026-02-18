package com.vinicarnot.sistema_de_pedidos.controllers;

import com.vinicarnot.sistema_de_pedidos.dto.requests.*;
import com.vinicarnot.sistema_de_pedidos.dto.responses.*;
import com.vinicarnot.sistema_de_pedidos.services.CategoriaService;
import com.vinicarnot.sistema_de_pedidos.services.PedidoService;
import com.vinicarnot.sistema_de_pedidos.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final ProdutoService produtoService;
    private final CategoriaService categoriaService;
    private final PedidoService pedidoService;

    public AdminController(ProdutoService produtoService, CategoriaService categoriaService, PedidoService pedidoService) {
        this.produtoService = produtoService;
        this.categoriaService = categoriaService;
        this.pedidoService = pedidoService;
    }

    @PostMapping("/produtos")
    public ResponseEntity<CreateProdutoResponseDTO> adicionarProduto(@Valid @RequestBody CreateProdutoRequestDTO dtoRequest) {
        CreateProdutoResponseDTO dtoResponse = produtoService.adicionarProduto(dtoRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dtoResponse.getId()).toUri();
        return ResponseEntity.created(uri).body(dtoResponse);
    }

    @DeleteMapping("/produtos/{id}")
    public ResponseEntity<String> removerProduto(@PathVariable Long id) {
        produtoService.removerProduto(id);
        return ResponseEntity.ok("Produto deletado com sucesso.");
    }

    @PutMapping("/produtos/{id}")
    public ResponseEntity<UpdateProdutoResponseDTO> atualizarProduto(@PathVariable Long id, @Valid @RequestBody UpdateProdutoRequestDTO dtoRequest) {
        return ResponseEntity.ok(produtoService.atualizarProduto(id, dtoRequest));
    }

    @GetMapping("/produtos/{id}")
    public ResponseEntity<ReadProdutoResponseAdminDTO> adminLerProduto(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.adminLerProduto(id));
    }

    @GetMapping("/produtos")
    public ResponseEntity<Page<ReadProdutoResponseAdminDTO>> adminLerProdutos(Pageable pageable) {
        return ResponseEntity.ok(produtoService.adminLerProdutos(pageable));
    }

    @PostMapping("/categorias")
    public ResponseEntity<CreateCategoriaResponseDTO> adicionarCategoria(@Valid @RequestBody CreateCategoriaRequestDTO dtoRequest) {
        CreateCategoriaResponseDTO dtoResponse = categoriaService.adicionarCategoria(dtoRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{nome}").buildAndExpand(dtoResponse.getNome()).toUri();
        return ResponseEntity.created(uri).body(dtoResponse);
    }

    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<String> removerCategoria(@PathVariable Long id) {
        categoriaService.removerCategoria(id);
        return ResponseEntity.ok("Categoria removida com sucesso.");
    }

    @PutMapping("/categorias/{id}")
    public ResponseEntity<UpdateCategoriaResponseDTO> atualizarCategoria(@PathVariable Long id, @Valid @RequestBody UpdateCategoriaRequestDTO dtoRequest) {
        return ResponseEntity.ok(categoriaService.atualizarCategoria(id, dtoRequest));
    }

    @GetMapping("/categorias/{id}")
    public ResponseEntity<ReadCategoriaResponseAdminDTO> adminLerCategoria(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.adminLerCategoria(id));
    }

    @GetMapping("/categorias")
    public ResponseEntity<Page<ReadCategoriaResponseAdminDTO>> adminLerCategorias(Pageable pageable) {
        return ResponseEntity.ok(categoriaService.adminLerCategorias(pageable));
    }

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

}

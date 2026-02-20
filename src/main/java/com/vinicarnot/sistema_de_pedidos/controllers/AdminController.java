package com.vinicarnot.sistema_de_pedidos.controllers;

import com.vinicarnot.sistema_de_pedidos.dto.requests.*;
import com.vinicarnot.sistema_de_pedidos.dto.responses.*;
import com.vinicarnot.sistema_de_pedidos.services.CategoriaService;
import com.vinicarnot.sistema_de_pedidos.services.PedidoService;
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

    @Operation(summary = "Usuário 'admin' adiciona um produto.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Erro: login inválido ou login expirado."),
            @ApiResponse(responseCode = "403", description = "Erro: falha na autorização. Usuário sem as permissões necessárias."),
            @ApiResponse(responseCode = "422", description = "Erro: dados inválidos."),
            @ApiResponse(responseCode = "400", description = "Erro: falha ao adicionar um produto. Já existe um produto com o mesmo nome."),
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso.")
    })
    @PostMapping("/produtos")
    public ResponseEntity<CreateProdutoResponseDTO> adicionarProduto(@Valid @RequestBody CreateProdutoRequestDTO dtoRequest) {
        CreateProdutoResponseDTO dtoResponse = produtoService.adicionarProduto(dtoRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dtoResponse.getId()).toUri();
        return ResponseEntity.created(uri).body(dtoResponse);
    }

    @Operation(summary = "Usuário 'admin' remove um produto.", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Erro: login inválido ou login expirado."),
            @ApiResponse(responseCode = "403", description = "Erro: falha na autorização. Usuário sem as permissões necessárias."),
            @ApiResponse(responseCode = "404", description = "Erro: não foi possível encontrar um produto com o id informado."),
            @ApiResponse(responseCode = "200", description = "Produto deletado com sucesso.")

    })
    @DeleteMapping("/produtos/{id}")
    public ResponseEntity<String> removerProduto(@PathVariable Long id) {
        produtoService.removerProduto(id);
        return ResponseEntity.ok("Produto deletado com sucesso.");
    }

    @Operation(summary = "Usuário 'admin' atualiza dados de um produto.", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Erro: login inválido ou login expirado."),
            @ApiResponse(responseCode = "403", description = "Erro: falha na autorização. Usuário sem as permissões necessárias."),
            @ApiResponse(responseCode = "422", description = "Erro: dados inválidos."),
            @ApiResponse(responseCode = "404", description = "Erro: não foi possível encontrar um produto com o id informado."),
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso.")
    })
    @PutMapping("/produtos/{id}")
    public ResponseEntity<UpdateProdutoResponseDTO> atualizarProduto(@PathVariable Long id, @Valid @RequestBody UpdateProdutoRequestDTO dtoRequest) {
        return ResponseEntity.ok(produtoService.atualizarProduto(id, dtoRequest));
    }

    @Operation(summary = "Retorna dados, para um usuário 'admin', de um produto.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Erro: login inválido ou login expirado."),
            @ApiResponse(responseCode = "403", description = "Erro: falha na autorização. Usuário sem as permissões necessárias."),
            @ApiResponse(responseCode = "404", description = "Erro: produto não encontrado com o id informado."),
            @ApiResponse(responseCode = "200", description = "Dados do produto recuperados com sucesso.")
    })
    @GetMapping("/produtos/{id}")
    public ResponseEntity<ReadProdutoResponseAdminDTO> adminLerProduto(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.adminLerProduto(id));
    }

    @Operation(summary = "Retorna dados, para um usuário 'admin', de todos os produtos.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Erro: login inválido ou login expirado."),
            @ApiResponse(responseCode = "403", description = "Erro: falha na autorização. Usuário sem as permissões necessárias."),
            @ApiResponse(responseCode = "200", description = "Dados dos produtos recuperados com sucesso.")
    })
    @GetMapping("/produtos")
    public ResponseEntity<Page<ReadProdutoResponseAdminDTO>> adminLerProdutos(Pageable pageable) {
        return ResponseEntity.ok(produtoService.adminLerProdutos(pageable));
    }

    @Operation(summary = "Usuário 'admin' adiciona uma categoria no catálogo.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Erro: login inválido ou login expirado."),
            @ApiResponse(responseCode = "403", description = "Erro: falha na autorização. Usuário sem as permissões necessárias."),
            @ApiResponse(responseCode = "422", description = "Erro: dados inválidos."),
            @ApiResponse(responseCode = "400", description = "Erro: falha ao adicionar uma categoria. Já existe uma categoria com o mesmo nome."),
            @ApiResponse(responseCode = "404", description = "Erro: falha ao adicionar um produto a essa categoria. Não foi possível encontrar um produto com o id informado."),
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso.")
    })
    @PostMapping("/categorias")
    public ResponseEntity<CreateCategoriaResponseDTO> adicionarCategoria(@Valid @RequestBody CreateCategoriaRequestDTO dtoRequest) {
        CreateCategoriaResponseDTO dtoResponse = categoriaService.adicionarCategoria(dtoRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{nome}").buildAndExpand(dtoResponse.getNome()).toUri();
        return ResponseEntity.created(uri).body(dtoResponse);
    }

    @Operation(summary = "Usuário 'admin' remove uma categoria do catálogo.", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Erro: login inválido ou login expirado."),
            @ApiResponse(responseCode = "403", description = "Erro: falha na autorização. Usuário sem as permissões necessárias."),
            @ApiResponse(responseCode = "404", description = "Erro: não foi possível encontrar uma categoria com o id informado."),
            @ApiResponse(responseCode = "200", description = "Categoria removida com sucesso.")
    })
    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<String> removerCategoria(@PathVariable Long id) {
        categoriaService.removerCategoria(id);
        return ResponseEntity.ok("Categoria removida com sucesso.");
    }

    @Operation(summary = "Usuário 'admin' atualiza uma categoria do catálogo.", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Erro: login inválido ou login expirado."),
            @ApiResponse(responseCode = "403", description = "Erro: falha na autorização. Usuário sem as permissões necessárias."),
            @ApiResponse(responseCode = "422", description = "Erro: dados inválidos."),
            @ApiResponse(responseCode = "404", description = "Erro: não foi possível encontrar uma categoria com o id informado."),
            @ApiResponse(responseCode = "400", description = "Erro: falha ao atualizar uma categoria. Já existe uma categoria com o mesmo nome."),
            @ApiResponse(responseCode = "404", description = "Erro: falha ao adicionar um produto a essa categoria. Não foi possível encontrar um produto com o id informado."),
            @ApiResponse(responseCode = "201", description = "Categoria atualizada com sucesso.")
    })
    @PutMapping("/categorias/{id}")
    public ResponseEntity<UpdateCategoriaResponseDTO> atualizarCategoria(@PathVariable Long id, @Valid @RequestBody UpdateCategoriaRequestDTO dtoRequest) {
        return ResponseEntity.ok(categoriaService.atualizarCategoria(id, dtoRequest));
    }

    @Operation(summary = "Retorna dados, para um usuário 'admin', de uma categoria e de seus produtos.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Erro: login inválido ou login expirado."),
            @ApiResponse(responseCode = "403", description = "Erro: falha na autorização. Usuário sem as permissões necessárias."),
            @ApiResponse(responseCode = "404", description = "Erro: categoria não encontrada com o id informado."),
            @ApiResponse(responseCode = "200", description = "Dados da categoria e de seus produtos, recuperados com sucesso.")
    })
    @GetMapping("/categorias/{id}")
    public ResponseEntity<ReadCategoriaResponseAdminDTO> adminLerCategoria(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.adminLerCategoria(id));
    }

    @Operation(summary = "Retorna dados, para um usuário 'admin', das categorias e de seus produtos.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Erro: login inválido ou login expirado."),
            @ApiResponse(responseCode = "403", description = "Erro: falha na autorização. Usuário sem as permissões necessárias."),
            @ApiResponse(responseCode = "200", description = "Dados das categorias e de seus produtos, recuperados com sucesso.")
    })
    @GetMapping("/categorias")
    public ResponseEntity<Page<ReadCategoriaResponseAdminDTO>> adminLerCategorias(Pageable pageable) {
        return ResponseEntity.ok(categoriaService.adminLerCategorias(pageable));
    }

    @Operation(summary = "Usuário 'admin' cancela um pedido.", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Erro: login inválido ou login expirado."),
            @ApiResponse(responseCode = "403", description = "Erro: falha na autorização. Usuário sem as permissões necessárias."),
            @ApiResponse(responseCode = "404", description = "Erro: não foi possível encontrar um pedido com o id informado."),
            @ApiResponse(responseCode = "200", description = "Pedido cancelado com sucesso.")
    })
    @DeleteMapping("/pedidos/{id}")
    public ResponseEntity<String> adminCancelarPedido(@PathVariable Long id) {
        pedidoService.adminCancelarPedido(id);
        return ResponseEntity.ok("Pedido cancelado com sucesso.");
    }

    @Operation(summary = "Usuário 'admin' atualiza um pedido.", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Erro: login inválido ou login expirado."),
            @ApiResponse(responseCode = "403", description = "Erro: falha na autorização. Usuário sem as permissões necessárias."),
            @ApiResponse(responseCode = "422", description = "Erro: dados inválidos."),
            @ApiResponse(responseCode = "404", description = "Erro: não foi possível encontrar um pedido com o id informado."),
            @ApiResponse(responseCode = "400", description = "Erro: falha ao atualizar forma de pagamento. Forma de pagamento inválida."),
            @ApiResponse(responseCode = "404", description = "Erro: não foi possível encontrar uma cidade com o id informado."),
            @ApiResponse(responseCode = "404", description = "Erro: não foi possível encontrar um estado com o id informado."),
            @ApiResponse(responseCode = "200", description = "Pedido atualizado com sucesso.")
    })
    @PutMapping("/pedidos/{id}")
    public ResponseEntity<UpdatePedidoResponseAdminDTO> adminAtualizarPedido(@PathVariable Long id, @Valid @RequestBody UpdatePedidoRequestDTO dtoRequest) {
        return ResponseEntity.ok(pedidoService.adminAtualizarPedido(id, dtoRequest));
    }

    @Operation(summary = "Retorna dados, para um usuário 'admin', de um pedido.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Erro: login inválido ou login expirado."),
            @ApiResponse(responseCode = "403", description = "Erro: falha na autorização. Usuário sem as permissões necessárias."),
            @ApiResponse(responseCode = "404", description = "Erro: pedido não encontrado com o id informado."),
            @ApiResponse(responseCode = "200", description = "Dados do pedido recuperados com sucesso.")
    })
    @GetMapping("/pedidos/{id}")
    public ResponseEntity<ReadPedidoResponseAdminDTO> adminLerPedido(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.adminLerPedido(id));
    }

    @Operation(summary = "Retorna dados, para um usuário 'admin', dos pedidos.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Erro: login inválido ou login expirado."),
            @ApiResponse(responseCode = "403", description = "Erro: falha na autorização. Usuário sem as permissões necessárias."),
            @ApiResponse(responseCode = "200", description = "Dados dos pedidos recuperados com sucesso.")
    })
    @GetMapping("/pedidos")
    public ResponseEntity<Page<ReadPedidoResponseAdminDTO>> adminLerPedidos(Pageable pageable) {
        return ResponseEntity.ok(pedidoService.adminLerPedidos(pageable));
    }

}

package com.vinicarnot.sistema_de_pedidos.controllers;

import com.vinicarnot.sistema_de_pedidos.dto.responses.LerCategoriaProdutoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.LerCategoriaRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.LerProdutoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.services.CategoriaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<Page<LerCategoriaRespostaDTO>> lerCategorias(@PageableDefault(page = 0, size = 12, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(categoriaService.lerCategorias(pageable));
    }

    @GetMapping("/{idCategoria}")
    public ResponseEntity<LerCategoriaRespostaDTO> lerCategoria(@PathVariable Long idCategoria) {
        return ResponseEntity.ok(categoriaService.lerCategoria(idCategoria));
    }

    @GetMapping("/{idCategoria}/produtos")
    public ResponseEntity<Page<LerProdutoRespostaDTO>> lerProdutosDeUmaCategoria(@PathVariable Long idCategoria, @PageableDefault(page = 0, size = 12, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(categoriaService.lerProdutosDeUmaCategoria(idCategoria, pageable));
    }
}

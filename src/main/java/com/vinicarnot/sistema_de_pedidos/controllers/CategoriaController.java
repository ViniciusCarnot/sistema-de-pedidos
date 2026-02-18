package com.vinicarnot.sistema_de_pedidos.controllers;

import com.vinicarnot.sistema_de_pedidos.dto.responses.ReadCategoriaResponseDTO;
import com.vinicarnot.sistema_de_pedidos.services.CategoriaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/{id}")
    public ResponseEntity<ReadCategoriaResponseDTO> lerCategoria(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.lerCategoria(id));
    }

    @GetMapping
    public ResponseEntity<Page<ReadCategoriaResponseDTO>> lerCategorias(Pageable pageable) {
        return ResponseEntity.ok().body(categoriaService.lerCategorias(pageable));
    }
}

package com.vinicarnot.sistema_de_pedidos.controllers;

import com.vinicarnot.sistema_de_pedidos.dto.CategoriaDTO;
import com.vinicarnot.sistema_de_pedidos.services.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> adicionarCategoria(@Valid @RequestBody CategoriaDTO dto) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{nome}").buildAndExpand(dto.getNome()).toUri();
        return ResponseEntity.created(uri).body(categoriaService.adicionarCategoria(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removerCategoria(@PathVariable Long id) {
        categoriaService.removerCategoria(id);
        return ResponseEntity.ok().body("Categoria removida com sucesso.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> lerCategoria(@PathVariable Long id) {
        return ResponseEntity.ok().body(categoriaService.lerCategoria(id));
    }

    @GetMapping
    public ResponseEntity<Page<CategoriaDTO>> lerCategorias(Pageable pageable) {
        return ResponseEntity.ok().body(categoriaService.lerCategorias(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> atualizarCategoria(@PathVariable Long id, @Valid @RequestBody CategoriaDTO dto) {
        return ResponseEntity.ok().body(categoriaService.atualizarCategoria(id, dto));
    }


}

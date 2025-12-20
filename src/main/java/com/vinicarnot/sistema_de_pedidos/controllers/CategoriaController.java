package com.vinicarnot.sistema_de_pedidos.controllers;

import com.vinicarnot.sistema_de_pedidos.dto.CategoriaDTO;
import com.vinicarnot.sistema_de_pedidos.services.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    @DeleteMapping("/{nome}")
    public ResponseEntity<String> removerCategoria(@PathVariable String nome) {
        categoriaService.removerCategoria(nome);
        return ResponseEntity.ok().body("Categoria deletada com sucesso.");
    }

}

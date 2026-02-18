package com.vinicarnot.sistema_de_pedidos.repositories;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Optional<Categoria> findById(Long id);

    Optional<Categoria> findByNomeIgnoreCase(String nome);

    @EntityGraph(attributePaths = {"produtos"})
    @Query("SELECT cat FROM Categoria cat")
    Page<Categoria> findAll(Pageable pageable);

}

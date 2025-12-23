package com.vinicarnot.sistema_de_pedidos.repositories;

import com.vinicarnot.sistema_de_pedidos.dto.CategoriaDTO;
import com.vinicarnot.sistema_de_pedidos.entities.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query("SELECT obj FROM Categoria obj " +
            "WHERE UPPER(obj.nome) LIKE UPPER(:nome)")
    Optional<Categoria> procurarPorNome(String nome);

    Optional<Categoria> findById(Long id);

    @Query("SELECT c FROM Categoria c")
    Page<Categoria> lerCategorias(Pageable pageable);

    @Query("SELECT c FROM Categoria c JOIN FETCH c.produtos WHERE c IN :categorias")
    List<Categoria> lerCategoriasComProdutos(@Param("categorias") List<Categoria> categorias);

}

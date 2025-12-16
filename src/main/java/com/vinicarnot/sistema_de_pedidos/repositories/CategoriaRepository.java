package com.vinicarnot.sistema_de_pedidos.repositories;

import com.vinicarnot.sistema_de_pedidos.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query("SELECT obj FROM Categoria obj " +
            "WHERE UPPER(obj.nome) LIKE UPPER(:nome)")
    Optional<Categoria> procurarPorNome(String nome);

}

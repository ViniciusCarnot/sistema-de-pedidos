package com.vinicarnot.sistema_de_pedidos.repositories;

import com.vinicarnot.sistema_de_pedidos.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("SELECT obj FROM Produto obj " +
            "WHERE UPPER(obj.nome) LIKE UPPER(:nome)")
    Optional<Produto> procurarPorNome(String nome);

}

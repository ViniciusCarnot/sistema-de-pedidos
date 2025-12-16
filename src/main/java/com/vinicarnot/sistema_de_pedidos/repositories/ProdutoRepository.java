package com.vinicarnot.sistema_de_pedidos.repositories;

import com.vinicarnot.sistema_de_pedidos.dto.ProdutoDTO;
import com.vinicarnot.sistema_de_pedidos.entities.Produto;
import com.vinicarnot.sistema_de_pedidos.projections.ProdutoProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("SELECT obj FROM Produto obj " +
            "WHERE UPPER(obj.nome) LIKE UPPER(:nome)")
    Optional<Produto> procurarPorNome(String nome);

    @Query("SELECT new com.vinicarnot.sistema_de_pedidos.dto.ProdutoDTO (obj.nome, obj.preco) FROM Produto obj " +
            "ORDER BY obj.nome")
    Page<ProdutoDTO> lerProdutos(Pageable pageable);

}

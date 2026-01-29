package com.vinicarnot.sistema_de_pedidos.repositories;

import com.vinicarnot.sistema_de_pedidos.dto.ProdutoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.ReadProdutoResponseDTO;
import com.vinicarnot.sistema_de_pedidos.entities.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findById(Long id);

    Optional<Produto> findByNome(String nome);

    @Query("SELECT obj FROM Produto obj " +
            "WHERE UPPER(obj.nome) LIKE UPPER(:nome)")
    Optional<Produto> procurarPorNome(String nome);

    @Query("SELECT new com.vinicarnot.sistema_de_pedidos.dto.responses.ReadProdutoResponseDTO(obj.nome, obj.preco) FROM Produto obj " +
            "ORDER BY obj.nome")
    Page<ReadProdutoResponseDTO> lerProdutos(Pageable pageable);


}

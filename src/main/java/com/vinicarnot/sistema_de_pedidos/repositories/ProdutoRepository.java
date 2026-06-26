package com.vinicarnot.sistema_de_pedidos.repositories;

import com.vinicarnot.sistema_de_pedidos.dto.responses.ReadProdutoResponseDTO;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, JpaSpecificationExecutor<Produto> {

    @Query(value = "SELECT obj FROM Produto obj " +
            "WHERE (obj.id = :id) AND (UPPER(obj.statusProduto) != 'INATIVO')")
    Optional<Produto> procurarPorIdComStatusAtivo(Long id);

    Optional<Produto> findByNomeIgnoreCase(String nome);

    @Query(value = "SELECT new com.vinicarnot.sistema_de_pedidos.dto.responses.ReadProdutoResponseDTO(obj.id, obj.nome, obj.preco) " +
            "FROM Produto obj " +
            "WHERE UPPER(obj.statusProduto) != 'INATIVO'")
    Page<ReadProdutoResponseDTO> lerProdutosComStatusAtivo(Pageable pageable);

}

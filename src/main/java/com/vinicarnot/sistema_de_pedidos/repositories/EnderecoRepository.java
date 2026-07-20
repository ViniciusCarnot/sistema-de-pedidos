package com.vinicarnot.sistema_de_pedidos.repositories;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Cidade;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Endereco;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Estado;
import com.vinicarnot.sistema_de_pedidos.projections.AdminLerEnderecoRespostaProjecao;
import com.vinicarnot.sistema_de_pedidos.projections.LerEnderecoRespostaProjecao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    Optional<Endereco> findByLogradouroAndNumeroAndBairroAndCidadeAndCidadeEstado(
            String logradouro, String numero, String bairro, Cidade cidade, Estado estado
    );

    @Query(nativeQuery = true, value = "SELECT e.id AS enderecoId, " +
            "e.logradouro AS logradouro, " +
            "e.numero AS numero, " +
            "e.bairro AS bairro, " +
            "c.id AS cidadeId, " +
            "c.nome AS cidadeNome, " +
            "est.id AS estadoId, " +
            "est.nome AS estadoNome " +
            "FROM tb_cliente_endereco AS cest " +
            "INNER JOIN tb_cliente AS cli ON cli.id = cest.cliente_id " +
            "INNER JOIN tb_endereco AS e ON e.id = cest.endereco_id " +
            "INNER JOIN tb_cidade AS c ON c.id = e.cidade_id " +
            "INNER JOIN tb_estado AS est ON est.id = c.estado_id " +
            "WHERE cli.email = :emailCliente"
    )
    List<AdminLerEnderecoRespostaProjecao> adminProcurarEnderecosPorCliente(String emailCliente);

    @Query(nativeQuery = true, value = "SELECT e.logradouro AS logradouro, " +
            "e.numero AS numero, " +
            "e.bairro AS bairro, " +
            "c.nome AS cidadeNome, " +
            "est.nome AS estadoNome " +
            "FROM tb_cliente_endereco AS cest " +
            "INNER JOIN tb_cliente AS cli ON cli.id = cest.cliente_id " +
            "INNER JOIN tb_endereco AS e ON e.id = cest.endereco_id " +
            "INNER JOIN tb_cidade AS c ON c.id = e.cidade_id " +
            "INNER JOIN tb_estado AS est ON est.id = c.estado_id " +
            "WHERE cli.email = :emailCliente"
    )
    List<LerEnderecoRespostaProjecao> procurarEnderecosPorCliente(String emailCliente);

}

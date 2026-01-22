package com.vinicarnot.sistema_de_pedidos.repositories;

import com.vinicarnot.sistema_de_pedidos.entities.Cidade;
import com.vinicarnot.sistema_de_pedidos.entities.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    Optional<Endereco> findByLogradouroAndNumeroAndBairroAndCidade(
            String logradouro, String numero, String bairro, Cidade cidade
    );

}

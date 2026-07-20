package com.vinicarnot.sistema_de_pedidos.repositories;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Cliente;
import com.vinicarnot.sistema_de_pedidos.projections.UserDetailsProjecao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmail(String email);

    Optional<Cliente> findById(Long id);

    @Query(nativeQuery = true, value =
		"SELECT " +
			"tb_cliente.email AS username, " +
			"tb_cliente.senha AS senha, " +
			"tb_role.id AS roleId, " +
			"tb_role.nome AS roleNome, " +
			"tb_cliente.ativo AS ativo " +
		"FROM tb_cliente " +
		"INNER JOIN tb_cliente_role ON tb_cliente.id = tb_cliente_role.cliente_id " +
		"INNER JOIN tb_role ON tb_role.id = tb_cliente_role.role_id " +
		"WHERE tb_cliente.email = :email "
	)
    List<UserDetailsProjecao> procurarUserDetailsProjectionPorEmail(String email);

	@EntityGraph(attributePaths = {"roles", "telefone"})
	@Query(value = "SELECT c FROM Cliente c")
	Page<Cliente> procurarTodosOsClientesERoles(Pageable pageable);

	@EntityGraph(attributePaths = {"roles", "telefone"})
	@Query(value = "SELECT c FROM Cliente c WHERE c.email = :email")
	Optional<Cliente> procurarClienteERolesPorEmail(String email);


	boolean existsByEmail(String email);
}

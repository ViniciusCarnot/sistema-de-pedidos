package com.vinicarnot.sistema_de_pedidos.repositories;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @EntityGraph(attributePaths = {"pagamento", "cliente.telefone", "enderecoDeEntrega.cidade.estado", "itemsPedidos"})
    Optional<Pedido> findById(Long idPedido);

    @Query("SELECT p FROM Pedido p WHERE p.id = :pedidoId")
    @EntityGraph(attributePaths = {"pagamento", "cliente", "enderecoDeEntrega.cidade.estado", "itemsPedidos"})
    Optional<Pedido> procurarPedidoEPagamentoEClienteEEnderecoECidadeEEstadoEItemsPedidoPorId(@Param("pedidoId") Long pedidoId);

    @Query("SELECT p FROM Pedido p WHERE p.cliente.email = :clienteEmail")
    @EntityGraph(attributePaths = {"pagamento", "cliente.telefone"})
    Page<Pedido> procurarPedidoEPagamentoEClientePorClienteEmail(@Param("clienteEmail") String clienteEmail, Pageable pageable);

    @EntityGraph(attributePaths = {"pagamento", "cliente", "enderecoDeEntrega.cidade.estado", "itemsPedidos"})
    Page<Pedido> findByClienteId(Long clienteId, Pageable pageable);

    @EntityGraph(attributePaths = {"pagamento", "cliente.telefone", "enderecoDeEntrega.cidade.estado", "itemsPedidos"})
    Page<Pedido> findByClienteEmail(String clienteEmail, Pageable pageable);

}

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

    Optional<Pedido> findById(Long idPedido);

    @EntityGraph(attributePaths = {"pagamento", "cliente", "enderecoDeEntrega.cidade.estado", "itemsPedidos"})
    @Query("SELECT p FROM Pedido p WHERE p.id = :pedidoId")
    Optional<Pedido> procurarPedidoEPagamentoEClienteEEnderecoECidadeEEstadoEItemsPedidoPorId(@Param("pedidoId") Long pedidoId);

    @EntityGraph(attributePaths = {"pagamento", "cliente", "enderecoDeEntrega.cidade.estado", "itemsPedidos"})
    Page<Pedido> findByClienteId(Long clienteId, Pageable pageable);

    @EntityGraph(attributePaths = {"pagamento", "cliente", "enderecoDeEntrega"})
    @Query("SELECT obj FROM Pedido obj")
    Page<Pedido> adminFindAll(Pageable pageable);

}

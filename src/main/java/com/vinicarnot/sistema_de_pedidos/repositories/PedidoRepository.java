package com.vinicarnot.sistema_de_pedidos.repositories;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("SELECT obj FROM Pedido obj " +
            "JOIN FETCH obj.pagamento " +
            "JOIN FETCH obj.enderecoDeEntrega " +
            "JOIN FETCH obj.cliente " +
            "JOIN FETCH obj.itemsPedidos " +
            "WHERE obj.id = :idPedido")
    Optional<Pedido> findById(Long idPedido);

    @EntityGraph(attributePaths = {"pagamento", "enderecoDeEntrega"})
    @Query("SELECT obj FROM Pedido obj WHERE obj.cliente.id = :clienteId")
    Page<Pedido> findByClienteId(Long clienteId, Pageable pageable);

    @EntityGraph(attributePaths = {"pagamento", "cliente", "enderecoDeEntrega"})
    @Query("SELECT obj FROM Pedido obj")
    Page<Pedido> adminFindAll(Pageable pageable);

}

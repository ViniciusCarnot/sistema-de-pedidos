package com.vinicarnot.sistema_de_pedidos.repositories;

import com.vinicarnot.sistema_de_pedidos.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}

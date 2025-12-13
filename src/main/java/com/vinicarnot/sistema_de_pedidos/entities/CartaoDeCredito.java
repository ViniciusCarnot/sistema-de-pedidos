package com.vinicarnot.sistema_de_pedidos.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "tb_cartao_de_credito")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class CartaoDeCredito extends Pagamento {

    public CartaoDeCredito(Long id, EstadoPagamento estadoAtual,
                           Pedido pedido,
                           Integer quantidadeParcelas)
    {
        super(id, estadoAtual, pedido);
        this.quantidadeParcelas = quantidadeParcelas;
    }

    private Integer quantidadeParcelas;

}

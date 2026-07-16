package com.vinicarnot.sistema_de_pedidos.domain.entites;

import com.vinicarnot.sistema_de_pedidos.domain.enums.EstadoPagamento;
import com.vinicarnot.sistema_de_pedidos.domain.enums.TipoPagamento;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_cartao_de_credito")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("CARTAO_DE_CREDITO")
public class CartaoDeCredito extends Pagamento {

    public CartaoDeCredito(Long id, EstadoPagamento estadoPagamento, TipoPagamento tipoPagamento, Pedido pedido, Integer quantidadeParcelas) {
        super(id, estadoPagamento, tipoPagamento, pedido);
        this.quantidadeParcelas = quantidadeParcelas;
    }

    private Integer quantidadeParcelas;

}

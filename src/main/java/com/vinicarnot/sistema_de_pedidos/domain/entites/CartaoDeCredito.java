package com.vinicarnot.sistema_de_pedidos.domain.entites;

import com.vinicarnot.sistema_de_pedidos.domain.enums.EstadoPagamento;
import com.vinicarnot.sistema_de_pedidos.domain.enums.TipoPagamento;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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

    @Column(nullable = false)
    private Integer quantidadeParcelas;

    private boolean salvarCartaoParaProximasCompras;

    @Column(columnDefinition = "DATE", nullable = false)
    private LocalDate dataVencimento;

}

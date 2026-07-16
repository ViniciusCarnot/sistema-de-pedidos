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
@Table(name = "tb_boleto")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("BOLETO")
public class Boleto extends Pagamento {

    public Boleto(Long id, EstadoPagamento estadoPagamento, TipoPagamento tipoPagamento, Pedido pedido, LocalDate dataVencimento, LocalDate dataPagamento) {
        super(id, estadoPagamento, tipoPagamento, pedido);
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
    }

    @Column(columnDefinition = "DATE")
    private LocalDate dataVencimento;

    @Column(columnDefinition = "DATE")
    private LocalDate dataPagamento;

}

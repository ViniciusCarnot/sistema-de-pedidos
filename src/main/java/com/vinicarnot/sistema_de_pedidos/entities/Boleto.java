package com.vinicarnot.sistema_de_pedidos.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tb_boleto")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Boleto extends Pagamento {

    public Boleto(Long id, EstadoPagamento estadoAtual,
                  Pedido pedido, LocalDate dataVencimento,
                  LocalDate dataPagamento)
    {
        super(id, estadoAtual, pedido);
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
    }

    @Column(columnDefinition = "DATE")
    private LocalDate dataVencimento;

    @Column(columnDefinition = "DATE")
    private LocalDate dataPagamento;

}

package com.vinicarnot.sistema_de_pedidos.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_boleto")
@PrimaryKeyJoinColumn(name = "pagamento_id")
public class Boleto extends Pagamento {


    @Column(columnDefinition = "DATE")
    private LocalDate dataVencimento;

    @Column(columnDefinition = "DATE")
    private LocalDate dataPagamento;

}

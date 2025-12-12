package com.vinicarnot.sistema_de_pedidos.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_cartao_de_credito")
@PrimaryKeyJoinColumn(name = "pagamento_id")
public class CartaoDeCredito {

    private Integer quantidadeParcelas;

}

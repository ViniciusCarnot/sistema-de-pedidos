package com.vinicarnot.sistema_de_pedidos.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_item_pedido")
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = {"id"})
public class ItemPedido {

    @EmbeddedId
    @Setter
    private ItemPedidoPK id = new ItemPedidoPK();

    @Setter
    private Integer quantidade;

    @Column(precision = 12, scale = 2)
    @Setter
    private BigDecimal preco;

    @Setter
    private BigDecimal desconto;

    public ItemPedido(Produto produto, Pedido pedido, Integer quantidade, BigDecimal preco, BigDecimal desconto) {
        id.setProduto(produto);
        id.setPedido(pedido);
        this.quantidade = quantidade;
        this.preco = preco;
        this.desconto = desconto;
    }

    public Produto getProduto() {
        return id.getProduto();
    }

    public void setProduto(Produto produto) {
        id.setProduto(produto);
    }

    public Pedido getPedido() {
        return id.getPedido();
    }

    public void setPedido(Pedido pedido) {
        id.setPedido(pedido);
    }

}

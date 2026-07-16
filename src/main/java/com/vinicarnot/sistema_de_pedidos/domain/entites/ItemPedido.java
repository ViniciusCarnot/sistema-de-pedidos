package com.vinicarnot.sistema_de_pedidos.domain.entites;

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
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class ItemPedido {

    @EmbeddedId
    @Setter
    private ItemPedidoPK id = new ItemPedidoPK();

    @Setter
    private String nomeProduto;

    @Setter
    private Integer quantidade;

    @Column(precision = 12, scale = 2)
    @Setter
    private BigDecimal precoUnitario;

    @Setter
    private BigDecimal descontoUnitario;

    public ItemPedido(Produto produto, Pedido pedido, Integer quantidade, BigDecimal descontoUnitario) {
        id.setProduto(produto);
        id.setPedido(pedido);
        nomeProduto = produto.getNome();
        this.quantidade = quantidade;
        precoUnitario = produto.getPreco();
        this.descontoUnitario = descontoUnitario;
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
